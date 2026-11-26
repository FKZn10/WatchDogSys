package com.jpmsoft.projectProducer.service.construction.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.requestAndResponse.Construction.ConDelayModel;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.construction.ConDelayMapper;
import com.jpmsoft.projectProducer.pojo.entity.construction.ConDelay;
import com.jpmsoft.projectProducer.service.construction.IConDelayService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ConDelayImpl extends FileImpl<ConDelayMapper, ConDelay, ConDelayModel> implements IConDelayService {

    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateConDelay(ConDelayModel request) {
        ConDelay entity = new ConDelay();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getDeId());
    }

    @Override
    public BaseResponse getConDelayList(ConDelayModel request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Delay", uId, "De_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "De_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ConDelayModel> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pId").put("deTitle").put("deRemark").put("deState")
                .put("deCreateUname").put("deReason").put("pName").put("pNameHistory");
        IPage<ConDelayModel> page = new Page<>(request.getPage(), request.getSize());
        List<ConDelayModel> datas = baseMapper.getConDelayList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConDelayById(String deId) {
        return BaseResponse.ok(baseMapper.getConDelayById(deId));
    }
}

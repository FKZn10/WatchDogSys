package com.jpmsoft.projectProducer.service.construction.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.requestAndResponse.Construction.ConCompleteModel;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.construction.ConCompleteMapper;
import com.jpmsoft.projectProducer.pojo.entity.construction.ConComplete;
import com.jpmsoft.projectProducer.service.construction.IConCompleteService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ConCompleteImpl extends FileImpl<ConCompleteMapper, ConComplete, ConCompleteModel> implements IConCompleteService {

    @Resource
    private ISysUserService sysUserService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateConComplete(ConCompleteModel request) {
        ConComplete entity = new ConComplete();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getCoId());
    }

    @Override
    public BaseResponse getConCompleteList(ConCompleteModel request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Complete", uId, "Co_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Co_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ConCompleteModel> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pId").put("pName").put("pNameHistory")
                .put("coTitle").put("coRemark").put("coState").put("coCreateUname");
        IPage<ConCompleteModel> page = new Page<>(request.getPage(), request.getSize());
        List<ConCompleteModel> datas = baseMapper.getConCompleteList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConCompleteById(String coId) {
        return BaseResponse.ok(baseMapper.getConCompleteById(coId));
    }
}

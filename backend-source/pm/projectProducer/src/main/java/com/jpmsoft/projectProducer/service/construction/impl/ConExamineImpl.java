package com.jpmsoft.projectProducer.service.construction.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.requestAndResponse.Construction.ConExamineModel;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.construction.ConExamineMapper;
import com.jpmsoft.projectProducer.pojo.entity.construction.ConExamine;
import com.jpmsoft.projectProducer.service.construction.IConExamineService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ConExamineImpl extends FileImpl<ConExamineMapper, ConExamine, ConExamineModel> implements IConExamineService {

    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateConExamine(ConExamineModel request) {
        ConExamine entity = new ConExamine();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getExId());
    }

    @Override
    public BaseResponse getConExamineList(ConExamineModel request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Examine", uId, "Ex_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Ex_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ConExamineModel> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pId").put("pName").put("pNameHistory").put("exSupervisorName")
                .put("exSupervisorNameHistory").put("exContractorName").put("exContractorNameHistory").put("exRemark")
                .put("exTitle").put("exState").put("exCreateUname");
        IPage<ConExamineModel> page = new Page<>(request.getPage(), request.getSize());
        List<ConExamineModel> datas = baseMapper.getConExamineList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConExamineById(String exId) {
        return BaseResponse.ok(baseMapper.getConExamineById(exId));
    }
}

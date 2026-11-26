package com.jpmsoft.projectProducer.service.construction.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.DesignChange.ConDcBudgetRequest;
import com.jpmsoft.common.model.response.DesignChange.ConDcBudgetResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.construction.ConDcBudgetMapper;
import com.jpmsoft.projectProducer.pojo.entity.construction.ConDcBudget;
import com.jpmsoft.projectProducer.service.construction.IConDcBudgetService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ConDcBudgetImpl extends FileImpl<ConDcBudgetMapper, ConDcBudget, ConDcBudgetRequest> implements IConDcBudgetService {


    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateConDcBudget(ConDcBudgetRequest request) {
        ConDcBudget entity = new ConDcBudget();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getDcbId());
    }

    @Override
    public BaseResponse getConDcBudgetList(ConDcBudgetRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Dc_Budget", uId, "Dcb_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Dcb_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ConDcBudgetRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("pId").put("conName").put("dcbType")
                .put("dcoName").put("dcoCode").put("dcoProjectName").put("dcoSubCode");
        IPage<ConDcBudgetResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConDcBudgetResponse> datas = baseMapper.getConDcBudgetList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConDcBudgetByDcbId(String dcbId) {
        ConDcBudgetResponse data = baseMapper.getConDcBudgetByDcbId(dcbId);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getNotContainAmtByConId(String conId) {
        return BaseResponse.ok(baseMapper.getNotContainAmtByConId(conId));
    }
}

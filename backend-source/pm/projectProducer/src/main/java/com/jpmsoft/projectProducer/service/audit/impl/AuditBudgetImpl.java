package com.jpmsoft.projectProducer.service.audit.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.requestAndResponse.Audit.AuditBudgetModel;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.audit.AuditBudgetMapper;
import com.jpmsoft.projectProducer.pojo.entity.audit.AuditBudget;
import com.jpmsoft.projectProducer.service.audit.IAuditBudgetService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AuditBudgetImpl extends FileImpl<AuditBudgetMapper, AuditBudget, AuditBudgetModel> implements IAuditBudgetService {

    @Resource
    private ISysUserService sysUserService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateAuditBudget(AuditBudgetModel request) {
        AuditBudget entity = new AuditBudget();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getAbId());
    }

    @Override
    public BaseResponse getAuditBudgetList(AuditBudgetModel request, String uId) {
        String authSql = sysUserService.getAuthSql("Audit_Budget", uId, "Ab_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "Ab_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<AuditBudgetModel> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("secId").put("secName").put("pName").put("pId")
                .put("cName").put("abCreateCid").put("abContactName").put("abContactTel").put("abState");
        IPage<AuditBudgetModel> page = new Page<>(request.getPage(), request.getSize());
        List<AuditBudgetModel> datas = baseMapper.getAuditBudgetList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getAuditBudgetById(String abId) {
        AuditBudgetModel data = baseMapper.getAuditBudgetById(abId);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getAuditBudgetBySecId(String secId) {
        List<AuditBudgetModel> data = baseMapper.getAuditBudgetBySecId(secId);
        return BaseResponse.ok(data);
    }
}

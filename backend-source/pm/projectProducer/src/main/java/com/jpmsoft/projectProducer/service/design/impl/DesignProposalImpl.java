package com.jpmsoft.projectProducer.service.design.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignProposalModel;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.design.DesignProposalMapper;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignProposal;
import com.jpmsoft.projectProducer.service.design.IDesignProposalService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DesignProposalImpl extends FileImpl<DesignProposalMapper, DesignProposal, DesignProposalModel> implements IDesignProposalService {

    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "dpId", codeParam = "dpCode", method = "setDpCode", codeRule = "DesignProposal", beanClass = DesignProposalModel.class)
    public BaseResponse createOrUpdateDesignProposal(DesignProposalModel request) {
        DesignProposal entity = new DesignProposal();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getDpId());
    }

    @Override
    public BaseResponse getDesignProposalList(DesignProposalModel request, String uId) {
        String authSql = sysUserService.getAuthSql("Design_Proposal", uId, "Dp_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "Dp_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<DesignProposalModel> params = getQueryMap(request, authSql);
        IPage<DesignProposalModel> page = new Page<>(request.getPage(), request.getSize());
        List<DesignProposalModel> datas = baseMapper.getDesignProposalList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getDesignProposalListNoAuth(DesignProposalModel request) {
        QueryMap<DesignProposalModel> params = getQueryMap(request, null);
        IPage<DesignProposalModel> page = new Page<>(request.getPage(), request.getSize());
        List<DesignProposalModel> datas = baseMapper.getDesignProposalList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    private QueryMap<DesignProposalModel> getQueryMap(DesignProposalModel request, String authSql) {
        QueryMap<DesignProposalModel> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pId").put("pName").put("pNameHistory")
                .put("dpFeasibilityUnitName").put("dpEstimationUnitName").put("dpName")
                .put("dpState").put("dpCreateUname").put("conName").put("conCode")
                .put("dpFeasibilityUnitTel").put("dpCode");
        return params;
    }

    @Override
    public BaseResponse getDesignProposalById(String dpId) {
        return BaseResponse.ok(baseMapper.getDesignProposalById(dpId));
    }
}

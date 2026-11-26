package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecProvisionalEstimateModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecProvisionalEstimateMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecProvisionalEstimate;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.secInfo.ISecProvisionalEstimateService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SecProvisionalEstimateImpl extends ServiceImpl<SecProvisionalEstimateMapper, SecProvisionalEstimate> implements ISecProvisionalEstimateService {

    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecProvisionalEstimate(SecProvisionalEstimateModel request) {
        SecProvisionalEstimate entity = new SecProvisionalEstimate();
        BeanUtils.copyProperties(request, entity);
        QueryWrapper<SecProvisionalEstimate> wrapper = new QueryWrapper<>();
        wrapper.eq("Pe_Code", request.getPeCode());
        if (!ValidateUtil.isEmpty(entity.getPeId()))
            wrapper.ne("Pe_Id", request.getPeId());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(ResMsg.codeIsRepeat);
        if (ValidateUtil.isEmpty(request.getPeId())) {
            entity.setPeId(GenerateUUID.getUUID());
            entity.setPeCreateTime(new Date());
            entity.setState(ValidateUtil.isNull(request.getState()) ? ApprovalStatusEnum.Not_Started.getIndex() : request.getState());
            entity.setStateTime(new Date());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getPeId(), request.gettId(), entity.getState() + "", entity.getPeCreateUid(), entity.getPeCreateUname()
                    , entity.getPeCreateTime(), null, request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getPeId());

        } else {
            SecProvisionalEstimate model = baseMapper.selectById(request.getPeId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setPeCreateTime(model.getPeCreateTime());
            entity.setPeCreateUid(model.getPeCreateUid());
            entity.setPeCreateUname(model.getPeCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getPeId(), request.gettId(), entity.getState() + "", entity.getPeCreateUid(), entity.getPeCreateUname()
                    , entity.getPeCreateTime(), null, request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getPeId());

        }
        return BaseResponse.ok(entity.getPeId());
    }

    @Override
    public BaseResponse getSecProvisionalEstimateModelList(SecProvisionalEstimateModel request, String uId) {
        String authSql = sysUserService.getAuthSql("Sec_Provisional_Estimate", uId, "Pe_Create_Uid", OutUserAuthEnum.Organization.getIndex());
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<SecProvisionalEstimateModel> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("pCode").put("conName").put("conCode")
                .put("peName").put("peCode").put("peCreateUname").put("peType").put("peProType")
                .put("peOverallName").put("peProfessionalName").put("peMode").put("pePricingMode")
                .put("peApprovalCompanyName");
        List<SecProvisionalEstimateModel> datas = baseMapper.getSecProvisionalEstimateModelList(params.getQuery());
        Integer total = baseMapper.getSecProvisionalEstimateModelListCount(params.getQuery());
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getSecProvisionalEstimateByPeId(String peId) {
        return BaseResponse.ok(baseMapper.getSecProvisionalEstimateByPeId(peId));
    }
}

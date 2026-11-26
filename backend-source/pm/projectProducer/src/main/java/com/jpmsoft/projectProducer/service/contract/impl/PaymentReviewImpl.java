package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.Contract.PaymentReviewRequest;
import com.jpmsoft.common.model.response.Contract.PaymentReviewResponse;
import com.jpmsoft.common.model.response.SecInfo.SecReviewMaterialResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.contract.PaymentReviewMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.PaymentReview;
import com.jpmsoft.projectProducer.service.contract.IPaymentReviewService;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.secInfo.ISecReviewMaterialService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class PaymentReviewImpl extends ServiceImpl<PaymentReviewMapper, PaymentReview> implements IPaymentReviewService {

    @Resource
    private ISysFileService sysFileService;

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private IApiUtilsService apiUtilsService;


    @Resource
    private ISecReviewMaterialService secReviewMaterialService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdatePaymentReview(PaymentReviewRequest request) {
        // region 编码规则 合同支付 与项目支付走不同的编码规则
        String codeRule = "PaymentReview";
        PaymentReview entity = new PaymentReview();
        BeanUtils.copyProperties(request, entity);
        entity.setPId(request.getProjectId());
        String createOrUpdate = "create";
        if (ValidateUtil.isEmpty(request.getId())) {
            entity.setCreateTime(new Date());
            entity.setId(GenerateUUID.getUUID());
            entity.setState(ApprovalStatusEnum.Not_Started.getIndex());
            // 编码生成
            if (ValidateUtil.isNotEmpty(codeRule) && apiUtilsService.sequenceRestEnable(codeRule)) {
                request.setPrCode(apiUtilsService.generateCodeByCustomized(codeRule, request.getPId(), request.getConId(), request.getSecId(), request));
                if (ValidateUtil.isEmpty(request.getPrCode())) throw new JPMException(ResMsg.codeGenerError);
            } else codeRule = null;
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getId(), request.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname()
                    , entity.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), null, request.gettCode(), request.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId()), entity.getId());
        } else {
            createOrUpdate = "update";
            PaymentReview entity2 = baseMapper.selectById(request.getId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(entity.getState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getId(), request.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname()
                    , entity.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId()),
                    Arrays.asList(request.getFileIds()), entity.getId());
        }
        // region 刷新编码序号索引
        if (ValidateUtil.isNotEmpty(codeRule))
            apiUtilsService.refershAutoNoByCustomized(codeRule, request.getPId(), request.getConId(), request.getSecId(), request);
        secReviewMaterialService.createOrUpdateReviewMaterial(request.getReviewMaterialList(), createOrUpdate, "PaymentReview", entity.getId());
        return BaseResponse.ok(entity.getId());


    }


    @Override
    public BaseResponse getPaymentReviewList(PaymentReviewRequest request) {
        QueryMap<PaymentReviewRequest> params = new QueryMap<>(request);
        params.put("pName").put("prCode").put("conName").put("applyUnit").put("state").put("applyContent").put("createUname");
        IPage<PaymentReviewRequest> page = new Page<>(request.getPage(), request.getSize());
        List<PaymentReviewResponse> datas = baseMapper.getPaymentReviewList(params.getQuery(), page);
        return BaseResponse.ok(datas, (int) page.getTotal());
    }


    @Override
    public BaseResponse getPaymentReviewById(String id) {
        PaymentReviewResponse entity = baseMapper.getPaymentReviewById(id);
        List<SecReviewMaterialResponse> reviewMaterialList = secReviewMaterialService.getReviewMaterialList("PaymentReview", id);
        entity.setReviewMaterialList(reviewMaterialList);
        return BaseResponse.ok(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deletePaymentReviewById(String id) {
        PaymentReview entity = baseMapper.selectById(id);
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        baseMapper.deleteById(id);
        sysFileService.deleteFileById(id);
        secReviewMaterialService.deleteByBusinessId("PaymentReview", id);
        return BaseResponse.ok();
    }
}

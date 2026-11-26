package com.jpmsoft.projectProducer.service.secInfo.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.SecInfo.SecContractSignPleaseReviewRequest;
import com.jpmsoft.common.model.response.SecInfo.SecContractSignPleaseReviewResponse;
import com.jpmsoft.common.model.response.SecInfo.SecReviewMaterialResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecContractSignPleaseReviewMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecContractSignPleaseReview;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.secInfo.ISecContractSignPleaseReviewService;
import com.jpmsoft.projectProducer.service.secInfo.ISecReviewMaterialService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 合同签订请审
 *
 */
@Service
public class SecContractSignPleaseReviewServiceImpl extends ServiceImpl<SecContractSignPleaseReviewMapper, SecContractSignPleaseReview> implements ISecContractSignPleaseReviewService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISecReviewMaterialService secReviewMaterialService;

    @Override
    public BaseResponse createOrUpdateContractSignPleaseReview(SecContractSignPleaseReviewRequest request) {
        SecContractSignPleaseReview sec = new SecContractSignPleaseReview();
        BeanUtils.copyProperties(request, sec);
        if (ValidateUtil.isEmpty(request.getSecId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        String createOrUpdate = "create";
        if (ValidateUtil.isEmpty(request.getCsprId())) {
            sec.setCreateTime(new Date());
            sec.setCsprId(GenerateUUID.getUUID());
            sec.setState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(sec);
            sysFileService.saveFile(sec.getCsprId(), request.gettId(), sec.getState() + "", sec.getCreateUid(), sec.getCreateUname()
                    , sec.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), request.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId()), sec.getCsprId());
        }else{
            createOrUpdate="update";
            SecContractSignPleaseReview entity = baseMapper.selectById(request.getCsprId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(entity.getState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            sec.setCreateTime(entity.getCreateTime());
            sec.setCreateUid(entity.getCreateUid());
            sec.setCreateUname(entity.getCreateUname());
            sec.setSecId(entity.getSecId());
            baseMapper.updateById(sec);
            sysFileService.saveFile(sec.getCsprId(), request.gettId(), sec.getState() + "", sec.getCreateUid(), sec.getCreateUname()
                    , sec.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId()),
                    Arrays.asList(request.getFileIds()), sec.getCsprId());
        }

        secReviewMaterialService.createOrUpdateReviewMaterial(request.getReviewMaterialList(), createOrUpdate,"SecContractSignPleaseReview" ,ValidateUtil.isEmpty(request.getCsprId()) ?  sec.getCsprId() : request.getCsprId() );
        return BaseResponse.ok(sec.getCsprId());
    }

    @Override
    public BaseResponse deleteContractSignPleaseReviewById(String csprId) {
        SecContractSignPleaseReview entity = baseMapper.selectById(csprId);
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        baseMapper.deleteById(csprId);
        sysFileService.deleteFileById(csprId);
        secReviewMaterialService.deleteByBusinessId("SecContractSignPleaseReview",csprId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getContractSignPleaseReviewById(String csprId) {
        SecContractSignPleaseReviewResponse response = baseMapper.getContractSignPleaseReviewById(csprId);
        List<SecReviewMaterialResponse> reviewMaterialList = secReviewMaterialService.getReviewMaterialList("SecContractSignPleaseReview", csprId);
        response.setSecReviewMaterialList(reviewMaterialList);
        return BaseResponse.ok(response);
    }

    @Override
    public BaseResponse getContractSignPleaseReviewList(SecContractSignPleaseReviewRequest request) {
        QueryMap<SecContractSignPleaseReviewRequest> params = new QueryMap<>(request);
        params.put("cpprReviewMatter").put("cpprApprovalUnit").put("state").put("cpprApprovalContent").put("createUname");
        IPage<SecContractSignPleaseReviewRequest> page=new Page<>(request.getPage(), request.getSize());
        List<SecContractSignPleaseReviewResponse> datas = baseMapper.getContractSignPleaseReviewList(params.getQuery(),page);
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

}

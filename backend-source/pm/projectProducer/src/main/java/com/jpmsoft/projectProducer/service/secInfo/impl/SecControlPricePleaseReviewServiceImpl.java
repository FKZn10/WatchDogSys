package com.jpmsoft.projectProducer.service.secInfo.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.SecInfo.SecControlPricePleaseReviewRequest;
import com.jpmsoft.common.model.response.SecInfo.SecControlPricePleaseReviewResponse;
import com.jpmsoft.common.model.response.SecInfo.SecControlPriceResponse;
import com.jpmsoft.common.model.response.SecInfo.SecReviewMaterialResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecControlPricePleaseReviewMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecControlPricePleaseReview;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.secInfo.ISecControlPricePleaseReviewService;
import com.jpmsoft.projectProducer.service.secInfo.ISecReviewMaterialService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * 控制价请审
 *
 */
@Service
public class SecControlPricePleaseReviewServiceImpl extends ServiceImpl<SecControlPricePleaseReviewMapper, SecControlPricePleaseReview> implements ISecControlPricePleaseReviewService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private SecControlPricePleaseReviewMapper secControlPricePleaseReviewMapper;
    @Resource
    private ISecReviewMaterialService secReviewMaterialService;

    @Override
    public BaseResponse createOrUpdateControlPriceReview(SecControlPricePleaseReviewRequest request) {
        SecControlPricePleaseReview sec = new SecControlPricePleaseReview();
        BeanUtils.copyProperties(request, sec);
        if (ValidateUtil.isEmpty(request.getSecId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        String createOrUpdate = "create";
        if (ValidateUtil.isEmpty(request.getCpprId())) {
            sec.setCreateTime(new Date());
            sec.setCpprId(GenerateUUID.getUUID());
            sec.setState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(sec);
            sysFileService.saveFile(sec.getCpprId(), request.gettId(), sec.getState() + "", sec.getCreateUid(), sec.getCreateUname()
                    , sec.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), request.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId()), sec.getCpprId());
        }else{
            createOrUpdate="update";
            SecControlPricePleaseReview entity = baseMapper.selectById(request.getCpprId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(entity.getState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            sec.setCreateTime(entity.getCreateTime());
            sec.setCreateUid(entity.getCreateUid());
            sec.setCreateUname(entity.getCreateUname());
            sec.setSecId(entity.getSecId());
            baseMapper.updateById(sec);
            sysFileService.saveFile(sec.getCpprId(), request.gettId(), sec.getState() + "", sec.getCreateUid(), sec.getCreateUname()
                    , sec.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), request.getConId());
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId()),
                    Arrays.asList(request.getFileIds()), sec.getCpprId());
        }
        secReviewMaterialService.createOrUpdateReviewMaterial(request.getReviewMaterialList(), createOrUpdate,"SecControlPricePleaseReview" ,sec.getCpprId());
        return BaseResponse.ok(sec.getCpprId());
    }

    @Override
    public BaseResponse deleteControlPriceReviewById(String cpprId) {
        SecControlPricePleaseReview entity = secControlPricePleaseReviewMapper.selectById(cpprId);
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        baseMapper.deleteById(cpprId);
        sysFileService.deleteFileById(cpprId);
        secReviewMaterialService.deleteByBusinessId("SecControlPricePleaseReview",cpprId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getControlPriceReviewById(String cpprId) {
        SecControlPricePleaseReviewResponse response = secControlPricePleaseReviewMapper.getControlPriceReviewById(cpprId);
        List<SecReviewMaterialResponse> reviewMaterialList = secReviewMaterialService.getReviewMaterialList("SecControlPricePleaseReview", cpprId);
        response.setSecReviewMaterialList(reviewMaterialList);
        return BaseResponse.ok(response);
    }

    @Override
    public BaseResponse getControlPriceReviewList(SecControlPricePleaseReviewRequest request) {
        QueryMap<SecControlPricePleaseReviewRequest> params = new QueryMap<>(request);
        params.put("cpprReviewMatter").put("cpprApprovalUnit").put("state").put("cpprApprovalContent").put("createUname");
        IPage<SecControlPricePleaseReviewRequest> page=new Page<>(request.getPage(), request.getSize());
        List<SecControlPricePleaseReviewResponse> datas = baseMapper.getControlPriceReviewList(params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public SecControlPriceResponse getControlPriceReviewBysecId(String secId) {
        return null;
    }


}

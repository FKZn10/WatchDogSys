package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSelectionRecordMultipleModel;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.ExtractExpertMultipleDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecSelectionRecordMultiple;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

public interface ISecSelectionRecordMultipleService extends IService<SecSelectionRecordMultiple> {


    BaseResponse createOrUpdateSecSelectionRecordMultiple(SecSelectionRecordMultipleModel request, LogUserInfoVo user);


    BaseResponse getSecSelectionRecordMultipleList(SecSelectionRecordMultipleModel request, String uid);


    BaseResponse getSecSelectionRecordMultipleById(String id);


    BaseResponse deleteSecSelectionRecordMultipleById(String id);


    BaseResponse createOrUpdateExpertExtractMultiple(SecSelectionRecordMultipleModel request, LogUserInfoVo user);


    BaseResponse getSecExpertExtractMultipleById(String id);


    /**
     * 专家抽取
     * @param request
     * @param teId
     * @return
     */
    BaseResponse extractJGExpertMultiple(ExtractExpertMultipleDto request, String teId);


    BaseResponse approvalData(SecSelectionRecordMultipleModel request);

    /**
     * 通过标段获取开标时间等信息
     * @param secId
     * @return
     */
    BaseResponse getOtherExpertSecInfo(String secId);

}

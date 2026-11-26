package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecBiddingPlanChangeRequest;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBiddingPlanChange;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

public interface ISecBiddingPlanChangeService extends IService<SecBiddingPlanChange> {

    BaseResponse createOrUpdateSecBiddingPlanChange(SecBiddingPlanChangeRequest request, LogUserInfoVo user);

    BaseResponse getSecBiddingPlanChangeList(SecBiddingPlanChangeRequest request, String uId);


    BaseResponse getSecBiddingPlanChangeById(String bpcId);


    BaseResponse approval2UpdateSecInfo(SecBiddingPlanChangeRequest request, String uId);

    BaseResponse getSecInfoChangeListByBpId(String bpId);

    /**
     * 获取指定标段是否有流程中的变更
     *
     * @param secId
     * @return
     */
    BaseResponse haveChangeSecInfoBySecId(String secId);

    /**
     * 更新指定计划的项目分管领导
     *
     * @param bpcId
     * @param responsibleLeader
     * @return
     */
    BaseResponse updateSecBiddingPlanChangeResponsibleLeader(String bpcId, String responsibleLeader);
}

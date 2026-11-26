package com.jpmsoft.projectProducer.service.peInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeInfoRankingRequest;
import com.jpmsoft.common.model.requestAndResponse.PeInfo.PeInfoModel;
import com.jpmsoft.projectProducer.pojo.dto.peinfo.PeInfoUnMeetDto;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeInfo;

import java.util.List;

public interface IPeInfoService extends IService<PeInfo> {

    BaseResponse createOrUpdatePeInfo(PeInfoModel model);

    BaseResponse getPeInfoList(PeInfoModel model, String uId);

    BaseResponse getPeInfoListNoAuth(PeInfoModel model);

    BaseResponse getPeInfoRanking(PeInfoRankingRequest request);

    BaseResponse getPeInfoByPeId(String peId);

    BaseResponse deletePeInfoByPeId(String peId);

    BaseResponse updateFlowState(PeInfoModel model);


    /**
     * 上会确认时修改
     *
     * @param model
     * @return
     */
    BaseResponse updatePeInfoWhenMeetingConfirm(PeInfoModel model);


    List<PeInfoModel> getPeInfoByMaId(String maId);

    /**
     * 获取未上会呈批的考核评分
     *
     * @param model
     * @param uId
     * @return
     */
    BaseResponse getPeInfoUnMeetApproval(PeInfoUnMeetDto model, String uId);


    BaseResponse getPeInfoStatistics(String yearStr);
}

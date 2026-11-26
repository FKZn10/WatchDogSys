package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.FullProcessConsultServiceProtocolMeterageCreateOrUpdateRequest;
import com.jpmsoft.common.model.request.Contract.FullProcessConsultServiceProtocolMeteragePageRequest;
import com.jpmsoft.projectProducer.pojo.entity.contract.FullProcessConsultServiceProtocolMeterageApply;
import com.jpmsoft.projectProducer.pojo.vo.contract.FullProcessConsultServiceProtocolMeterageApplyVO;
import com.jpmsoft.projectProducer.pojo.vo.contract.FullProcessConsultServiceProtocolMeterageDetailVo;
import com.jpmsoft.projectProducer.pojo.vo.contract.FullProcessConsultServiceProtocolVo;

import java.util.List;

public interface IFullProcessConsultServiceProtocolMeterageApplyService extends IService<FullProcessConsultServiceProtocolMeterageApply> {
    FullProcessConsultServiceProtocolVo getServiceProtocolContentItemByProjectId(String projectId);

    FullProcessConsultServiceProtocolMeterageDetailVo getDetailById(String id);

    FullProcessConsultServiceProtocolMeterageApply createOrUpdate(FullProcessConsultServiceProtocolMeterageCreateOrUpdateRequest fullProcessConsultServiceProtocolMeterageCreateOrUpdateRequest);

    void deleteById(String id);

    BaseResponse pageFullProcessConsultServiceProtocolMeterageApply(FullProcessConsultServiceProtocolMeteragePageRequest request, String token);

    List<FullProcessConsultServiceProtocolMeterageApplyVO> listFullProcessConsultServiceProtocolMeterageApply(FullProcessConsultServiceProtocolMeteragePageRequest request, String token);

    BaseResponse getTZClassInfo(List<String> projectIdList, String token) throws ClassNotFoundException, IllegalAccessException;

    BaseResponse getLastDeadline(String pId, String conId);
}

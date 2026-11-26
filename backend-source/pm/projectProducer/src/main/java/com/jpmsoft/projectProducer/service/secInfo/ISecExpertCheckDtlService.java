package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertCheckDtlModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSelectionRecordDtlModel;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertCheckDtlDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertCheckDtl;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/2 13:32
 */
public interface ISecExpertCheckDtlService extends IService<SecExpertCheckDtl> {


    void createOrUpdateSecExpertCheckDtl(List<SecExpertCheckDtlDTO> data, String ecId);

    void deleteByEcId(String ecId);

    void updateResultByIds(List<String> ids,Integer result);

    BaseResponse getExpertCheckDtlList(String secId, String uId);

    BaseResponse getSecExpertCheckDtlRecord(SecSelectionRecordDtlModel request);

    void createOrupdateSecExpertCheckDetailAndDtl(List<SecExpertCheckDtlModel> rawResult, String id);

    List<SecExpertCheckDtlModel> getSecExpertCheckDtlByScdId(String scdId);

}

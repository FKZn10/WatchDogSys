package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSelectionRecordModel;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertSelectDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecSelectionRecord;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

public interface ISecSelectionRecordService extends IService<SecSelectionRecord> {

    BaseResponse createOrUpdateSecSelectionRecord(SecSelectionRecordModel request, LogUserInfoVo user);

    BaseResponse getSecSelectionRecordList(SecSelectionRecordModel request, LogUserInfoVo user);

    BaseResponse getSectionDocumentCount(SecSelectionRecordModel request);

    BaseResponse getSecSelectionRecordById(String srId);

    BaseResponse deleteSecSelectionRecordById(String srId);


    /****************************************专家抽取***********************************************/
    BaseResponse createOrUpdateExpertExtract(SecSelectionRecordModel request, LogUserInfoVo user);

    BaseResponse getSecExpertExtractById(String srId);

    /**
     * 专家抽取申请ID查询抽取记录
     *
     * @param esId
     * @return
     */
    BaseResponse getSecExpertExtractByEsId(String esId);

    BaseResponse getSecExpertExtractList(SecExpertSelectDto request, String uId);

    BaseResponse cancelExtract2cancelSelect(String srId);

    BaseResponse getSecSelectionRecordDtlModelById(String srId);

    void updateStateById(Integer state,String srId);

    BaseResponse getSecSelectionRecordClassInfo(SecSelectionRecordModel request, LogUserInfoVo user);

    BaseResponse getSecSelectionRecordInfoBySecId(SecSelectionRecordModel request);

    /**
     * 根据项目和标段查询已结束流程的评标方法。
     *
     * @param pId 项目ID
     * @param secId 标段ID
     * @return 评标方法和招标控制价
     */
    BaseResponse getTenderingMethodByProjectAndSection(String pId, String secId);
}

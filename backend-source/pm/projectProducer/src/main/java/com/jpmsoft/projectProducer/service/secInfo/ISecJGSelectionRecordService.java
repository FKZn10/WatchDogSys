package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGSelectionRecordModel;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.ExtractJGExpertDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecJGExpertSelectDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecJGSelectionRecord;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

public interface ISecJGSelectionRecordService extends IService<SecJGSelectionRecord> {

    /**
     * 创建或修改专家选取记录
     * @param request
     * @param userInfoVo
     * @return
     */
    BaseResponse createOrUpdateSecJGSelectionRecord(SecJGSelectionRecordModel request, LogUserInfoVo userInfoVo);

    /**
     * 分页查询专家选取记录
     * @param request
     * @param uid
     * @return
     */
    BaseResponse getSecJGSelectionRecordList(SecJGSelectionRecordModel request, String uid);

    /**
     * 查询同标段的招标文件审批结束记录数
     * @param request
     * @return
     */
    BaseResponse getJGSectionDocumentCount(SecJGSelectionRecordModel request);

    /**
     * ID查询专家选取记录
     * @param srId
     * @return
     */
    BaseResponse getSecJGSelectionRecordById(String srId);


    /**
     * ID删除专家选取记录
     * @param srId
     * @return
     */
    BaseResponse deleteSecJGSelectionRecordById(String srId);


    /**
     * 创建新增专家抽取
     * @param request
     * @param user
     * @return
     */
    BaseResponse createOrUpdateJGExpertExtract(SecJGSelectionRecordModel request, LogUserInfoVo user);

    /**
     * ID查询专家抽取
     * @param srId
     * @return
     */
    BaseResponse getSecJGExpertExtractById(String srId);

    /**
     * 抽取申请ID查询专家抽取
     * @param esId
     * @return
     */
    BaseResponse getSecJGExpertExtractByEsId(String esId);


    BaseResponse getSecJGSelectionRecordDtlModelById(String srId);

    /**
     * 分页查询专家抽取
     * @param request
     * @param uId
     * @return
     */
    BaseResponse getSecJGExpertExtractList(SecJGExpertSelectDto request, String uId);

    /**
     * 作废专家抽取同时作废对应专家选取信息
     * @param srId
     * @return
     */
    BaseResponse cancelJGExtractCancelSelect(String srId);

    /**
     * 选取时校验专家人数是否满足
     * @param request
     * @param uId
     * @return
     */
    BaseResponse checkJGExPeople(ExtractJGExpertDto request, String uId);

    void updateStateById(Integer state,String srId);

    BaseResponse getSecJGSelectionRecordPrintById(String srId);

    BaseResponse getSecJGSelectionRecordPrintInsecDtlModelsById(String srId,String token);

    BaseResponse getSecJGSelectionRecordPrintOutsecDtlModelsById(String srId,String token);

    BaseResponse getSecJGSelectionRecordPrintSrInsideExListById(String srId, String token);

    BaseResponse getSecJGSelectionRecordPrintSrOutsideExListById(String srId, String token);
}

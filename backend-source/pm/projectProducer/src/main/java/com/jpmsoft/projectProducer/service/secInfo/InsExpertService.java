package com.jpmsoft.projectProducer.service.secInfo;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.InsExpertRequest;
import com.jpmsoft.common.model.request.SecInfo.InsExpertSearchDTO;
import com.jpmsoft.common.model.request.SecInfo.JudgeExtractionDTO;
import com.jpmsoft.common.model.request.SecInfo.JudgeExtractionSearchDTO;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.StorageChangeRequest;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.InsExpert;

/**
* @author Administrator
* @description 针对表【ins_expert】的数据库操作Service
* @createDate 2025-04-24 08:49:27
*/
public interface InsExpertService extends IService<InsExpert> {


    BaseResponse getInsExpertClassInfo(InsExpertSearchDTO request);

    BaseResponse createOrUpdateInsideExpert(InsExpertRequest insExpertRequest);

    BaseResponse queryNotInLib();

    BaseResponse queryInLib();

    BaseResponse queryNotInLibByPage(Integer page,Integer size);

    BaseResponse queryInLibByPage(Integer page,Integer size);

    BaseResponse chooseExpert(JudgeExtractionSearchDTO judgeExtractionSearchDTO);

    BaseResponse getExpertByExId(String exId);

    BaseResponse inLibraryOrChange(StorageChangeRequest request);

    BaseResponse rewriteInsExpert(String storageChangeId);

    BaseResponse reject(String exId);

    BaseResponse deleteExpert(String exId);

    BaseResponse queryStorageChange(Integer page, Integer size,String uid);

    BaseResponse queryStorageDetail(String id);

    BaseResponse createOrUpdateJudgeExtraction(JudgeExtractionDTO judgeExtraction);

    BaseResponse deleteJudgeExtraction(String judgeExtractionId);

    BaseResponse queryJudgeExtraction(String judgeExtractionId);

    BaseResponse queryJudgeExtractionByPage(JudgeExtractionSearchDTO request,String uid);

    BaseResponse getJudgeExtractionBySecId(String secId);

    void encryptIdNumber();
}

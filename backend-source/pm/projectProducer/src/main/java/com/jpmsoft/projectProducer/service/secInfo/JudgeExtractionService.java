package com.jpmsoft.projectProducer.service.secInfo;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.JudgeExtractionDTO;
import com.jpmsoft.common.model.request.SecInfo.JudgeExtractionSearchDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.JudgeExtraction;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

/**
 * @author Administrator
 * @description 针对表【judge_extraction(评委抽取表)】的数据库操作Service
 * @createDate 2025-04-24 18:08:19
 */
public interface JudgeExtractionService extends IService<JudgeExtraction> {

    JudgeExtractionDTO selectById(String id);

    BaseResponse getJudgeExtractionClassInfo(JudgeExtractionSearchDTO request, LogUserInfoVo user);

    /**
     * 专家抽取补抽
     *
     * @param request
     * @return
     */
    BaseResponse expertExtraction(JudgeExtractionSearchDTO request);

    BaseResponse queryJudgeExtractionByPage(JudgeExtractionSearchDTO request, LogUserInfoVo user);
}

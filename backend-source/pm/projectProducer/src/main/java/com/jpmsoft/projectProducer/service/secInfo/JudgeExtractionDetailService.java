package com.jpmsoft.projectProducer.service.secInfo;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.SecInfo.InsExpertRequest;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.JudgeExtractionDetail;

import java.util.List;

/**
 * @author Administrator
 * @description 针对表【judge_extraction_detail(评委抽取详情表)】的数据库操作Service
 * @createDate 2025-04-24 18:08:19
 */
public interface JudgeExtractionDetailService extends IService<JudgeExtractionDetail> {

    void saveInsideExpert(List<InsExpertRequest> insExperts, String id);

    void deleteByExId(String exId);

    List<InsExpertRequest> selectByExId(String exId);
}

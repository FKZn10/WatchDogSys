package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.jpbpm.pojo.entity.OaProblemFeedback;
import org.springframework.stereotype.Service;

@Service
public interface OaProblemFeedbackService extends IService<OaProblemFeedback> {

    void sncUpdateOAProblemDtlStatus(String pmfId);
}

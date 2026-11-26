package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.jpbpm.mapper.OaProblemFeedbackMapper;
import com.jpmsoft.jpbpm.pojo.entity.OaProblemFeedback;
import com.jpmsoft.jpbpm.service.OaProblemFeedbackService;
import org.springframework.stereotype.Service;
import com.jpmsoft.common.utils.ValidateUtil;

@Service
public class OaProblemFeedbackImpl extends ServiceImpl<OaProblemFeedbackMapper, OaProblemFeedback> implements OaProblemFeedbackService {
    @Override
    public void sncUpdateOAProblemDtlStatus(String pmfId) {
        OaProblemFeedback feedback = baseMapper.selectById(pmfId);
        if (feedback.getPmfStatus() != null && feedback.getPmfStatus() == 1 && !ValidateUtil.isEmpty(feedback.getPmdId()))
            baseMapper.sncUpdateOAProblemDtlStatus(feedback.getPmfStatus(), feedback.getPmdId());
    }
}

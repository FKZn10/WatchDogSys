package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.jpbpm.pojo.entity.RiskWarn;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RiskWarnService extends IService<RiskWarn> {
    /**
     * 廉政预警信息生成代办
     *
     * @param request
     * @return
     */
    BaseResponse alertGenerationAgent(List<RiskWarn> request,String customerId);
}

package com.jpmsoft.jpbpm.service;
import com.jpmsoft.common.base.BaseResponse;
public interface RiskWarnClueTaskService {
    BaseResponse sync(String rwcId, String operator, String token);
}

package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.jpbpm.pojo.entity.SysMultiFormFlow;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysMultiFormFlowService extends IService<SysMultiFormFlow> {
    BaseResponse create(List<SysMultiFormFlow> request,String customerId);
    List<SysMultiFormFlow>  GetSysMultiFormFlow(SysMultiFormFlow request,String customerId);
}

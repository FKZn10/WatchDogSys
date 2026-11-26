package com.jpmsoft.userProducer.service.impl;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.*;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.service.IFlowRestTemplateService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class FlowRestTemplateImpl implements IFlowRestTemplateService {

    @Resource(name = "restTemplate")
    private RestTemplate restTemplate;

    @Override
    public BaseResponse getCarbonCopyList(SysCarbonCopyRequest request,String token) {
        //指定服务名称调用则可以实现负载 若指定IP端口则无法负载
        String url = "http://bpm-server//v1/carbonCopy/getCarbonCopyList";
        BaseResponse baseResponse = restTemplate.postForObject(url, assemblyRequestParam(request,token), BaseResponse.class);
        return baseResponse;
    }

    @Override
    public BaseResponse getFileList(SysFileRequest request,String token) {
        String url = "http://bpm-server//v1/sysFile/getFileList";
        BaseResponse baseResponse = restTemplate.postForObject(url, assemblyRequestParam(request,token), BaseResponse.class);
        return baseResponse;
    }

    @Override
    public BaseResponse getFlowList(SysFlowRequest request, String token) {
        String url = "http://bpm-server//v1/flow/getFlowList";
        HttpEntity<String> formEntity = assemblyRequestParam(request, token);
        BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
        return baseResponse;
    }

    @Override
    public BaseResponse getCoordinationTaskList(SysCoordinationTaskRequest request,String token) {
        String url = "http://bpm-server//v1/coordinationTask/getCoordinationTaskList";
        BaseResponse baseResponse = restTemplate.postForObject(url, assemblyRequestParam(request,token), BaseResponse.class);
        return baseResponse;
    }

    @Override
    public BaseResponse getTaskList(SysTaskRequest request,String token) {
        String url = "http://bpm-server//v1/task/getTaskList";
        BaseResponse baseResponse = restTemplate.postForObject(url, assemblyRequestParam(request,token), BaseResponse.class);
        return baseResponse;
    }

    @Override
    public BaseResponse getCoordinationProcessList(SysCoordinationProcessRequest request,String token) {
        String url = "http://bpm-server//v1/coordinationProcess/getCoordinationProcessList";
        BaseResponse baseResponse = restTemplate.postForObject(url, assemblyRequestParam(request,token), BaseResponse.class);
        return baseResponse;
    }

    private HttpEntity<String> assemblyRequestParam(Object object, String token) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        if (ValidateUtil.isNotEmpty(token))
            headers.add("token", token);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<>(JacksonUtils.toJSONString(object), headers);
        return formEntity;
    }
}

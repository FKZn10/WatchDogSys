package com.jpmsoft.projectProducer.service.other.impl;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.*;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.service.other.ITodoCenterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Slf4j
@Service
public class TodoCenterImpl implements ITodoCenterService {


    @Resource
    private RestTemplate restTemplate;

    @Override
    public BaseResponse getUserTaskList(String userId, UserTaskRequest request) {
        //指定服务名称调用则可以实现负载 若指定IP端口则无法负载
        HttpEntity<String> formEntity = assemblyRequestParam(userId, request);
        String url = "http://bpm-server/v1/task/getUserTaskList";
        BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
        return baseResponse;
    }

    @Override
    public BaseResponse getUserTaskProcessList(String userId, UserTaskProcessinRequest request) {
        HttpEntity<String> formEntity = assemblyRequestParam(userId, request);
        String url = "http://bpm-server/v1/taskProcessin/getUserTaskProcessList";
        BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
        return baseResponse;
    }

    @Override
    public BaseResponse getUserCoordinationTaskList(String userId, UserCoordinationTaskRequest request) {
        HttpEntity<String> formEntity = assemblyRequestParam(userId, request);
        String url = "http://bpm-server/v1/coordinationTask/getUserCoordinationTaskList";
        BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
        return baseResponse;
    }

    @Override
    public BaseResponse getUserCarbonCopyList(String userId, UserCarbonCopyRequest request) {
        HttpEntity<String> formEntity = assemblyRequestParam(userId, request);
        String url = "http://bpm-server/v1/carbonCopy/getUserCarbonCopyList";
        BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
        return baseResponse;
    }

    @Override
    public BaseResponse getUserCoordinationProcessList(String userId, UserCoordinationProcessRequest request) {
        HttpEntity<String> formEntity = assemblyRequestParam(userId, request);
        String url = "http://bpm-server/v1/coordinationProcess/getUserCoordinationProcessList";
        BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
        return baseResponse;
    }

    @Override
    public BaseResponse getUserToDoSummary(String userId) {
        String url = "http://bpm-server/v1/coordinationTask/getUserToDoSummary?userId=" + userId;
        BaseResponse baseResponse = restTemplate.getForObject(url, BaseResponse.class);
        return baseResponse;
    }


    /**
     * 组装POST请求参数
     * 附带请求头 token参数
     *
     * @param header
     * @param object
     * @return
     */
    private HttpEntity<String> assemblyRequestParam(String header, Object object) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("token", header);
        log.info("服务调用时:" + JacksonUtils.toJSONString(object));
        HttpEntity<String> formEntity = new HttpEntity<>(JacksonUtils.toJSONString(object), headers);
        return formEntity;
    }
}

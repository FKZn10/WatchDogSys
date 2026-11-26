package com.jpmsoft.jpbpm.service.impl;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.FileResponse;
import com.jpmsoft.jpbpm.service.SysFileAnnexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import com.jpmsoft.common.utils.ValidateUtil;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysFileAnnexImpl implements SysFileAnnexService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public BaseResponse refreshRelationFile(List<Map<String, String>> data,String  token) {
        //指定服务名称调用则可以实现负载 若指定IP端口则无法负载
        String url = "http://file-server/fileRecord/refreshRelationFile";
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        HttpEntity<Object> formEntity = new HttpEntity<>(data, headers);
        BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
        return baseResponse;
    }

    @Override
    public BaseResponse refreshRelationFile(String fileRelationId, String fileIds,String  token) {
        List<Map<String, String>> params = new ArrayList<>();
        Map<String, String> param = new HashMap<>();
        param.put("fileRelationId",fileRelationId);
        param.put("fileIds", fileIds);
        params.add(param);
        BaseResponse baseResponse = refreshRelationFile(params,token);
        return baseResponse;
    }


    @Override
    public BaseResponse addFormRefreshRelationFile(List<String> ids,String  token) {
        String url = "http://file-server/fileRecord/addFormRefreshRelationFile";
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        HttpEntity<Object> formEntity = new HttpEntity<>(ids, headers);
        BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
        return baseResponse;
    }


    @Override
    public BaseResponse addFormRefreshRelationFile(String relationId,String  token) {
        List<String> ids = new ArrayList<>();
        ids.add(relationId);
        return addFormRefreshRelationFile(ids,token);
    }

    @Override
    public List<FileResponse> getFileByFileRelationId(String fileRelationId,String  token) {
        if (ValidateUtil.isEmpty(fileRelationId))
            return new ArrayList<>();
        String url = "http://file-server/fileRecord/getFileByFileRelationId?fileRelationId=" + fileRelationId;
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        HttpEntity<Object> formEntity = new HttpEntity<>( headers);
        List<FileResponse> data = restTemplate.getForObject(url, List.class,formEntity);
        if (data == null)
            data = new ArrayList<>();
        return data;
    }
}
package com.jpmsoft.gmgws.service.impl;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.FileResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.gmgws.service.ISysFileAnnexService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysFileAnnexImpl implements ISysFileAnnexService {

    @Resource
    private RestTemplate restTemplate;

    @Override
    public BaseResponse refreshRelationFile(List<String> raltionIds, List<String> fileIds, String entityId) {
        List<Map<String, String>> data = new ArrayList<>();
        for (int i = 0; i < raltionIds.size(); i++) {
            if (!ValidateUtil.isEmpty(raltionIds.get(i))) {
                Map<String, String> param = new HashMap<>();
                param.put("fileRelationId", raltionIds.get(i));
                param.put("fileIds", fileIds.get(i));
                data.add(param);
            }
        }
        if (data.size() < 1)
            return new BaseResponse(true, "");
        String url = "http://file-server/fileRecord/refreshRelationFile?entityId=" + entityId;
        BaseResponse baseResponse = restTemplate.postForObject(url, data, BaseResponse.class);
        return baseResponse;
    }

    @Override
    public BaseResponse refreshRelationFile(List<Map<String, String>> data, String entityId) {
        if (CollectionUtils.isEmpty(data)) return BaseResponse.ok();
        //指定服务名称调用则可以实现负载 若指定IP端口则无法负载
        String url = "http://file-server/fileRecord/refreshRelationFile?entityId=" + entityId;
        BaseResponse baseResponse = restTemplate.postForObject(url, data, BaseResponse.class);
        return baseResponse;
    }

    @Override
    public BaseResponse refreshRelationFile(String fileRelationId, String fileIds, String entityId) {
        if (ValidateUtil.isAnObjEmpty(fileRelationId, fileIds)) return BaseResponse.ok();
        List<Map<String, String>> params = new ArrayList<>();
        Map<String, String> param = new HashMap<>();
        param.put("fileRelationId", fileRelationId);
        param.put("fileIds", fileIds);
        params.add(param);
        return refreshRelationFile(params, entityId);
    }

    @Override
    public BaseResponse addFormRefreshRelationFile(List<String> ids, String entityId) {
        if (CollectionUtils.isEmpty(ids)) return BaseResponse.ok();
        String url = "http://file-server/fileRecord/addFormRefreshRelationFile?entityId=" + entityId;
        return restTemplate.postForObject(url, ids, BaseResponse.class);
    }

    @Override
    public BaseResponse addFormRefreshRelationFile(String relationId, String entityId) {
        if (ValidateUtil.isEmpty(relationId)) return BaseResponse.ok();
        List<String> ids = new ArrayList<>();
        ids.add(relationId);
        return addFormRefreshRelationFile(ids, entityId);
    }

    @Override
    public List<FileResponse> getFileByFileRelationId(String fileRelationId) {
        if (ValidateUtil.isEmpty(fileRelationId))
            return new ArrayList<>();
        String url = "http://file-server/fileRecord/getFileByFileRelationId?fileRelationId=" + fileRelationId;
        List<FileResponse> data = restTemplate.getForObject(url, List.class);
        if (data == null)
            data = new ArrayList<>();
        return data;
    }
}

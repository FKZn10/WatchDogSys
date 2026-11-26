package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.AchievementsRequest;
import com.jpmsoft.common.model.request.Archive.FileServerToArchiveRequest;
import com.jpmsoft.common.model.response.FileResponse;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.jpbpm.mapper.FileAnnexMapper;
import com.jpmsoft.jpbpm.pojo.entity.FileAnnex;
import com.jpmsoft.jpbpm.service.FileAnnexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jpmsoft.common.utils.ValidateUtil;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class FileAnnexImpl extends ServiceImpl<FileAnnexMapper, FileAnnex> implements FileAnnexService {
    @Override
    public Integer updateFileAnnexByrelationid(String id, String fileids) {
        QueryWrapper<FileAnnex> Wrapper = new QueryWrapper<>();
        //Wrapper.eq("file_relation_id",id);
        Wrapper.in("file_id", id);
        FileAnnex enty = new FileAnnex();
        enty.setUploadStatus(1);
        //enty.set
        baseMapper.update(enty, Wrapper);
        QueryWrapper<FileAnnex> Wrapperdelete = new QueryWrapper<>();
        //Wrapperdelete.eq("file_relation_id",id);
        Wrapperdelete.notIn("file_id", fileids);
        baseMapper.delete(Wrapperdelete);
        return 1;
    }


    @Autowired
    private RestTemplate restTemplate;

    @Override
    public BaseResponse refreshRelationFile(List<String> raltionIds, List<String> fileIds, String entityId,String token) {
        List<Map<String, String>> data = new ArrayList<>();
        for (int i = 0; i < raltionIds.size(); i++) {
            Map<String, String> param = new HashMap<>();
            param.put("fileRelationId", raltionIds.get(i));
            param.put("fileIds", fileIds.get(i));
            data.add(param);
        }
        String url = "http://file-server/fileRecord/refreshRelationFile?entityId=" + entityId;
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        HttpEntity<Object> formEntity = new HttpEntity<>(data, headers);
        BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
        return baseResponse;
    }

    @Override
    public BaseResponse refreshRelationFile(String fileRelationId, String fileIds, String entityId,String token) {
        List<Map<String, String>> params = new ArrayList<>();
        Map<String, String> param = new HashMap<>();
        param.put("fileRelationId", fileRelationId);
        param.put("fileIds", fileIds);
        params.add(param);
        String url = "http://file-server/fileRecord/refreshRelationFile?entityId=" + entityId;
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        HttpEntity<Object> formEntity = new HttpEntity<>(params, headers);
        BaseResponse baseResponse = restTemplate.postForObject(url, params, BaseResponse.class);
        return baseResponse;
    }


    @Override
    public BaseResponse addFormRefreshRelationFile(List<String> ids,String token) {
        String url = "http://file-server/fileRecord/addFormRefreshRelationFile";
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        HttpEntity<Object> formEntity = new HttpEntity<>(ids, headers);
        BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
        return baseResponse;
    }


    @Override
    public BaseResponse addFormRefreshRelationFile(String relationId,String token) {
        List<String> ids = new ArrayList<>();
        ids.add(relationId);
        return addFormRefreshRelationFile(ids,token);
    }

    @Override
    public List<FileResponse> getFileByFileRelationId(String fileRelationId,String token) {
        if (ValidateUtil.isEmpty(fileRelationId))
            return new ArrayList<>();
        String url = "http://file-server/fileRecord/getFileByFileRelationId?fileRelationId=" + fileRelationId;
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        HttpEntity<Object> formEntity = new HttpEntity<>(headers);
        List<FileResponse> data = restTemplate.getForObject(url, List.class);
        if (data == null)
            data = new ArrayList<>();
        return data;
    }

    @Override
    public List<FileResponse> getFileByFileRelationIds(List<String> fileRelationIds,String token) {
        if (fileRelationIds.size() == 0)
            return new ArrayList<>();
        HttpEntity<Object> newRequest = assemblyRequestParam(fileRelationIds,token);
        String url = "http://file-server/fileRecord/getFileByRelationIds";
        //List<FileResponse> data = restTemplate.postForObject(url, newRequest, List.class);
        BaseResponse baseResponse = restTemplate.postForObject(url, newRequest, BaseResponse.class);
        if (baseResponse.getData() != null) {
            List<FileResponse> data = JacksonUtils.parseObject(JacksonUtils.toJSONString(baseResponse.getData()), new TypeReference<List<FileResponse>>() {
            });
            if (data == null)
                data = new ArrayList<>();
            return data;
        } else {
            return new ArrayList<>();
        }

    }

    @Override
    public BaseResponse getAchievementsList(AchievementsRequest request,String token) {
        String url = "http://file-server/fileRecord/getAchievementsList";
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        HttpEntity<Object> formEntity = new HttpEntity<>(request, headers);
        BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
        return baseResponse;
    }

    @Override
    public BaseResponse archiveFile(FileServerToArchiveRequest request,String token) {
        String url = "http://file-server/fileRecord/archiveFile";
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        HttpEntity<Object> formEntity = new HttpEntity<>(request, headers);
        BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
        return baseResponse;
    }

    /**
     * 组装POST请求参数
     *
     * @param object
     * @return
     */
    private HttpEntity<Object> assemblyRequestParam(Object object,String token) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("token", token);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<Object> formEntity = new HttpEntity<>(object, headers);
        return formEntity;
    }

    @Override
    public String generateContractCode(String id,String token) {
        String url = "http://project-producer/v1/contract/generateContractCode?id=" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);
        HttpEntity<Object> formEntity = new HttpEntity<>(headers);
        return restTemplate.getForObject(url, String.class);
    }
}

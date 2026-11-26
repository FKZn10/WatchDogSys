package com.jpmsoft.projectProducer.service.other.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.AchievementsRequest;
import com.jpmsoft.common.model.request.Archive.FileServerToArchiveRequest;
import com.jpmsoft.common.model.response.FileResponse;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.other.SysFileannexMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFileannex;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SysFileAnnexImpl extends ServiceImpl<SysFileannexMapper, SysFileannex> implements  ISysFileAnnexService {

    @Resource
    private RestTemplate restTemplate;

    @Override
    public BaseResponse refreshRelationFile(List<String> raltionIds, List<String> fileIds, String entityId) {
        List<Map<String, String>> data = new ArrayList<>();
        for (int i = 0; i < raltionIds.size(); i++) {
            if (!ValidateUtil.isEmpty(raltionIds.get(i))) {
                Map<String, String> param = new HashMap<>();
                param.put("fileRelationId", raltionIds.get(i));
                param.put("fileIds", ValidateUtil.isEmpty(fileIds.get(i)) ? null : fileIds.get(i));
                data.add(param);
            }
        }
        if (data.size() < 1)
            return BaseResponse.ok();
        String url = "http://file-server/fileRecord/refreshRelationFile?entityId=" + entityId;
        BaseResponse baseResponse = restTemplate.postForObject(url, data, BaseResponse.class);
        if (baseResponse.getStatusCode() != HttpStatus.OK.value() || baseResponse.getSuccess() != Boolean.TRUE)
            throw new JPMException(ResMsg.fileSaveRefershError);
        return baseResponse;
    }

    @Override
    public BaseResponse refreshRelationFile(List<Map<String, String>> relationList, String entityId) {
        if (relationList.size() < 1)
            return BaseResponse.ok();
        String url = "http://file-server/fileRecord/refreshRelationFile?entityId=" + entityId;
        BaseResponse baseResponse = restTemplate.postForObject(url, relationList, BaseResponse.class);
        if (baseResponse.getStatusCode() != HttpStatus.OK.value() || baseResponse.getSuccess() != Boolean.TRUE)
            throw new JPMException(ResMsg.fileSaveRefershError);
        return baseResponse;
    }

    @Override
    public BaseResponse addFormCopyAndRefreshRelationFile(List<String> raltionIds, List<String> fileIds, String entityId) {
        log.info("addFormCopyAndRefreshRelationFile");
        log.info("raltionIds:{}", JacksonUtils.toJSONString(raltionIds));
        log.info("fileIds:{}", JacksonUtils.toJSONString(fileIds));
        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> param = new HashMap<>();
        for (int i = 0; i < raltionIds.size(); i++) {
            if (!ValidateUtil.isEmpty(raltionIds.get(i))) {
                param.put("fileRelationId", raltionIds.get(i));

            }
        }
        for (int i = 0; i < fileIds.size(); i++) {
            if (!ValidateUtil.isEmpty(fileIds.get(i))) {
                param.put("fileIds", ValidateUtil.isEmpty(fileIds.get(i)) ? null : fileIds.get(i));
            }
        }
        data.add(param);
        if (data.size() < 1)
            return BaseResponse.ok();
        String url = "http://file-server/fileRecord/addFormCopyAndRefreshRelationFile?entityId=" + entityId;
        BaseResponse baseResponse = restTemplate.postForObject(url, data, BaseResponse.class);
        return baseResponse;
    }

    @Override
    public BaseResponse refreshRelationFile(String fileRelationId, String fileIds, String entityId) {
        if (ValidateUtil.isEmpty(fileRelationId))
            return BaseResponse.ok();
        List<Map<String, String>> params = new ArrayList<>();
        Map<String, String> param = new HashMap<>();
        param.put("fileRelationId", fileRelationId);
        param.put("fileIds", fileIds);
        params.add(param);
        String url = "http://file-server/fileRecord/refreshRelationFile?entityId=" + entityId;
        BaseResponse baseResponse = restTemplate.postForObject(url, params, BaseResponse.class);
        return baseResponse;
    }


    @Override
    public BaseResponse addFormRefreshRelationFile(List<String> ids, String entityId) {
        List<String> allIds = new ArrayList<>();
        for (String id : ids) {
            if (!ValidateUtil.isEmpty(id))
                allIds.add(id);
        }
        if (allIds.size() < 1)
            return BaseResponse.ok();
        String url = "http://file-server/fileRecord/addFormRefreshRelationFile?entityId=" + entityId;
        BaseResponse baseResponse = restTemplate.postForObject(url, allIds, BaseResponse.class);
        if (baseResponse.getStatusCode() != HttpStatus.OK.value() || baseResponse.getSuccess() != Boolean.TRUE)
            throw new JPMException(ResMsg.fileSaveRefershError);
        return baseResponse;
    }


    @Override
    public BaseResponse addFormRefreshRelationFile(String relationId, String entityId) {
        List<String> ids = new ArrayList<>();
        ids.add(relationId);
        return addFormRefreshRelationFile(ids, entityId);
    }

    @Override
    public List<FileResponse> getFileByFileRelationId(String fileRelationId) {
        if (ValidateUtil.isEmpty(fileRelationId))
            return new ArrayList<>();
        String url = "http://file-server/fileRecord/getFileByFileRelationId?fileRelationId=" + fileRelationId;
        List<FileResponse> res = restTemplate.getForObject(url, List.class);
        if (res != null && res.size() > 0) {
            //java.util.LinkedHashMap 需要转型
            String str = JacksonUtils.toJSONString(res);
            List<FileResponse> datas = JacksonUtils.parseArray(str, FileResponse.class);
            return datas;
        }
        return res;
    }

    @Override
    public List<FileResponse> getFileByFileRelationIds(List<String> fileRelationIds) {
        String url = "http://file-server/fileRecord/getFileByFileRelationIds";
        List<FileResponse> res = restTemplate.postForObject(url, fileRelationIds, List.class);
        if (res != null && res.size() > 0) {
            //java.util.LinkedHashMap 需要转型
            String str = JacksonUtils.toJSONString(res);
            List<FileResponse> datas = JacksonUtils.parseArray(str, FileResponse.class);
            return datas;
        }
        return res;
    }

    @Override
    public BaseResponse getAchievementsList(AchievementsRequest request) {
        String url = "http://file-server/fileRecord/getAchievementsList";
        BaseResponse baseResponse = restTemplate.postForObject(url, assemblyRequestParam(request), BaseResponse.class);
        return baseResponse;
    }

    @Override
    public BaseResponse archiveFile(FileServerToArchiveRequest request) {
        String url = "http://file-server/fileRecord/archiveFile";
        BaseResponse baseResponse = restTemplate.postForObject(url, assemblyRequestParam(request), BaseResponse.class);
        return baseResponse;
    }

    @Override
    public BaseResponse copyFileAnnex2NewRelation(List<String> oldIds, List<String> newIds, String entityId) {
        return copyFileAnnex2NewRelation(oldIds, newIds, entityId, false);
    }

    @Override
    public BaseResponse copyFileAnnex2NewRelation(List<String> oldIds, List<String> newIds, String entityId, Boolean skipArchive) {
        try {
            Map<String, Object> request = new HashMap<>();
            request.put("oldIds", oldIds);
            request.put("newIds", newIds);
            HttpEntity<String> formEntity = assemblyRequestParam(request);
            String url = "http://file-server/fileRecord/copyFileAnnex2NewRelation?entityId=" + entityId + "&skipArchive=" + skipArchive;
            BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
            return baseResponse;
        } catch (Exception ex) {
            return BaseResponse.fail();
        }
    }

    @Override
    public BaseResponse replaceFormFile(List<String> oldIds, List<String> newIds, String entityId) {
        try {
            Map<String, Object> request = new HashMap<>();
            request.put("oldIds", oldIds);
            request.put("newIds", newIds);
            HttpEntity<String> formEntity = assemblyRequestParam(request);
            String url = "http://file-server/fileRecord/replaceFormFile?entityId=" + entityId;
            BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
            return baseResponse;
        } catch (Exception ex) {
            return BaseResponse.fail();
        }
    }


    /**
     * 组装POST请求参数
     *
     * @param object
     * @return
     */
    private HttpEntity<String> assemblyRequestParam(Object object) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        log.info("服务调用时:" + JacksonUtils.toJSONString(object));
        HttpEntity<String> formEntity = new HttpEntity<>(JacksonUtils.toJSONString(object), headers);
        return formEntity;
    }

    @Override
    public BaseResponse copyFileWithFileAnnex(List<SysFileannex> model, Boolean skipArchive) {
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(model);
            String url = "http://file-server/fileRecord/copyFileWithFileAnnex?skipArchive=" + skipArchive;
            BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
            return baseResponse;
        } catch (Exception ex) {
            return BaseResponse.fail();
        }
    }
}

package com.jpmsoft.custom.service.impl;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.requestAndResponse.Oa.ReportedPersonnelDtlModel;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.custom.constants.CustomMsg;
import com.jpmsoft.custom.service.IApiUtilsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ApiUtilsImpl implements IApiUtilsService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String SelfSm2Decrypt(String encDataHex) {
        if (ValidateUtil.isEmpty(encDataHex) || encDataHex.equals("null")) return null;
        String url = "http://api-utils/v1/decrypt/SelfSm2Decrypt?encDataHex=" + encDataHex;
        String data = restTemplate.getForObject(url, String.class);
        return data;
    }


    @Override
    public String generateCodeByTCode(String tCode, Object request) {
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(request);
            String url = "http://user-producer/v1/seqSet/generateCodeByTCode?tCode=" + tCode;
            String res = restTemplate.postForObject(url, formEntity, String.class);
            return res;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public String generateCodeByCustomized(String tCode, String pId, String conId, String secId, Object request) {
        try {
            if (ValidateUtil.isEmpty(tCode))
                return null;
            String url = "http://user-producer/v1/seqSet/generateCodeByCustomized?tCode=" + tCode;
            if (!ValidateUtil.isEmpty(pId))
                url += "&pId=" + pId;
            if (!ValidateUtil.isEmpty(conId))
                url += "&conId=" + conId;
            if (!ValidateUtil.isEmpty(secId))
                url += "&secId=" + secId;
            HttpEntity<String> formEntity = assemblyRequestParam(request);
            String res = restTemplate.postForObject(url, formEntity, String.class);
            return res;
        } catch (Exception ex) {
            return null;
        }
    }


    @Override
    public BaseResponse refershAutoNo(String tCode, Object request) {
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(request);
            String url = "http://user-producer/v1/seqSet/refershAutoNo?tCode=" + tCode;
            return restTemplate.postForObject(url, formEntity, BaseResponse.class);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public BaseResponse refershAutoNoByCustomized(String tCode, String pId, String conId, String secId, Object request) {
        try {
            if (ValidateUtil.isEmpty(tCode))
                return null;
            String url = "http://user-producer/v1/seqSet/refershAutoNoByCustomized?tCode=" + tCode;
            if (!ValidateUtil.isEmpty(pId))
                url += "&pId=" + pId;
            if (!ValidateUtil.isEmpty(conId))
                url += "&conId=" + conId;
            if (!ValidateUtil.isEmpty(secId))
                url += "&secId=" + secId;
            HttpEntity<String> formEntity = assemblyRequestParam(request);
            return restTemplate.postForObject(url, formEntity, BaseResponse.class);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Boolean sequenceRestEnable(String tCode) {
        if (ValidateUtil.isEmpty(tCode))
            return false;
        try {
            String url = "http://user-producer/v1/seqSet/sequenceRestEnable?tCode=" + tCode;
            Boolean data = restTemplate.getForObject(url, Boolean.class);
            return data == null ? false : data;
        } catch (Exception ex) {
            log.error("校验是否开启自动编码失败！", ex);
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public BaseResponse createOrDeletePUPByBath(List<ReportedPersonnelDtlModel> datas) {
        try {
            Map<String, String> data = new HashMap<>();
            data.put("models", JacksonUtils.toJSONString(datas));
            String url = "http://user-producer/v1/OrganizationManage/createOrDeletePUPByBath";
            HttpEntity<String> formEntity = assemblyRequestParam(data);
            BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
            return baseResponse;
        } catch (Exception ex) {
            return BaseResponse.fail();
        }
    }

    @Override
    public List<String> getAllChildDepartment(String uId) {
        try {
            String url = "http://user-producer/v1/OrganizationManage/getAllChildDepartment?uId=" + uId;
            List<String> res = restTemplate.getForObject(url, List.class);
            return res;
        } catch (Exception ex) {
            return null;
        }
    }


    @Override
    public String getAutoCode(String rule, Object request) {
        String str = null;
        if (sequenceRestEnable(rule)) {
            str = generateCodeByTCode(rule, request);
            if (ValidateUtil.isEmpty(str))
                throw new JPMException(CustomMsg.codeGenerError);
        }
        return str;
    }

    @Override
    public String PasswrodEncryptionResult(String passWord, String salt) {
        try {
            String url = "http://api-utils/v1/encryption/passwrodEncryptionResult?passWord=" + URLEncoder.encode(passWord, "UTF-8") + "&salt=" + URLEncoder.encode(salt, "UTF-8");
            String data = restTemplate.getForObject(url, String.class);
            return data;
        } catch (Exception ex) {
            throw new RuntimeException("服务内部错误！");
        }
    }

    @Override
    public BaseResponse sendPost(String url,String pk, Object data) {
        try {
            if (ValidateUtil.isNotEmpty(pk))
                url=url+"?formPriKey="+URLEncoder.encode(pk,"UTF-8");
            HttpEntity<String> formEntity = assemblyRequestParam(data);
            BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
            return baseResponse;
        } catch (Exception ex) {
            throw new JPMException("调用接口失败!");
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
}

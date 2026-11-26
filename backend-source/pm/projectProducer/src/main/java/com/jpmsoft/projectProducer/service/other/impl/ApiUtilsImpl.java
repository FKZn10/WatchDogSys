package com.jpmsoft.projectProducer.service.other.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.model.request.SignAndVerifyDTO;
import com.jpmsoft.common.model.requestAndResponse.Oa.ReportedPersonnelDtlModel;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.SecretStringGenerator;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.pojo.dto.base.SendFiledChangeDto;
import com.jpmsoft.projectProducer.pojo.dto.base.VueTreeDto;
import com.jpmsoft.projectProducer.pojo.request.ReturnInitiatorDto;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Service
public class ApiUtilsImpl implements IApiUtilsService {

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private ISysConfigService sysConfigService;

    @Override
    public String SelfSm2Decrypt(String encDataHex) {
        if (ValidateUtil.isEmpty(encDataHex) || encDataHex.equals("null")) return null;
        String url = "http://api-utils/v1/decrypt/SelfSm2Decrypt?encDataHex=" + encDataHex;
        String data = restTemplate.getForObject(url, String.class);
        return data;
    }

    @Override
    public String Sm2EncryptionFront(String json, String publicKey) {
        if (ValidateUtil.isEmpty(json) || json.equals("null")) return null;
        String url = "http://api-utils/v1/decrypt/Sm2EncryptionFront?json=" + json;
        if (!ValidateUtil.isEmpty(publicKey))
            url += "&publicKey=" + publicKey;
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
            log.error("校验是否开启自动编码失败！" + ex.getMessage(), ex);
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public BaseResponse insertFiledChangeRecord(Object olds, Object news, String tableName, String uId) {
        SendFiledChangeDto request = new SendFiledChangeDto();
        request.setNews(JacksonUtils.toJSONString(news));
        request.setOlds(JacksonUtils.toJSONString(olds));
        request.setTableName(tableName);
        request.setUserId(uId);
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(request);
            String url = "http://user-producer/v1/FiledChange/insertFiledChangeRecord";
            BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
            return baseResponse;
        } catch (Exception ex) {
            return BaseResponse.fail();
        }
    }

    @Override
    public BaseResponse insertFiledChangeRecordBath(List<Object> olds, List<Object> news, String tableName, String uId) {
        if (ValidateUtil.isEmpty(olds) || ValidateUtil.isEmpty(news)) return BaseResponse.ok();
        List<SendFiledChangeDto> request = new ArrayList<>();
        for (int i = 0; i < olds.size(); i++) {
            SendFiledChangeDto sfc = new SendFiledChangeDto();
            sfc.setNews(JacksonUtils.toJSONString(news.get(i)));
            sfc.setOlds(JacksonUtils.toJSONString(olds.get(i)));
            sfc.setTableName(tableName);
            sfc.setUserId(uId);
            request.add(sfc);
        }
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(request);
            String url = "http://user-producer/v1/FiledChange/insertFiledChangeRecordBath";
            BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
            return baseResponse;
        } catch (Exception ex) {
            return BaseResponse.fail();
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
    public List<VueTreeDto> getDictionaryTree(String code) {
        try {
            Map<String, String> data = new HashMap<>();
            data.put("code", code);
            String url = "http://user-producer/v1/SystemManage/getDictionaryTree";
            HttpEntity<String> formEntity = assemblyRequestParam(data);
            BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
            List<VueTreeDto> datas = JacksonUtils.parseArray(JacksonUtils.toJSONString(baseResponse.getData()), VueTreeDto.class);
            return datas;
        } catch (Exception ex) {
            log.error("获取字段{}失败", code);
            return null;
        }
    }

    @Override
    public String getAutoCode(String rule, Object request) {
        String str = null;
        if (sequenceRestEnable(rule)) {
            str = generateCodeByTCode(rule, request);
            if (ValidateUtil.isEmpty(str))
                throw new JPMException(ResMsg.codeGenerError);
        }
        return str;
    }

    public String crcSha256Encryption(String account, String pwd) {
        //SHA-256("CRCLDAP+" + LDAP账户名.toUpperCase(Locale.ENGLISH) + "+" + SHA-256("密码").toUpperCase(Locale.ENGLISH)).toUpperCase(Locale.ENGLISH)
        return DigestUtil.sha256Hex("CRCLDAP+" + account.toUpperCase(Locale.ENGLISH) + "+" + DigestUtil.sha256Hex(pwd).toUpperCase(Locale.ENGLISH)).toUpperCase(Locale.ENGLISH);
    }

    /**
     * 调用第三方接口加密
     *
     * @param salt
     * @param pwd
     * @return
     */
    public String interfaceEnc(String salt, String pwd) {
        InterfaceEncDecDTO cnf = sysConfigService.getInterfaceEncDec();
        try {
            String url = cnf.getEncInterfaceUrl() + URLEncoder.encode(pwd + salt, "UTF-8");
            BaseResponse data = restTemplate.getForObject(url, BaseResponse.class);
            if (data != null && ValidateUtil.isNotNull(data.getData())) return data.getData().toString();
            else
                throw new JPMException("密码加密失败!");
        } catch (Exception ex) {
            throw new JPMException("密码加密失败！");
        }
    }

    @Override
    public String PasswrodEncryptionResult(String passWord, String salt, String account) {
        String rule = sysConfigService.getConfigByCode("pwdEncryptionRule");
        rule = ValidateUtil.isEmpty(rule) ? "1" : rule;
        if (rule.equals("2")) {//华润sha256加密
            return crcSha256Encryption(account, passWord);
        } else if (rule.equals("3"))//MD5加密
            return DigestUtils.md5DigestAsHex(passWord.getBytes(StandardCharsets.UTF_8));
        else if (rule.equals("4"))//第三方接口加密
            return interfaceEnc(salt, passWord);
        else {//默认国密3加密
            try {
                String url = "http://api-utils/v1/encryption/passwrodEncryptionResult?passWord=" + URLEncoder.encode(passWord, "UTF-8") + "&salt=" + URLEncoder.encode(salt, "UTF-8");
                String data = restTemplate.getForObject(url, String.class);
                return data;
            } catch (Exception ex) {
                throw new RuntimeException("服务内部错误！");
            }
        }
    }

    @Override
    public String PasswrodEncryptionResultNoToken(String passWord, String salt, String account) {
        String rule = sysConfigService.selectConfigByCodeNoToken("pwdEncryptionRule");
        rule = ValidateUtil.isEmpty(rule) ? "1" : rule;
        if (rule.equals("2")) {//华润sha256加密
            return crcSha256Encryption(account, passWord);
        } else if (rule.equals("3"))//MD5加密
            return DigestUtils.md5DigestAsHex(passWord.getBytes(StandardCharsets.UTF_8));
        else if (rule.equals("4"))//第三方接口加密
            return interfaceEnc(salt, passWord);
        else {//默认国密3加密
            try {
                String url = "http://api-utils/v1/encryption/passwrodEncryptionResult?passWord=" + URLEncoder.encode(passWord, "UTF-8") + "&salt=" + URLEncoder.encode(salt, "UTF-8");
                String data = restTemplate.getForObject(url, String.class);
                return data;
            } catch (Exception ex) {
                throw new RuntimeException("服务内部错误！");
            }
        }
    }

    @Override
    public BaseResponse sendPost(String url, Object data) {
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(data);
            BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
            return baseResponse;
        } catch (Exception ex) {
            return BaseResponse.fail("服务内部调用失败！");
        }
    }

    @Override
    public BaseResponse rejectFlowToInitiator(ReturnInitiatorDto returnInitiatorDto) {
        String url = "http://bpm-server/v1/flow/returnInitiatorToTask";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("token", "");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ReturnInitiatorDto> returnInitiatorDtoHttpEntity = new HttpEntity<>(returnInitiatorDto, httpHeaders);
        BaseResponse baseResponse = restTemplate.postForObject(url, returnInitiatorDtoHttpEntity, BaseResponse.class);
        return baseResponse;
    }

    @Override
    public String getFileTemplateTableById(String id) {
        String url = "http://user-producer/v1/FlowManage/getFileTemplateTableById?id=" + id;
        String data = restTemplate.getForObject(url, String.class);
        return data;
    }

    @Override
    public String sign(InterfaceEncDecDTO ied, Boolean sign, Object object) {
        if (sign) {
            if (ValidateUtil.isNotEmpty(ied.getSignUrl())) {
                SignAndVerifyDTO sg = new SignAndVerifyDTO();
                sg.setContent(SecretStringGenerator.toString(object));
                BaseResponse res = sendPost(ied.getSignUrl(), sg);
                if (ValidateUtil.isNotNull(res) && ValidateUtil.isNotNull(res.getData())) {
                    System.out.println("签名结果:" + res.getData().toString());
                    return res.getData().toString();
                }
                return  null;
            }
            return  null;
        } else return null;
    }

    @Override
    public Boolean signVerify(InterfaceEncDecDTO ied, Boolean verify, Object object, String sign) {
        if (verify) {
            if (ValidateUtil.isNotEmpty(ied.getVerifyUrl())) {
                SignAndVerifyDTO sg = new SignAndVerifyDTO();
                sg.setContent(SecretStringGenerator.toString(object));
                sg.setSign(sign);
                BaseResponse res = sendPost(ied.getVerifyUrl(), sg);
                if (ValidateUtil.isNotNull(res) && ValidateUtil.isNotNull(res.getData())) {
                    System.out.println("验签结果:" + res.getData());
                    if (ValidateUtil.isNotNull(res.getData()) && !Boolean.parseBoolean(res.getData() + ""))
                        throw new JPMException(ResMsg.dataVerifyError);
                    else return true;
                }
            }
        } else return true;
        return false;
    }

    @Resource
    private RedisUtils redisUtils;

    /**
     * 组装POST请求参数
     *
     * @param object
     * @return
     */
    private HttpEntity<String> assemblyRequestParam(Object object) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        String token = redisUtils.getReqToken();
        if (ValidateUtil.isNotEmpty(token))
            headers.add("token", token);
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        log.info("服务调用时:" + JacksonUtils.toJSONString(object));
        HttpEntity<String> formEntity = new HttpEntity<>(JacksonUtils.toJSONString(object), headers);
        return formEntity;
    }
}

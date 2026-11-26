package com.jpmsoft.userProducer.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.model.request.SignAndVerifyDTO;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.SecretStringGenerator;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.pojo.dto.UsbKeyVerifyDTO;
import com.jpmsoft.userProducer.pojo.dto.sms.*;
import com.jpmsoft.userProducer.pojo.entity.SysUser;
import com.jpmsoft.userProducer.service.IApiUtilsService;
import com.jpmsoft.userProducer.service.ISysConfigEntityService;
import com.jpmsoft.userProducer.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Service
public class ApiUtilsImpl implements IApiUtilsService {

    @Resource(name = "restTemplate")
    private RestTemplate restTemplate;
    @Resource
    private ISysConfigEntityService sysConfigEntityService;
    @Resource
    private ISysUserService sysUserService;

    @Override
    public boolean checkPassword(String passWord, String salt, String account, InterfaceEncDecDTO ied, String userPwd) {
        String pwd = PasswrodEncryptionResult(passWord, salt, account);
        if (ValidateUtil.isEmpty(pwd)) return false;
        if (pwd.equals(secretDecrypt(ied, userPwd))) return true;
        return false;
    }

    @Override
    public String PasswrodEncryptionResult(InterfaceEncDecDTO ied, String passWord, String salt, String account) {
        String pwd = PasswrodEncryptionResult(passWord, salt, account);
        return secretEncrypt(ied, pwd);
    }

    @Override
    public String PasswrodEncryptionResult(String passWord, String salt, String account) {
        String rule = sysConfigEntityService.getSysConfigByCode("pwdEncryptionRule");
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
        InterfaceEncDecDTO cnf = sysConfigEntityService.getInterfaceEncDec();
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
    public String SelfSm2Decrypt(String encDataHex) {
        if (ValidateUtil.isEmpty(encDataHex) || encDataHex.equals("null")) return null;
        String url = "http://api-utils/v1/decrypt/SelfSm2Decrypt?encDataHex=" + encDataHex;
        String data = restTemplate.getForObject(url, String.class);
        return data;
    }

    @Override
    public String SelfSm2Encryption(String json) {
        try {
            if (ValidateUtil.isEmpty(json) || json.equals("null")) return null;
            String url = "http://api-utils/v1/decrypt/SelfSm2Encryption?json=" + URLEncoder.encode(json, "UTF-8");
            String data = restTemplate.getForObject(url, String.class);
            return data;
        } catch (Exception ex) {
            log.error("web传输加密失败！");
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public List<String> SelfSm2EncryptionBath(List<String> jsons) {
        if (CollectionUtils.isEmpty(jsons)) return null;
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(jsons);
            String url = "http://api-utils/v1/decrypt/SelfSm2EncryptionBath";
            List res = restTemplate.postForObject(url, formEntity, List.class);
            return res;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Boolean verificationSm2Sign(String encDataHex, String data) {
        try {
            String url = "http://api-utils/v1/decrypt/verificationSm2Sign?encDataHex=" + encDataHex + "&data=" + URLEncoder.encode(data, "UTF-8");
            Boolean response = restTemplate.getForObject(url, Boolean.class);
            return response;
        } catch (Exception ex) {
            throw new RuntimeException("服务内部错误！");
        }
    }

    @Override
    public String Sm2EncryptionGetUrl(String json) {
        try {
            String url = "http://api-utils/v1/decrypt/Sm2EncryptionGetUrl?json=" + URLEncoder.encode(json, "UTF-8");
            return restTemplate.getForObject(url, String.class);
        } catch (UnsupportedEncodingException ex) {
            return null;
        }
    }

    @Override
    public String Sm2Decrypt(String encDataHex) {
        String url = "http://api-utils/v1/decrypt/Sm2Decrypt?encDataHex=" + encDataHex;
        String data = restTemplate.getForObject(url, String.class);
        return data;
    }

    @Override
    public String Sm2EncryptionFront(String json) {
        try {
            String url = "http://api-utils/v1/decrypt/Sm2EncryptionFront?json=" + URLEncoder.encode(json, "UTF-8");
            return restTemplate.getForObject(url, String.class);
        } catch (UnsupportedEncodingException ex) {
            return null;
        }
    }

    @Override
    public BaseResponse pushUserPwd(Map<String, Object> data) {
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(data);
            String url = "http://run-bath/pushData/pushUserPwd";
            BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
            return baseResponse;
        } catch (Exception ex) {
            return new BaseResponse("");
        }
    }

    @Override
    public BaseResponse sendAliyunSms(AliyunSmsDto sms) {
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(sms);
            String url = "http://api-utils/v1/sms/sendAliyunSms";
            BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
            return baseResponse;
        } catch (Exception ex) {
            return BaseResponse.fail(BaseMsg.serverReqFail);
        }
    }

    @Override
    public BaseResponse sendCrcSms(CrcSmsDto sms) {
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(sms);
            String url = "http://api-utils/v1/sms/sendCrcSms";
            BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
            return baseResponse;
        } catch (Exception ex) {
            return BaseResponse.fail(BaseMsg.serverReqFail);
        }
    }

    @Override
    public BaseResponse sendHwSms(HwSmsDto sms) {
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(sms);
            String url = "http://api-utils/v1/sms/sendHwSms";
            BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
            return baseResponse;
        } catch (Exception ex) {
            return BaseResponse.fail(BaseMsg.serverReqFail);
        }
    }

    @Override
    public BaseResponse sendHwIotSms(HwSmsDto sms) {
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(sms);
            String url = "http://api-utils/v1/sms/sendHwIotSms";
            BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
            return baseResponse;
        } catch (Exception ex) {
            return BaseResponse.fail(BaseMsg.serverReqFail);
        }
    }

    @Override
    public BaseResponse sendSxSms(SxSmsDto sms) {
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(sms);
            String url = "http://api-utils/v1/sms/sendSxSms";
            BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
            return baseResponse;
        } catch (Exception ex) {
            return BaseResponse.fail(BaseMsg.serverReqFail);
        }
    }

    @Override
    public BaseResponse sendSubSms(HwSmsDto sms) {
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(sms);
            String url = "http://api-utils/v1/sms/sendSubSms";
            BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
            return baseResponse;
        } catch (Exception ex) {
            return BaseResponse.fail(BaseMsg.serverReqFail);
        }
    }

    @Override
    public BaseResponse sendGmSms(GmSmsWebServiceDto sms) {
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(sms);
            String url = "http://api-utils/v1/sms/sendGmSms";
            BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
            return baseResponse;
        } catch (Exception ex) {
            return BaseResponse.fail(BaseMsg.serverReqFail);
        }
    }

    @Override
    public BaseResponse sendPost(String url, Object data, Boolean refreshNo) {
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(data);
            if (ValidateUtil.isNotNull(refreshNo))
                url = url + "?refreshNo=" + refreshNo;
            BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
            return baseResponse;
        } catch (Exception ex) {
            return BaseResponse.fail(BaseMsg.serverReqFail);
        }
    }

    @Override
    public BaseResponse sendYdSms(YdSmsDto sms) {
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(sms);
            String url = "http://api-utils/v1/sms/sendYdSms";
            BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
            return baseResponse;
        } catch (Exception ex) {
            return BaseResponse.fail(BaseMsg.serverReqFail);
        }
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
                return null;
            }
            return null;
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
                        throw new JPMException(BaseMsg.dataVerifyError);
                    else return true;
                }
            }
        } else return true;
        return false;
    }

    @Override
    public String secretEncrypt(InterfaceEncDecDTO ied, String plainText) {
        if (ValidateUtil.isEmpty(plainText)) return plainText;
        if (ied.getEncrypt()) {
            try {
                String url = ied.getEncInterfaceUrl() + URLEncoder.encode(plainText, "UTF-8");
                BaseResponse data = restTemplate.getForObject(url, BaseResponse.class);
                if (data != null && ValidateUtil.isNotNull(data.getData())) return data.getData().toString();
            } catch (Exception ex) {
                log.error("密改加密调用加密方法失败！");
            }
        }
        return plainText;
    }

    @Override
    public String secretDecrypt(InterfaceEncDecDTO ied, String encryptedText) {
        if (ValidateUtil.isEmpty(encryptedText)) return encryptedText;
        if (ied.getEncrypt()) {
            try {
                String url = ied.getDecInterfaceUrl() + URLEncoder.encode(encryptedText, "UTF-8");
                BaseResponse data = restTemplate.getForObject(url, BaseResponse.class);
                if (data != null && ValidateUtil.isNotNull(data.getData())) return data.getData().toString();
            } catch (Exception ex) {
                log.error("密改解密调用解密方法失败！");
            }
        }
        return encryptedText;
    }

    @Override
    public boolean verifyUkey(String userId, String plaintext, String signature) {
        SysUser user = sysUserService.getById(userId);
        if (user == null) return false;
        if (ValidateUtil.isEmpty(user.getuKey())) return false;
        UsbKeyVerifyDTO verify = new UsbKeyVerifyDTO();
        verify.setPlaintext(plaintext);
        verify.setSignature(signature);
        verify.setUserId(userId);
        verify.setPublicKey(user.getuKeyPubKey());
        verify.setKeyType(sysConfigEntityService.getUkeyType());
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(verify);
            return restTemplate.postForObject("http://api-utils/v1/decrypt/verifyUkey", formEntity, Boolean.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public BaseResponse sendPost(String url, Object data) {
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(data);
            BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
            return baseResponse;
        } catch (Exception ex) {
            return BaseResponse.fail("服务内部调用失败！");
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
        HttpEntity<String> formEntity = new HttpEntity<>(JacksonUtils.toJSONString(object), headers);
        return formEntity;
    }

    private HttpEntity<String> assemblyRequestParam(Object object, String token) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("token", token);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<>(JacksonUtils.toJSONString(object), headers);
        return formEntity;
    }
}

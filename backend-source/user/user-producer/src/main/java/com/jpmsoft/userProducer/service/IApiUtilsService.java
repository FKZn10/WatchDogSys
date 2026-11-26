package com.jpmsoft.userProducer.service;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.userProducer.pojo.dto.sms.*;

import java.util.List;
import java.util.Map;

public interface IApiUtilsService {

    /**
     * 校验密码
     *
     * @param passWord 密码明文
     * @param salt     盐值
     * @param account  账户
     * @param ied      密改配置
     * @param userPwd  用户存储数据库密码
     * @return
     */
    boolean checkPassword(String passWord, String salt, String account, InterfaceEncDecDTO ied, String userPwd);

    /**
     * 密码自有加密后使用密改密码机再次加密
     *
     * @param ied
     * @param passWord
     * @param salt
     * @param account
     * @return
     */
    String PasswrodEncryptionResult(InterfaceEncDecDTO ied, String passWord, String salt, String account);

    /**
     * 密码盐值获取加密结果
     *
     * @param passWord
     * @param salt
     * @param account
     * @return
     */
    String PasswrodEncryptionResult(String passWord, String salt, String account);

    /**
     * 华润SHA-256密码加密
     * <p>
     * SHA-256("CRCLDAP+" + LDAP账户名.toUpperCase(Locale.ENGLISH) + "+" + SHA-256("密码").toUpperCase(Locale.ENGLISH)).toUpperCase(Locale.ENGLISH)
     *
     * @param account
     * @param pwd
     * @return
     */
    String crcSha256Encryption(String account, String pwd);

    /**
     * web传输数据 解密字段
     * 前端加密后端解密
     *
     * @param encDataHex
     * @return
     */
    String SelfSm2Decrypt(String encDataHex);

    /**
     * web传输加密  后端加密前端解密
     *
     * @param json
     * @return
     */
    String SelfSm2Encryption(String json);

    /**
     * web传输加密  后端加密前端解密
     * 批量
     *
     * @param jsons
     * @return
     */
    List<String> SelfSm2EncryptionBath(List<String> jsons);

    /**
     * 验签
     *
     * @param encDataHex sign
     * @param data       sign明文
     * @return
     */
    Boolean verificationSm2Sign(String encDataHex, String data);


    /**
     * 对接返回数据时加密
     * 例：第三方登录加密token
     *
     * @param json
     * @return
     */
    String Sm2EncryptionGetUrl(String json);

    /**
     * 对接数据解密
     *
     * @param encDataHex
     * @return
     */
    String Sm2Decrypt(String encDataHex);

    /**
     * 使用前端web用的公钥进行加密
     *
     * @return
     */
    String Sm2EncryptionFront(String json);

    /**
     * 修改密码推送
     *
     * @param data
     * @return
     */
    BaseResponse pushUserPwd(Map<String, Object> data);


    /**
     * 发送阿里云短信
     * 单条
     *
     * @param sms
     * @return
     */
    BaseResponse sendAliyunSms(AliyunSmsDto sms);


    BaseResponse sendCrcSms(CrcSmsDto sms);

    /**
     * 华为云短信
     *
     * @param sms
     * @return
     */
    BaseResponse sendHwSms(HwSmsDto sms);

    /**
     * 华为IOT短信发送
     *
     * @param sms
     * @return
     */
    BaseResponse sendHwIotSms(HwSmsDto sms);

    /**
     * 苏相短信发送
     *
     * @param sms
     * @return
     */
    BaseResponse sendSxSms(SxSmsDto sms);

    /**
     * submail短信发送
     *
     * @param sms
     * @return
     */
    BaseResponse sendSubSms(HwSmsDto sms);

    /**
     * 光明短信发送
     *
     * @param sms
     * @return
     */
    BaseResponse sendGmSms(GmSmsWebServiceDto sms);

    BaseResponse sendPost(String url, Object data, Boolean refershNo);


    /**
     * 发送移动短信
     *
     * @param sms
     * @return
     */
    BaseResponse sendYdSms(YdSmsDto sms);


    /**
     * 签名
     *
     * @param ied    基础配置
     * @param sign   是否生成签名
     * @param object 实体类对象@SecretField注解自动生成明文
     * @return
     */
    String sign(InterfaceEncDecDTO ied, Boolean sign, Object object);

    /**
     * 签名验证
     *
     * @param ied    基础配置
     * @param verify 是否验证签名
     * @param object 实体类对象@SecretField注解自动生成明文
     * @param sign   签名密文
     * @return
     */
    Boolean signVerify(InterfaceEncDecDTO ied, Boolean verify, Object object, String sign);


    /**
     * 密改加密
     *
     * @param plainText 明文
     * @return
     */
    String secretEncrypt(InterfaceEncDecDTO ied, String plainText);

    /**
     * 密改解密
     *
     * @param encryptedText 密文
     * @return
     */
    String secretDecrypt(InterfaceEncDecDTO ied, String encryptedText);

    boolean verifyUkey(String userId, String plaintext, String signature);
}

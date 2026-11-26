package com.jpmsoft.userProducer.service.impl;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.model.requestAndResponse.Sms.BlSmsSendRequest;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.common.utils.VoInfoUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.enums.SmsPlatformEnum;
import com.jpmsoft.userProducer.feign.DockFeign;
import com.jpmsoft.userProducer.pojo.dto.sms.*;
import com.jpmsoft.userProducer.pojo.entity.SysShortMessageLog;
import com.jpmsoft.userProducer.pojo.entity.SysSmsConf;
import com.jpmsoft.userProducer.pojo.entity.SysUser;
import com.jpmsoft.userProducer.pojo.vo.LogUserInfoVo;
import com.jpmsoft.userProducer.service.*;
import com.jpmsoft.userProducer.utils.RedisUtils;
import com.jpmsoft.userProducer.utils.VerifiactionCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Service
public class SmsServerImpl implements ISmsServerService {
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysConfigEntityService sysConfigEntityService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private ISysShortMessageLogService sysShortMessageLogService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ISysSmsConfService sysSmsConfService;
    @Resource
    private DockFeign dockFeign;

    @Override
    public BaseResponse sendCodeSms(String phoneNumber, String type) {
        return sendCodeSms(phoneNumber, type,null, true);
    }

    @Override
    public BaseResponse sendCodeSms(String phoneNumber, String type,String tempCode,boolean checkUser) {
        SysUser user = new SysUser();
        if (checkUser) {
            user = sysUserService.getUserInfoByTel(phoneNumber);
            if (user == null)
                return BaseResponse.fail(BaseMsg.smsSendFail);
        } else {
            user.setuMtel(phoneNumber);
            user.setuId(GenerateUUID.getUUID());
            user.setuName("非系统用户");
        }
        SysSmsConf temp = sysSmsConfService.getCodeSms(tempCode);
        if (temp == null)
            return BaseResponse.fail(BaseMsg.unSmsConf);
        SmsTimeConfDto timeConf = sysConfigEntityService.getSmsTimeConf();
        // 校验发送时间，短时间内同电话号码不允许重复发送
        if (!can2SendCode(phoneNumber, type))
            return BaseResponse.fail(BaseMsg.codeSendAlready);
        //region 通过配置选择发送平台 调用不同的短信发送接口
        BaseResponse res = null;
        switch (Objects.requireNonNull(SmsPlatformEnum.getEnum(temp.getSscPlatform()))) {
            //阿里云平台
            case Aliyun:
                res = sendAliyunCodeSms(user, temp, getSmsCode(phoneNumber, type, timeConf), type);
                break;
            case Huawei:
                res = sendHwCodeSms(user, temp, getSmsCode(phoneNumber, type, timeConf), type, SmsPlatformEnum.Huawei.getkey());
                break;
            case HwIot:
                res = sendHwCodeSms(user, temp, getSmsCode(phoneNumber, type, timeConf), type, SmsPlatformEnum.HwIot.getkey());
                break;
            case Sxsms:
                res = sendSxCodeSms(user, temp, getSmsCode(phoneNumber, type, timeConf), type);
                break;
            case CrcSms:
                res = sendCrcCodeSms(user, temp, getSmsCode(phoneNumber, type, timeConf), type);
                break;
            case YdSms:
                res = sendYdCodeSms(user, temp, getSmsCode(phoneNumber, type, timeConf), type);
                break;
            case SubSms:
                res = senSubCodeSms(user, temp, getSmsCode(phoneNumber, type, timeConf), type);
                break;
            case BlSms:
                res = sendBlCodeSms(user, temp, getSmsCode(phoneNumber, type, timeConf), type);
                break;
            default:
                res = BaseResponse.fail(BaseMsg.unSupportSmsPlatform);
                break;
        }
        if (res != null && res.getSuccess().equals(Boolean.TRUE) && res.getStatusCode() == HttpStatus.SC_OK) {
            //电话号码后四位
            return BaseResponse.ok(BaseMsg.smsSendSuccess, VoInfoUtil.phoneHandle(phoneNumber));
        }
        //endregion
        return res;
    }

    @Override
    public BaseResponse sendCodeSmsByToken(LogUserInfoVo user, String type) {
        SysUser u = sysUserService.getById(user.getId());
        if (u != null) {
            if (ValidateUtil.isNotEmpty(u.getuMtel()))
                return sendCodeSms(u.getuMtel(), type);
            else return BaseResponse.fail(BaseMsg.ukonwPhoneNum);
        }
        return BaseResponse.fail(BaseMsg.smsSendFail);
    }


    @Override
    public boolean checkSmsCode(String phoneNumber, String type, String code) {
        //redis 短信验证码 key
        String key = "Sms_" + phoneNumber + "_" + type + "_" + code;
        //redis 手机号锁定
        String locked = "Sms_Lock_" + phoneNumber + "_" + type;
        if (redisUtils.get(key) != null) {
            //校验通过删除缓存
            redisUtils.delete(key, locked);
            return true;
        }
        return false;
    }

    /**
     * 发送华润验证码短信
     *
     * @param user 用户信息
     * @param temp
     * @param code
     * @return
     */
    public BaseResponse sendCrcCodeSms(SysUser user, SysSmsConf temp, String code, String type) {
        CrcSmsDto sms = new CrcSmsDto();
        sms.setUrl(temp.getSscUrl());
        sms.setTmpCode(temp.getSscTempCode());
        sms.setPhoneNumber(user.getuMtel());
        sms.setSignName(temp.getSscSign());
        sms.setAccessKey(temp.getSscAccess());
        sms.setKeySecret(temp.getSscSecret());
        sms.setRegion(temp.getSscRegion());
        sms.setContent(codeContent(code, temp.getSscJson()));
        sms.setSubmiter(temp.getSscSubmiter());
        sms.setTenantId(temp.getSscTenant());
        //发送短信
        BaseResponse res = apiUtilsService.sendCrcSms(sms);
        createSmsLog(user, res, sms, type, SmsPlatformEnum.CrcSms.getName());
        res.setData(null);
        return res;
    }

    /**
     * 发送移动验证码短信
     *
     * @param user 用户信息
     * @param temp
     * @param code
     * @return
     */
    public BaseResponse sendYdCodeSms(SysUser user, SysSmsConf temp, String code, String type) {
        YdSmsDto sms = new YdSmsDto();
        sms.setUrl(temp.getSscUrl());
        sms.setContent(codeContent(code, temp.getSscContent()));
        sms.setSign(temp.getSscSign());
        sms.setPhone(user.getuMtel());
        sms.setEcName(temp.getSscRemark());
        sms.setUserName(temp.getSscAccess());
        sms.setUserPwd(temp.getSscSecret());
        //发送短信
        BaseResponse res = apiUtilsService.sendYdSms(sms);
        createSmsLog(user, res, sms, type, SmsPlatformEnum.YdSms.getName());
        res.setData(null);
        return res;
    }

    /**
     * 发送阿里云验证码短信
     *
     * @param user 用户信息
     * @param temp
     * @param code
     * @return
     */
    public BaseResponse sendAliyunCodeSms(SysUser user, SysSmsConf temp, String code, String type) {
        AliyunSmsDto sms = new AliyunSmsDto();
        sms.setTmpCode(temp.getSscTempCode());
        sms.setPhoneNumber(user.getuMtel());
        sms.setSignName(temp.getSscSign());
        sms.setAccessKey(temp.getSscAccess());
        sms.setKeySecret(temp.getSscSecret());
        sms.setRegion(temp.getSscRegion());
        sms.setContent(codeContent(code));
        //发送短信
        BaseResponse res = apiUtilsService.sendAliyunSms(sms);
        createSmsLog(user, res, sms, type, SmsPlatformEnum.Aliyun.getName());
        res.setData(null);
        return res;
    }

    /**
     * 苏相发送短信
     *
     * @param user
     * @param temp
     * @param code
     * @param type
     * @param hwSmsType SmsPlatformEnum
     * @return
     */
    public BaseResponse sendHwCodeSms(SysUser user, SysSmsConf temp, String code, String type, String hwSmsType) {
        HwSmsDto sms = new HwSmsDto();
        sms.setTmpCode(temp.getSscTempCode());
        sms.setPhoneNumber(user.getuMtel());
        sms.setSignName(temp.getSscSign());
        sms.setAccessKey(temp.getSscAccess());
        sms.setKeySecret(temp.getSscSecret());
        sms.setRegion(temp.getSscRegion());
        sms.setUrl(temp.getSscUrl());
        BaseResponse res = BaseResponse.fail();
        if (hwSmsType.equals(SmsPlatformEnum.Huawei.getkey())) {
            sms.setContent(JacksonUtils.toJSONString(Collections.singletonList(code)));
            res = apiUtilsService.sendHwSms(sms);
        } else if (hwSmsType.equals(SmsPlatformEnum.HwIot.getkey())) {
            Map<String, String> content = new HashMap<>();
            content.put("code", code);
            sms.setContent(JacksonUtils.toJSONString(content));
            res = apiUtilsService.sendHwIotSms(sms);
        }
        //发送短信
        createSmsLog(user, res, sms, type, SmsPlatformEnum.Huawei.getName());
        res.setData(null);
        return res;
    }

    /**
     * 苏相发送短信
     *
     * @param user
     * @param temp
     * @param code
     * @param type
     * @return
     */
    public BaseResponse sendSxCodeSms(SysUser user, SysSmsConf temp, String code, String type) {
        SxSmsDto sms = new SxSmsDto();
        sms.setSscUrl(temp.getSscUrl());
        sms.setSscRegion(temp.getSscRegion());
        sms.setSscSign(temp.getSscSign());
        sms.setSscTempCode(temp.getSscTempCode());
        sms.setSalt(temp.getSscAccess());
        sms.setTempParam(codeContent(code));
        sms.setPhoneNumber(user.getuMtel());
        //发送短信
        BaseResponse res = apiUtilsService.sendSxSms(sms);
        createSmsLog(user, res, sms, type, sms.getSscUrl());
        res.setData(null);
        return res;
    }


    /**
     * submail发送短信
     *
     * @return
     */
    public BaseResponse senSubCodeSms(SysUser user, SysSmsConf temp, String code, String type) {
        HwSmsDto sms = new HwSmsDto();
        sms.setTmpCode(temp.getSscTempCode());
        sms.setPhoneNumber(user.getuMtel());
        sms.setSignName(temp.getSscSign());
        sms.setAccessKey(temp.getSscAccess());
        sms.setKeySecret(temp.getSscSecret());
        sms.setRegion(temp.getSscRegion());
        sms.setUrl(temp.getSscUrl());
        sms.setContent(codeContent(code, temp.getSscContent()));
        BaseResponse res = apiUtilsService.sendSubSms(sms);
        //发送短信
        createSmsLog(user, res, sms, type, SmsPlatformEnum.SubSms.getName());
        res.setData(null);
        return res;
    }

    /**
     * 发送北林短信
     *
     * @param user
     * @param smsConf
     * @param code
     * @param type
     * @return
     */
    private BaseResponse sendBlCodeSms(SysUser user, SysSmsConf smsConf, String code, String type) {
        BlSmsSendRequest sms = new BlSmsSendRequest();
        sms.setUrl(smsConf.getSscUrl());
        // 授权系统名称
        sms.setTpName(smsConf.getSscSign());
        // 使用短信配额的机构id
        sms.setOperatorUnitId(smsConf.getSscAccess());
        // 密钥
        sms.setSecretKey(smsConf.getSscSecret());

        // 拼接接收人信息
        // 接收人姓名
        String smUname = user.getuName();
        // 接收人手机号
        String smMobilePhone = user.getuMtel();

        String personInfo;
        // 如果手机号是8位数字，则认为是北林内部的学工号
        if (ValidateUtil.isNotEmpty(smMobilePhone) && smMobilePhone.matches("\\d{8}")) {
            personInfo = smUname + "|" + smMobilePhone + "|||";
        } else {
            personInfo = smUname + "||||" + smMobilePhone;
        }

        // 接收人信息,每个人员信息格式为：名字|学工号|部门ID|部门名称|电话号码，人与人之间用“^@^”隔开（如果数据无法提供，可以写空，但是“|”不可省略）
        sms.setPersonInfo(personInfo);

        sms.setSmsInfo(code);
        sms.setSendPriority("3");
        sms.setTempletId("0");
        sms.setReceiptId("0");
        sms.setSendSysId("1");
        // 调用接口的系统厂商名称
        sms.setSendSysName(smsConf.getSscRegion());
        BaseResponse res = dockFeign.sendBlSms(sms);
        createSmsLog(user, res, sms, type, SmsPlatformEnum.BlSms.getName());
        res.setData(null);
        dockFeign.destroyResources();
        return res;
    }

    private void createSmsLog(SysUser user, BaseResponse res, Object sms, String type, String url) {
        //发送后生成记录
        Date now = new Date();
        SysShortMessageLog log = new SysShortMessageLog();
        log.setSmlMobilePhone(user.getuMtel());
        log.setSmlUid(user.getuId());
        log.setSmlUname(user.getuName());
        log.setSmlContent(JacksonUtils.toJSONString(sms));
        log.setSmlCreateTime(now);
        log.setSmlCreateUid(user.getuId());
        log.setSmlCreateUname(user.getuName());
        log.setSmlSendTime(now);
        log.setSmlSendUrl(url);
        log.setSmlSendResult("0");
        if (res.getSuccess() && res.getStatusCode() == HttpStatus.SC_OK)
            log.setSmlSendResult("ok");
        else { //发送失败
            String locked = "Sms_Lock_" + user.getuMtel() + "_" + type;
            if (redisUtils.get(locked) != null)
                redisUtils.delete(redisUtils.get(locked).toString(), locked);
            else redisUtils.delete(locked);
        }
        log.setSmlSendResultMsg(JacksonUtils.toJSONString(res.getData()));
        log.setSmlId(GenerateUUID.getUUID());
        InterfaceEncDecDTO encDec = sysConfigEntityService.getInterfaceEncDec();
        log.setSign(apiUtilsService.sign(encDec, encDec.getSmsSign(), log));
        sysShortMessageLogService.save(log);
    }

    /**
     * 生成验证码
     * 暂存redis
     *
     * @param phoneNumber
     * @param codeType
     * @return
     */
    public String getSmsCode(String phoneNumber, String codeType, SmsTimeConfDto conf) {
        //6位数验证码
        int code = VerifiactionCodeUtil.code();
        //redis 短信验证码 key
        String key = "Sms_" + phoneNumber + "_" + codeType + "_" + code;
        //redis 手机号锁定
        String locked = "Sms_Lock_" + phoneNumber + "_" + codeType;
        //验证码存入缓存中
        redisUtils.set(key, code, conf.getPrescription());
        redisUtils.set(locked, key, conf.getInterval());
        return code + "";
    }


    /**
     * 校验手机号是否可发送短信
     *
     * @param phoneNumber
     * @param codeType
     * @return
     */
    private boolean can2SendCode(String phoneNumber, String codeType) {
        //redis 手机号锁定
        String locked = "Sms_Lock_" + phoneNumber + "_" + codeType;
        if (redisUtils.get(locked) != null)
            return false;
        return true;
    }

    private String codeContent(String code) {
        Map<String, String> content = new HashMap<>();
        content.put("code", code);
        return JacksonUtils.toJSONString(content);
    }

    private String codeContent(String code, String temp) {
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        StringSubstitutor sub = new StringSubstitutor(map);
        return sub.replace(temp);
    }
}

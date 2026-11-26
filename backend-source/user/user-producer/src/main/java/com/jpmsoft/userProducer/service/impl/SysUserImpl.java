package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.UserTypeEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.model.request.SignAndVerifyDTO;
import com.jpmsoft.common.model.response.ThirdParty.ThirdBaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.common.utils.VoInfoUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.enums.ChannelTypeEnum;
import com.jpmsoft.userProducer.enums.CompanyTypeEnum;
import com.jpmsoft.userProducer.enums.ThirdPartyAppIdEnum;
import com.jpmsoft.userProducer.mapper.SysPositionRoleMapper;
import com.jpmsoft.userProducer.mapper.SysUserMapper;
import com.jpmsoft.userProducer.pojo.dto.*;
import com.jpmsoft.userProducer.pojo.dto.sms.SmsUpdatePwdDto;
import com.jpmsoft.userProducer.pojo.dto.sms.UserSmsLoginDto;
import com.jpmsoft.userProducer.pojo.entity.SysCompany;
import com.jpmsoft.userProducer.pojo.entity.SysDepartment;
import com.jpmsoft.userProducer.pojo.entity.SysUser;
import com.jpmsoft.userProducer.pojo.vo.LogInfoVo;
import com.jpmsoft.userProducer.pojo.vo.LogUserInfoVo;
import com.jpmsoft.userProducer.pojo.vo.SysUserVo;
import com.jpmsoft.userProducer.pojo.vo.UserPDPVo;
import com.jpmsoft.userProducer.rabbitMQ.Sender;
import com.jpmsoft.userProducer.service.*;
import com.jpmsoft.userProducer.utils.CheckPwdUtil;
import com.jpmsoft.userProducer.utils.DateUtil;
import com.jpmsoft.userProducer.utils.RedisUtils;
import com.jpmsoft.userProducer.utils.UserStatisticsUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysUserImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Resource
    private ISysPowerService sysPowerService;

    @Resource
    private ISysPositionRoleService sysPositionRoleService;

    @Resource
    private ISysUserPwdChangeService sysUserPwdChangeService;

    @Resource
    private ISysCompanyService sysCompanyService;

    @Resource
    private ISysDepartmentService sysDepartmentService;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private IApiUtilsService apiUtilsService;

    @Resource
    private ISysConfigEntityService sysConfigEntityService;

    @Resource
    private ISysSequenceSetService sysSequenceSetService;

    @Resource
    private IOaAttendanceGroupService oaAttendanceGroupService;

    @Resource
    private ISysLoginLogService sysLoginLogService;

    @Resource
    private Sender sender;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private UserStatisticsUtil userStatisticsUtil;
    @Resource
    private ISmsServerService smsServerService;
    @Resource
    private ISysPwdDictionaryService sysPwdDictionaryService;
    @Resource
    private ISysTenantRuleService sysTenantRuleService;
    //北京触发推送
    @Value("${spring.profiles.active}")
    private String activeValue;
    @Resource
    private SysPositionRoleMapper sysPositionRoleMapper;

    @Override
    public BaseResponse login(UserDto user, String sessionId, boolean isBack, HttpServletRequest request) {
        checkSessionCode(user, sessionId, isBack);
        return loginType(user, "web-Account", isBack, request);
    }


    @Override
    public BaseResponse backLogin(UserDto user, String sessionId, boolean isBack, HttpServletRequest request) {
        if (ValidateUtil.isEmpty(user.getSmsCode())) {
            checkSessionCode(user, sessionId, isBack);
            user.setSendSms(true);
            return loginType(user, "web-Sms", isBack, request);
        } else {
            if (ValidateUtil.isAnObjEmpty(user.getuAccount(), user.getuPassword()))
                return BaseResponse.fail(BaseMsg.notActPwd);
            SysUser pojo = getUserByAccount(user.getuAccount());
            if (pojo == null) return BaseResponse.fail(BaseMsg.actPwdError);
            if (!smsServerService.checkSmsCode(pojo.getuMtel(), "login", user.getSmsCode()))
                return BaseResponse.fail(BaseMsg.smsCodeInvalid);
            return loginMain(user, pojo, "web-Account", isBack, 1, request);
        }
    }

    @Override
    public BaseResponse loginSms(UserSmsLoginDto user, boolean isBack, HttpServletRequest request) {
        if (!smsServerService.checkSmsCode(user.getPhoneNumber(), "login", user.getCode()))
            return BaseResponse.fail(BaseMsg.smsCodeInvalid);
        SysUser userInfo = getUserInfoByTel(user.getPhoneNumber());
        if (userInfo == null) return BaseResponse.fail(BaseMsg.smsSendFail);
        user.setuAccount(userInfo.getuAccount());
        return loginMain(user, userInfo, user.getLoginType() + "-Sms", isBack, 0, request);
    }

    @Override
    public BaseResponse usbKeyLogin(UsbKeyLoginDTO info, String sessionId, boolean isBack, HttpServletRequest request) {
        UserDto user = new UserDto();
        user.setAuthCode(info.getAuthCode());
        //校验验证码
        checkSessionCode(user, sessionId, isBack);
        //校验usbkey
        user.setuAccount(info.getAccount());
        user.setuPassword(info.getPassWord());
        //校验usbkey标识
        checkUsbKey(info.getAccount(), info.getKeyId());
        return loginType(user, "web-Account", isBack, request);
    }

    @Override
    public BaseResponse loginType(UserDto user, String type, boolean isBack, HttpServletRequest request) {
        if (ValidateUtil.isAnObjEmpty(user.getuAccount(), user.getuPassword()))
            return BaseResponse.fail(BaseMsg.notActPwd);
        SysUser pojo = getUserByAccount(user.getuAccount());
        if (pojo == null) return BaseResponse.fail(BaseMsg.actPwdError);
        return loginMain(user, pojo, type, isBack, 1, request);
    }

    @Override
    public LogUserInfoVo getLogUserInfoById(String uid) {
        SysUser pojo = baseMapper.selectById(uid);
        if (pojo == null) return null;
        LogInfoVo logInfo = new LogInfoVo();
        logInfo = sysPositionRoleService.setUserRoleNameAndBtnRole(logInfo, pojo);
        LogUserInfoVo userInfo = new LogUserInfoVo();
        Map<String, String> conf = sysConfigEntityService.getConfigByCodes(Arrays.asList("loginLockTime", "loginRetryCount", "authorizeButton", "tenantModeConfig"), null);
        userInfo.setName(pojo.getuName()).setId(pojo.getuId()).setCode(pojo.getuAccount()).setUType(pojo.getuType()).setTeId(pojo.getTeId())
                .setUFirstLogin(0).setAttendanceGroup(oaAttendanceGroupService.getOaAttendanceGroupByUid(pojo.getuId()))
                .setPhone(VoInfoUtil.phoneHandle(pojo.getuMtel())).setRoleNames(logInfo.getRoles());
        //是否开启租户模式
        if (ValidateUtil.isNotEmpty(conf.getOrDefault("tenantModeConfig", null))) {
            TenantModeConfigDTO tenantConf = JacksonUtils.parseObject(conf.get("tenantModeConfig"), TenantModeConfigDTO.class);
            if (tenantConf != null) userInfo.setEnableTenant(tenantConf.isEnable());
        }
        screenParams(pojo, userInfo);
        userInfo.setConTables(baseMapper.getAllHaveContractTable());
        return userInfo;
    }

    @Override
    public BaseResponse clearPassWordRetry(String account, String uId) {
        SysUser user = baseMapper.selectById(uId);
        if (user == null) return BaseResponse.fail(org.apache.http.HttpStatus.SC_UNAUTHORIZED, BaseMsg.tokenInvalid);
        if (!user.getuAccount().equals("admin") && !user.getuAccount().equals("jpmadmin"))
            return BaseResponse.fail(BaseMsg.notAuthUser);
        redisUtils.delete("loginRetryCount_" + account.trim().toLowerCase(Locale.ENGLISH));
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse thirdPartLogin(String token, HttpServletRequest request) {
        Object redisData = redisUtils.get("OtherAuth_" + token);
        if (redisData == null) return BaseResponse.fail(BaseMsg.thirdTokenInvalid);
        Map<String, String> datas = JacksonUtils.parseObject(redisData.toString(), Map.class);
        redisUtils.delete("OtherAuth_" + token);
        String uId = datas.get("uId");
        SysUser pojo = baseMapper.selectById(uId);
        if (pojo == null) return BaseResponse.fail(BaseMsg.notUserInfo + uId);
        if (pojo.getuState().equals("0")) return BaseResponse.fail(BaseMsg.actIsDisable);
        //校验用户是否首次登录，密码是否超过使用时间
        Integer userFirstLog = checkPwdOverdue(pojo);
        String RandKey = GenerateUUID.getToekn();
        LogInfoVo logInfo = new LogInfoVo();
        logInfo.setToken(RandKey);
        Map<String, String> configs = sysConfigEntityService.getConfigByCodes(Arrays.asList("authorizeButton", "tenantModeConfig"), null);
        String authorizeButton = configs.getOrDefault("authorizeButton", null);
        if (ValidateUtil.isNotEmpty(authorizeButton) && authorizeButton.equals("1")) logInfo.setAuthorizeBtn(true);
        logInfo = sysPositionRoleService.setUserRoleNameAndBtnRole(logInfo, pojo);
        LogUserInfoVo userInfo = new LogUserInfoVo();
        userInfo.setName(pojo.getuName()).setId(pojo.getuId()).setCode(pojo.getuAccount()).setUType(pojo.getuType()).setUFirstLogin(userFirstLog)
                .setAttendanceGroup(oaAttendanceGroupService.getOaAttendanceGroupByUid(pojo.getuId())).setTeId(pojo.getTeId()).setRoleNames(logInfo.getRoles());
        //是否开启租户模式
        if (ValidateUtil.isNotEmpty(configs.getOrDefault("tenantModeConfig", null))) {
            TenantModeConfigDTO tenantConf = JacksonUtils.parseObject(configs.get("tenantModeConfig"), TenantModeConfigDTO.class);
            if (tenantConf != null) userInfo.setEnableTenant(tenantConf.isEnable());
        }
        screenParams(pojo, userInfo);
        logInfo.setUser(userInfo);
        Date dt = new Date();
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        //中国时区+8  +4小时延后
        rightNow.add(Calendar.HOUR, +12);
        Date expireAt = rightNow.getTime();
        logInfo.setExpireAt(expireAt);
        //todo: 第三方登录是否需要区分web和app端 待定。
        Object oldRandK = redisUtils.get(pojo.getuId());
        if (oldRandK != null)
            redisUtils.delete(oldRandK.toString(), "lastActionTime_" + oldRandK, "firstLogin_" + oldRandK);
        redisUtils.set(pojo.getuId(), RandKey);
        redisUtils.set(RandKey, pojo.getuId());
        //表单关联有con_id的表名
        userInfo.setConTables(baseMapper.getAllHaveContractTable());
        //第三方登录无虚再验证短信
        userInfo.setUCheckSms(0);
        userInfo = sysTenantRuleService.setRedis(userInfo);
        redisUtils.set("userLoginInfo_" + pojo.getuId(), JacksonUtils.toJSONString(userInfo));
        redisUtils.set("firstLogin_" + RandKey, userFirstLog);
        redisUtils.set("lastActionTime_" + RandKey, DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
        //sysLoginLogService.create(request, pojo.getuId(), "third");
        return BaseResponse.ok(BaseMsg.logSuccess, logInfo);
    }

    @Override
    public SysUser getUserByAccountAndPassword(UserDto user) {
        if (ValidateUtil.isEmpty(user.getuAccount()) || ValidateUtil.isEmpty(user.getuPassword())) {
            return null;
        }
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("U_Account", user.getuAccount().trim());
        SysUser pojo = baseMapper.selectOne(queryWrapper);
        if (apiUtilsService.checkPassword(user.getuPassword(), pojo.getuSalt(), user.getuAccount()
                , sysConfigEntityService.getInterfaceEncDec(), pojo.getuPassword()))
            return pojo;
        else return null;
    }

    @Override
    public BaseResponse routes(String powerCode, String token) {
        return sysPowerService.getVueRoute(powerCode, redisUtils.getTeId(token));
    }

    @Override
    public BaseResponse GetUserList(UserDto user, String redisUId, boolean sensitive) {
        SysUser actionUsr = baseMapper.selectById(redisUId);
        QueryMap<UserDto> queryMap = new QueryMap<>(user);
        queryMap.put("uState").put("uName").put("uType").put("cId").put("cName").put("uIdList")
                .put("plId").put("uAccount").put("uCode").put("uMtel").put("uId").put("uMdmNo")
                .put("pdCode").put("pdType").put("projectId")
                .put("timeStart", ValidateUtil.isEmpty(user.getStart()) ? null : DateUtil.String2Date(user.getStart(), "yyyy-MM-dd"));
        if (ValidateUtil.isEmpty(user.getEnd())) {
            queryMap.put("timeEnd", null);
        } else {
            Date timeEnd = DateUtil.DateChangeDay(DateUtil.String2Date(user.getEnd(), "yyyy-MM-dd"), 1);
            queryMap.put("timeEnd", timeEnd);
        }
        List<SysUserVo> sysUserPojoIPage = baseMapper.GetUserList(queryMap.getQuery());
        Integer total = baseMapper.GetUserListCount(queryMap.getQuery());
        String conf = sysConfigEntityService.getSysConfigByCode("userInfoEncryptionSend");
        List<String> datas = new ArrayList<>();
        List<SysUserVo> vos = new ArrayList<>();
        if (total != null && total > 0) {
            InterfaceEncDecDTO ied = sysConfigEntityService.getInterfaceEncDec();
            sysUserPojoIPage.forEach(data -> {
                if (!userSignVerify(ied, ied.getUserListVerify(), data.signContent(), data.getSign()))
                    throw new JPMException(BaseMsg.userVerifyError);
                data.setSign(null);
                if (sensitive) {
                    data.setuIdcard(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(decIdcard(ied, data.getuIdcard()))));
                    data.setuMtel(VoInfoUtil.phoneHandle(data.getuMtel()));
                    if (!actionUsr.getuAccount().equals("admin") && !actionUsr.getuAccount().equals("jpmadmin"))
                        data.setuPassword(null);
                    data.setuPhone(VoInfoUtil.phoneHandle(apiUtilsService.secretDecrypt(ied, data.getuPhone())));
                } else {
                    data.setuIdcard(null);
                    data.setuPassword(null);
                }
                datas.add(JacksonUtils.toJSONString(data));
                vos.add(data);
            });
        }
        if (ValidateUtil.isNotEmpty(conf) && conf.equals("1"))
            return BaseResponse.ok(apiUtilsService.SelfSm2EncryptionBath(datas), total);
        else return BaseResponse.ok(vos, total);
    }

    @Override
    public BaseResponse getUserListByUnitPid(UserDto user) {
        QueryMap<UserDto> queryMap = new QueryMap<>(user);
        queryMap.put("uState").put("uName").put("uType").put("cId")
                .put("cName").put("plId").put("uAccount").put("uCode")
                .put("uMtel").put("pId").put("excludeClass").put("uId").put("projectId")
                .put("timeStart", ValidateUtil.isEmpty(user.getStart()) ? null : DateUtil.String2Date(user.getStart(), "yyyy-MM-dd"));
        if (ValidateUtil.isEmpty(user.getEnd())) {
            queryMap.put("timeEnd", null);
        } else {
            Date timeEnd = DateUtil.DateChangeDay(DateUtil.String2Date(user.getEnd(), "yyyy-MM-dd"), 1);
            queryMap.put("timeEnd", timeEnd);
        }
        List<SysUserVo> sysUserPojoIPage = baseMapper.getUserListByUnitPid(queryMap.getQuery());
        Integer total = baseMapper.getUserListByUnitPidCount(queryMap.getQuery());
        List<SysUserVo> datas = new ArrayList<>();
        if (total != null && total > 0) {
            InterfaceEncDecDTO ied = sysConfigEntityService.getInterfaceEncDec();
            sysUserPojoIPage.forEach(data -> {
                data.setuIdcard(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(decIdcard(ied, data.getuIdcard()))));
                data.setuPassword(null);
                datas.add(data);
            });
        }
        return BaseResponse.ok(datas, total);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse CreateOrUpdateUser(UserDto user, String token) {
        SysUser sysUser;
        String codeRule = null;
        //是否由禁用改为启用状态
        boolean dis2Enable = false;
        if (ValidateUtil.isNotEmpty(user.getuId())) {
            sysUser = baseMapper.selectById(user.getuId());
            if (sysUser.getuState().equals("0") && user.getuState().equals("1")) dis2Enable = true;
        } else {
            sysUser = new SysUser();
            if (token != null) {
                sysTenantRuleService.checkTenantRuleByToken(token, "user");
            }
            if (sysSequenceSetService.sequenceRestEnable("SysUser", user.getTeId())) {
                user.setuCode(sysSequenceSetService.generateCodeByTCode("SysUser", user, user.getTeId()));
                if (ValidateUtil.isEmpty(user.getuCode())) return BaseResponse.fail(BaseMsg.codeGenerateFail);
                codeRule = "SysUser";
            }
        }
        //获取身份证是否必填的配置信息
        String idNumber = sysConfigEntityService.getSysConfigByCode("idNumber");
        sysUser.setcId(user.getcId());
        sysUser.setPlId(user.getPlId());
        if (ValidateUtil.isNotEmpty(user.getuAccount())) sysUser.setuAccount(user.getuAccount());
        sysUser.setuBirthday(user.getuBirthday());
        //下方可能需要用到该值 NULL默认不修改则实体中保留这两个值
        if (!ValidateUtil.isEmpty(user.getuCode())) sysUser.setuCode(user.getuCode());
        if (!ValidateUtil.isEmpty(user.getuState())) sysUser.setuState(user.getuState());
        if (!ValidateUtil.isEmpty(user.getuType())) sysUser.setuType(user.getuType());
        if (!ValidateUtil.isEmpty(user.getuTel())) sysUser.setuTel(user.getuTel());
        if (!ValidateUtil.isEmpty(user.getuMtel())) {
            sysUser.setuMtel(user.getuMtel());
            user.setuPhone(user.getuMtel());
        }
        if (!ValidateUtil.isEmpty(user.getuMdmNo())) sysUser.setuMdmNo(user.getuMdmNo());
        if (!ValidateUtil.isEmpty(user.getuEmail())) sysUser.setuEmail(user.getuEmail());
        if (!ValidateUtil.isEmpty(user.getuName())) sysUser.setuName(user.getuName());
        if (!ValidateUtil.isEmpty(user.getuPhone())) {
            sysUser.setuPhone(apiUtilsService.secretEncrypt(sysConfigEntityService.getInterfaceEncDec(), user.getuPhone()));
        }
        sysUser.setuSex(user.getuSex());
        sysUser.setuOffice(user.getuOffice());
        sysUser.setuCornet(user.getuCornet());
        sysUser.setuSort(user.getuSort());
        sysUser.setuSignature(user.getuSignature());
        sysUser.setuQualification(user.getuQualification());
        sysUser.setTeId(user.getTeId());
        sysUser.setuClass(user.getuClass());
        sysUser.setuKey(user.getuKey());
        sysUser.setuKeyPubKey(user.getuKeyPubKey());
        if (ValidateUtil.isNotEmpty(user.getuIdcard()))
            sysUser.setuPlaintextNum(apiUtilsService.SelfSm2Decrypt(user.getuIdcard()));
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("1", "1");
        if (ValidateUtil.isNotEmpty(user.getTeId()))
            wrapper.eq("Te_Id", user.getTeId());
        wrapper.and(w -> {
            w.eq("U_Account", user.getuAccount());
            if (ValidateUtil.isNotEmpty(user.getuCode())) w.or().eq("U_Code", user.getuCode());
            if (!ValidateUtil.isEmpty(idNumber) && idNumber.equals("1"))
                w.or().eq("U_IdCard", sysUser.getuPlaintextNum());
        });
        if (!ValidateUtil.isEmpty(user.getuId())) wrapper.ne("U_ID", user.getuId());
        if (baseMapper.selectCount(wrapper) > 0) return BaseResponse.fail(BaseMsg.infoIsExist);
        InterfaceEncDecDTO ied = sysConfigEntityService.getInterfaceEncDec();
        if (ValidateUtil.isNotEmpty(sysUser.getuId())) {
            if (!ValidateUtil.isEmpty(user.getuPassword())) {
                String pwd = apiUtilsService.SelfSm2Decrypt(user.getuPassword());
                if (pwd == null) return BaseResponse.fail(BaseMsg.pwdError);
                if (token != null) {
                    if (!checkPwdFormat(pwd, sysUser)) return BaseResponse.fail(BaseMsg.pwdFormatError);
                }
                if (ValidateUtil.isEmpty(sysUser.getuSalt())) sysUser.setuSalt(GenerateUUID.getUUID());
                String newPwd = apiUtilsService.PasswrodEncryptionResult(pwd, sysUser.getuSalt(), sysUser.getuAccount());
                String oldPwd = apiUtilsService.secretDecrypt(ied, sysUser.getuPassword());
                sysUser.setuPassword(apiUtilsService.secretEncrypt(ied, newPwd));
                //region 修改密码
                if (ValidateUtil.isEmpty(oldPwd) || !newPwd.equals(oldPwd)) {//修改了密码
                    //后台重置密码后首次登录是否要求强制改密
                    String scpm = sysConfigEntityService.getSysConfigByCode("systemChangePwdMust");
                    if (ValidateUtil.isNotEmpty(scpm) && scpm.equals("1")) sysUser.setuFirstLogin(1);
                    //更新用户密码修改时间
                    sysUser.setuPassUpdateTime(new Date());
                    //插入密码修改记录
                    sysUserPwdChangeService.insertUserPwdChange(oldPwd, newPwd, sysUser.getuId(), sysUser.getuName(), sysUser.getuId());
                    //触发北京数据对接推送
                    sendUserPwdByBjProject(sysUser);
                }
                //endregion
            }
            if (!ValidateUtil.isEmpty(user.getuIdcard())) {
                sysUser.setuIdcard(apiUtilsService.secretEncrypt(sysConfigEntityService.getInterfaceEncDec(), user.getuIdcard()));
            }
            //下次登录越过超时禁用账户的逻辑
            if (dis2Enable) sysUser.setuAutoDisable(true);
            baseMapper.updateById(userSign(sysUser));
            //region 用户被改为禁用 清楚redis内用户信息
            if (sysUser.getuState().equals("0")) {
                //pc的token
                Object upToken = redisUtils.get(sysUser.getuId());
                if (upToken != null)
                    redisUtils.delete(upToken.toString(), sysUser.getuId(), "lastActionTime_" + upToken, "firstLogin_" + upToken);
                //app的token
                upToken = redisUtils.get(sysUser.getuId() + "app");
                if (upToken != null)
                    redisUtils.delete(upToken.toString(), sysUser.getuId() + "app", "lastActionTime_" + upToken, "firstLogin_" + upToken);
                //后台管理端token
                upToken = redisUtils.get(sysUser.getuId() + "back");
                if (upToken != null)
                    redisUtils.delete(upToken.toString(), sysUser.getuId() + "back", "lastActionTime_" + upToken, "firstLogin_" + upToken);
            }
            //endregion
            sysFileAnnexService.refreshRelationFile(Arrays.asList(user.getuSignature(), user.getuQualification()), Arrays.asList(user.getuSignatureIds(), user.getuQualificationIds()), sysUser.getuId());
        } else {
            if (ValidateUtil.isEmpty(user.getuPassword()) || (ValidateUtil.isEmpty(user.getuIdcard()) && (!ValidateUtil.isEmpty(idNumber) && idNumber.equals("1"))))
                return BaseResponse.fail(BaseMsg.notReqParam);
            String pwd = apiUtilsService.SelfSm2Decrypt(user.getuPassword());
            if (pwd == null) return BaseResponse.fail(BaseMsg.pwdError);
            if (token != null) {
                if (!checkPwdFormat(pwd, sysUser)) return BaseResponse.fail(BaseMsg.pwdFormatError);
            }
            if (ValidateUtil.isNotEmpty(user.getuIdcard())) {
                sysUser.setuIdcard(apiUtilsService.secretEncrypt(sysConfigEntityService.getInterfaceEncDec(), user.getuIdcard()));
            }
            sysUser.setuSalt(GenerateUUID.getUUID());
            String newPwd = apiUtilsService.PasswrodEncryptionResult(pwd, sysUser.getuSalt(), sysUser.getuAccount());
            sysUser.setuPassword(apiUtilsService.secretEncrypt(sysConfigEntityService.getInterfaceEncDec(), newPwd));
            sysUser.setuCreateuName(user.getuCreateuName());
            sysUser.setuCreateuId(user.getuCreateuId());
            sysUser.setuCreatetime(new Date());
            sysUser.setuId(UUID.randomUUID().toString().replaceAll("-", ""));
            //获取身份证是否必填的配置信息
            String firstLogin = sysConfigEntityService.getSysConfigByCode("userFirstLogin");
            if (!ValidateUtil.isEmpty(firstLogin) && firstLogin.equals("1")) sysUser.setuFirstLogin(0);
            else sysUser.setuFirstLogin(1);
            sysUser.setuPassUpdateTime(new Date());
            baseMapper.insert(userSign(sysUser));
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(user.getuSignature(), user.getuQualification()), sysUser.getuId());
            sysUserPwdChangeService.insertUserPwdChange(null, user.getuPassword(), user.getuId(), user.getuName(), user.getuId());
        }
        if (ValidateUtil.isNotEmpty(codeRule)) {
            sysSequenceSetService.refershAutoNo(codeRule, user, user.getTeId());
        }
        return BaseResponse.ok(sysUser.getuId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse DeleteUser(UserDto user) {
        if (ValidateUtil.isEmpty(user.getuId())) return BaseResponse.fail(BaseMsg.notReqParam);
        SysUser sysUser = baseMapper.selectById(user.getuId());
        if (sysUser == null) return BaseResponse.fail(BaseMsg.infoNotExist);
        //删除用户密码修改记录
        sysUserPwdChangeService.delUserPwdChangeRecord(user.getuId());
        //删除用户
        baseMapper.deleteById(user.getuId());
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse GetUserListByDId(UserDto request) {
        QueryMap<UserDto> query = new QueryMap<>(request);
        query.put("dId").put("uName").put("uAccount").put("uId").put("filterPosition");
        IPage<SysUserVo> page = new Page<>(request.getPage(), request.getSize());
        List<SysUserVo> datas = baseMapper.GetUserListByDId(page, query.getQuery());
        return BaseResponse.ok(datas, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse GetUserListByPDP(UserPDPDto request) {
        QueryMap<UserPDPDto> query = new QueryMap<>(request);
        query.put("pdId").put("uName").put("uAccount").put("uId").put("pdName").put("pName").put("pCode").put("pdCode").put("ppName").put("ppCode");
        IPage<UserPDPVo> page = new Page<>(request.getPage(), request.getSize());
        List<UserPDPVo> datas = baseMapper.GetUserListByPDP(page, query.getQuery());
        return BaseResponse.ok(datas, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getUserListByMoreCondition(UserDto user) {
        QueryMap<UserDto> params = new QueryMap<>(user);
        params.put("dId").put("uState").put("uName").put("pName").put("uType").put("cId").put("cName").put("plId").put("uAccount").put("uCode").put("uMtel").put("uId").put("all");
        if (ValidateUtil.isNotEmpty(user.getDepCid())) {
            params.put("depIds", sysDepartmentService.getAllChildDepartmentByCid(user.getDepCid()));
        }
        List<SysUserVo> data = baseMapper.getUserListByMoreCondition(params.getQuery());
        data = userIdCardHandle(data);
        fillDepartmentPath(data);
        Integer total = baseMapper.getUserListByMoreConditionCount(params.getQuery());
        return BaseResponse.ok(data, total);
    }

    private void fillDepartmentPath(List<SysUserVo> users) {
        if (ValidateUtil.isEmpty(users)) {
            return;
        }

        // 全部启用部门
        List<SysDepartment> departments = sysDepartmentService.list(
                Wrappers.<SysDepartment>lambdaQuery().eq(SysDepartment::getdStatus, "1")
        );
        if (ValidateUtil.isEmpty(departments)) {
            return;
        }

        Map<String, SysDepartment> depMap = departments.stream()
                .filter(Objects::nonNull)
                .filter(d -> ValidateUtil.isNotEmpty(d.getdId()))
                .collect(Collectors.toMap(SysDepartment::getdId, d -> d, (a, b) -> a));

        for (SysUserVo u : users) {
            if (u == null || ValidateUtil.isEmpty(u.getdId())) {
                continue;
            }
            u.setDepartmentPath(buildDepartmentPath(u.getdId(), depMap));
        }
    }

    private String buildDepartmentPath(String depId, Map<String, SysDepartment> depMap) {
        List<String> names = new ArrayList<>();
        String cur = depId;
        Set<String> guard = new HashSet<>();
        while (ValidateUtil.isNotEmpty(cur) && depMap.containsKey(cur) && !guard.contains(cur)) {
            SysDepartment dep = depMap.get(cur);
            if (ValidateUtil.isNotEmpty(dep.getdName())) {
                names.add(dep.getdName());
            }
            guard.add(cur);
            cur = dep.getdParentid();
        }

        Collections.reverse(names);
        return String.join("/", names);
    }

    @Override
    public BaseResponse getUserListByMoreConditionTree(UserDto request) {
        QueryMap<UserDto> params = new QueryMap<>(request);
        params.put("dId").put("uState").put("uName").put("pName").put("uType").put("cId")
                .put("cName").put("plId").put("uAccount").put("uCode").put("uMtel")
                .put("uId").put("all");
        if (ValidateUtil.isNotEmpty(request.getDepCid())) {
            params.put("depIds", sysDepartmentService.getAllChildDepartmentByCid(request.getDepCid()));
        }

        List<SysUserVo> data = baseMapper.getUserListByMoreCondition(params.getQuery());
        if (ValidateUtil.isEmpty(data)) {
            return BaseResponse.ok();
        }

        data = userIdCardHandle(data);

        List<SysDepartment> departments = sysDepartmentService.list(
                Wrappers.<SysDepartment>lambdaQuery().eq(SysDepartment::getdStatus, "1")
        );

        List<SysUserVo> tree = new ArrayList<>();
        buildDepartmentTree(tree, data, departments);
        return BaseResponse.ok(tree);
    }

    private void buildDepartmentTree(List<SysUserVo> tree, List<SysUserVo> data, List<SysDepartment> departments) {
        if (ValidateUtil.isEmpty(data) || ValidateUtil.isEmpty(departments)) {
            return;
        }

        Map<String, SysDepartment> depMap = departments.stream()
                .filter(Objects::nonNull)
                .filter(d -> ValidateUtil.isNotEmpty(d.getdId()))
                .collect(Collectors.toMap(SysDepartment::getdId, d -> d, (a, b) -> a));

        // 命中部门（用于算最小部门树）
        Set<String> hitDepIds = data.stream()
                .map(SysUserVo::getdId)
                .filter(ValidateUtil::isNotEmpty)
                .collect(Collectors.toSet());

        if (hitDepIds.isEmpty()) {
            return;
        }

        // 按部门分组用户明细
        Map<String, List<SysUserVo>> usersByDepId = data.stream()
                .filter(Objects::nonNull)
                .filter(u -> ValidateUtil.isNotEmpty(u.getdId()))
                .collect(Collectors.groupingBy(SysUserVo::getdId, LinkedHashMap::new, Collectors.toList()));

        Set<String> requiredDepIds = new HashSet<>();
        for (String depId : hitDepIds) {
            String cur = depId;
            Set<String> loopGuard = new HashSet<>();
            while (ValidateUtil.isNotEmpty(cur) && depMap.containsKey(cur) && !loopGuard.contains(cur)) {
                requiredDepIds.add(cur);
                loopGuard.add(cur);
                cur = depMap.get(cur).getdParentid();
            }
        }

        if (requiredDepIds.isEmpty()) {
            return;
        }

        Map<String, List<SysDepartment>> childrenMap = new HashMap<>();
        List<SysDepartment> requiredDeps = requiredDepIds.stream()
                .map(depMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        for (SysDepartment dep : requiredDeps) {
            String parentId = dep.getdParentid();
            if (ValidateUtil.isNotEmpty(parentId) && !requiredDepIds.contains(parentId)) {
                continue;
            }
            childrenMap.computeIfAbsent(parentId, k -> new ArrayList<>()).add(dep);
        }

        List<SysDepartment> roots = requiredDeps.stream()
                .filter(dep -> {
                    String pid = dep.getdParentid();
                    return ValidateUtil.isEmpty(pid) || !requiredDepIds.contains(pid);
                })
                .collect(Collectors.toList());

        for (SysDepartment root : roots) {
            tree.add(convertDepToTreeVo(root, childrenMap, usersByDepId));
        }
    }

    private SysUserVo convertDepToTreeVo(SysDepartment dep,
                                         Map<String, List<SysDepartment>> childrenMap,
                                         Map<String, List<SysUserVo>> usersByDepId) {
        SysUserVo depNode = new SysUserVo();
        depNode.setdId(dep.getdId());
        depNode.setdName(dep.getdName());
        depNode.setdParentId(dep.getdParentid());

        List<SysUserVo> children = new ArrayList<>();

        // 先挂子部门
        List<SysDepartment> depChildren = childrenMap.get(dep.getdId());
        if (!CollectionUtils.isEmpty(depChildren)) {
            for (SysDepartment childDep : depChildren) {
                children.add(convertDepToTreeVo(childDep, childrenMap, usersByDepId));
            }
        }

        // 再挂当前部门用户明细
        List<SysUserVo> users = usersByDepId.get(dep.getdId());
        if (!CollectionUtils.isEmpty(users)) {
            for (SysUserVo u : users) {
                SysUserVo userNode = new SysUserVo();
                org.springframework.beans.BeanUtils.copyProperties(u, userNode);
                userNode.setChildren(new ArrayList<>());
                children.add(userNode);
            }
        }

        depNode.setChildren(children);
        return depNode;
    }

    @Override
    public BaseResponse GetUserListByDPId(UserDto request) {
        QueryMap<UserDto> params = new QueryMap<>(request);
        params.put("pdId").put("filterPosition").put("pdName").put("projectName").put("pId").put("uName").put("uAccount").put("uCode").put("uMtel").put("cName").put("cId").put("uState").put("uType").put("ppName").put("ppId").put("ppCode").put("pName")
                .put("pIds").put("pdType").put("pdCode");
        IPage<SysUserVo> page = new Page<>(request.getPage(), request.getSize());
        List<SysUserVo> datas = baseMapper.GetUserListByDPId(params.getQuery(), page);
        datas = userIdCardHandle(datas);
        return BaseResponse.ok(datas, (int) page.getTotal());

    }

    @Override
    public BaseResponse GetUserListByDPIdDistinct(UserDto request) {
        QueryMap<UserDto> params = new QueryMap<>(request);
        params.put("pdId").put("filterPosition").put("pdName").put("projectName").put("pId").put("uName").put("uAccount").put("uCode").put("uMtel").put("cName").put("cId").put("uState").put("uType").put("ppName").put("ppId").put("ppCode").put("pName")
                .put("pIds");
        IPage<SysUserVo> page = new Page<>(request.getPage(), request.getSize());
        List<SysUserVo> datas = baseMapper.GetUserListByDPIdDistinct(params.getQuery(), page);
        datas = userIdCardHandle(datas);
        return BaseResponse.ok(datas, (int) page.getTotal());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertUser(String account, String password, String plId, String cId, String token) {
        sysTenantRuleService.checkTenantRuleByToken(token, "user");
        SysUser user = new SysUser();
        Date now = new Date();
        user.setuId(GenerateUUID.getUUID());
        user.setcId(cId);
        user.setPlId(plId);
        user.setuAccount(account);
        //String pwd = apiUtilsService.SelfSm2Decrypt(user.getuPassword());
        user.setuSalt(GenerateUUID.getUUID());
        user.setuPassword(apiUtilsService.PasswrodEncryptionResult(sysConfigEntityService.getInterfaceEncDec(), password.trim(), user.getuSalt(), account));
        user.setuCreatetime(now);
        user.setuCode("SystemCreate");
        user.setuCreateuName("System");
        user.setuName(account);
        user.setuSex("0");
        user.setuType("1");
        user.setuPassUpdateTime(now);
        String firstLogin = sysConfigEntityService.getSysConfigByCode("userFirstLogin");
        if (!ValidateUtil.isEmpty(firstLogin) && firstLogin.equals("1")) user.setuFirstLogin(0);
        else user.setuFirstLogin(1);
        baseMapper.insert(userSign(user));
        sysUserPwdChangeService.insertUserPwdChange(null, user.getuPassword(), user.getuId(), user.getuName(), user.getuId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse updateUserPassword(String oldPw, String newPw, String uId) {
        oldPw = apiUtilsService.SelfSm2Decrypt(oldPw);
        newPw = apiUtilsService.SelfSm2Decrypt(newPw);
        if (oldPw == null || newPw == null) return BaseResponse.fail(BaseMsg.pwdError);
        //TODO:需解密密码
        if (oldPw.equals(newPw)) return BaseResponse.fail(BaseMsg.newOldPwdEqually);
        SysUser userPojo = baseMapper.selectById(uId);
        if (userPojo == null) return BaseResponse.fail(HttpStatus.UNAUTHORIZED.value(), BaseMsg.tokenInvalid);
        if (!checkPwdFormat(newPw, userPojo)) return BaseResponse.fail(BaseMsg.pwdFormatError);
        InterfaceEncDecDTO ied = sysConfigEntityService.getInterfaceEncDec();
        if (apiUtilsService.checkPassword(oldPw, userPojo.getuSalt(), userPojo.getuAccount(), ied, userPojo.getuPassword())) {
            String newPwd = apiUtilsService.PasswrodEncryptionResult(newPw, userPojo.getuSalt(), userPojo.getuAccount());
            String oldPwd = apiUtilsService.secretDecrypt(ied, userPojo.getuPassword());
            updatePwdAndDelRedisInfo(userPojo, newPwd, oldPwd, uId);
            sendUserPwdByBjProject(userPojo);
            return BaseResponse.ok(BaseMsg.pwdChangeSuccess);
        } else return BaseResponse.fail(BaseMsg.oldPwdCheckError);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse updateUserPwd(String pwd, LogUserInfoVo logInfo) {
        SysUser user = baseMapper.selectById(logInfo.getId());
        if (user == null) return BaseResponse.fail(HttpStatus.UNAUTHORIZED.value(), BaseMsg.tokenInvalid);
        pwd = apiUtilsService.SelfSm2Decrypt(pwd);
        //新密码与旧密码一致
        String mwPwd = apiUtilsService.PasswrodEncryptionResult(pwd, user.getuSalt(), user.getuAccount());
        if (user.getuPassword().equals(mwPwd))
            return BaseResponse.fail(BaseMsg.pwdReuse);
        updatePwdAndDelRedisInfo(user, mwPwd, user.getuPassword(), user.getuId());
        return BaseResponse.ok(BaseMsg.pwdChangeSuccess);
    }

    private void updatePwdAndDelRedisInfo(SysUser userPojo, String newPwd, String oldPwd, String uId) {
        if (sysUserPwdChangeService.checkPwdUsed(newPwd, uId)) throw new JPMException(BaseMsg.pwdReuse);
        userPojo.setuPassword(apiUtilsService.secretEncrypt(sysConfigEntityService.getInterfaceEncDec(), newPwd));
        userPojo.setuFirstLogin(0);
        userPojo.setuPassUpdateTime(new Date());
        baseMapper.updateById(userSign(userPojo));
        sysUserPwdChangeService.insertUserPwdChange(oldPwd, newPwd, uId, userPojo.getuName(), userPojo.getuId());
        Object oldToken = redisUtils.get(uId);
        if (oldToken != null)
            redisUtils.delete(oldToken.toString(), uId, "lastActionTime_" + oldToken, "firstLogin_" + oldToken);
        Object appOldToken = redisUtils.get(uId + "app");
        //app端token
        if (appOldToken != null)
            redisUtils.delete(appOldToken.toString(), uId, "lastActionTime_" + appOldToken, "firstLogin_" + appOldToken);
        Object backOldToken = redisUtils.get(uId + "back");
        //后台管理端token
        if (backOldToken != null)
            redisUtils.delete(backOldToken.toString(), uId, "lastActionTime_" + backOldToken, "firstLogin_" + backOldToken);
        redisUtils.delete(uId, "userLoginInfo_" + uId);
    }

    @Override
    public BaseResponse smsUpdateUserPwd(SmsUpdatePwdDto request) {
        SysUser user = getUserInfoByTel(request.getPhoneNumber());
        //电话号找不到用户  找到的用户账户名对不上
        if (user == null || !user.getuAccount().equalsIgnoreCase(request.getAccount()))
            return BaseResponse.fail(BaseMsg.phoneNumIsError);
        //验证验证码是否有效
        if (!smsServerService.checkSmsCode(request.getPhoneNumber(), "secret", request.getCode()))
            return BaseResponse.fail(BaseMsg.authCodeInvalid);
        String newPwd = apiUtilsService.SelfSm2Decrypt(request.getNewPwd());
        //密码无法解密 强度限制
        if (ValidateUtil.isEmpty(newPwd)) return BaseResponse.fail(BaseMsg.pwdError);
        if (!checkPwdFormat(newPwd, user)) return BaseResponse.fail(BaseMsg.pwdFormatError);
        //region 更新密码
        //密码加密
        String jmPwd = apiUtilsService.PasswrodEncryptionResult(newPwd, user.getuSalt(), user.getuAccount());
        if (sysUserPwdChangeService.checkPwdUsed(jmPwd, user.getuId())) return BaseResponse.fail(BaseMsg.pwdReuse);
        String oldPwd = user.getuPassword();
        user.setuPassword(apiUtilsService.secretEncrypt(sysConfigEntityService.getInterfaceEncDec(), jmPwd));
        user.setuPassUpdateTime(new Date());
        user.setuFirstLogin(0);
        baseMapper.updateById(userSign(user));
        //密码修改记录
        sysUserPwdChangeService.insertUserPwdChange(oldPwd, jmPwd, user.getuId(), user.getuName(), user.getuId());
        //endregion
        //region 清除token
        Object oldToken = redisUtils.get(user.getuId());
        if (oldToken != null)
            redisUtils.delete(oldToken.toString(), user.getuId(), "lastActionTime_" + oldToken, "firstLogin_" + oldToken);
        Object appOldToken = redisUtils.get(user.getuId() + "app");
        //app端token
        if (appOldToken != null)
            redisUtils.delete(appOldToken.toString(), user.getuId(), "lastActionTime_" + appOldToken, "firstLogin_" + appOldToken);
        Object backOldToken = redisUtils.get(user.getuId() + "back");
        //后台管理端token
        if (backOldToken != null)
            redisUtils.delete(backOldToken.toString(), user.getuId(), "lastActionTime_" + backOldToken, "firstLogin_" + backOldToken);
        redisUtils.delete(user.getuId(), "userLoginInfo_" + user.getuId());
        //endregion
        return BaseResponse.ok(BaseMsg.pwdChangeSuccess);
    }

    @Override
    public BaseResponse checkUserLoginTimeOut(String token) {
        //userLoginTimeout
        Object redisTime = redisUtils.get("lastActionTime_" + token);
        Object uId = redisUtils.get(token);
        if (uId != null) userStatisticsUtil.userOnline(uId.toString());
        if (redisTime == null)
            return BaseResponse.fail(org.apache.http.HttpStatus.SC_UNAUTHORIZED, BaseMsg.tokenInvalid);
        //region 当前账号token不超时
        Object noTimeoutUsers = redisUtils.get("Config_noTimeoutAccount");
        if (noTimeoutUsers != null && ValidateUtil.isNotEmpty(noTimeoutUsers.toString().trim())) {
            LogUserInfoVo user = redisUtils.getUserInfoByUid(uId.toString());
            String[] accounts = noTimeoutUsers.toString().trim().split(",");
            if (Arrays.asList(accounts).contains(user.getCode())) {
                //基础配置该账号token不超时
                redisUtils.set("lastActionTime_" + token, DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
                return BaseResponse.ok(Boolean.FALSE);
            }
        }
        //endregion
        String userLoginTimeout = sysConfigEntityService.getSysConfigByCode("userLoginTimeout");
        Integer outTime = ValidateUtil.isEmpty(userLoginTimeout) ? 300 : Integer.parseInt(userLoginTimeout);
        Date outTimeDate = DateUtil.DateChangeSecond(DateUtil.String2Date(redisTime.toString(), "yyyy-MM-dd HH:mm:ss"), outTime);
        if (outTimeDate.getTime() < new Date().getTime()) {
            if (uId != null) {
                Object webRedisToken = redisUtils.get(uId.toString());
                Object appRedisToken = redisUtils.get(uId + "app");
                Object backRedisToken = redisUtils.get(uId + "back");
                //web端的token
                if (webRedisToken != null && webRedisToken.toString().equals(token)) redisUtils.delete(uId.toString());
                //app端的token
                if (appRedisToken != null && appRedisToken.toString().equals(token)) redisUtils.delete(uId + "app");
                //后台管理端的token
                if (backRedisToken != null && backRedisToken.toString().equals(token)) redisUtils.delete(uId + "back");
            }
            redisUtils.delete(token, "lastActionTime_" + token, "firstLogin_" + token);
            return BaseResponse.ok(Boolean.TRUE);
        } else return BaseResponse.ok(Boolean.FALSE);
    }

    @Override
    public ThirdBaseResponse getJpBpmAuth(OtherBaseDto request) {
        ThirdBaseResponse base = PreJudgment(request);
        if (base != null) return new ThirdBaseResponse(base.getMsgId(), base.getErrMsg());
        Map<String, String> data;
        try {
            data = JacksonUtils.parseObject(request.getBusiContent(), Map.class);
        } catch (Exception ex) {
            return new ThirdBaseResponse(request.getMsgId(), "请确认请求格式!");
        }
        if (data == null || !data.containsKey("userId") || !data.containsKey("channelType") || !data.containsKey("platform"))
            return new ThirdBaseResponse(request.getMsgId(), BaseMsg.notReqParam);
        //验证渠道
        if (ValidateUtil.isEmpty(ChannelTypeEnum.getName(data.get("channelType"))))
            return new ThirdBaseResponse(request.getMsgId(), "未知渠道");
        if ((ValidateUtil.isEmpty(data.get("platform")) || !sysPowerService.checkIsSubSystem(data.get("platform"))) && !data.get("platform").equals("EngineeringManagement"))
            return new ThirdBaseResponse(request.getMsgId(), "未知系统标识!");
        String uCode = data.get("userId");
        SysUser userPojo = getUserWithAppId(request.getAppId(), uCode);
        if (userPojo == null) return new ThirdBaseResponse(request.getMsgId(), "账户未注册!");
        String otherToken = GenerateUUID.getUUID();
        Map<String, String> response = new HashMap<>();
        response.put("token", apiUtilsService.Sm2EncryptionGetUrl(otherToken));
        Map<String, String> redisData = new HashMap<>();
        redisData.put("uId", userPojo.getuId());
        redisData.put("channelType", data.get("channelType"));
        redisData.put("platform", data.get("platform"));
        //设置300秒过期
        redisUtils.set("OtherAuth_" + otherToken, JacksonUtils.toJSONString(redisData), 300);
        return new ThirdBaseResponse(request.getMsgId(), null, JacksonUtils.toJSONString(response));
    }

    /**
     * 根据APPid识别 获取用户信息
     *
     * @param appId
     * @param uCode
     * @return
     */
    private SysUser getUserWithAppId(String appId, String uCode) {
        SysUser userPojo;
        if (appId.equals(ThirdPartyAppIdEnum.JS_YINBAO.getkey()) || appId.equals(ThirdPartyAppIdEnum.SX_PM.getkey()) || appId.equals(ThirdPartyAppIdEnum.PM_ACT.getkey()))
            //银宝传递得是用户账户名
            userPojo = getUserByAccount(uCode);
        else if (appId.equals(ThirdPartyAppIdEnum.PM_TEL.getkey())) userPojo = getUserInfoByTel(uCode);
        else userPojo = getUserByCode(uCode);
        return userPojo;
    }

    @Override
    public ThirdBaseResponse getJpBpmSubsystemUrl(OtherBaseDto request) {
        ThirdBaseResponse base = PreJudgment(request);
        if (base != null) return new ThirdBaseResponse(base.getMsgId(), base.getErrMsg());
        Map<String, String> data;
        try {
            data = JacksonUtils.parseObject(request.getBusiContent(), Map.class);
        } catch (Exception ex) {
            return new ThirdBaseResponse(request.getMsgId(), "请确认请求格式!");
        }
        if (!data.containsKey("token")) return new ThirdBaseResponse(request.getMsgId(), BaseMsg.notReqParam);
        String token = apiUtilsService.Sm2Decrypt(data.get("token"));
        if (ValidateUtil.isEmpty(token)) return new ThirdBaseResponse(request.getMsgId(), "数据加密验证未通过");
        Object redisData = redisUtils.get("OtherAuth_" + token);
        if (redisData == null) return new ThirdBaseResponse(request.getMsgId(), BaseMsg.thirdTokenInvalid);
        Map<String, String> datas = JacksonUtils.parseObject(redisData.toString(), Map.class);
        String channelType = datas.get("channelType");
        String platform = datas.get("platform");
        Map<String, String> returnD = new HashMap<>();
        if (channelType.equals(ChannelTypeEnum.Dedicated_Line.getkey())) {
            String specialUrl = sysConfigEntityService.getSysConfigByCode("specialUrl");
            if (ValidateUtil.isEmpty(specialUrl))
                return new ThirdBaseResponse(request.getMsgId(), String.format(BaseMsg.configIsError, "specialUrl"));
            returnD.put("url", specialUrl + platform);
            return new ThirdBaseResponse(request.getMsgId(), null, JacksonUtils.toJSONString(returnD));
        } else if (channelType.equals(ChannelTypeEnum.Internet.getkey())) {
            String internetUrl = sysConfigEntityService.getSysConfigByCode("internetUrl");
            if (ValidateUtil.isEmpty(internetUrl))
                return new ThirdBaseResponse(request.getMsgId(), String.format(BaseMsg.configIsError, "internetUrl"));
            returnD.put("url", internetUrl + platform);
            return new ThirdBaseResponse(request.getMsgId(), null, JacksonUtils.toJSONString(returnD));
        } else return new ThirdBaseResponse(request.getMsgId(), "未知渠道");
    }


    @Override
    public BaseResponse getUserInfoById(String uId) {
        SysUserVo data = baseMapper.getUserInfoById(uId);
        if (data == null) return BaseResponse.fail(BaseMsg.infoNotExist);
        data.setuPassword(null);
        data.setuAccount(null);

        data.setuIdcard(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(decIdcard(sysConfigEntityService.getInterfaceEncDec(), data.getuIdcard()))));
        List<Object> deps = sysDepartmentService.getUserDepartmentSelectByUid(uId);
        data.setDepartments(deps);
        return BaseResponse.ok(data);
    }

    /**
     * 用户ID获取用户信息(特定岗位手机号加密)
     *
     * @param uId
     * @return
     */
    @Override
    public BaseResponse getUserInfoWithEncryptedPhoneById(String uId) {
        SysUserVo data = baseMapper.getUserInfoById(uId);
        if (ValidateUtil.isNull(data)) {
            return BaseResponse.fail(BaseMsg.infoNotExist);
        }
        data.setuPassword(null);
        data.setuAccount(null);

        if (ValidateUtil.isNotEmpty(data.getuMtel())) {
            String code = sysConfigEntityService.getConfigByCode("withoutTelPosition", null);
            if (ValidateUtil.isNotEmpty(code)) {
                List<String> positionNames = Arrays.asList(code.split(","));
                List<String> userPositionNames = sysPositionRoleMapper.getUserPositionNamesByUid(uId);
                if (ValidateUtil.isNotEmpty(positionNames) && ValidateUtil.isNotEmpty(userPositionNames)) {
                    if (!Collections.disjoint(positionNames, userPositionNames)) {
                        data.setuMtel(VoInfoUtil.phoneHandle(data.getuMtel()));
                    }
                }
            }
        }

        data.setuIdcard(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(decIdcard(sysConfigEntityService.getInterfaceEncDec(), data.getuIdcard()))));
        List<Object> deps = sysDepartmentService.getUserDepartmentSelectByUid(uId);
        data.setDepartments(deps);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getUserInfoByIds(List<String> uIds) {
        List<SysUserVo> models = baseMapper.getUesrInfoByIds(uIds);
        List<SysUserVo> datas = new ArrayList<>();
        if (models != null && models.size() > 0) {
            InterfaceEncDecDTO ied = sysConfigEntityService.getInterfaceEncDec();
            models.forEach(data -> {
                data.setuIdcard(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(decIdcard(ied, data.getuIdcard()))));
                data.setuPassword(null);
                data.setuAccount(null);
                datas.add(data);
            });
        }
        return BaseResponse.ok(datas);
    }

    @Override
    public BaseResponse setUserOpenID(String uId, String openId) {
        SysUser user = baseMapper.selectById(uId);
        if (user == null) return new BaseResponse(BaseMsg.infoNotExist);
        user.setOpenId(openId);
        baseMapper.updateById(user);
        return new BaseResponse(true, BaseMsg.actSuccess);
    }

    @Override
    public SysUser getUserInfoByTel(String phoneNumber) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("U_MTel", phoneNumber);
        wrapper.orderByDesc("U_CreateTime");
        List<SysUser> users = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(users)) return users.get(0);
        return null;
    }

    @Override
    public BaseResponse getUserByTel(String tel) {
        SysUser user = getUserInfoByTel(tel);
        if (user == null) return BaseResponse.ok();
        SysUserVo data = new SysUserVo();
        BeanUtils.copyProperties(user, data);
        data.setuIdNumber(user.getuIdcard());
        data.setuIdcard(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(decIdcard(sysConfigEntityService.getInterfaceEncDec(), user.getuIdcard()))));
        data.setuPassword(null);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse resetAllOutUserPwd(String pwd, String uId) {
        SysUser admin = baseMapper.selectById(uId);
        if (admin == null || !admin.getuAccount().equals("admin")) return BaseResponse.fail("仅限管理员操作！");
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("U_Type", UserTypeEnum.Out_User.getIndex());
        List<SysUser> users = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(users)) {
            List<SysUser> ups = new ArrayList<>();
            InterfaceEncDecDTO ied = sysConfigEntityService.getInterfaceEncDec();
            for (SysUser user : users) {
                user.setuSalt(GenerateUUID.getUUID());
                if (ValidateUtil.isNotEmpty(user.getuAccount())) {
                    user.setuPassword(apiUtilsService.PasswrodEncryptionResult(ied, pwd, user.getuSalt(), user.getuAccount()));
                    user.setuPassUpdateTime(new Date());
                    user.setuFirstLogin(1);
                    ups.add(user);
                }
            }
            if (!CollectionUtils.isEmpty(ups)) updateBatchById(ups);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse disablePwdUser(String pwd, String uId) {
        SysUser admin = baseMapper.selectById(uId);
        if (admin == null || !admin.getuAccount().equals("admin")) return BaseResponse.fail("仅限管理员操作！");
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("U_Type", UserTypeEnum.Out_User.getIndex());
        List<SysUser> users = baseMapper.selectList(wrapper);
        List<SysUser> ups = new ArrayList<>();
        if (!CollectionUtils.isEmpty(users)) {
            InterfaceEncDecDTO ied = sysConfigEntityService.getInterfaceEncDec();
            for (SysUser user : users) {
                if (apiUtilsService.checkPassword(pwd, user.getuSalt(), user.getuAccount(), ied, user.getuPassword())) {
                    user.setuState("0");
                    ups.add(user);
                }
            }
            if (!CollectionUtils.isEmpty(ups)) updateBatchById(ups);
        }
        return BaseResponse.ok(ups.size());
    }


    @Override
    public BaseResponse havingPmUserInProjectDep(String pId, String token) {
        String confStr = sysConfigEntityService.getConfigByCode("contractInfos", token);
        if (ValidateUtil.isEmpty(confStr))
            return BaseResponse.fail(String.format(BaseMsg.configIsHiatus, "contractInfos"));
        ConfContractInfosDTO conf = JacksonUtils.parseObject(confStr, ConfContractInfosDTO.class);
        if (conf == null || CollectionUtils.isEmpty(conf.getPositionName()))
            return BaseResponse.fail(String.format(BaseMsg.configIsError, "contractInfos"));
        int count = baseMapper.havingPmUserInProjectDep(pId, conf.getPositionName());
        if (count > 0) return BaseResponse.ok(true);
        return BaseResponse.ok(false);
    }

    @Override
    public BaseResponse getPmUserInProjectDep(String pId, String token) {
        String confStr = sysConfigEntityService.getConfigByCode("contractInfos", token);
        if (ValidateUtil.isEmpty(confStr))
            return BaseResponse.fail(String.format(BaseMsg.configIsHiatus, "contractInfos"));
        ConfContractInfosDTO conf = JacksonUtils.parseObject(confStr, ConfContractInfosDTO.class);
        if (conf == null || CollectionUtils.isEmpty(conf.getPositionName()))
            return BaseResponse.fail(String.format(BaseMsg.configIsError, "contractInfos"));
        return BaseResponse.ok(baseMapper.getPmUserInProjectDep(pId, conf.getPositionName()));
    }

    @Override
    public BaseResponse getProjectDepUserByPpName(DepPUserBatchSelectDTO request, String token) {
        if (ValidateUtil.isNull(request)) return BaseResponse.ok();
        if (CollectionUtils.isEmpty(request.getPpNames())) return BaseResponse.fail(BaseMsg.notReqParam);
        if (CollectionUtils.isEmpty(request.getProjectIds())) request.setProjectIds(null);
        return BaseResponse.ok(baseMapper.getProjectDepUserByPpName(request.getProjectIds(), request.getPpNames(), redisUtils.getTeId(token)));
    }


    @Override
    public BaseResponse smsCheckByToken(LogUserInfoVo user, String code) {
        SysUser u = baseMapper.selectById(user.getId());
        if (u == null) return BaseResponse.fail(BaseMsg.tokenInvalid);
        if (!smsServerService.checkSmsCode(u.getuMtel(), "login", code))
            return BaseResponse.fail(BaseMsg.smsCodeInvalid);
        //修改用户标记
        u.setuCheckSms(0);
        baseMapper.updateById(u);
        //重置缓存标记
        user.setUCheckSms(0);
        redisUtils.set("userLoginInfo_" + user.getId(), JacksonUtils.toJSONString(user));
        return BaseResponse.ok(BaseMsg.smsCheckPass);
    }

    @Override
    public BaseResponse resetUserPlaintextNum() {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("U_IdCard");
        wrapper.isNull("U_Plaintext_Num");
        List<SysUser> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            data = data.stream().peek(d -> {
                d.setuPlaintextNum(apiUtilsService.SelfSm2Decrypt(d.getuIdcard()));
            }).collect(Collectors.toList());
            updateBatchById(data);
        }
        return BaseResponse.ok();
    }

    @Override
    public String getTeIdByUserId(String uid) {
        SysUser user = baseMapper.selectById(uid);
        if (user == null) return null;
        TenantModeConfigDTO conf = sysConfigEntityService.getTenantConf();
        if (conf != null && conf.isEnable()) return user.getTeId();
        return null;
    }

    @Override
    public BaseResponse getUserByToken(String token) {
        String uid = redisUtils.getUid(token);
        if (ValidateUtil.isEmpty(token)) return BaseResponse.fail(BaseMsg.tokenInvalid);
        SysUserVo data = baseMapper.getUserInfoById(uid);
        if (data == null) return BaseResponse.fail(BaseMsg.infoNotExist);
        data.setuPassword(null);
        data.setuAccount(null);
        data.setuIdcard(apiUtilsService.SelfSm2Decrypt(decIdcard(sysConfigEntityService.getInterfaceEncDec(), data.getuIdcard())));
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getDepBatchUser(DepPUserBatchSelectDTO request) {
        return BaseResponse.ok(baseMapper.getDepBatchUser(request));
    }


    private ThirdBaseResponse PreJudgment(OtherBaseDto request) {
        //验证来源标识
        if (ValidateUtil.isEmpty(ThirdPartyAppIdEnum.getName(request.getAppId())))
            return new ThirdBaseResponse(request.getMsgId(), "无法识别AppId");
        //region 验证请求时间
        if (ValidateUtil.isEmpty(request.getTimestamp()))
            return new ThirdBaseResponse(request.getMsgId(), BaseMsg.thirdMsg_IllegalTime);
        Date requestTime = DateUtil.String2Date(request.getTimestamp(), "yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        if (now.getTime() < requestTime.getTime())
            return new ThirdBaseResponse(request.getMsgId(), BaseMsg.thirdMsg_IllegalTime);
        Long timeOut = now.getTime() - requestTime.getTime();
        if (timeOut / 1000L > sysConfigEntityService.getThirdLoginTimeOut()) {
            return new ThirdBaseResponse(request.getMsgId(), BaseMsg.thirdMsg_IllegalTime);
        }
        //endregion
        //region验签
        //String s = apiUtilsService.Sm2EncryptionGetUrl(request.getBusiContent());
        //apiUtilsService.verificationSm2Sign(s, request.getBusiContent());
        if (!apiUtilsService.verificationSm2Sign(request.getSign(), request.getBusiContent()))
            return new ThirdBaseResponse(request.getMsgId(), "验签失败!");
        //endregion
        return null;
    }

    /**
     * 校验用户是否首次登录，密码是否超过使用时间
     *
     * @return userFirstLog用户首次登录强制改密标记
     */
    private Integer checkPwdOverdue(SysUser pojo) {
        Map<String, String> conf = sysConfigEntityService.getConfigByCodes(Arrays.asList("userFirstLogin", "pwdUsageLimitOnlyOutUser", "userPasswordUsageLimit"), null);
        //region 用户超过指定日期没有登录过 直接禁用账号
        UserPwdExpirationVerificationDTO pev = sysConfigEntityService.getUserUserPwdExpirationConf(pojo.getuType());
        if (pev.isOutTimeDisabledSwitch()) { //启用了超时禁用用户的校验
            if (pojo.getuAutoDisable() != null && pojo.getuAutoDisable()) {
                baseMapper.refreshAuthDisableTag(pojo.getuId(), false);
            } else {
                if (sysLoginLogService.getUserUseInterval(pojo, null) > pev.getOutTimeDisabledDays()) {
                    baseMapper.disableUser(pojo.getuId(), "0");
                    throw new JPMException(BaseMsg.userIsDisable);
                }
            }
        }
        //endregion
        //region 密码超出使用期限，首次登录是否需要强制改密
        String pwdUsLimit = "0";
        String olnyOut = null;
        String userFirstLogin = null;
        if (ValidateUtil.isNotNull(conf)) {
            //密码使用天数限制
            pwdUsLimit = conf.getOrDefault("userPasswordUsageLimit", "0");
            //密码超出使用期限是否仅对外部用户生效
            olnyOut = conf.getOrDefault("pwdUsageLimitOnlyOutUser", null);
            //是否取消校验首次登录 1：取消校验
            userFirstLogin = conf.getOrDefault("userFirstLogin", null);
        }
        if (ValidateUtil.isNotEmpty(userFirstLogin) && userFirstLogin.equals("1")) return 0;
        Integer userFirstLog = pojo.getuFirstLogin();
        if (ValidateUtil.isNotNull(userFirstLog) && userFirstLog != 1) {
            Integer passTime = ValidateUtil.isEmpty(pwdUsLimit) ? 0 : Integer.parseInt(pwdUsLimit);
            //开启了密码超过使用天数强制改密仅对外部用户生效的配置
            if (ValidateUtil.isNotEmpty(olnyOut) && olnyOut.equals("1") && ValidateUtil.isNotEmpty(pojo.getuType()) && pojo.getuType().equals(UserTypeEnum.Inner_User.getIndex() + ""))
                return userFirstLog;
            if (passTime != 0) {
                Date passUpdateTime = pojo.getuPassUpdateTime();
                if (DateUtil.daysBetweenDay(passUpdateTime, new Date()) > passTime) userFirstLog = 1;
            }
        } else if (ValidateUtil.isNull(userFirstLog)) return 0;
        //endregion
        return userFirstLog;
    }

    /**
     * 用户编码查询账号信息
     */
    private SysUser getUserByCode(String uCode) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("U_Code", uCode);
        return baseMapper.selectOne(queryWrapper);
    }

    /**
     * 账号查询用户信息
     */
    private SysUser getUserByAccount(String account) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("U_Account", account.trim());
        List<SysUser> users = baseMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(users)) {
            //优先返回启用的用户
            List<SysUser> enables = users.stream().filter(n -> ValidateUtil.isNotEmpty(n.getuState()) && n.getuState().equals("1")).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(enables)) return enables.get(0);
            return users.get(0);
        }
        return null;
    }

    /**
     * 登录时返回权限信息
     */
    private void screenParams(SysUser user, LogUserInfoVo userInfo) {
        userInfo.setCId(user.getcId());
        SysCompany company = sysCompanyService.getById(user.getcId());
        if (company != null) userInfo.setCName(company.getcName());
        if (Integer.parseInt(user.getuType()) == UserTypeEnum.Out_User.getIndex()) {//是外部单位
            if (ValidateUtil.isNotEmpty(company.getcType()) && (company.getcType().equals(CompanyTypeEnum.Construction_Control.getkey()) || company.getcType().equals(CompanyTypeEnum.Construction_Unit.getkey()))) {
                //是监理单位或全过程咨询单位
                userInfo.setConstructionControl(true);
            } else {//其他单位
                userInfo.setConstructionUnit(true);
            }
        } else {
            //用户所在组织机构部门和子部门Id
            List<String> userDId = sysDepartmentService.getUserDepartment(userInfo);
            if (userDId != null) userInfo.setDepartments(userDId);
            else userInfo.setDepartments("all");
        }
    }

    //包含大写字母、小写字母、数字、特殊符号（不是字母，数字，下划线，汉字的字符）的8位以上
    //private final static String regex = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,}$";

    /**
     * 校验密码格式
     * 包含大写字母、小写字母、数字、特殊符号
     * （不是字母，数字，下划线，汉字的字符）的8-16位
     *
     * @return true 通过
     */
    private Boolean checkPwdFormat(String pwd, SysUser user) {
        if (CheckPwdUtil.isHz(pwd)) throw new JPMException(BaseMsg.pwdContainsHz);
        Map<String, String> confs = sysConfigEntityService.getConfigByCodes(Arrays.asList("pwdStrength", "pwdRuleChckConf"), null);
        String pwdStrength = confs.getOrDefault("pwdStrength", "0");
        String pwdRuleChckConf = confs.getOrDefault("pwdRuleChckConf", null);
        //不校验密码强度
        if (!ValidateUtil.isEmpty(pwdStrength) && pwdStrength.equals("0")) return true;
        PwdRuleChckConfDTO conf = null;
        if (ValidateUtil.isNotEmpty(pwdRuleChckConf))
            conf = JacksonUtils.parseObject(pwdRuleChckConf, PwdRuleChckConfDTO.class);
        if (conf == null) conf = new PwdRuleChckConfDTO();
        //校验密码库
        if (conf.isCheckLib() && sysPwdDictionaryService.pwdExist(pwd)) throw new JPMException(BaseMsg.pwdIsDisable);
        return CheckPwdUtil.check(pwd, user, conf);
    }

    /**
     * 北京项目修改密码后推送密码到平台
     */
    private void sendUserPwdByBjProject(SysUser user) {
        if (!ValidateUtil.isEmpty(activeValue) && activeValue.equals("bj")) {
            Map<String, Object> map = new HashMap<>();
            map.put("userId", user.getuCode());
            map.put("userPwd", user.getuPassword());
            map.put("salt", user.getuSalt());
            map.put("action", "pushUserPwd");
            sender.send(JacksonUtils.toJSONString(map));
        }
    }

    /**
     * 响应结果身份证转掩码
     */
    private List<SysUserVo> userIdCardHandle(List<SysUserVo> models) {
        List<SysUserVo> datas = new ArrayList<>();
        if (ValidateUtil.isNotEmpty(models)) {
            InterfaceEncDecDTO ied = sysConfigEntityService.getInterfaceEncDec();
            datas = models.stream().map(n -> {
                n.setuIdcard(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(decIdcard(ied, n.getuIdcard()))));
                return n;
            }).collect(Collectors.toList());
        }
        return datas;
    }

    /**
     * 登录主要逻辑
     * pc,app,短信登录
     *
     * @param user     入参信息
     * @param pojo     用户信息
     * @param type     登录类型 web|app
     * @param isBack   是否后台管理端登录
     * @param checkPwd 是否校验密码 1：校验
     */
    private BaseResponse loginMain(UserDto user, SysUser pojo, String type, boolean isBack, int checkPwd, HttpServletRequest request) {
        //如果是后台管理端登录 校验是否限定指定账户
        checkAccountBackLogin(user, isBack);
        //region 密码是否处于多次重试失败锁定中
        //最近密码重试次数
        String retryKey = "loginRetryCount_" + user.getuAccount().trim().toLowerCase(Locale.ENGLISH);
        //最近一次登录时间
        String lockKey = "lastLoginTime_" + user.getuAccount().trim().toLowerCase(Locale.ENGLISH);
        Integer redisRetryCount = redisUtils.get(retryKey) == null ? 0 : Integer.parseInt(redisUtils.get(retryKey).toString());
        Map<String, String> conf = sysConfigEntityService.getConfigByCodes(Arrays.asList("loginLockTime", "loginRetryCount", "authorizeButton", "tenantModeConfig", "appOuterCannotLogin"), null);
        String appOuterCannotLogin = ValidateUtil.isEmpty(conf.get("appOuterCannotLogin")) ? "0" : conf.get("appOuterCannotLogin").toString();
        //移动端登录识别基础配置禁止外部用户登录
        if (type.startsWith("app") && pojo.getuType().equals(UserTypeEnum.Out_User.getIndex().toString()) && appOuterCannotLogin.equals("1"))
            throw new JPMException(BaseMsg.notAuthUser);
        //锁定策略账户锁定时间  默认5分钟
        Integer loginLockTime = ValidateUtil.isEmpty(conf.get("loginLockTime")) ? 300 : Integer.parseInt(conf.get("loginLockTime"));
        //锁定策略重试次数 默认五次
        Integer loginRetryCount = ValidateUtil.isEmpty(conf.get("loginRetryCount")) ? 5 : Integer.parseInt(conf.get("loginRetryCount"));
        if (!ValidateUtil.isNull(redisRetryCount)) {
            if (loginRetryCount <= redisRetryCount) {//触发超时重试
                if (redisUtils.get(lockKey) == null) {
                    //更新最后一次登录时间
                    redisUtils.set(lockKey, DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    return BaseResponse.fail(BaseMsg.actIsLock);
                } else {
                    Date lastLoginDate = DateUtil.DateChangeSecond(DateUtil.String2Date(redisUtils.get(lockKey).toString(), "yyyy-MM-dd HH:mm:ss"), loginLockTime);
                    if (lastLoginDate.getTime() > new Date().getTime()) return BaseResponse.fail(BaseMsg.actIsLock);
                    else {
                        redisRetryCount = 0;
                        redisUtils.delete(retryKey);
                    }
                }
            }
        }
        //endregion
        //更新最后一次登录时间
        redisUtils.set(lockKey, DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
        //region 校验密码正确性
        if (checkPwd == 1) {
            String pwd = apiUtilsService.SelfSm2Decrypt(user.getuPassword());
            if (pwd == null) {
                //解密失败
                redisUtils.set(retryKey, redisRetryCount + 1);
                return BaseResponse.fail(BaseMsg.actPwdError);
            }
            user.setuPassword(pwd);
            if (!apiUtilsService.checkPassword(user.getuPassword(), pojo.getuSalt(), user.getuAccount(), sysConfigEntityService.getInterfaceEncDec(), pojo.getuPassword())) {
                redisUtils.set(retryKey, redisRetryCount + 1);
                return BaseResponse.fail(BaseMsg.actPwdError);
            }
        }
        //endregion
        //成功登录 重置失败计数
        redisUtils.set(retryKey, 0);
        //账户是否被禁用
        if (pojo.getuState().equals("0")) return BaseResponse.fail(BaseMsg.actIsDisable);
        //校验用户是否首次登录，密码是否超过使用时间
        Integer userFirstLog = checkPwdOverdue(pojo);
        //发送短信
        LogInfoVo logInfo = new LogInfoVo();
        if (user.isSendSms() && ValidateUtil.isNotEmpty(pojo.getuMtel())) {
            logInfo.setCheckSmsCode(true);
            BaseResponse res = smsServerService.sendCodeSms(pojo.getuMtel(), "login");
            res.setData(logInfo);
            return res;
        } else if (ValidateUtil.isEmpty(pojo.getuMtel())) logInfo.setCompletionTel(true);
        //region 用户登录信息
        //token
        String RandKey = GenerateUUID.getToekn();
        if (ValidateUtil.isNotEmpty(conf.get("authorizeButton")) && conf.get("authorizeButton").equals("1"))
            logInfo.setAuthorizeBtn(true);
        logInfo = sysPositionRoleService.setUserRoleNameAndBtnRole(logInfo, pojo);
        logInfo.setToken(RandKey);
        LogUserInfoVo userInfo = new LogUserInfoVo();
        userInfo.setName(pojo.getuName()).setId(pojo.getuId()).setCode(pojo.getuAccount()).setUType(pojo.getuType()).setTeId(pojo.getTeId()).setLoginWay(type)
                .setUFirstLogin(userFirstLog).setAttendanceGroup(oaAttendanceGroupService.getOaAttendanceGroupByUid(pojo.getuId()))
                .setPhone(VoInfoUtil.phoneHandle(pojo.getuMtel())).setRoleNames(logInfo.getRoles());
        //是否开启租户模式
        if (ValidateUtil.isNotEmpty(conf.getOrDefault("tenantModeConfig", null))) {
            TenantModeConfigDTO tenantConf = JacksonUtils.parseObject(conf.get("tenantModeConfig"), TenantModeConfigDTO.class);
            if (tenantConf != null) userInfo.setEnableTenant(tenantConf.isEnable());
        }
        screenParams(pojo, userInfo);
        Date dt = new Date();
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        //中国时区+8  +4小时延后
        rightNow.add(Calendar.HOUR, +12);
        Date expireAt = rightNow.getTime();
        logInfo.setExpireAt(expireAt);
        //region 区分app登录还是pc登录 获取redis中的老token并讲新的token存入redis
        Object oldRandK;
        boolean refreshUser = false;
        if (type.endsWith("Account")) {
//            if (!type.startsWith("app")) { //仅对web登录有效
            LoginWayDTO way = sysConfigEntityService.getLoginWay();
            if (ValidateUtil.isNotEmpty(pojo.getuMtel())) {
                if (isBack) {
                    if (way.getCheckSmsCodeAdmin()) pojo.setuCheckSms(1);
                    else pojo.setuCheckSms(0);
                } else {
                    if (way.getCheckSmsCode()) pojo.setuCheckSms(1);
                    else pojo.setuCheckSms(0);
                }
            } else pojo.setuCheckSms(0);
            refreshUser = true;
//            }
        }
        if (type.startsWith("app")) {
            if (!ValidateUtil.isEmpty(user.getuPushId())) {
                //更新同步推送标识
                pojo.setuPushId(user.getuPushId());
                refreshUser = true;
            }
            oldRandK = redisUtils.get(pojo.getuId() + "app");
            redisUtils.set(pojo.getuId() + "app", RandKey);
        } else {
            if (isBack) {
                oldRandK = redisUtils.get(pojo.getuId() + "back");
                redisUtils.set(pojo.getuId() + "back", RandKey);
            } else {
                oldRandK = redisUtils.get(pojo.getuId());
                redisUtils.set(pojo.getuId(), RandKey);
            }
        }
        if (refreshUser) baseMapper.updateById(pojo);
        userInfo.setUPushId(ValidateUtil.isEmpty(pojo.getuPushId()) ? "" : pojo.getuPushId())
                .setUCheckSms(ValidateUtil.isNull(pojo.getuCheckSms()) ? 0 : pojo.getuCheckSms());
        logInfo.setUser(userInfo);
        //endregion
        userInfo = sysTenantRuleService.setRedis(userInfo);
        //region 删除redis中旧的用户缓存信息，存入新的用户信息
        if (oldRandK != null)
            redisUtils.delete(oldRandK.toString(), "lastActionTime_" + oldRandK, "firstLogin_" + oldRandK);
        redisUtils.set(RandKey, pojo.getuId());
        //表单关联有con_id的表名
        userInfo.setConTables(baseMapper.getAllHaveContractTable());
        redisUtils.set("userLoginInfo_" + pojo.getuId(), JacksonUtils.toJSONString(userInfo));
        redisUtils.set("firstLogin_" + RandKey, userFirstLog);
        redisUtils.set("lastActionTime_" + RandKey, DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
        //endregion
        //endregion
        //登录记录
        sysLoginLogService.create(request, pojo.getuId(), type, user);
        return BaseResponse.ok(BaseMsg.logSuccess, logInfo);
    }

    private void checkSessionCode(UserDto user, String sessionId, Boolean isBack) {
        LoginWayDTO way = sysConfigEntityService.getLoginWay();
        if (isBack ? way.getCheckAuthCodeAdmin() : way.getCheckAuthCode()) {
            if (ValidateUtil.isEmpty(user.getAuthCode())) throw new JPMException(BaseMsg.authCodeInvalid);
            if (ValidateUtil.isEmpty(sessionId)) throw new JPMException(BaseMsg.authCodeInvalid);
            Object serverCode = redisUtils.get("session_auto_code_" + sessionId);
            if (serverCode == null) throw new JPMException(BaseMsg.authCodeInvalid);
            if (!user.getAuthCode().equalsIgnoreCase(serverCode.toString()))
                throw new JPMException(BaseMsg.authCodeInvalid);
            redisUtils.delete("session_auto_code_" + sessionId);
        }
    }

    private void checkAccountBackLogin(UserDto user, boolean isBack) {
        if (isBack) {
            String accountStr = sysConfigEntityService.getSysConfigByCode("can2LoginBackWebAccount");
            if (ValidateUtil.isNotEmpty(accountStr)) {
                List<String> accounts = Arrays.asList(accountStr.split(","));
                if (!accounts.contains(user.getuAccount()))
                    throw new JPMException(HttpStatus.UNAUTHORIZED.value(), BaseMsg.userCannotLoginBack);
            }
        }
    }

    /**
     * 用户数据完整性签名
     *
     * @param user
     * @return
     */
    private SysUser userSign(SysUser user) {
        InterfaceEncDecDTO ied = sysConfigEntityService.getInterfaceEncDec();
        if (ied.getUserSign()) {
            if (ValidateUtil.isNotEmpty(ied.getSignUrl())) {
                SignAndVerifyDTO sg = new SignAndVerifyDTO();
                sg.setContent(user.signContent());
                BaseResponse res = apiUtilsService.sendPost(ied.getSignUrl(), sg, null);
                if (ValidateUtil.isNotNull(res) && ValidateUtil.isNotNull(res.getData())) {
                    user.setSign(res.getData().toString());
                }
            }
        }
        return user;
    }

    /**
     * 用户验签 验证完整性
     *
     * @param content
     * @param sign
     * @return
     */
    public Boolean userSignVerify(InterfaceEncDecDTO ied, Boolean verify, String content, String sign) {
        if (verify) {
            if (ValidateUtil.isNotEmpty(ied.getVerifyUrl())) {
                SignAndVerifyDTO sg = new SignAndVerifyDTO();
                sg.setContent(content);
                sg.setSign(sign);
                BaseResponse res = apiUtilsService.sendPost(ied.getVerifyUrl(), sg, null);
                if (ValidateUtil.isNotNull(res) && ValidateUtil.isNotNull(res.getData())) {
                    System.out.println("验签结果:" + res.getData());
                    return Boolean.parseBoolean(res.getData().toString());
                }
            }
        } else return true;
        return false;
    }

    /**
     * 根据岗位名称获取用户信息
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse getUserInfoByPositionNames(UserDto request) {
        if (ValidateUtil.isNull(request) || ValidateUtil.isEmpty(request.getPositionNames())) {
            return BaseResponse.ok();
        }
        // 去除空格
        List<String> positionNames = request.getPositionNames();
        positionNames = positionNames.stream().map(String::trim).collect(Collectors.toList());
        List<SysUserVo> models = baseMapper.getUserInfoByPositionNames(positionNames);
        return BaseResponse.ok(models);
    }

    /**
     * 验证USBKEY
     *
     * @param account 账户名
     * @param keyId   设备唯一标识
     * @return
     */
    public boolean checkUsbKey(String account, String keyId) {
        if (ValidateUtil.isEmpty(keyId))
            throw new JPMException(BaseMsg.usbKeyInvalid);
        SysUser user = getUserByAccount(account);
        if (user != null) {
            if (ValidateUtil.isEmpty(user.getuKey()))
                return false;
            return user.getuKey().equals(keyId);
        }
        return false;
    }

    /**
     * 身份证解密
     *
     * @param ied
     * @param idcard
     * @return
     */
    public String decIdcard(InterfaceEncDecDTO ied, String idcard) {
        if (ValidateUtil.isNotEmpty(idcard)) {
            String text = apiUtilsService.secretDecrypt(ied, idcard);
            if (ValidateUtil.isNotEmpty(text)) return text;
        }
        return idcard;
    }
}

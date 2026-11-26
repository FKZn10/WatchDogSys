package com.jpmsoft.userProducer.service.v2.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.UserTypeEnum;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.enums.CompanyTypeEnum;
import com.jpmsoft.userProducer.mapper.SysUserMapper;
import com.jpmsoft.userProducer.pojo.dto.UserDto;
import com.jpmsoft.userProducer.pojo.entity.SysCompany;
import com.jpmsoft.userProducer.pojo.entity.SysUser;
import com.jpmsoft.userProducer.pojo.vo.LogInfoVo;
import com.jpmsoft.userProducer.pojo.vo.LogUserInfoVo;
import com.jpmsoft.userProducer.service.*;
import com.jpmsoft.userProducer.service.v2.ISysUserService;
import com.jpmsoft.userProducer.utils.DateUtil;
import com.jpmsoft.userProducer.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service("UserImpl2")
@Deprecated
public class SysUserImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {


    @Resource
    private ISysPositionRoleService sysPositionRoleService;

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
    private IOaAttendanceGroupService oaAttendanceGroupService;

    @Resource
    private ISysLoginLogService sysLoginLogService;

    @Value("${system.checkAuthCode}")
    private String checkAuthCode;


    @Override
    public BaseResponse login(UserDto user, String sessionId, HttpServletRequest request) {
        if (ValidateUtil.isEmpty(checkAuthCode)) checkAuthCode = "1";
        if (checkAuthCode.equals("1")) {
            if (ValidateUtil.isEmpty(user.getAuthCode())) return new BaseResponse("请输入验证码！");
            if (ValidateUtil.isEmpty(sessionId)) return new BaseResponse("请求错误！");
            Object serverCode = redisUtils.get("session_auto_code_" + sessionId);
            if (serverCode == null) return new BaseResponse("验证码无效！");
            if (!user.getAuthCode().toUpperCase(Locale.ENGLISH).equals(serverCode.toString().toUpperCase(Locale.ENGLISH)))
                return new BaseResponse("验证码错误！");
            redisUtils.delete("session_auto_code_" + sessionId);
        }
        BaseResponse baseResponse = loginType(user, "web", request);
        return baseResponse;
    }

    @Override
    public BaseResponse loginType(UserDto user, String type, HttpServletRequest request) {
        if (ValidateUtil.isEmpty(user.getuAccount()) || ValidateUtil.isEmpty(user.getuPassword()))
            return BaseResponse.fail(BaseMsg.notActPwd);

        SysUser pojo = getUserByAccount(user.getuAccount());
        if (pojo == null) return BaseResponse.fail(BaseMsg.actPwdError);

        //region 密码是否处于多次重试失败锁定中
        //最近密码重试次数
        String retryKey = "loginRetryCount_" + user.getuAccount().trim().toLowerCase(Locale.ENGLISH);
        //最近一次登录时间
        String lockKey = "lastLoginTime_" + user.getuAccount().trim().toLowerCase(Locale.ENGLISH);
        Integer redisRetryCount = redisUtils.get(retryKey) == null ? 0 : Integer.parseInt(redisUtils.get(retryKey).toString());
        Map<String, String> conf = sysConfigEntityService.getConfigByCodes(Arrays.asList("loginLockTime", "loginRetryCount"), pojo.getuId());
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
                    else redisUtils.set(retryKey, 0);
                }
            }
        }
        //endregion

        //更新最后一次登录时间
        redisUtils.set(lockKey, DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
        //region 校验密码正确性
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
        //endregion

        //成功登录 重置失败计数
        redisUtils.set(retryKey, 0);
        //账户是否被禁用
        if (pojo.getuState().equals("0")) return BaseResponse.fail(BaseMsg.actIsDisable);
        //校验用户是否首次登录，密码是否超过使用时间
        Integer userFirtLog = checkPwdOverdue(pojo);
        //region 用户登录信息
        //token
        String RandKey = GenerateUUID.getToekn();
        LogInfoVo logInfo = new LogInfoVo();
        logInfo.setRoles(sysPositionRoleService.loginToGetUserRoleName(pojo.getuId(), null));
        logInfo.setToken(RandKey);
        LogUserInfoVo userInfo = new LogUserInfoVo();
        userInfo.setName(pojo.getuName()).setId(pojo.getuId()).setCode(pojo.getuAccount()).setUType(pojo.getuType()).setUFirstLogin(userFirtLog).setAttendanceGroup(oaAttendanceGroupService.getOaAttendanceGroupByUid(pojo.getuId())).setRoleNames(logInfo.getRoles());
        screenParams(pojo, userInfo);
        Date dt = new Date();
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        //中国时区+8  +4小时延后
        rightNow.add(Calendar.HOUR, +12);
        Date expireAt = rightNow.getTime();
        logInfo.setExpireAt(expireAt);
        //region 区分app登录还是pc登录 获取redis中的老token并讲新的token存入redis
        Object oldRandK = null;
        if (type.equals("app")) {
            if (!ValidateUtil.isEmpty(user.getuPushId())) {
                //更新同步推送标识
                pojo.setuPushId(user.getuPushId());
                baseMapper.updateById(pojo);
            }
            oldRandK = redisUtils.get(pojo.getuId() + "app");
            redisUtils.set(pojo.getuId() + "app", RandKey);
        } else {
            oldRandK = redisUtils.get(pojo.getuId());
            redisUtils.set(pojo.getuId(), RandKey);
        }
        userInfo.setUPushId(ValidateUtil.isEmpty(pojo.getuPushId()) ? "" : pojo.getuPushId());
        logInfo.setUser(userInfo);
        //endregion
        //region 删除redis中旧的用户缓存信息，存入新的用户信息
        if (oldRandK != null)
            redisUtils.delete(oldRandK.toString(), "lastActionTime_" + oldRandK, "firstLogin_" + oldRandK);
        redisUtils.set(RandKey, pojo.getuId());
        redisUtils.set("userLoginInfo_" + pojo.getuId(), JacksonUtils.toJSONString(userInfo));
        redisUtils.set("firstLogin_" + RandKey, userFirtLog);
        redisUtils.set("lastActionTime_" + RandKey, DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
        //endregion
        //endregion
        //登录记录
        sysLoginLogService.create(request, pojo.getuId(), type, user);
        return BaseResponse.ok(BaseMsg.logSuccess, logInfo);
    }


    private SysUser getUserByCode(String uCode) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("U_Code", uCode);
        SysUser pojo = baseMapper.selectOne(queryWrapper);
        return pojo;
    }


    private SysUser getUserByAccount(String account) {
        try {
            QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("U_Account", account);
            SysUser pojo = baseMapper.selectOne(queryWrapper);
            return pojo;
        } catch (Exception ex) {
            log.error("账户查询用户出现多个用户信息！", ex);
            return null;
        }
    }

    /**
     * 登录时返回权限信息
     *
     * @param user
     * @param userInfo
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

    /**
     * 校验用户是否首次登录，密码是否超过使用时间
     *
     * @param pojo
     * @return userFirtLog用户首次登录强制改密标记
     */
    private Integer checkPwdOverdue(SysUser pojo) {
        Integer userFirtLog = pojo.getuFirstLogin();
        if (ValidateUtil.isNotNull(userFirtLog) && userFirtLog != 1) {
            Integer passTime = sysConfigEntityService.getUserPasswordUsageLimit();
            if (passTime != 0) {
                Date passUpdateTime = pojo.getuPassUpdateTime();
                if (DateUtil.daysBetweenDay(passUpdateTime, new Date()) > passTime) userFirtLog = 1;
            }
        }
        return userFirtLog;
    }

}

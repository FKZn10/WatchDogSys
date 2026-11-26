package com.jpmsoft.userProducer.controller.v1;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.ThirdParty.ThirdBaseResponse;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.annotations.TenantId;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.pojo.dto.DepPUserBatchSelectDTO;
import com.jpmsoft.userProducer.pojo.dto.OtherBaseDto;
import com.jpmsoft.userProducer.pojo.dto.UserDto;
import com.jpmsoft.userProducer.pojo.dto.UserPDPDto;
import com.jpmsoft.userProducer.pojo.dto.sms.SmsUpdatePwdDto;
import com.jpmsoft.userProducer.pojo.entity.SysUser;
import com.jpmsoft.userProducer.pojo.vo.LogUserInfoVo;
import com.jpmsoft.userProducer.service.ISysLoginLogService;
import com.jpmsoft.userProducer.service.ISysUserService;
import com.jpmsoft.userProducer.utils.RedisUtils;
import com.jpmsoft.userProducer.utils.UserStatisticsUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Api(tags = "用户", value = "用户")
@RestController
@RequestMapping("/v1/OrganizationManage")
public class SysUserController {

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private UserStatisticsUtil userStatisticsUtil;

    @Resource
    private ISysLoginLogService sysLoginLogService;


    @GetMapping("/setThirdRedis")
    public BaseResponse setThirdRedis(@RequestParam String token) {
        Map<String, String> redisData = new HashMap<>();
        redisData.put("uId", "d5559434-a311-400e-a5e3-e862e41f71c2");
        //设置过期
        redisUtils.set("OtherAuth_" + token, JacksonUtils.toJSONString(redisData), 1800);
        return BaseResponse.ok();
    }


    @TenantId
    @ApiOperation(value = "用户列表(查脱敏身份证)")
    @PostMapping("/GetUserList")
    public BaseResponse GetUserList(@RequestBody UserDto user, @RequestHeader String token) {
        log.info("获取用户信息 GetUserList");
        return sysUserService.GetUserList(user, redisUtils.getUid(token), true);
    }


    @ApiOperation(value = "用户令牌获取登录信息")
    @GetMapping("/getUserLogInfo")
    public BaseResponse getUserLogInfo(@RequestHeader String token) {
        log.info("获取用户信息 getUserLogInfo");
        return BaseResponse.ok(redisUtils.getUserInfoByToken(token));
    }

    @ApiOperation(value = "用户令牌获取登录信息")
    @GetMapping("/getCurrentUserPhone")
    public BaseResponse getCurrentUserPhone(@RequestHeader String token) {
        log.info("获取用户信息 getCurrentUserPhone");
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        SysUser us = sysUserService.getById(user.getId());
        return BaseResponse.ok(us.getuMtel());
    }

    @TenantId
    @ApiOperation(value = "用户列表（不查询返回密码以及身份证等铭感信息）")
    @PostMapping("/getUserNoSensitiveList")
    public BaseResponse getUserNoSensitiveList(@RequestBody UserDto user, @RequestHeader String token) {
        log.info("获取用户信息 getUserNoSensitiveList");
        return sysUserService.GetUserList(user, redisUtils.getUid(token), false);
    }

    @TenantId
    @ApiOperation(value = "项目参建单位获取用户信息")
    @PostMapping("/getUserListByUnitPid")
    public BaseResponse getUserListByUnitPid(@RequestBody UserDto user) {
        log.info("项目参建单位获取用户信息 getUserListByUnitPid");
        return sysUserService.getUserListByUnitPid(user);
    }

    @TenantId
    @ApiOperation(value = "创建或修改")
    @PostMapping("/CreateOrUpdateUser")
    public BaseResponse CreateOrUpdateUser(@RequestBody UserDto user, @RequestHeader String token) {
        log.info("新增或者修改用户信息");
        return sysUserService.CreateOrUpdateUser(user, token);
    }

    @ApiOperation(value = "删除用户")
    @PostMapping("/DeleteUser")
    public BaseResponse DeleteUser(@RequestBody UserDto user) {
        log.info("删除用户");
        return sysUserService.DeleteUser(user);
    }

    @TenantId
    @ApiOperation(value = "查询用户组织机构岗位信息")
    @PostMapping("/GetUserListByDId")
    public BaseResponse GetUserListByDId(@RequestBody UserDto request) {
        log.info("GetUserListByDId");
        return sysUserService.GetUserListByDId(request);
    }

    @TenantId
    @ApiOperation(value = "查询用户项目组织机构岗位信息")
    @PostMapping("/GetUserListByPDP")
    public BaseResponse GetUserListByPDP(@RequestBody UserPDPDto request) {
        log.info("GetUserListByPDP");
        return sysUserService.GetUserListByPDP(request);
    }

    @TenantId
    @ApiOperation(value = "根据部门id获取人员信息(更多其他条件筛选)")
    @PostMapping("/getUserListByMoreCondition")
    public BaseResponse getUserListByMoreCondition(@RequestBody UserDto request) {
        log.info("getUserListByMoreCondition");
        return sysUserService.getUserListByMoreCondition(request);
    }

    @TenantId
    @ApiOperation(value = "根据部门id获取人员信息的树形(更多其他条件筛选)")
    @PostMapping("/getUserListByMoreConditionTree")
    public BaseResponse getUserListByMoreConditionTree(@RequestBody UserDto request) {
        log.info("getUserListByMoreConditionTree");
        return sysUserService.getUserListByMoreConditionTree(request);
    }

    @TenantId
    @ApiOperation(value = "根据项目部门id获取人员信息")
    @PostMapping("/GetUserListByDPId")
    public BaseResponse GetUserListByDPId(@RequestBody UserDto request) {
        log.info("GetUserListByDPId");
        return sysUserService.GetUserListByDPId(request);
    }

    @TenantId
    @ApiOperation(value = "根据项目部门id获取人员信息 （去重）")
    @PostMapping("/GetUserListByDPIdDistinct")
    public BaseResponse GetUserListByDPIdDistinct(@RequestBody UserDto request) {
        log.info("GetUserListByDPId");
        return sysUserService.GetUserListByDPIdDistinct(request);
    }

    @TenantId
    @ApiOperation(value = "根据项目ID批量查询指定岗位的人员")
    @PostMapping("/getDepBatchUser")
    public BaseResponse getDepBatchUser(@RequestBody DepPUserBatchSelectDTO request) {
        log.info("getDepBatchUser");
        return sysUserService.getDepBatchUser(request);
    }


    @ApiOperation(value = "修改用户密码")
    @GetMapping("/updateUesrPassword")
    public BaseResponse updateUserPassword(@RequestParam String oldPw, @RequestParam String newPw, @RequestHeader String token) {
        log.info("updateUserPassword");
        return sysUserService.updateUserPassword(oldPw, newPw, redisUtils.getUid(token));
    }

    @ApiOperation(value = "修改用户密码(根据访问令牌，无需旧密码)")
    @GetMapping("/updateUserPwd")
    public BaseResponse updateUserPwd(@RequestParam String pwd, @RequestHeader String token) {
        log.info("updateUserPwd");
        return sysUserService.updateUserPwd(pwd, redisUtils.getUserInfoByToken(token));
    }


    @ApiOperation(value = "修改用户密码(短信验证码)")
    @PostMapping("/smsUpdateUserPwd")
    public BaseResponse smsUpdateUserPwd(@RequestBody @Validated SmsUpdatePwdDto request) {
        return sysUserService.smsUpdateUserPwd(request);
    }

    @ApiOperation(value = "校验账户是否超时")
    @GetMapping("/checkUserLoginTimeOut")
    public BaseResponse checkUserLoginTimeOut(@RequestHeader String token) {
        return sysUserService.checkUserLoginTimeOut(token);
    }

    @ApiOperation(value = "第三方获取我方登录授权TOKEN(SM2加密)")
    @PostMapping("/getJpBpmAuth")
    public Object getJpBpmAuth(@RequestBody OtherBaseDto request) {
        try {
            Object data = sysUserService.getJpBpmAuth(request);
            return data;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            return new ThirdBaseResponse(request.getMsgId(), BaseMsg.thirdTokenVaildError);
        }
    }

    /**
     * 获取子系统URL
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "第三方获取我方登录URL")
    @PostMapping("/getJpBpmSubsystemUrl")
    public Object getJpBpmSubsystemUrl(@RequestBody OtherBaseDto request) {
        try {
            return sysUserService.getJpBpmSubsystemUrl(request);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            return new ThirdBaseResponse(request.getMsgId(), BaseMsg.thirdLogUrlError);
        }
    }


    @ApiOperation(value = "用户ID获取用户信息")
    @GetMapping("/getUserInfoById")
    public BaseResponse getUserInfoById(@RequestParam String uId) {
        return sysUserService.getUserInfoById(uId);
    }

    @ApiOperation(value = "用户ID获取用户信息(特定岗位手机号加密)")
    @GetMapping("/getUserInfoWithEncryptedPhoneById")
    public BaseResponse getUserInfoWithEncryptedPhoneById(@RequestParam String uId) {
        return sysUserService.getUserInfoWithEncryptedPhoneById(uId);
    }

    @ApiOperation(value = "当前登录人令牌获取用户信息")
    @GetMapping("/getUserByToken")
    public BaseResponse getUserByToken(@RequestHeader String token) {
        return sysUserService.getUserByToken(token);
    }

    @ApiOperation(value = "用户ID批量获取用户信息")
    @PostMapping("/getUesrInfoByIds")
    public BaseResponse getUserInfoByIds(@RequestBody List<String> uIds) {
        return sysUserService.getUserInfoByIds(uIds);
    }

    @ApiOperation(value = "清楚密码错误重试锁定！仅限admin操作")
    @GetMapping("/clearPassWordRetry")
    public BaseResponse clearPassWordRetry(@RequestParam String account, @RequestHeader String token) {
        log.info("clearPassWordRetry");
        return sysUserService.clearPassWordRetry(account, redisUtils.getUid(token));
    }

    @ApiOperation(value = "设置用户第三方openId")
    @GetMapping("/setUserOpenID")
    public BaseResponse setUserOpenID(@RequestParam String uId, @RequestParam String openId) {
        log.info("setUserOpenID");
        return sysUserService.setUserOpenID(uId, openId);
    }

    @ApiOperation(value = "获取用户在线人数,访问总数")
    @GetMapping("/getUserOnlineCount")
    public BaseResponse getUserOnlineCount() {
        log.info("getUserOnlineCount");
        Map<String, Long> data = new HashMap<>();
        data.put("userOnline", userStatisticsUtil.userOnlineCount());
        data.put("visitsTotal", sysLoginLogService.getCount());
        return BaseResponse.ok(data);
    }

    @ApiOperation(value = "手机号查询用户")
    @GetMapping("/getUserByTel")
    public BaseResponse getUserByTel(@RequestParam String tel) {
        log.info("getUserByTel");
        return sysUserService.getUserByTel(tel);
    }

    @ApiIgnore
    @ApiOperation(value = "重置所有外部用户密码")
    @GetMapping("/resetAllOutUserPwd")
    public BaseResponse resetAllOutUserPwd(@RequestParam String pwd, @RequestHeader String token) {
        return sysUserService.resetAllOutUserPwd(pwd, redisUtils.getUid(token));
    }

    @ApiIgnore
    @ApiOperation(value = "重置用户身份证明文")
    @GetMapping("/resetUserPlaintextNum")
    public BaseResponse resetUserPlaintextNum() {
        return sysUserService.resetUserPlaintextNum();
    }

    @ApiIgnore
    @ApiOperation(value = "禁用所有使用指定密码的外部用户")
    @GetMapping("/disablePwdUser")
    public BaseResponse disablePwdUser(@RequestParam String pwd, @RequestHeader String token) throws UnsupportedEncodingException {
        return sysUserService.disablePwdUser(URLDecoder.decode(pwd, "UTF-8"), redisUtils.getUid(token));
    }

    @ApiOperation(value = "查询指定项目的项目组织机构内是否有岗位叫项目经理的用户")
    @GetMapping("/havingPmUserInProjectDep")
    public BaseResponse havingPmUserInProjectDep(@RequestParam String pId, @RequestHeader String token) {
        log.info("havingPmUserInProjectDep");
        return sysUserService.havingPmUserInProjectDep(pId, token);
    }


    @ApiOperation(value = "查询指定项目的项目组织机构内岗位叫项目经理的用户")
    @GetMapping("/getPmUserInProjectDep")
    public BaseResponse getPmUserInProjectDep(String pId, String token) {
        log.info("getPmUserInProjectDep");
        return sysUserService.getPmUserInProjectDep(pId, token);
    }

    @ApiOperation(value = "查询指定项目组织机构下指定岗位的用户")
    @PostMapping("/getProjectDepUserByPpName")
    public BaseResponse getProjectDepUserByPpName(@RequestBody DepPUserBatchSelectDTO request, @RequestHeader String token) {
        log.info("getProjectDepUserByPpName");
        return sysUserService.getProjectDepUserByPpName(request, token);
    }

    @ApiIgnore
    @ApiOperation(value = "用户ID实时获取登录时缓存的用户信息")
    @GetMapping("/getLogUserInfoById")
    public LogUserInfoVo getLogUserInfoById(@RequestParam String uid) {
        return sysUserService.getLogUserInfoById(uid);
    }

    @ApiOperation(value = "根据岗位名称获取用户信息")
    @PostMapping("/getUserInfoByPositionNames")
    public BaseResponse getUserInfoByPositionNames(@RequestBody UserDto request) {
        log.info("getUserInfoByPositionNames");
        return sysUserService.getUserInfoByPositionNames(request);
    }

    @ApiIgnore
    @ApiOperation(value = "校验UKey是否被其他用户已绑定")
    @GetMapping("/checkUKeyUsed")
    public BaseResponse checkUKeyUsed(@RequestParam String uKey, @RequestParam(required = false) String uid) {
        if (ValidateUtil.isEmpty(uKey)) return BaseResponse.ok();
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("u_key", uKey);
        List<SysUser> users = sysUserService.list(wrapper);
        if (!CollectionUtils.isEmpty(users)) {
            SysUser user = users.stream().findFirst().get();
            if (ValidateUtil.isEmpty(uid) || !user.getuId().equals(uid))
                return BaseResponse.fail(String.format(BaseMsg.uKeyUsed, user.getuName()), false);
        }
        return BaseResponse.ok(true);
    }
}

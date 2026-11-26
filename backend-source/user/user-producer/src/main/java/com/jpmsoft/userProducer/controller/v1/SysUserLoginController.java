package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.pojo.dto.SysLoginLogDto;
import com.jpmsoft.userProducer.pojo.dto.UsbKeyLoginDTO;
import com.jpmsoft.userProducer.pojo.dto.UsbKeyVerifyDTO;
import com.jpmsoft.userProducer.pojo.dto.UserDto;
import com.jpmsoft.userProducer.pojo.dto.sms.UserSmsLoginDto;
import com.jpmsoft.userProducer.pojo.vo.LogInfoVo;
import com.jpmsoft.userProducer.service.*;
import com.jpmsoft.userProducer.utils.RedisUtils;
import com.jpmsoft.userProducer.utils.VerifiactionCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Api(tags = "用户登录", value = "用户登录")
@RestController
@RequestMapping("/v1/SystemManage")
public class SysUserLoginController {


    @Resource
    private ISysUserService sysUserService;
    @Resource
    private RedisUtils redisUtils;

    @Resource
    private ISysPowerService sysPowerService;

    @Resource
    private ISysLoginLogService sysLoginLogService;
    @Resource
    private ISysConfigEntityService sysConfigEntityService;
    @Resource
    private IApiUtilsService apiUtilsService;

    @ApiOperation(value = "用户登出")
    @GetMapping("/loginOut")
    public BaseResponse loginOut(HttpServletRequest request, @RequestHeader String token) {
        log.info("用户登出 loginOut");
        //region 删除redis中旧的用户缓存信息，存入新的用户信息
        String uid = redisUtils.getUid(token);
        sysLoginLogService.create(request, uid, "loginout", new UserDto());
        redisUtils.delete(token, "lastActionTime_" + token, "firstLogin_" + token);
        return BaseResponse.ok();
    }


    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public BaseResponse login(@RequestBody UserDto user, HttpServletRequest request) {
        log.info("登录 login");
        if (!sysConfigEntityService.getLoginWay().getAccount())
            return BaseResponse.fail(BaseMsg.notAccountLogin);
        String sessionId = request.getHeader("Se-Auth-Code");
        return sysUserService.login(user, sessionId, false, request);
    }

    @ApiOperation(value = "用户登录(后台),验证密码+短信验证码")
    @PostMapping("/backLogin")
    public BaseResponse backLogin(@RequestBody UserDto user, HttpServletRequest request) {
        log.info("登录 backLogin");
        String sessionId = request.getHeader("Se-Auth-Code");
        return sysUserService.backLogin(user, sessionId, true, request);
    }


    @ApiOperation(value = "用户登录,短信验证码方式")
    @PostMapping("/loginSms")
    public BaseResponse loginSms(@RequestBody @Validated UserSmsLoginDto user, HttpServletRequest request) {
        log.info("登录 loginSms");
        if (!sysConfigEntityService.getLoginWay().getMobile())
            return BaseResponse.fail(BaseMsg.notSmsLogin);
        return sysUserService.loginSms(user, false, request);
    }

    @ApiOperation(value = "usbKey用户名+keyid登录(前台)  返回随机数需要验证签名")
    @PostMapping("/usbKeyLogin")
    public BaseResponse usbKeyLogin(HttpServletRequest request, @RequestBody UsbKeyLoginDTO info) {
        return usbKeyLogin(request, info, false);
    }


    @ApiOperation(value = "usbKey用户名+keyid登录(后台) 返回随机数需要验证签名")
    @PostMapping("/usbKeyLoginBack")
    public BaseResponse usbKeyLoginBack(HttpServletRequest request, @RequestBody UsbKeyLoginDTO info) {
        return usbKeyLogin(request, info, true);
    }

    @ApiOperation(value = "校验usbKey随机数签名")
    @PostMapping("/checkUsbKeyCode")
    public BaseResponse checkUsbKeyCode(@RequestBody UsbKeyVerifyDTO request) {
        Object info = redisUtils.get("ukeyLogin_" + request.getPlaintext());
        if (info == null) return BaseResponse.fail(BaseMsg.usbKeySignOutTime);
        if (apiUtilsService.verifyUkey(request.getUserId(), request.getPlaintext(), request.getSignature())) {
            //成功登录将缓存信息直接返回
            return BaseResponse.ok(BaseMsg.logSuccess, JacksonUtils.parseObject(info.toString(), LogInfoVo.class));
        } else
            return BaseResponse.fail(BaseMsg.usbKeyVerifyFail);
    }

    public BaseResponse usbKeyLogin(HttpServletRequest request, UsbKeyLoginDTO info, boolean isBack) {
        log.info("登录 usbKeyLogin");
        if (!sysConfigEntityService.getLoginWay().getUkey())
            return BaseResponse.fail(BaseMsg.notUsbKeyLogin);
        String sessionId = request.getHeader("Se-Auth-Code");
        BaseResponse res = sysUserService.usbKeyLogin(info, sessionId, isBack, request);
        if (res != null && res.getSuccess() && ValidateUtil.isNotNull(res.getData())) {
            LogInfoVo user = JacksonUtils.parseObject(JacksonUtils.toJSONString(res.getData()), LogInfoVo.class);
            int code = VerifiactionCodeUtil.code();
            redisUtils.set("ukeyLogin_" + code, JacksonUtils.toJSONString(res.getData()), 10);
            Map<String, String> map = new HashMap<>();
            map.put("userId", user.getUser().getId());
            map.put("code", code + "");
            return BaseResponse.ok(map);
        }
        return res;
    }

    @ApiOperation(value = "用户登录(不校验验证码)")
    @PostMapping("/loginNotCode")
    public BaseResponse loginNotCode(@RequestBody UserDto user, HttpServletRequest request) {
        log.info("不校验验证码登录 loginNotCode");
        if (!sysConfigEntityService.getLoginWay().getAccount())
            return BaseResponse.fail(BaseMsg.notAccountLogin);
        return sysUserService.loginType(user, "app-Account", false, request);
    }


    @ApiOperation(value = "第三方使用TOKEN登录")
    @GetMapping("/thirdPartLogin")
    public BaseResponse thirdPartLogin(@RequestHeader String token, HttpServletRequest request) {
        log.info("第三方登录");
        if (ValidateUtil.isEmpty(token))
            return BaseResponse.fail(BaseMsg.thirdTokenInvalid);
        return sysUserService.thirdPartLogin(token, request);
    }

    @ApiOperation(value = "用户routes")
    @GetMapping("/routes")
    public BaseResponse routes(@RequestParam(required = false) String powerCode, @RequestHeader String token) {
        log.info("routes");
        return sysUserService.routes(powerCode, token);
    }

    @ApiOperation(value = "用户routes(不包含后台节点)")
    @GetMapping("/getUserVueRoute")
    public BaseResponse getUserVueRoute(@RequestParam(required = false) String powerCode, @RequestHeader String token) {
        log.info("getUserVueRoute");
        return sysPowerService.getUserVueRoute(powerCode, token);
    }

    @ApiOperation(value = "分页查询登录日志")
    @PostMapping("/getSysLoginLogList")
    public BaseResponse getSysLoginLogList(@RequestBody SysLoginLogDto request) {
        log.info("getSysLoginLogList");
        return sysLoginLogService.getSysLoginLogList(request);
    }

    @ApiOperation(value = "登录后通过token令牌校验验证码")
    @GetMapping("/smsCheck")
    public BaseResponse smsCheck(@RequestParam String code, @RequestHeader String token) {
        log.info("smsCheck");
        return sysUserService.smsCheckByToken(redisUtils.getUserInfoByToken(token), code);
    }

//    /**
//     * token续约
//     *
//     * @param token
//     * @return
//     */
//    @ApiOperation(value = "token续约")
//    @GetMapping("/tokenRenewal")
//    public BaseResponse tokenRenewal(@RequestHeader String token) {
//        log.info("tokenRenewal");
//        return sysUserService.tokenRenewal(token);
//    }

}

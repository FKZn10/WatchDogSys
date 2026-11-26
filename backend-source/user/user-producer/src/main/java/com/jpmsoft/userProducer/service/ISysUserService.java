package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.model.response.ThirdParty.ThirdBaseResponse;
import com.jpmsoft.userProducer.pojo.dto.*;
import com.jpmsoft.userProducer.pojo.dto.sms.SmsUpdatePwdDto;
import com.jpmsoft.userProducer.pojo.dto.sms.UserSmsLoginDto;
import com.jpmsoft.userProducer.pojo.entity.SysUser;
import com.jpmsoft.userProducer.pojo.vo.LogUserInfoVo;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface ISysUserService extends IService<SysUser> {

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    BaseResponse login(UserDto user, String sessionId, boolean isBack, HttpServletRequest request);

    /**
     * 后台登录 验证密码+短信验证码
     *
     * @param user
     * @param sessionId
     * @param request
     * @return
     */
    BaseResponse backLogin(UserDto user, String sessionId, boolean isBack, HttpServletRequest request);

    /**
     * 短信验证码登录
     *
     * @return
     */
    BaseResponse loginSms(UserSmsLoginDto user, boolean isBack, HttpServletRequest request);

    BaseResponse usbKeyLogin(UsbKeyLoginDTO info, String sessionId, boolean isBack, HttpServletRequest request);

    /**
     * @param user
     * @param type web app 登录平台（区分web和APP  app无验证码）
     * @return
     */
    BaseResponse loginType(UserDto user, String type, boolean isBack, HttpServletRequest request);

    LogUserInfoVo getLogUserInfoById(String uid);

    BaseResponse clearPassWordRetry(String account, String uId);

    /**
     * 第三方登录
     *
     * @param token
     * @return
     */
    BaseResponse thirdPartLogin(String token, HttpServletRequest request);

    SysUser getUserByAccountAndPassword(UserDto user);

    BaseResponse routes(String powerCode, String token);

    /**
     * 获取用户信息
     *
     * @param user
     * @param sensitive 查询铭感信息（身份证）
     * @return
     */
    BaseResponse GetUserList(UserDto user, String redisUId, boolean sensitive);

    BaseResponse getUserListByUnitPid(UserDto user);

    /**
     * 新增或者修改
     *
     * @param user
     * @return
     */
    BaseResponse CreateOrUpdateUser(UserDto user, String token);


    /**
     * 删除用户
     *
     * @param user
     * @return
     */
    BaseResponse DeleteUser(UserDto user);


    BaseResponse GetUserListByDId(UserDto request);

    BaseResponse GetUserListByPDP(UserPDPDto request);

    BaseResponse getUserListByMoreCondition(UserDto user);

    BaseResponse getUserListByMoreConditionTree(UserDto request);

    /**
     * 根据项目部门id获取人员信息
     *
     * @param request
     * @return
     */
    BaseResponse GetUserListByDPId(UserDto request);


    BaseResponse GetUserListByDPIdDistinct(UserDto request);
    /**
     * 创建系统用户
     *
     * @param account
     * @param password
     * @param plId
     * @param cId
     */
    void insertUser(String account, String password, String plId, String cId, String token);


    /**
     * 修改用户密码
     *
     * @param oldPw
     * @param newPw
     * @return
     */
    BaseResponse updateUserPassword(String oldPw, String newPw, String uId);

    /**
     * 根据token修改密码
     * 不需要旧密码
     *
     * @param pwd
     * @param logInfo
     * @return
     */
    BaseResponse updateUserPwd(String pwd, LogUserInfoVo logInfo);

    /**
     * 短信验证码修改密码
     * 忘记密码场景
     *
     * @param request
     * @return
     */
    BaseResponse smsUpdateUserPwd(SmsUpdatePwdDto request);

    /**
     * 校验用户是否登录超时
     *
     * @param token
     * @return
     */
    BaseResponse checkUserLoginTimeOut(String token);


    /**
     * 第三方获取我方认证token
     *
     * @param request
     * @return
     */
    ThirdBaseResponse getJpBpmAuth(OtherBaseDto request);

    /**
     * 获取子系统URL
     *
     * @param request
     * @return
     */
    ThirdBaseResponse getJpBpmSubsystemUrl(OtherBaseDto request);

    /**
     * 用户ID获取用户信息
     *
     * @param uId
     * @return
     */
    BaseResponse getUserInfoById(String uId);

    /**
     * 用户ID获取用户信息(特定岗位手机号加密)
     *
     * @param uId
     * @return
     */
    BaseResponse getUserInfoWithEncryptedPhoneById(String uId);

    /**
     * 用户ID批量获取用户信息
     *
     * @param uIds
     * @return
     */
    BaseResponse getUserInfoByIds(List<String> uIds);

    /**
     * 更新用户第三方标识
     *
     * @param uId
     * @param openId
     * @return
     */
    BaseResponse setUserOpenID(String uId, String openId);

    /**
     * 手机号查询用户
     *
     * @param phoneNumber
     * @return
     */
    SysUser getUserInfoByTel(String phoneNumber);

    BaseResponse getUserByTel(@RequestParam String tel);

//    /**
//     * token续约
//     * @param token
//     * @return
//     */
//    BaseResponse tokenRenewal(String token);

    BaseResponse resetAllOutUserPwd(String pwd, String uId);

    BaseResponse disablePwdUser(String pwd, String uId);

    /**
     * 查询项目下 项目组织机构内是否有岗位叫项目经理的用户
     *
     * @param pId
     * @return
     */
    BaseResponse havingPmUserInProjectDep(String pId, String token);

    BaseResponse getPmUserInProjectDep(String pId, String token);

    /**
     * 查询指定项目组织机构下指定岗位的用户
     *
     * @param request
     * @param token
     * @return
     */
    BaseResponse getProjectDepUserByPpName(DepPUserBatchSelectDTO request, String token);

    BaseResponse smsCheckByToken(LogUserInfoVo user, String code);

    /**
     * 重设用户身份证明文
     * 解密密文赋值
     *
     * @return
     */
    BaseResponse resetUserPlaintextNum();

    /**
     * 租户模式开启 且用户有teId
     * 否则返回null
     *
     * @param uid
     * @return
     */
    String getTeIdByUserId(String uid);

    BaseResponse getUserByToken(String token);

    /**
     * 根据项目ID批量查询指定岗位的人员
     *
     * @param request
     * @return
     */
    BaseResponse getDepBatchUser(DepPUserBatchSelectDTO request);

    /**
     * 数据完整性验签
     *
     * @param ied
     * @param content
     * @param sign
     * @return
     */
    Boolean userSignVerify(InterfaceEncDecDTO ied, Boolean verify ,String content, String sign);

    /**
     * 根据岗位名称获取用户信息
     *
     * @param request
     * @return
     */
    BaseResponse getUserInfoByPositionNames(UserDto request);
}

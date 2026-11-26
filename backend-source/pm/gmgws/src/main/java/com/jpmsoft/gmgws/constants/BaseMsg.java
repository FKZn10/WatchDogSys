package com.jpmsoft.gmgws.constants;

/**
 * 消息定义
 *
 * @author: 李文涛 aaron
 * @date: 2022/7/26 15:05
 */
public class BaseMsg {

    public final static String userVerifyError = "验证不通过，用户信息可能已被篡改！";
    public final static String dataVerifyError = "验证不通过，数据可能已被篡改！";
    public final static String notAccountLogin = "未开启账号密码登录方式！";
    public final static String notSmsLogin = "未开启短信验证码登录方式！";
    public final static String cannotHoliday = "无法获取工作日信息！";
    public final static String tenantUserMax = "当前租户用户数已达到上限！";
    public final static String tenantProjectMax = "当前租户项目数已达到上限！";
    public final static String tenantExpire = "当前租户已过期！";
    public final static String pwdLenthAsk = "密码长度必须大于%s位小于%s位！";
    public final static String pwdIsDisable = "当前密码已禁用！";
    public final static String pwdContainsHz = "密码不能使用中文！";
    public final static String pwdContainsAccount = "密码不能包含账户名！";
    public final static String pwdContainsName = "密码不能包含姓名(单字或全拼)！";
    public final static String pwdCategoryAsk = "密码必须包含数字、大写字母、小写字母、特殊字符中的其中%s种！";
    public final static String pwdContinuousChar = "密码不能包含三个及以上连续的数值、字符、键盘连续按键！";
    public final static String thirdTokenVaildError = "获取认证token失败！";
    public final static String thirdLogUrlError = "获取登录url失败！";
    public final static String actSuccess = "操作成功！";
    public final static String tokenInvalid = "访问令牌失效，请重新登录！";
    //第三方登录
    public final static String thirdTokenInvalid = "访问令牌失效！";
    public final static String infoNotExist = "信息不存在，请刷新重试！";
    public final static String infoIsExist = "记录已存在，请勿重复操作！";
    public final static String notReqParam = "缺少请求参数！";
    public final static String notTableConfig = "缺少数据库表配置！";
    public final static String notPrimaryConfig = "缺少主键字段配置！";
    public final static String notUserInfo = "用户不存在！";
    public final static String notMove = "无法移动！";
    public final static String notActPwd = "账号或者密码为空！";
    public final static String actPwdError = "账号或者密码错误！";
    public final static String pwdNotNull = "密码不能为空！";
    public final static String actIsLock = "账户已锁定，请稍后重试！";
    public final static String actIsDisable = "账户已停用！";
    public final static String logSuccess = "登录成功，正在跳转!";
    public final static String smsCodeInvalid = "短信验证码无效!";
    public final static String authCodeInvalid = "验证码无效!";
    public final static String authCodeIsEmpty = "验证码不能为空!";
    public final static String authCodePhoneIsEmpty = "电话号码不能为空!";
    public final static String notAuthUser = "无权操作!";
    public final static String codeGenerateFail = "编码生成失败!";
    public final static String codeAlreadyExists = "编码已存在!";
    public final static String pwdError = "密码异常!";
    public final static String oldPwdCheckError = "旧密码验证失败!";
    public final static String pwdFormatError = "密码长度不能低于8位，且需包含数字、大小写字符、符号。且密码不能包含账户名以及连续的数值、字母、符号!";
    public final static String newOldPwdEqually = "输入的新旧密码一致!";
    public final static String pwdReuse = "密码已使用过，请勿重复使用！";
    public final static String pwdChangeSuccess = "密码修改成功！";
    public final static String delSubFirst = "请先删除下级节点！";
    public final static String onlyRootNode = "仅有一个根节点,无法删除！";
    public final static String selectSuperiorError = "上级节点选择错误！";
    public final static String unknownAction = "未识别操作！";
    public final static String unknownUType = "未知用户类型！";
    public final static String configIsError = "基础配置参数错误:%s！";
    public final static String configIsHiatus = "缺少基础配置参数:%s！";
    public final static String reqCodeIsEmpty = "配置编码不能为空！";
    public final static String noOperationTypeSelected = "未指定操作类型！";

    public final static String uIdIsNull = "用户ID不能为空！";
    public final static String pIdIsNull = "项目ID不能为空！";
    public final static String tableIdIsNull = "数据表ID不能为空！";
    public final static String cIdIsNull = "单位ID不能为空！";
    public final static String ppIdIsNull = "项目岗位ID不能为空！";
    public final static String positionIsNull = "岗位ID不能为空！";
    public final static String tpIdIsNull = "模板岗位ID不能为空！";
    public final static String roleIdIsNull = "角色ID不能为空！";
    public final static String flowIdIsNull = "流程ID不能为空！";
    public final static String kIdIsNull = "流程规则关键字ID不能为空！";
    public final static String cnIdIsNull = "单位更名记录ID不能为空！";
    public final static String configIdIsNull = "基础配置ID不能为空！";
    public final static String codeSendAlready = "验证码已发送，请勿重复操作！";
    public final static String smsSendSuccess = "短信已发送！";
    public final static String uknowLoginType = "未知登录类型！";
    public final static String loginTypeIsNull = "请指定登录类型！";
    public final static String ukonwPhoneNum = "未知手机号！";
    public final static String phoneNumIsError = "请输入正确的手机号！";
    public final static String unSmsConf = "缺少短信配置！";
    public final static String serverReqFail = "服务调用失败！";
    public final static String unSupportSmsPlatform = "不支持的短信平台";
    public final static String unknownCpyDep = "未知单位部门！";
    public final static String smsSendFail = "短信发送失败！";
    public final static String smsCheckPass = "短信验证通过！";

    public final static String delTDepFirst = "请先删除关联项目组织机构模板！";


    public final static String thirdMsg_IllegalTime = "请求时间不合法！";

    public final static String smtCodeNoNull = "模板编码不能为空！";
    public final static String smIdNoNull = "系统消息ID不能为空！";
    public final static String smReceiveIdNoNull = "消息接收人不能为空！";
    public final static String msgTempCodeExist = "消息模板编码已存在！";
    public final static String msgTempNoNull = "消息模板不能为空！";
    public final static String sqlError = "非法的SQL语句！";
    public final static String sequenceIdIsNull = "规则ID不能为空！";


    public final static String columnNotPk = "列配置缺少主键！";
    public final static String userIsDisable = "用户已禁用！";
    public final static String userCannotLoginBack = "当前用户没有权限登录！";
    public final static String delPositionFirst = "部门下存在岗位信息无法删除！";
    public final static String contractNotPartyB = "合同缺少乙方单位信息！";

    public final static String bpmNotDel = "流程中正在使用，无法删除！";
}

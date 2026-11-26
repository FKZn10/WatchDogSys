package com.jpmsoft.custom.constants;

/**
 * 定义response msg
 * create by:Aaron
 */
public class CustomMsg {

    public final static String success = "操作成功！";
    public final static String stateNotModify = "流程已结束或作废，无法修改！";
    public final static String notTheReq = "实体中不包含字段:%s";
    public final static String formCUError = "表单创建或更新失败！";
    public final static String fileSaveRefershError = "附件保存失败，请稍后重试！";
    public final static String reqIllegal = "请求参数不合法！";
    public final static String dateFormatError = "时间格式异常！";
    public final static String unknowFormConf = "缺少表单配置！";
    public final static String formDataFormatError = "表单配置格式错误！";
    public final static String columnNotPk = "列配置缺少主键！";
    public final static String createTbError = "创建表操作执行失败！";
    public final static String tableExist = "表[%s]已存在！";
    public final static String tableNotExist = "表[%s]不存在！";
    public final static String notColumn = "缺少列配置！";
    public final static String infoNotExist = "信息不存在，请刷新重试！";
    public final static String noPermission = "无权操作！";
    public final static String fileSaveRefreshError = "附件保存失败，请稍后重试！";
    public final static String codeGenerError = "编码生成失败！";
    public final static String tokenInvalid = "访问令牌失效，请重新登录！";
    public final static String uTypeNull = "无法识别用户类型！";
    public final static String authTypeNull = "未配置鉴权类型！";
    public final static String notSyncTable = "未检索到数据库表信息！";
    public final static String notSyncTableColumn = "未检索到数据库表下的字段信息！";
    public final static String createUidStateNotOnly = "创建人、审批等相关字段编码配置与数据库表配置中的信息不一致，或缺失！";
}

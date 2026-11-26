package com.jpmsoft.gmgws.constants;

/**
 * 定义response msg
 * create by:Aaron
 */
public class ResMsg {

    public final static String dataVerifyError = "验证不通过，数据可能已被篡改！";
    public final static String dataWzSignError = "数据完整性签名失败！";
    public final static String canNotExpertCheck = "没有可申诉的考核事项或已超出可申诉期限！";
    public final static String notConId = "缺少合同ID！";
    public final static String secConExist = "当前项目标段下已存在合同信息！";
    public final static String maxAge = "超过最大年龄限制！";
    public final static String unExpertSelect2Vacation = "专家未被选取，无需申请请假！";
    public final static String userNotPlaintextNum = "用户信息缺少明文身份证信息！";
    public final static String userPmCannotGet = "用户信息(项目经理)获取失败！";
    public final static String vacationLatestTime = "已超过最迟申请时间，无法申请请假！";
    public final static String secDocumentNoBidTime = "开标时间未知！";
    public final static String tenantUserMax = "当前租户用户数已达到上限！";
    public final static String tenantProjectMax = "当前租户项目数已达到上限！";
    public final static String tenantExpire = "当前租户已过期！";
    public final static String onlyOneMilestonePlan = "当前项目已存在里程碑计划！";
    public final static String infoNotExist = "信息不存在，请刷新重试！";
    public final static String stateNotModify = "流程已结束或作废，无法修改！";
    public final static String recordUnFinish = "存在未结束流程，请勿重复操作！";
    public final static String stateNotDel = "流程已结束，无法删除！";
    public final static String stateCannotAct = "当前流程状态，不允许该操作！";
    public final static String noPermission = "无权操作！";
    public final static String fileNotFoundError = "缺少附件！";
    public final static String fileSaveRefershError = "附件保存失败，请稍后重试！";
    public final static String recordExist = "记录已存在！";
    public final static String notReqParam = "缺少请求参数！";
    public final static String codeGenerError = "编码生成失败！";
    public final static String codeIsEmpty = "编码为空！";
    public final static String codeIsRepeat = "编码重复！";
    public final static String nameCodeRepeat = "名称或编码重复！";
    public final static String archiveError = "归档失败！";
    public final static String archiveNotDel = "已确认归档，无法删除！";
    public final static String delAssociated = "请先删除关联信息！";
    public final static String delDownNodeFirst = "请先删除下级节点！";
    public final static String disableDownNodeFirst = "请先禁用下级节点！";
    public final static String disableRelationData = "存在关联数据，无法禁用！";
    public final static String correctScale = "请输入正确的比例！";
    public final static String replyNoExist = "批复编号已存在！";
    public final static String readRecord = "已有阅读记录，无法删除！";
    public final static String selectPeInfoCollectDtl = "请选择考核分汇总清单！";
    public final static String proCpProcess = "项目程序办理中！";
    public final static String parentNodeError = "上级节点选择错误！";
    public final static String canNotMove = "无法移动！";
    public final static String unknownAction = "未知操作！";
    public final static String notProCpDtl = "请勿删除已办理事项！";
    public final static String syncProDepError = "同步项目组织机构人员失败！";
    public final static String tokenInvalid = "访问令牌失效，请重新登录！";
    public final static String actError = "操作失败！";
    public final static String unClosedConMea = "存在未结束的计量审核，请勿重复发起！";
    public final static String delMeasureRecordFirst = "已发起计量，无法删除！";
    public final static String notConf = "缺少基础配置参数:%s";
    public final static String confFormatError = "基础配置参数:%s,格式异常！";
    public final static String notTheReq = "实体中不包含字段:%s";
    public final static String formCUError = "表单创建或更新失败！";
    public final static String projHaveMilestones = "该项目已存在里程碑！";
    public final static String confError = "配置有误！";
    public final static String proHavingBudget = "该项目已存在概算，请勿重复操作！";
    public final static String onDropOut = "不能跨级别拖拽排序！";
    public final static String secBidPlanChangeExist = "存在未结束的计划变更，请勿重复操作！";
    public final static String archiveTempExists = "文档模板\"%s\"已在\"%s\"目录关联，请先移除重复的归档文档模板！";
    public final static String lackMustPPUser = "缺少项目岗位人员配置:%s！";
    public final static String notToExpertPause = "%s天内存在评标会议，无法申请暂停评标！";
    public final static String ganttTaskNotDel = "任务<%s>已填报，无法变更删除！";
    public final static String relationUnToDelArchiveDir = "目录下存在关联文档模板或归档信息，删除失败！";
    public final static String notFoundFileTmp = "缺少文档模板配置[%s]！";
    public final static String wiOrderNoExists = "委托单编号已存在！";
    public final static String caCpyContractExists = "单位合同权限重复！";
    public final static String cpltNameExists = "模板名称重复！";
    public final static String ipvDeclareYearExists = "项目下当前年度记录重复！";
    public final static String icmReportingMonthExists = "项目下当前月份记录重复！";
    public final static String oaAmUserExists = "当前用户记录重复！";
    public final static String reportedCpyExists = "项目单位（统一社会信用代码）重复！";

    public final static String thisYearHavingAnnualPlan = "当前年度的项目计划已存在！";
    public final static String notId = "缺少主键ID！";
    public final static String taskCreateFail = "待办生成失败！";
    public final static String conPowerAttorneyCF = "合同下存在未作废的授权委托，请勿重复操作！";

    public final static String extractTechError = "技术类专家不满足抽取需求！";
    public final static String extractEcoError = "经济类专家不满足抽取需求！";

    public final static String noExpert2Extract = "没有专家可用于抽取！";

    public final static String exTechNumberError = "技术类专家人数不足！";
    public final static String exEcoNumberError = "经济类专家人数不足！";

    public final static String noData= "未查询到业务数据！";
}

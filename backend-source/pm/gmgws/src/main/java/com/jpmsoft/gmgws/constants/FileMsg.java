package com.jpmsoft.gmgws.constants;

/**
 * 响应msg
 * Created By:李文涛 aaron
 * Created Time:2022/6/30 12:06
 */
public class FileMsg {
    public final static String stateCannotAct = "当前流程状态，不允许该操作！";
    public final static String infoNotExist = "信息不存在，请刷新重试！";
    public final static String actSuccess = "操作成功！";
    public final static String uploadSuccess = "上传成功！";
    public final static String tokenInvalid = "访问令牌失效，请重新登录！";
    public final static String codeIsEmpty = "编码为空！";
    public final static String fileIdMust = "缺少业务表单ID！";
    public final static String lackProInfo = "缺少项目信息,请补充后提交！";
    public final static String missSysConfig = "缺少系统参数配置！";
    public final static String notFoundSysConfig = "未找到基础配置:%s！";
    public final static String missArchiveDirectory = "缺少归档目录配置！";
    public final static String missFileDirectory = "缺少根目录配置！";
    public final static String dirJgError = "目录结构无法解析！";
    public final static String fileUpError = "附件上传异常！";
    public final static String getDownUrlFail = "获取下载地址失败！";
    public final static String getFileInfoFail = "文件信息获取失败！";
    public final static String delFileFail = "删除文件失败！";
    public final static String downFail = "下载文件失败！";
    public final static String saveFail = "文件保存失败！";
    public final static String docx2PdfFail = "doc、docx转换pdf失败！";
    public final static String fileParsingFail = "文件解析失败！";
    public final static String exportGanttPlanWordFail = "进度计划Word导出失败！";
    public final static String importInvProjectPlanFail = "资金计划下达导入失败！";
    public final static String importConBopDtlFail = "工程量清单导入失败！";
    public final static String refreshFail = "附件状态刷新失败！";
    public final static String batchDownFail = "批量下载文件失败！";
    public final static String noneFile = "文件信息不存在！";
    public final static String noneDownFile = "无可下载文件！";
    public final static String createZipFail = "创建压缩文件失败！";
    public final static String onlyZip = "仅支持zip文件！";
    public final static String onlyXml = "仅支持xml文件！";
    public final static String onlyExcel = "仅支持excel文件！";
    public final static String notFoundExcel = "未检索到excel文件！";
    public final static String onlyExcelOrMpp = "仅支持excel、mpp文件！";
    public final static String onlyDoc = "仅支持doc、docx文件！";
    public final static String unSupportedFormat = "不支持的文件格式！";
    public final static String unSupportedCompress = "不支持的压缩包格式！";
    public final static String noneExportRecord = "没有可导出记录！";
    public final static String noneReplaceFile = "没有可替换文件！";
    public final static String fileFormatError = "文件格式错误！";
    public final static String notFoundInvBudgetDeclareDtl = "项目未申请概算申报！";
    public final static String notFoundBoqDtl = "缺少合同工程量清单！";
    public final static String notFoundBoqParent = "导入失败，序号：%s，项目名称：%s，无法匹配上级，请检查文件格式是否正确！";
    public final static String notFoundBudgetDeclareDtlCode = "不存在编码为%s概算清单！";
    public final static String notFoundBudgetDeclareDtlName = "不存在名称为%s概算清单！";
    public final static String notFoundProjByCode = "不存在编码为%s项目！";
    public final static String notFoundBoqDtlCode = "不存在编码为%s合同工程量清单详情！";
    public final static String unknownFormat = "%s行%s列格式异常,解析失败！";
    public final static String cellIsEmpty = "{0}行{1}列必填,请补充后提交！";
    public final static String cellIsFail = "{0}行{1}列解析失败！";
    public final static String excelNotData = "excel未解析到数据！";
    public final static String excelValidFail = "excel导入参数校验失败！";
    public final static String notFoundFile = "找不到源文件：%s！";
    public final static String moveError = "移动源文件时操作失败！";
    public final static String notFoundDictionary = "获取字典失败：%s！";
    public final static String archiveSuccess = "归档成功！";
    public final static String noReqParam = "缺少请求参数！";
    public final static String proConDy = "项目合同未对应！";

    public final static String fileAlreadyUp = "文件[%s]已上传，请勿重复上传！";
    public final static String upNoParaUid = "缺少必须参数[用户ID]！";
    public final static String upNoParaCode = "缺少必须参数[编码]！";
    public final static String upNoParaRelation = "缺少必须参数[关联ID]！";
    public final static String notFoundParentTemp = "文档模板%s的上级模板不存在！";
    public final static String notFoundTemp = "未找到文档模板[%s]！";
    public final static String notFoundTCode = "缺少文档模板编码！";
    public final static String notSelectProCon = "未选择项目合同！";

    public final static String ossActionError = "OSS%s操作失败！";
    public final static String ossUploadError = "OSS文件上传失败！";

    public final static String oaQuarterlyReportExist = "当年该季度季报已存在！";

    public final static String notData2Export = "没有可导出的数据！";

    public final static String dataExistsUnImport = "已存在数据，无法导入！";
    public final static String fileNotRemove = "附件[%s]不允许删除！";

    public final static String ikSegmenterException = "分词查询失败！";

    public final static String childCannotDel = "存在下级无法删除！";
    public final static String notSeeColumn = "未检测到有效列信息！";

    public final static String tableColumnDeletion = "数据表信息缺失或未设置导入导出列！";

    public final static String unKnowDic = "未知字典值[%s]";
    public final static String unKnowLinked = "未知链表项[%s]";
    public final static String onlyNotIns = "[%s]违反唯一约束！";
    public final static String expError = "导出失败！";

    public final static String noPk = "缺少主键列！";

    public final static String unzipError = "解压失败！";
    public final static String tmFileOut = "导入成功，已自动忽略相同文件！";

    public final static String ganttRateOut = "节点[%s]完成度已超出！";

    public final static String notFoundArchiveFile = "未检索到归档文件信息！";
    public final static String archiveMvTML = "同目录间无法复制！";

    public final static String archiveTM = "存在同名文件[%s]！";
}

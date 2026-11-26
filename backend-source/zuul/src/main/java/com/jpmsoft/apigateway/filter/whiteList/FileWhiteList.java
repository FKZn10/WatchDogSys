package com.jpmsoft.apigateway.filter.whiteList;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件相关接口名单
 * 文件操作接口日志操作时不重写入参
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 9:45
 */
public class FileWhiteList {

    private final static List<String> file_list = new ArrayList<>();

    static {
        file_list.add("/uploadFile");
        file_list.add("/downloadFile");
        file_list.add("/uploadRTEFile");
        file_list.add("/uploadArchiveFile");
        file_list.add("/uploadGanttPlanXml");
        file_list.add("/uploadGanttPlan");
        file_list.add("/uploadReplace");
        file_list.add("/toGetAuthCode");
        file_list.add("/uploadInvPlanExcel");
        file_list.add("/uploadBudgetDeclareExcel");
        file_list.add("/uploadDrBudgetDeclareExcel");
        file_list.add("/uploadNewBudgetDeclareExcel");
        file_list.add("/uploadProjectRegisterDtlExcel");
        file_list.add("/uploadPreProApprovalDtlExcel");
        file_list.add("/uploadPreProjectDtlExcel");
        file_list.add("/uploadFinalAccountsApplyExcel");
        file_list.add("/downloadAloneFile");
        file_list.add("/downloadOpenFile");
        file_list.add("/downloadArchivesFile");
        file_list.add("/doc2Pdf2View");
        file_list.add("/watermarkDown");
        file_list.add("/uploadAndCompressPdf");
        file_list.add("/uploadInvProPlanExcel");
        file_list.add("/uploadOaProReportExcel");
        file_list.add("/exportGanttTasksProcessWord");
        file_list.add("/uploadSecExpertExcel");
        file_list.add("/exportSecExpertSelectionRecordWord");
        file_list.add("/exportExpertSelectionRecordWordOffline");
        file_list.add("/bim/fileUpload");
        file_list.add("/jpm/batchDownloadByEntityIds");
        file_list.add("/fileServer/jpm/downloadAloneOpenFile");
        file_list.add("/fileServer/jpm/uploadBoqdXml");
        file_list.add("/fileServer/jpm/importConBoqDtl");
        file_list.add("/fileServer/jpm/importConBoqDtlByName");
        file_list.add("/fileServer/jpm/importSysConf");
        file_list.add("/fileServer/jpm/importOaQuarterlyReport");
        file_list.add("/fileServer/jpm/importSysFileTemp");
        file_list.add("/jimureport/jmreport/");
        file_list.add("/fileServer/jpm/uploadCjOaProReportExcel");
        file_list.add("/gmsso/cas");
        file_list.add("/fileServer/jpm/importGmInvProjectPlan");
        file_list.add("/fileServer/jpm/importConMeasureRecord");
        file_list.add("/fileServer/jpm/importInfrastructureMonth");
        file_list.add("/fileServer/jpm/importInfrastructureMonth");
        file_list.add("/fileServer/jpm/uploadGanttPlanOption");
        file_list.add("/fileServer/systemInit");
        file_list.add("/fileServer/jpm/batchDownloadByFileIds");
        file_list.add("/fileServer/tb/imxm");
        file_list.add("/fileServer/jpm/import");
        file_list.add("/fileServer/archive/importArchiveDirZip");
        file_list.add("/fileServer/jpm/achievementPackageDownload");
        file_list.add("/fileServer/ocr/ocrForm");
        file_list.add("/fileServer/ocr/ocr");
        file_list.add("/fileServer/jpm/openUpload");
    }

    public static Boolean contains(String path) {
        Boolean isFile = false;
        for (String str : file_list) {
            if (path.indexOf(str) != -1) {
                isFile = true;
                break;
            }
        }
        return isFile;
    }
}

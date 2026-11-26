package com.jpmsoft.apigateway.filter.whiteList;

import java.util.ArrayList;
import java.util.List;

/**
 * 不记入操作日志的接口
 * 打开Swagger页面的接口
 * 操作日志相关的接口
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 9:56
 */
public class NoLogWhiteList {

    private final static List<String> noLog_list = new ArrayList<>();

    static {
        noLog_list.add("/api/user");
        noLog_list.add("/pm/pm");
        noLog_list.add("/jpbpm/bpm");
        noLog_list.add("/fileServer/file");
        noLog_list.add("/SystemManage/GetLoggerInfoList");
        noLog_list.add("/SystemManage/DeleteLoggerInfo");
        noLog_list.add("/SystemManage/CreateLoggerInfo");
        noLog_list.add("/OrganizationManage/checkUserLoginTimeOut");
        noLog_list.add("/baseInfo/getCockpitDataBySec");
        noLog_list.add("/baseInfo/getCockpitDataByProject");
        noLog_list.add("/baseInfo/getProjectSecImgById");
        noLog_list.add("/intelligentEvaluateService/intelligentEvaluate");
    }

    public static Boolean contains(String path) {
        Boolean isFile = false;
        for (String str : noLog_list) {
            if (path.indexOf(str) != -1) {
                isFile = true;
                break;
            }
        }
        return isFile;
    }
}

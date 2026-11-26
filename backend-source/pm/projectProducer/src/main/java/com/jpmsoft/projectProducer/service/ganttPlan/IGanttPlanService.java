package com.jpmsoft.projectProducer.service.ganttPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Plan.GanttPlanRequest;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttPlan;
import com.jpmsoft.projectProducer.pojo.request.ganttplan.PreProjectPage;
import com.jpmsoft.projectProducer.pojo.request.ganttplan.TasksInfoRequest;
import org.apache.poi.ss.usermodel.Workbook;


public interface IGanttPlanService extends IService<GanttPlan> {

    GanttPlan selectByPid(String pId);

    BaseResponse createOrUpdateGanttPlanProjectOnly(GanttPlanRequest request);

    BaseResponse projectExistGanttPlan(String pId,String planId);

    BaseResponse createOrUpdateGanttPlan(GanttPlanRequest request);

    BaseResponse getGanttPlanList(GanttPlanRequest request, String uId);

    BaseResponse getGanttPlanListNJCJ(GanttPlanRequest request, String token);

    BaseResponse getGanttPlanClassInfo(GanttPlanRequest request, String uId);


    BaseResponse getGanttPlanByPlanId(String planId);

    BaseResponse statisticsProjectPlan(String uId);

    BaseResponse statisticsProJectPlanRanking(GanttPlanRequest request, String uId);

    /**
     * 根据项目ID统计计划总数
     *
     * @param pId
     * @return
     */
    BaseResponse statisticsPlanTotalBypId(String pId);

    BaseResponse statisticsPlanTotal(String teId);

    BaseResponse getGanttPlanAndTaskBypId(String pId, String type);

    BaseResponse GanttPlanStatisticsList(String uId, GanttPlanRequest request);

    /**
     * 进度计划里程碑汇总列表
     *
     * @param request
     * @param uId
     * @return
     */
    BaseResponse ganttPlanMilestoneList(GanttPlanRequest request, String uId);

     BaseResponse getPreprojectInfo(String uid, PreProjectPage page);

    BaseResponse getPreprojectClassInfo(String uId, PreProjectPage request);

    Workbook exportPreprojectInfoExcel(String uId, PreProjectPage request);

     BaseResponse getTaskImportance(String uid,PreProjectPage page);

    BaseResponse getTasksClassInfo(String uId, TasksInfoRequest request);

    Workbook exportTasksOverdueDueInfoExcel(String uId, TasksInfoRequest request);

    /**
     * 进度计划审批通过 生成建设程序模板，项目建设程序
     *
     * @param planId
     * @return
     */
    BaseResponse approved2GenerateProjectCplTemp(String planId);


    /**
     * 同步更新 建设程序模板，项目建设程序
     * 变更流程审批通过后调用
     * @param planId
     * @return
     */
    BaseResponse refreshProjectCplTempAntProjectCp(String planId);


    BaseResponse deleteUnStartGanttPlan(String planId);

    BaseResponse getTasksInfo(String uid, TasksInfoRequest request);

    BaseResponse getGanttPlanLedgerNJCJ(GanttPlanRequest request);

    BaseResponse getPreprojectInfoLedgerNJCJ(GanttPlanRequest request);
}

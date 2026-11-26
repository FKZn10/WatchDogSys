package com.jpmsoft.projectProducer.service.ganttPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.export.request.GanttPlanExportRequest;
import com.jpmsoft.common.model.request.Plan.GanttTasksRequest;
import com.jpmsoft.common.model.response.Plan.GanttPlanResponse;
import com.jpmsoft.common.model.response.Plan.GanttTasksResponse;
import com.jpmsoft.common.model.response.Plan.TaskProcessRateDTO;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.GanttTasksCjDTO;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.MilestoneSubmitDTO;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpDtl;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpRpr;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttPlan;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttTasks;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface IGanttTasksService extends IService<GanttTasks> {

    List<String> createOrUpdateGanttTasks(List<GanttTasksRequest> ganttTasks, String planId, String action);

    GanttPlanResponse getGanttTasksTreeByPlanId(GanttPlanResponse data);

    List<GanttTasks> getAllGanttTasksByPlanId(String planId);

    List<GanttTasks> getCplGanttTasksByPlanId(String planId);

    List<GanttTasks> getCplGanttTasksByPlanId(String planId,String taskType);

    List<GanttTasks> getGanttTasksByIds(List<String> ids);

    void updateParentTaskProgress(List<GanttTasks> datas, String taskParent, Set<GanttTasks> updates);

    /**
     * 任务完成情况填报比例完成后刷新任务状态以及上级任务
     *
     * @param gtp
     */
    void process2RefreshGanttTask(TaskProcessRateDTO gtp);

    /**
     * 统计逾期情况
     *
     * @return
     */
    BaseResponse GanttPlanStatisticsByOverdue(GanttTasksRequest request, String uId);

    /**
     * 任务逾期情况对应列表查询
     *
     * @param uId
     * @param request
     * @return
     */
    BaseResponse ganttPlanTaskStatisticsList(String uId, GanttTasksRequest request);

    /**
     * 查询逾期或者临期的数据
     *
     * @param request
     * @return
     */
    BaseResponse getGanttTasksByOverdue(GanttTasksRequest request, String uId);

    /**
     * 分页查询按期完成的数据
     *
     * @param request
     * @return
     */
    BaseResponse getGanttTasksByComplete(GanttTasksRequest request, String uId);


    /**
     * 根据项目ID获取所有计划任务根节点
     *
     * @param pId
     * @return
     */
    Map<String, Object> getRootGanttTasksBypId(String pId, List<String> types);

    BaseResponse getSecondaryNodesByTaskId(GanttTasksRequest request);

    GanttTasksResponse getGanttTasksById(String taskId);


    /**
     * 北京项目
     * 导出
     *
     * @param request
     * @return
     */
    BaseResponse getGanttTasksExport(GanttPlanExportRequest request);


    /**
     * 查询任务总数
     *
     * @return
     */
    Integer getGanttTasksCount(String teId);

    /**
     * 查询已完成任务总数
     *
     * @return
     */
    Integer getGanttTasksCompCount(String teId);

    /**
     * 单独更新开始时间接口
     *
     * @param request
     * @return
     */
    BaseResponse updateBeginTime(GanttTasksRequest request);

    /**
     * 里程碑任务一键完成
     *
     * @return
     */
    BaseResponse milestone2Complete(String taskId, String finishTime, LogUserInfoVo user);

    /**
     * 里程碑一键完成
     *
     * @return
     */
    BaseResponse milestone2Complete(MilestoneSubmitDTO request, LogUserInfoVo user);

    List<GanttTasksResponse> getAllTaskByPlanIds(List<String> planIds);

    /**
     * 建设程序成果上传通过回写进度计划完成状态
     */
    void cpApprove2SyncGanttTaskState(ProjectCpDtl dtl, GanttPlan plan, ProjectCpRpr rpr);

    void deleteTasksByPlanId(String planId);

    BaseResponse queryGanttTasksProcessByTaskID(String taskId);

//    BaseResponse createGanttTasksByTaskID(GanttTasksProcessRequest request);

    BaseResponse rewriteGanttTasksByTaskID(String taskId);

//    GanttTasksProcess convert2GanttTasksProcess(GanttTasksProcessRequest request);


    BaseResponse getGanttTasksByProject(GanttTasksCjDTO request);

    BaseResponse getGanttTasksByProjectClassInfo(GanttTasksCjDTO request);

    BaseResponse getCjMilestonesGanttTasks(GanttTasksRequest request);

    BaseResponse getCjGanttTasksById(String taskId);

    /**
     * 检查任务是否已完成
     *
     * @param pId
     * @param taskName
     * @param gsId
     * @return
     */
    BaseResponse checkGanttTaskCompleted(String pId, String taskName, String gsId);
}

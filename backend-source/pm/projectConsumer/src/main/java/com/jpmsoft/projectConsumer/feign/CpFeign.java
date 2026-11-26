package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.AchievementsRequest;
import com.jpmsoft.common.model.request.BatchUpdatePerson;
import com.jpmsoft.common.model.request.TimeFrameRequest;
import com.jpmsoft.common.model.request.proCp.*;
import com.jpmsoft.common.model.requestAndResponse.proCp.ProjectCpDtlCancelModel;
import com.jpmsoft.projectConsumer.pojo.ProjectCpDtl;
import com.jpmsoft.projectConsumer.pojo.dto.construction.LibraryFileTypeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 建设程序
 */
@FeignClient(name = "project-producer", contextId = "constructionProcedure")
public interface CpFeign {
    /*********************************************** 项目建设程序库 ***********************************************/

    /**
     * 新增或修改
     *
     * @param request
     * @return
     */
    @PostMapping("/v1/ConstructionProcedure/proCpLibrary/createOrUpdateProCpLibrary")
    BaseResponse createOrUpdateProCpLibrary(@RequestBody ProjectCpLibraryRequset request);

    /**
     * ID删除
     *
     * @param cplId
     * @return
     */
    @GetMapping("/v1/ConstructionProcedure/proCpLibrary/deleteProCpLibraryById")
    BaseResponse deleteProCpLibraryById(@RequestParam String cplId);

    /**
     * 获取List集合
     *
     * @return
     */
    @PostMapping("/v1/ConstructionProcedure/proCpLibrary/getProCpLibraryTreeList")
    BaseResponse getProCpLibraryTreeList(@RequestBody ProjectCpLibraryRequset request);

    @PostMapping("/v1/ConstructionProcedure/proCpLibrary/updateLibraryFileTypeBath")
    BaseResponse updateLibraryFileTypeBath(@RequestBody LibraryFileTypeDTO request);

    @GetMapping("/v1/ConstructionProcedure/proCpLibrary/getProCpChangeDtlLibraryTreeList")
    BaseResponse getProCpChangeDtlLibraryTreeList(@RequestParam String id, @RequestHeader String token);

    @GetMapping("/v1/ConstructionProcedure/proCpLibrary/upOrDownProCpLibrary")
    BaseResponse upOrDownProCpLibrary(@RequestParam String cplId, @RequestParam String action);

    /*********************************************** 建设程序项目模板 ***********************************************/

    /**
     * 新增或者修改 建设程序项目模板
     *
     * @param request
     * @return
     */
    @PostMapping("/v1/ConstructionProcedure/proCplTemplate/createOrUpdateProCplTemplate")
    BaseResponse createOrUpdateProCplTemplate(@RequestBody ProCplTemplateRequest request);

    /**
     * 获取集合
     *
     * @param request
     * @return
     */
    @PostMapping("/v1/ConstructionProcedure/proCplTemplate/getProCplTemplateList")
    BaseResponse getProCplTemplateList(@RequestBody ProCplTemplateRequest request);

    /**
     * 删除
     *
     * @param cpltId
     * @return
     */
    @GetMapping("/v1/ConstructionProcedure/proCplTemplate/deleteProCplTemplateById")
    BaseResponse deleteProCplTemplateById(@RequestParam String cpltId);

    /*********************************************** 建设程序项目模板详情 ***********************************************/
    /**
     * 获取建设程序项目模板详情树
     *
     * @param cpltId
     * @return
     */
    @GetMapping("/v1/ConstructionProcedure/proCplTemplateDtl/getProCplTemplateDtlTreeByCpltId")
    BaseResponse getProCplTemplateDtlTreeByCpltId(@RequestParam String cpltId);

    @GetMapping("/v1/ConstructionProcedure/proCplTemplateDtl/getProCplTemplateDtlIdListByCpltId")
    BaseResponse getProCplTemplateDtlIdListByCpltId(@RequestParam String cpltId);

    @GetMapping("/v1/ConstructionProcedure/proCplTemplateDtl/deleteProCplTemplateDtlByCpltdId")
    BaseResponse deleteProCplTemplateDtlByCpltdId(@RequestParam String cpltdId);

    /*********************************************** 项目建设程序 ***********************************************/

    @GetMapping("/v1/ConstructionProcedure/proCp/projectApprove2generateCp")
    BaseResponse projectApprove2generateCp(@RequestParam String pid, @RequestHeader String token);

    @GetMapping("/v1/ConstructionProcedure/proCp/generateProjectCpWithUnCpProject")
    BaseResponse generateProjectCpWithUnCpProject(@RequestHeader String token);

    @PostMapping("/v1/ConstructionProcedure/proCp/createOrUpdateProjectCp")
    BaseResponse createOrUpdateProjectCp(@RequestBody ProjectCpRequest request);

    @PostMapping("/v1/ConstructionProcedure/proCp/getProCpList")
    BaseResponse getProCpList(@RequestBody ProjectCpRequest request, @RequestHeader String token);

    @GetMapping("/v1/ConstructionProcedure/proCp/getProjectCpByCpId")
    BaseResponse getProjectCpByCpId(@RequestParam String cpId);

    @PostMapping("/v1/ConstructionProcedure/proCp/statisticalProCp")
    BaseResponse statisticalProCp(@RequestBody ProjectCpRequest request);

    /**
     * 统计用户建设项目
     *
     * @param request
     * @return
     */
    @PostMapping("/v1/ConstructionProcedure/proCp/statisticalUserProCp")
    BaseResponse statisticalUserProCp(@RequestBody ProjectCpRequest request);

    /**
     * ID删除记录
     *
     * @param cpId
     * @return
     */
    @GetMapping("/v1/ConstructionProcedure/proCp/deleteProCpByCpId")
    BaseResponse deleteProCpByCpId(@RequestParam String cpId);

    @GetMapping("/v1/ConstructionProcedure/proCp/getCpIdByPid")
    BaseResponse getCpIdByPid(@RequestParam String pId);

    @PostMapping("/v1/ConstructionProcedure/proCp/getProCpAndDtlList")
    BaseResponse getProCpAndDtlList(@RequestBody ProjectCpRequest request, @RequestHeader String token);

    /*********************************************** 项目建设程序详情 ***********************************************/
    /**
     * 根据项目建设程序ID获取树形数据
     *
     * @param cpId
     * @return
     */
    @GetMapping("/v1/ConstructionProcedure/proCpDtl/getProCpDtlTreeByCpId")
    BaseResponse getProCpDtlTreeByCpId(@RequestParam String cpId);

    /**
     * 城建 根据项目建设程序ID获取数据
     *
     * @param cpId
     * @return
     */
    @GetMapping("/v1/ConstructionProcedure/proCpDtl/getProCpDtlTreeByCpIdNJCJ")
    BaseResponse getProCpDtlTreeByCpIdNJCJ(@RequestParam String cpId);

    /**
     * 提交办理结果
     *
     * @param request
     * @return
     */
    @PostMapping("/v1/ConstructionProcedure/proCpDtl/submitProcessingResults")
    BaseResponse submitProcessingResults(@RequestBody ProjectCpDtlRequest request);

    @GetMapping("/v1/ConstructionProcedure/proCpDtl/proCpDtlHaveSysFile")
    BaseResponse proCpDtlHaveSysFile(@RequestParam String cpdId);

    @GetMapping("/v1/ConstructionProcedure/proCpDtl/getProCpDtlIdListByCpId")
    BaseResponse getProCpDtlIdListByCpId(@RequestParam String cpId);

    @GetMapping("/v1/ConstructionProcedure/proCpDtl/getProCpDtlIdListByPid")
    BaseResponse getProCpDtlIdListByPid(@RequestParam String pId);

    @PostMapping("/v1/ConstructionProcedure/proCpDtl/getProCpDtlListByCpId")
    BaseResponse getProCpDtlListByCpId(@RequestBody ProjectCpDtlRequest request);

    @PostMapping("/v1/ConstructionProcedure/proCpDtl/getProCpDtlListByStatistics")
    BaseResponse getProCpDtlListByStatistics(@RequestBody ProjectCpDtlRequest request);

    /**
     * 办理统计
     * 所有手续
     * 已完成 未完成
     * 超时未完成
     * 分页查询
     *
     * @param request
     * @return
     */
    @PostMapping("/v1/ConstructionProcedure/proCpDtl/getProCpDtlByUser")
    BaseResponse getProCpDtlByUser(@RequestBody ProjectCpDtlRequest request);

    /**
     * 项目ID获取建设项目详情
     *
     * @param pId
     * @return
     */
    @GetMapping("/v1/ConstructionProcedure/proCpDtl/getProCpDtlByPid")
    BaseResponse getProCpDtlByPid(@RequestParam String pId);


    /**
     * 项目ID获取建设项目详情 带文件信息
     *
     * @param request
     * @return
     */
    @PostMapping("/v1/ConstructionProcedure/proCpDtl/getProCpDtlTreeByPid")
    BaseResponse getProCpDtlTreeByPid(@RequestBody ProjectCpDtlRequest request);

    /**
     * ID删除建设项目详情
     *
     * @param cpDtlId
     * @return
     */
    @GetMapping("/v1/ConstructionProcedure/proCpDtl/deleteProCpDtlByCpDtlId")
    BaseResponse deleteProCpDtlByCpDtlId(@RequestParam String cpDtlId);

    /**
     * 修改单条信息
     *
     * @return
     */
    @PostMapping("/v1/ConstructionProcedure/proCpDtl/updateProCpDtlByCpDtlId")
    BaseResponse updateProCpDtlByCpDtlId(@RequestBody ProjectCpDtlRequest request);

    /**
     * 获取成果文件列表
     *
     * @param request
     * @return
     */
    @PostMapping("/v1/ConstructionProcedure/proCpDtl/getAchievementsList")
    BaseResponse getAchievementsList(@RequestBody AchievementsRequest request);

    /**
     * 项目程序与详情数量统计
     *
     * @return
     */
    @PostMapping("/v1/ConstructionProcedure/proCpDtl/statisticalProCpAndDtl")
    BaseResponse statisticalProCpAndDtl(@RequestBody(required = false) TimeFrameRequest request);

    @PostMapping("/v1/ConstructionProcedure/proCpDtl/statisticalProCpDtlByTimeFrame")
    BaseResponse statisticalProCpDtlByTimeFrame(@RequestBody TimeFrameRequest request);

    /**
     * 项目建设程序完成情况列表
     *
     * @param request
     * @return
     */
    @PostMapping("/v1/ConstructionProcedure/proCpDtl/getProCpDtlComplateList")
    BaseResponse getProCpDtlComplateList(@RequestBody ProjectCpDtlRequest request);

    /**
     * 修改建设程序父级状态
     *
     * @param cpdId
     * @return
     */
    @GetMapping("/v1/ConstructionProcedure/proCpDtl/setCpDtlStatus")
    BaseResponse setCpDtlStatus(@RequestParam String cpdId);

    @PostMapping("/v1/ConstructionProcedure/proCpDtl/updateCpdFileTypeBath")
    BaseResponse updateCpdFileTypeBath(@RequestBody LibraryFileTypeDTO request);
    @PostMapping("/v1/ConstructionProcedure/proCpDtl/createProjectCpDtlBath")
    BaseResponse createProjectCpDtlBath(@RequestBody List<ProjectCpDtl> request);
    @GetMapping("/v1/ConstructionProcedure/proCpDtl/selectProjectCpRprByCpdId")
    BaseResponse selectProjectCpRprByCpdId(@RequestParam String cpdId,@RequestHeader String token);

    /**
     * 项目建设程序临期情况列表
     *
     * @return
     */
    @PostMapping("/v1/ConstructionProcedure/proCpDtl/getProCpDtlTemporaryListByuid")
    BaseResponse getProCpDtlTemporaryListByuid(@RequestBody ProCpDtlTemporaryRequest request, @RequestHeader String token);

    @PostMapping("/v1/ConstructionProcedure/proCpDtl/batchUpdateCpDtlPerson")
    BaseResponse batchUpdateCpDtlPerson(@RequestBody BatchUpdatePerson request);


    @PostMapping("/v1/ConstructionProcedure/proCpDtl/batchUpdateCpDtlCreateUser")
    BaseResponse batchUpdateCpDtlCreateUser(@RequestBody BatchUpdatePerson request);

    @PostMapping("/v1/ConstructionProcedure/proCpDtl/batchUpdateCpDtlPersonWithDepPm")
    BaseResponse batchUpdateCpDtlPersonWithDepPm(@RequestBody BatchUpdatePerson request, @RequestHeader String token);

    @PostMapping("/v1/ConstructionProcedure/proCpDtl/batchUpdateCpDtlCreateUserWithDepPm")
    BaseResponse batchUpdateCpDtlCreateUserWithDepPm(@RequestBody BatchUpdatePerson request, @RequestHeader String token);

    @GetMapping("/v1/ConstructionProcedure/proCpDtl/getCpDtlById")
    BaseResponse getCpDtlById(@RequestParam String cpdId);

    /*********************************************** 子任务列表 ***********************************************/
    /**
     * 根据id删除子任务
     *
     * @param pcsId
     * @return
     */
    @GetMapping("/v1/ConstructionProcedure/ProjectCpSubtask/deleteProjectCpSubtaskById")
    BaseResponse deleteProjectCpSubtaskById(@RequestParam String pcsId);

    /**
     * 根据id获取子任务
     *
     * @param pcsId
     * @return
     */
    @GetMapping("/v1/ConstructionProcedure/ProjectCpSubtask/getProjectCpSubtaskById")
    BaseResponse getProjectCpSubtaskById(@RequestParam String pcsId);

    /**
     * 获取子任务列表
     *
     * @param request
     * @return
     */
    @PostMapping("/v1/ConstructionProcedure/ProjectCpSubtask/getProjectCpSubtaskList")
    BaseResponse getProjectCpSubtaskList(@RequestBody ProjectCpSubtaskRequest request);

    /**
     * 新建之任务
     *
     * @param request
     * @return
     */
    @PostMapping("/v1/ConstructionProcedure/ProjectCpSubtask/createOrUpdateProjectCpSubtask")
    BaseResponse createOrUpdateProjectCpSubtask(@RequestBody ProjectCpSubtaskRequest request);

    /*********************************************** 工作办理记录 ***********************************************/

    @PostMapping("/v1/ConstructionProcedure/proCpWorkRecord/createOrUpdateProCpWorkRecord")
    BaseResponse createOrUpdateProCpWorkRecord(@RequestBody ProjectCpWorkRecordRequest request);

    @GetMapping("/v1/ConstructionProcedure/proCpWorkRecord/deleteProCpWorkRecordById")
    BaseResponse deleteProCpWorkRecordById(@RequestParam String cpwrId);

    @PostMapping("/v1/ConstructionProcedure/proCpWorkRecord/getProCpWorkRecordList")
    BaseResponse getProCpWorkRecordList(@RequestBody ProjectCpWorkRecordRequest request);

    /*********************************************** 经验分享 ***********************************************/
    @PostMapping("/v1/ConstructionProcedure/proCpExperience/createOrUpdateProCpExperience")
    BaseResponse createOrUpdateProCpExperience(@RequestBody ProjectCpExperienceRequest request);

    @GetMapping("/v1/ConstructionProcedure/proCpExperience/deleteProCpExperienceById")
    BaseResponse deleteProCpExperienceById(@RequestParam String cpeId);

    @PostMapping("/v1/ConstructionProcedure/proCpExperience/getProCpExperienceList")
    BaseResponse getProCpExperienceList(@RequestBody ProjectCpExperienceRequest request);

    /*********************************************** 取消项目建设程序详情 ***********************************************/

    @PostMapping("/v1/ConstructionProcedure/cpDtlCancel/createOrUpdateProjectCpDtlCancel")
    BaseResponse createOrUpdateProjectCpDtlCancel(@RequestBody ProjectCpDtlCancelModel request);

    @PostMapping("/v1/ConstructionProcedure/cpDtlCancel/getProjectCpDtlCancelList")
    BaseResponse getProjectCpDtlCancelList(@RequestBody ProjectCpDtlCancelModel request, @RequestHeader String token);

    @GetMapping("/v1/ConstructionProcedure/cpDtlCancel/getProjectCpDtlCancelById")
    BaseResponse getProjectCpDtlCancelById(@RequestParam String cdcId);

    @GetMapping("/v1/ConstructionProcedure/cpDtlCancel/enableProjectCpDtlStatus")
    BaseResponse enableProjectCpDtlStatus(@RequestParam String cdcId);

    @GetMapping("/v1/ConstructionProcedure/cpDtlCancel/existApproveCpCancel")
    BaseResponse existApproveCpCancel(@RequestParam String cpdId);

    /*********************************************** 建设程序变更 ***********************************************/

    @PostMapping("/v1/ConstructionProcedure/proCpChange/createOrUpdateProjectCpChange")
    BaseResponse createOrUpdateProjectCpChange(@RequestBody ProjectCpChangeDTO request);

    @PostMapping("/v1/ConstructionProcedure/proCpChange/getProCpChangeList")
    BaseResponse getProCpChangeList(@RequestBody ProjectCpChangeDTO request, @RequestHeader String token);

    @GetMapping("/v1/ConstructionProcedure/proCpChange/deleteProCpChangeById")
    BaseResponse deleteProCpChangeById(@RequestParam String id);

    @GetMapping("/v1/ConstructionProcedure/proCpChange/getProjectCpChangeById")
    BaseResponse getProjectCpChangeById(@RequestParam String id);

    @GetMapping("/v1/ConstructionProcedure/proCpChange/getProjectCpChangeCplIdList")
    BaseResponse getProjectCpChangeCplIdList(@RequestParam String id);

    @GetMapping("/v1/ConstructionProcedure/proCpChange/approve2SyncProjectCpDtl")
    BaseResponse approve2SyncProjectCpDtl(@RequestParam String id);

    @GetMapping("/v1/ConstructionProcedure/proCpChange/getProCpChangeDtlTreeById")
    BaseResponse getProCpChangeDtlTreeById(@RequestParam String id);


    /*********************************************** 工作办理记录评论 ***********************************************/
    @PostMapping("/v1/ConstructionProcedure/proCpWorkComment/createOrUpdateWorkComment")
    BaseResponse createOrUpdateWorkComment(@RequestBody ProjectCpWorkCommentDTO request);

    @PostMapping("/v1/ConstructionProcedure/proCpWorkComment/getWorkCommentList")
    BaseResponse getWorkCommentList(@RequestBody ProjectCpWorkCommentDTO request);

    @GetMapping("/v1/ConstructionProcedure/proCpWorkComment/deleteById")
    BaseResponse deleteById(@RequestParam String id);

    /*********************************************** 成果上传，多次办理 ***********************************************/

    @GetMapping("/v1/ConstructionProcedure/ProjectCpRpr/approve2SyncGanttTaskState")
    BaseResponse approve2SyncGanttTaskState(@RequestParam String id);
    @GetMapping("/v1/ConstructionProcedure/ProjectCpRpr/canToDeleteGanttTaskChange")
    BaseResponse canToDeleteGanttTaskChange(@RequestParam String pId, @RequestParam String cplId);

    @GetMapping("/v1/ConstructionProcedure/proCp/getTaskIsManyByCplIdPlanId")
    BaseResponse getTaskIsManyByCplIdPlanId(@RequestParam("cpdCplId") String cpdCplId, @RequestParam("pId") String pId);

    @GetMapping("/v1/ConstructionProcedure/proCpDtl/getGantSectionListByPid")
    BaseResponse getGantSectionListByPid(@RequestParam("pId") String pId);

    @PostMapping("/v1/ConstructionProcedure/proCpDtl/updateCpdTimeByCprId")
    BaseResponse updateCpdTimeByCprId(@RequestBody ProjectCpDtlRequest request);

    @PostMapping("/v1/ConstructionProcedure/proCp/getProCpLedgerNJCJ")
    BaseResponse getProCpLedgerNJCJ(@RequestBody ProjectCpRequest request);

    @PostMapping("/v1/ConstructionProcedure/proCpDtl/getAllCpDtlList")
    BaseResponse getAllCpDtlList(@RequestBody TimeFrameRequest request);

    @PostMapping("/v1/ConstructionProcedure/proCp/generateProjectCpWithPid")
    BaseResponse generateProjectCpWithPid(@RequestBody ProjectCpRequest request);
}

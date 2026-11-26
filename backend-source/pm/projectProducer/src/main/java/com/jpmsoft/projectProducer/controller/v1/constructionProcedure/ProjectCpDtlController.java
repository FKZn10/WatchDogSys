package com.jpmsoft.projectProducer.controller.v1.constructionProcedure;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.AchievementsRequest;
import com.jpmsoft.common.model.request.BatchUpdatePerson;
import com.jpmsoft.common.model.request.TimeFrameRequest;
import com.jpmsoft.common.model.request.proCp.ProCpDtlTemporaryRequest;
import com.jpmsoft.common.model.request.proCp.ProjectCpDtlRequest;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.pojo.dto.construction.LibraryFileTypeDTO;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpDtl;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpDtlService;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/ConstructionProcedure/proCpDtl")
public class ProjectCpDtlController {
    @Resource
    private IProjectCpDtlService projectCpDtlService;
    @Resource
    private IProjectCpService projectCpService;

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private RedisUtils redisUtils;


    @GetMapping("/resetAllCpDtlState")
    public BaseResponse resetAllCpDtlState(@RequestParam String cpId) {
        projectCpDtlService.resetAllCpDtlState(projectCpDtlService.getAllByCpId(cpId), true);
        return BaseResponse.ok();
    }

    /**
     * 根据项目建设程序ID获取树形数据
     *
     * @param cpId
     * @return
     */
    @GetMapping("/getProCpDtlTreeByCpId")
    public BaseResponse getProCpDtlTreeByCpId(@RequestParam String cpId, @RequestHeader String token) {
        log.info("getProCpDtlTreeByCpId");
        return projectCpDtlService.getProCpDtlTreeByCpId(cpId, redisUtils.getTeId(token));
    }

    /**
     * 城建 根据项目建设程序ID获取数据
     *
     * @param cpId
     * @return
     */
    @GetMapping("/getProCpDtlTreeByCpIdNJCJ")
    public BaseResponse getProCpDtlTreeByCpIdNJCJ(@RequestParam String cpId, @RequestHeader String token) {
        log.info("getProCpDtlTreeByCpIdNJCJ");
        return projectCpDtlService.getProCpDtlTreeByCpIdNJCJ(cpId, redisUtils.getTeId(token));
    }

    @TenantId
    @PostMapping("/submitProcessingResults")
    public BaseResponse submitProcessingResults(@RequestBody ProjectCpDtlRequest request) {
        log.info("submitProcessingResults");
        return projectCpDtlService.submitProcessingResults(request);
    }


    @GetMapping("/proCpDtlHaveSysFile")
    public BaseResponse proCpDtlHaveSysFile(@RequestParam String cpdId) {
        log.info("proCpDtlHaveSysFile");
        return projectCpDtlService.proCpDtlHaveSysFile(cpdId);
    }

    @GetMapping("/getProCpDtlIdListByCpId")
    public BaseResponse getProCpDtlIdListByCpId(@RequestParam String cpId) {
        log.info("getProCpDtlIdListByCpId");
        return projectCpDtlService.getProCpDtlIdListByCpId(cpId);
    }

    /**
     * 根据项目获取其已勾选的程序
     * ID列表
     *
     * @param pId
     * @return
     */
    @GetMapping("/getProCpDtlIdListByPid")
    public BaseResponse getProCpDtlIdListByPid(@RequestParam String pId) {
        log.info("getProCpDtlIdListByPid");
        return projectCpDtlService.getProCpDtlIdListByPid(pId);
    }


    @TenantId
    @PostMapping("/getProCpDtlListByCpId")
    public BaseResponse getProCpDtlListByCpId(@RequestBody ProjectCpDtlRequest request) {
        log.info("getProCpDtlListByCpId");
        return projectCpDtlService.getProCpDtlListByCpId(request);
    }


    @TenantId
    @PostMapping("/getProCpDtlListByStatistics")
    public BaseResponse getProCpDtlListByStatistics(@RequestBody ProjectCpDtlRequest request) {
        log.info("getProCpDtlListByStatistics");
        return projectCpDtlService.getProCpDtlListByStatistics(request);
    }

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
    @TenantId
    @PostMapping("/getProCpDtlByUser")
    public BaseResponse getProCpDtlByUser(@RequestBody ProjectCpDtlRequest request) {
        log.info("getProCpDtlByUser");
        return projectCpDtlService.getProCpDtlByUser(request);
    }

    /**
     * 项目ID获取建设项目详情
     *
     * @param pId
     * @return
     */
    @GetMapping("/getProCpDtlByPid")
    public BaseResponse getProCpDtlByPid(@RequestParam String pId, @RequestHeader String token) {
        log.info("getProCpDtlByPid");
        return projectCpDtlService.getProCpDtlByPid(pId, redisUtils.getTeId(token));
    }


    /**
     * 项目ID获取建设项目详情 带文件信息
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/getProCpDtlTreeByPid")
    public BaseResponse getProCpDtlTreeByPid(@RequestBody ProjectCpDtlRequest request) {
        log.info("getProCpDtlTreeByPid");
        return projectCpDtlService.getProCpDtlTreeByPid(request);
    }

    /**
     * ID删除建设项目详情
     *
     * @param cpDtlId
     * @return
     */
    @GetMapping("/deleteProCpDtlByCpDtlId")
    public BaseResponse deleteProCpDtlByCpDtlId(@RequestParam String cpDtlId) {
        log.info("deleteProCpDtlByCpDtlId");
        return projectCpDtlService.deleteProCpDtlByCpDtlId(cpDtlId);
    }

    /**
     * 修改单条信息
     *
     * @return
     */
    @PostMapping("/updateProCpDtlByCpDtlId")
    public BaseResponse updateProCpDtlByCpDtlId(@RequestBody ProjectCpDtlRequest request) {
        log.info("updateProCpDtlByCpDtlId");
        return projectCpDtlService.updateProCpDtlByCpDtlId(request);
    }

    /**
     * 获取成果文件列表
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/getAchievementsList")
    public BaseResponse getAchievementsList(@RequestBody AchievementsRequest request) {
        log.info("getAchievementsList");
        return sysFileAnnexService.getAchievementsList(request);
    }

    /**
     * 项目程序与详情数量统计
     *
     * @return
     */
    @TenantId
    @PostMapping("/statisticalProCpAndDtl")
    public BaseResponse statisticalProCpAndDtl(@RequestBody(required = false) TimeFrameRequest request) {
        log.info("statisticalProCpAndDtl");
        return projectCpDtlService.statisticalProCpAndDtl(request);
    }

    @TenantId
    @PostMapping("/statisticalProCpDtlByTimeFrame")
    public BaseResponse statisticalProCpDtlByTimeFrame(@RequestBody TimeFrameRequest request) {
        log.info("statisticalProCpAndDtl");
        if (ValidateUtil.isEmpty(request.getStartTime()) || ValidateUtil.isEmpty(request.getEndTime()))
            return BaseResponse.fail(ResMsg.notReqParam);
        return projectCpDtlService.statisticalProCpDtlByTimeFrame(request);
    }

    /**
     * 项目建设程序完成情况列表
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/getProCpDtlComplateList")
    public BaseResponse getProCpDtlComplateList(@RequestBody ProjectCpDtlRequest request) {
        log.info("getProCpDtlComplateList");
        return projectCpDtlService.getProCpDtlComplateList(request);
    }


    /**
     * 项目建设程序临期情况列表
     *
     * @param token
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/getProCpDtlTemporaryListByuid")
    public BaseResponse getProCpDtlTemporaryListByuid(@RequestHeader String token, @RequestBody ProCpDtlTemporaryRequest request) {
        log.info("getProCpDtlTemporaryListByuid");
        request.setCpdPersonId(redisUtils.getUid(token));
        return projectCpDtlService.getProCpDtlTemporaryListByuid(request);
    }

    @PostMapping("/batchUpdateCpDtlPerson")
    public BaseResponse batchUpdateCpDtlPerson(@RequestBody BatchUpdatePerson request) {
        log.info("batchUpdateCpDtlPerson");
        return projectCpDtlService.batchUpdateCpDtlPerson(request);
    }

    @PostMapping("/batchUpdateCpDtlCreateUser")
    public BaseResponse batchUpdateCpDtlCreateUser(@RequestBody BatchUpdatePerson request) {
        log.info("batchUpdateCpDtlCreateUser");
        return projectCpDtlService.batchUpdateCpDtlCreateUser(request);
    }


    @PostMapping("/batchUpdateCpDtlPersonWithDepPm")
    public BaseResponse batchUpdateCpDtlPersonWithDepPm(@RequestBody BatchUpdatePerson request, @RequestHeader String token) {
        log.info("batchUpdateCpDtlPersonWithDepPm");
        return projectCpDtlService.batchUpdateCpDtlPersonWithDepPm(request, token);
    }

    @PostMapping("/batchUpdateCpDtlCreateUserWithDepPm")
    public BaseResponse batchUpdateCpDtlCreateUserWithDepPm(@RequestBody BatchUpdatePerson request, @RequestHeader String token) {
        log.info("batchUpdateCpDtlCreateUserWithDepPm");
        return projectCpDtlService.batchUpdateCpDtlCreateUserWithDepPm(request, token);
    }

    @GetMapping("/getCpDtlById")
    public BaseResponse getCpDtlById(@RequestParam String cpdId) {
        log.info("getCpDtlById");
        return projectCpDtlService.getCpDtlById(cpdId);
    }

    @GetMapping("/setCpDtlStatus")
    public BaseResponse setCpDtlStatus(@RequestParam String cpdId) {
        log.info("setCpDtlStatus");
        return projectCpDtlService.setCpDtlStatus(cpdId);
    }

    @PostMapping("/updateCpdFileTypeBath")
    public BaseResponse updateCpdFileTypeBath(@RequestBody  LibraryFileTypeDTO request){
        log.info("updateCpdFileTypeBath");
        return projectCpDtlService.updateFileTypeBath(request);
    }
    @PostMapping("/createProjectCpDtlBath")
    public BaseResponse createProjectCpDtlBath(@RequestBody  List<ProjectCpDtl> request){
        log.info("createProjectCpDtlBath");
        return projectCpDtlService.createProjectCpDtlBath(request);
    }

    @GetMapping("/selectProjectCpRprByCpdId")
    public BaseResponse selectProjectCpRprByCpdId(@RequestParam  String cpdId, @RequestHeader String token){
        log.info("selectProjectCpRprByCpdId");
       LogUserInfoVo user= redisUtils.getUserInfoByToken(token);
        return projectCpDtlService.selectProjectCpRprByCpdId(cpdId,user.getId(),user.getUType());
    }

    @GetMapping("/getGantSectionListByPid")
    public BaseResponse getGantSectionListByPid(@RequestParam("pId") String pId) {
        log.info("getGantSectionListByPid");
        return projectCpDtlService.getGantSectionListByPid(pId);
    }


    @PostMapping("/updateCpdTimeByCprId")
    public BaseResponse updateCpdTimeByCprId(@RequestBody ProjectCpDtlRequest request) {
        log.info("updateCpdTimeByCprId");
        return projectCpDtlService.updateCpdTimeByCprId(request);
    }

    @PostMapping("/getAllCpDtlList")
    public BaseResponse getAllCpDtlList(@RequestBody TimeFrameRequest request) {
        log.info("getAllCpDtlList");
        return projectCpDtlService.getAllCpDtlList(request);
    }
}

package com.jpmsoft.projectConsumer.controller.v1.constructionProcedure;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.proCp.ProjectCpRequest;
import com.jpmsoft.projectConsumer.feign.CpFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "项目建设程序", value = "项目建设程序")
@RequestMapping("/v1/ConstructionProcedure/proCp")
public class ProjectCpController {
    @Autowired
    private CpFeign cpFeign;


    /**
     * 项目审批通过生成项目建设程序
     *
     * @param pid
     * @param token
     * @return
     */
    @ApiOperation(value = "项目审批通过生成项目建设程序")
    @GetMapping("/projectApprove2generateCp")
    public BaseResponse projectApprove2generateCp(@RequestParam String pid, @RequestHeader String token) {
        log.info("projectApprove2generateCp");
        return cpFeign.projectApprove2generateCp(pid, token);
    }

    /**
     * 给没有项目建设程序的项目生成全量项目建设程序库的项目建设程序
     *
     * @return
     */
    @ApiOperation(value = "给没有项目建设程序的项目生成全量项目建设程序库的项目建设程序")
    @GetMapping("/generateProjectCpWithUnCpProject")
    public BaseResponse generateProjectCpWithUnCpProject(@RequestHeader String token) {
        log.info("generateProjectCpWithUnCpProject");
        return cpFeign.generateProjectCpWithUnCpProject(token);
    }


    @ApiOperation(value = "创建或修改项目建设程序（附带项目建设程序详情）")
    @PostMapping("/createOrUpdateProjectCp")
    public BaseResponse createOrUpdateProjectCp(@RequestBody ProjectCpRequest request) {
        log.info("createOrUpdateProjectCp");
        return cpFeign.createOrUpdateProjectCp(request);
    }

    @ApiOperation(value = "获取项目建设程序列表")
    @PostMapping("/getProCpList")
    public BaseResponse getProCpList(@RequestBody ProjectCpRequest request, @RequestHeader String token) {
        log.info("getProCpList");
        return cpFeign.getProCpList(request, token);
    }

    @ApiOperation(value = "ID获取项目建设程序")
    @GetMapping("/getProjectCpByCpId")
    public BaseResponse getProjectCpByCpId(@RequestParam String cpId) {
        log.info("getProjectCpByCpId");
        return cpFeign.getProjectCpByCpId(cpId);
    }


    @ApiOperation(value = "统计->项目建设程序统计")
    @PostMapping("/statisticalProCp")
    public BaseResponse statisticalProCp(@RequestBody ProjectCpRequest request) {
        log.info("statisticalProCp");
        return cpFeign.statisticalProCp(request);
    }

    /**
     * 统计用户建设项目
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "统计->用户建设项目统计")
    @PostMapping("/statisticalUserProCp")
    public BaseResponse statisticalUserProCp(@RequestBody ProjectCpRequest request) {
        log.info("statisticalUserProCp");
        return cpFeign.statisticalUserProCp(request);
    }

    /**
     * ID删除记录
     *
     * @param cpId
     * @return
     */
    @ApiOperation(value = "ID删除记录")
    @GetMapping("/deleteProCpByCpId")
    public BaseResponse deleteProCpByCpId(@RequestParam String cpId) {
        log.info("deleteProCpByCpId");
        return cpFeign.deleteProCpByCpId(cpId);
    }

    @ApiOperation(value = "项目ID获取建设程序ID")
    @GetMapping("/getCpIdByPid")
    public BaseResponse getCpIdByPid(@RequestParam String pId) {
        log.info("getCpIdByPid");
        return cpFeign.getCpIdByPid(pId);
    }

    @ApiOperation(value = "分页获取项目建设程序以及详情列表")
    @PostMapping("/getProCpAndDtlList")
    public BaseResponse getProCpAndDtlList(@RequestBody ProjectCpRequest request, @RequestHeader String token) {
        log.info("getProCpAndDtlList");
        return cpFeign.getProCpAndDtlList(request, token);
    }

    @ApiOperation(value = "获取指定成果上传表单的是否需要多次办理数据")
    @GetMapping("/getTaskIsManyByCplIdPlanId")
    public BaseResponse getTaskIsManyByCplIdPlanId(@RequestParam("cpdCplId") String cpdCplId, @RequestParam("pId") String pId) {
        log.info("getTaskIsManyByCplIdPlanId");
        return cpFeign.getTaskIsManyByCplIdPlanId(cpdCplId, pId);
    }

    @ApiOperation(value = "建设程序管理台账")
    @PostMapping("/getProCpLedgerNJCJ")
    public BaseResponse getProCpLedgerNJCJ(@RequestBody ProjectCpRequest request) {
        log.info("getProCpLedgerNJCJ");
        return cpFeign.getProCpLedgerNJCJ(request);
    }

    /**
     * 根据项目id和项目建设配置生成项目配置
     *
     * @param request
     * @return
     */
    @ApiOperation("根据项目id和项目建设配置生成项目配置")
    @PostMapping("/generateProjectCpWithPid")
    public BaseResponse generateProjectCpWithPid(@RequestBody ProjectCpRequest request) {
        log.info("generateProjectCpWithPid");
        return cpFeign.generateProjectCpWithPid(request);
    }
}

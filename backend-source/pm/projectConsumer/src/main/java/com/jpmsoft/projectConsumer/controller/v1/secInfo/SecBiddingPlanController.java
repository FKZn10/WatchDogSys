package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecBiddingPlanRequest;
import com.jpmsoft.common.model.request.SecInfo.SecBiddingPlanTZRequest;
import com.jpmsoft.common.model.request.SecInfo.SecInfoRequest;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import feign.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

@Slf4j
@RestController
@Api(tags = "招标计划", value = "招标计划")
@RequestMapping("/v1/biddingPlan")
public class SecBiddingPlanController {
    @Autowired
    private SecFeign secFeign;


    @ApiIgnore
    @GetMapping("/refreshSecBiddingPlanSign")
    public BaseResponse refreshSecBiddingPlanSign() {
        log.info("refreshSecBiddingPlanSign");
        return secFeign.refreshSecBiddingPlanSign();
    }

    @ApiOperation(value = "创建或修改招标计划")
    @PostMapping("/createOrUpdateSecBiddingPlan")
    public BaseResponse createOrUpdateSecBiddingPlan(@RequestBody SecBiddingPlanRequest request) {
        log.info("createOrUpdateSecBiddingPlan");
        return secFeign.createOrUpdateSecBiddingPlan(request);
    }

    @ApiOperation(value = "从旧招标计划创建或修改招标计划")
    @PostMapping("/createOrUpdateFromOldSecBiddingPlan")
    public BaseResponse createOrUpdateFromOldSecBiddingPlan(@RequestBody SecBiddingPlanRequest request) {
        log.info("createOrUpdateFromOldSecBiddingPlan");
        return secFeign.createOrUpdateFromOldSecBiddingPlan(request);
    }

    @ApiOperation(value = "分页查询招标计划")
    @PostMapping("/getSecBiddingPlanList")
    public BaseResponse getSecBiddingPlanList(@RequestBody SecBiddingPlanRequest request, @RequestHeader String token) {
        log.info("getSecBiddingPlanList");
        return secFeign.getSecBiddingPlanList(request, token);
    }

    @ApiOperation(value = "ID查询招标计划")
    @GetMapping("/getSecBiddingPlanById")
    public BaseResponse getSecBiddingPlanById(@RequestParam String bpId) {
        log.info("getSecBiddingPlanById");
        return secFeign.getSecBiddingPlanById(bpId);
    }

    @ApiOperation(value = "pId查询招标计划")
    @GetMapping("/getSecBiddingPlanByPIdNJCJ")
    public BaseResponse getSecBiddingPlanByPIdNJCJ(@RequestParam String pId) {
        log.info("getSecBiddingPlanByPIdNJCJ");
        return secFeign.getSecBiddingPlanByPIdNJCJ(pId);
    }

    @ApiOperation(value = "审批结束生成标段信息的Sys_File")
    @PostMapping("/appoval2CreateSecInfoSysFile")
    public BaseResponse appoval2CreateSecInfoSysFile(@RequestBody SecInfoRequest request) {
        log.info("appoval2CreateSecInfoSysFile");
        return secFeign.appoval2CreateSecInfoSysFile(request);
    }

    @ApiOperation(value = "分页查询招标计划 项目分组")
    @PostMapping("/getSecBiddingPlanGroupByProject")
    public BaseResponse getSecBiddingPlanGroupByProject(@RequestBody SecBiddingPlanRequest request, @RequestHeader String token) {
        log.info("getSecBiddingPlanGroupByProject");
        return secFeign.getSecBiddingPlanGroupByProject(request, token);
    }

    @PostMapping("/getTZClassInfo")
    public BaseResponse getTZClassInfo(@RequestBody SecBiddingPlanTZRequest request, @RequestHeader String token) {
        log.info("getTZClassInfo");
        return secFeign.getTZClassInfo(request,token);
    }

    @ApiOperation(value = "通过项目获取招标计划数据")
    @GetMapping("/getSecBiddingPlanByPId")
    public BaseResponse getSecBiddingPlanByPId(@RequestParam String pId) {
        log.info("getSecBiddingPlanByPId");
        return secFeign.getSecBiddingPlanByPId(pId);
    }

    @ApiOperation("通过项目判断计划阶段的状态")
    @GetMapping("/judgmentSecBiddingPlanByPId")
    public BaseResponse judgmentSecBiddingPlanByPId(@RequestParam("pId") String pId) {
        log.info("judgmentSecBiddingPlanByPId");
        return secFeign.judgmentSecBiddingPlanByPId(pId);
    }

    @ApiOperation("招标计划科目金额判断")
    @GetMapping("/judgmentSecBiddingPlanAmount")
    public BaseResponse judgmentSecBiddingPlanAmount(@RequestParam("type") String type, @RequestParam("id") String id, @RequestParam("money") BigDecimal money) {
        log.info("judgmentSecBiddingPlanAmount");
        return secFeign.judgmentSecBiddingPlanAmount(type, id, money);
    }

    @ApiOperation("获取招标计划科目列表")
    @GetMapping("/getSecBiddingPlanSubjectListByPId")
    public BaseResponse getSecBiddingPlanSubjectListByPId(
            @RequestParam("pId") String pId,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "excludeExtra", required = false) String excludeExtra,
            @RequestParam(value = "bpcId", required = false) String bpcId
    ) {
        log.info("getSecBiddingPlanSubjectListByPId");
        return secFeign.getSecBiddingPlanSubjectListByPId(pId, type, excludeExtra, bpcId);
    }

    @ApiOperation("获取招标计划科目列表树形结构")
    @GetMapping("/getSecBiddingPlanSubjectListByPIdTree")
    public BaseResponse getSecBiddingPlanSubjectListByPIdTree(
            @RequestParam("pId") String pId,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "excludeExtra", required = false) String excludeExtra,
            @RequestParam(value = "bpcId", required = false) String bpcId
    ) {
        log.info("getSecBiddingPlanSubjectListByPIdTree");
        return secFeign.getSecBiddingPlanSubjectListByPIdTree(pId, type, excludeExtra, bpcId);
    }

    @ApiOperation("获取招标计划关联的FileRelationId")
    @GetMapping("/getSecFileRelationIdByPId")
    public BaseResponse getSecFileRelationIdByPId(@RequestParam("pId") String pId) {
        log.info("getSecFileRelationIdByPId");
        return secFeign.getSecFileRelationIdByPId(pId);
    }

    /**
     * 通过项目获取估算概算总金额
     *
     * @param pId
     * @param type
     * @return
     */
    @ApiOperation("通过项目获取估算概算总金额")
    @GetMapping("/getEstimatedBudgetAmountByPId")
    public BaseResponse getEstimatedBudgetAmountByPId(@RequestParam("pId") String pId, @RequestParam(value = "type", required = false) String type) {
        log.info("getEstimatedBudgetAmountByPId");
        return secFeign.getEstimatedBudgetAmountByPId(pId, type);
    }

    /**
     * 招标计划信息导出EXCEL
     *
     * @param response
     * @param bpId
     */
    @ApiOperation(value = "招标计划信息导出EXCEL")
    @GetMapping("/getSecBiddingPlanDetailExportExcel")
    public void getSecBiddingPlanDetailExportExcel(HttpServletResponse response, @RequestParam("bpId") String bpId) {
        log.info("getSecBiddingPlanDetailExportExcel");
        Response feignResponse = secFeign.getSecBiddingPlanDetailExportExcel(bpId);
        Response.Body body = feignResponse.body();
        try (InputStream inputStream = body.asInputStream(); OutputStream outputStream = response.getOutputStream()) {
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, feignResponse.headers().get(HttpHeaders.CONTENT_DISPOSITION).stream().findFirst().get());
            response.setContentType(feignResponse.headers().get(HttpHeaders.CONTENT_TYPE).stream().findFirst().get());
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            log.error("getSecBiddingPlanDetailExportExcel  error");
        }
    }

    /**
     * 前置校验
     *
     * @param tCode
     * @param secId
     * @return
     */
    @ApiOperation(value = "前置校验")
    @GetMapping("/getSecPrecheck")
    public BaseResponse getSecPrecheck(@RequestParam("tCode") String tCode, @RequestParam("secId") String secId) {
        log.info("getSecPrecheck");
        return secFeign.getSecPrecheck(tCode, secId);
    }

    /**
     * 更新指定计划的项目分管领导
     *
     * @param bpId
     * @param responsibleLeader
     * @return
     */
    @ApiOperation("更新指定计划的项目分管领导")
    @GetMapping("/updateSecBiddingPlanResponsibleLeader")
    public BaseResponse updateSecBiddingPlanResponsibleLeader(@RequestParam("bpId") String bpId, @RequestParam("responsibleLeader") String responsibleLeader) {
        log.info("updateSecBiddingPlanResponsibleLeader");
        return secFeign.updateSecBiddingPlanResponsibleLeader(bpId, responsibleLeader);
    }
}

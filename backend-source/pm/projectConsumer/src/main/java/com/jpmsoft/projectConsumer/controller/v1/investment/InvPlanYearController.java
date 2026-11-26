package com.jpmsoft.projectConsumer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.request.InvestmentControl.InvPlanYearRequest;
import com.jpmsoft.common.model.request.InvestmentControl.InvPlanYearSearchRequest;
import com.jpmsoft.common.model.request.InvestmentControl.ProjectPresetYearRequest;
import com.jpmsoft.common.model.response.InvestmentControl.InvPlanYearResponse;
import com.jpmsoft.projectConsumer.config.CommonResponse;
import com.jpmsoft.projectConsumer.config.PageResult;
import com.jpmsoft.projectConsumer.feign.InvFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@Api(tags = "年度投资计划", value = "年度投资计划")
@RequestMapping("/inv/v1/invPlanYear")
public class InvPlanYearController {
    @Autowired
    private InvFeign invFeign;

    @ApiOperation(value = "新增或修改年度投资计划")
    @PostMapping("/createOrUpdateInvPlanYear")
    public BaseResponse createOrUpdateInvPlanYear(@RequestBody InvPlanYearRequest request) {
        log.info("createOrUpdateInvPlanYear");
        return invFeign.createOrUpdateInvPlanYear(request);
    }

    @ApiOperation(value = "分页查询年度投资计划")
    @PostMapping("/getInvPlanYearList")
    public BaseResponse getInvPlanYearList(@RequestBody InvPlanYearRequest request, @RequestHeader String token) {
        log.info("getInvPlanYearList");
        return invFeign.getInvPlanYearList(request, token);
    }

    @PostMapping("/getInvPlanYearSearchList")
    public CommonResponse<PageResult<InvPlanYearResponse>> getInvPlanYearSearchList(@RequestBody InvPlanYearSearchRequest request, @RequestHeader String token) {
        return invFeign.getInvPlanYearSearchList(request, token);
    }

    @PostMapping("/getSearchTableList")
    public CommonResponse<List<ClassInfoAnnotation>> getSearchTableList() {
        return invFeign.getSearchTableList();
    }

    @ApiOperation(value = "ID查询年度投资计划")
    @GetMapping("/getInvPlanYearByIpvId")
    public BaseResponse getInvPlanYearByIpvId(@RequestParam String ipvId) {
        log.info("getInvPlanYearByIpvId");
        return invFeign.getInvPlanYearByIpvId(ipvId);
    }

    @ApiOperation(value = "校验是否可以创建年度投资计划 true可以 false不可")
    @PostMapping("/canToCreateInvPlanYear")
    public BaseResponse canToCreateInvPlanYear(@RequestBody InvPlanYearRequest request) {
        log.info("canToCreateInvPlanYear");
        return invFeign.canToCreateInvPlanYear(request);
    }

    @ApiOperation(value = "项目ID部门ID申报年份查询投资计划-月进度计划")
    @PostMapping("/getInvPlanYearByPDYear")
    public BaseResponse getInvPlanYearByPDYear(@RequestBody InvPlanYearRequest request) {
        log.info("getInvPlanYearByPDYear");
        return invFeign.getInvPlanYearByPDYear(request);
    }

    @ApiOperation(value = "年度投资计划查询根据年份和pId")
    @PostMapping("/getInvPlanYearByPIdAndYear")
    public BaseResponse getInvPlanYearByPIdAndYear(@RequestBody InvPlanYearRequest request) {
        log.info("getInvPlanYearByPDYear");
        return invFeign.getInvPlanYearByPIdAndYear(request);
    }


    @ApiOperation(value = "查询计划总览-西藏")
    @GetMapping("/getPlanOverview")
    public BaseResponse getPlanOverview(@RequestParam String pName, @RequestParam String strYear, @RequestParam Integer page, @RequestParam Integer size,
    @RequestParam String pCompetentDepartment,@RequestParam String pSecType,@RequestParam String projectState) {
        log.info("getPlanOverview");
        return invFeign.getPlanOverview(pName, strYear, page, size,pCompetentDepartment,pSecType,projectState);
    }

    @ApiOperation(value = "新增或修改年度投资计划西藏")
    @PostMapping("/createOrUpdateInvPlanYearXz")
    public BaseResponse createOrUpdateInvPlanYearXz(@RequestBody InvPlanYearRequest request) {
        log.info("createOrUpdateInvPlanYearXz");
        return invFeign.createOrUpdateInvPlanYearXz(request);
    }

    @ApiOperation(value = "项目ID部门ID申报年份查询投资计划-月进度计划-西藏")
    @PostMapping("/getInvPlanYearByPDYearXz")
    public BaseResponse getInvPlanYearByPDYearXz(@RequestBody InvPlanYearRequest request) {
        log.info("getInvPlanYearByPDYearXz");
        return invFeign.getInvPlanYearByPDYearXz(request);
    }

    @ApiOperation(value = "修改项目状态")
    @GetMapping("/updatePreState")
    public BaseResponse updatePreState(@RequestParam String pid, @RequestParam String preState,@RequestParam String icmId) {
        log.info("updatePreState");
        return invFeign.updatePreState(pid, preState,icmId);

    }

    @ApiOperation(value = "ID查询年度投资计划-西藏")
    @GetMapping("/getInvPlanYearByIpvIdXz")
    public BaseResponse getInvPlanYearByIpvIdXz(@RequestParam String ipvId) {
        log.info("getInvPlanYearByIpvIdXz");
        return invFeign.getInvPlanYearByIpvIdXz(ipvId);
    }

    @ApiOperation(value = "判断年度计划是否存在")
    @GetMapping("/determineAnnualPlan")
    public BaseResponse determineAnnualPlan(@RequestParam String pId){
        return   invFeign.determineAnnualPlan(pId);

    }

    @ApiOperation(value = "判断年度计划是否存在")
    @GetMapping("/determinemonthlyPlan")
    public BaseResponse determinemonthlyPlan(@RequestParam String pId, @RequestParam Integer year, @RequestParam Integer month){
        return   invFeign.determinemonthlyPlan(pId,year,month);

    }

    @ApiOperation(value = "保存或修改年度项目资金预设-义乌")
    @PostMapping("/saveOrUpdatePresetYear")
    private BaseResponse saveOrUpdatePresetYear(@RequestBody ProjectPresetYearRequest request, @RequestHeader String token){
        return invFeign.saveOrUpdatePresetYear(request,token);
    }

    @ApiOperation(value = "查询年度项目资金预设-义乌")
    @PostMapping("/getProjectPresetYearList")
    private BaseResponse getProjectPresetYearList(@RequestBody ProjectPresetYearRequest request){
        return invFeign.getProjectPresetYearList(request);
    }

    @ApiOperation(value = "id查询年度项目资金预设-义乌")
    @GetMapping("/getProjectPresetYearId")
    private BaseResponse getProjectPresetYearId(@RequestParam String id){
        return invFeign.getProjectPresetYearId(id);
    }

    @ApiOperation(value = "年度投资计划申报-义乌")
    @PostMapping("/createOrUpdateInvPlanYearYw")
    public BaseResponse createOrUpdateInvPlanYearYw(@RequestBody InvPlanYearRequest request) {
        log.info("createOrUpdateInvPlanYearYw");
        return invFeign.createOrUpdateInvPlanYearYw(request);
    }


    @ApiOperation(value = "id查询年度投资计划申报-义乌")
    @GetMapping("/getInvPlanYearYwByIpvId")
    public BaseResponse getInvPlanYearYwByIpvId(@RequestParam String ipvId) {
        log.info("getInvPlanYearYwByIpvId");
        return invFeign.getInvPlanYearYwByIpvId(ipvId);
    }

    @ApiOperation(value = "查询年度投资计划申报列表-义乌")
    @PostMapping("/getInvPlanYearYwList")
    public BaseResponse getInvPlanYearYwList(@RequestBody InvPlanYearRequest request, @RequestHeader String token) {
        log.info("getInvPlanYearYwList");
        return invFeign.getInvPlanYearYwList(request, token);
    }

    @ApiOperation(value = "项目id查询年度计划申报且审批通过的-义乌")
    @GetMapping("/getInvPlanYearYwBypId")
    public BaseResponse getInvPlanYearYwBypId(@RequestParam String pId,@RequestParam(required = false) String year) {
        log.info("getInvPlanYearYwBypId");
        return invFeign.getInvPlanYearYwBypId(pId,year);
    }

    @ApiOperation(value = "查询年度投资汇总申报列表-义乌")
    @PostMapping("/getInvPlanYearSummaryYwList")
    public BaseResponse  getInvPlanYearSummaryYwList(@RequestBody InvPlanYearRequest request, @RequestHeader String token){
        log.info("getInvPlanYearSummaryYwList");
        return invFeign.getInvPlanYearSummaryYwList(request, token);
    }

    @ApiOperation(value = "导出年度投资汇总申报-义乌")
    @PostMapping("/getInvPlanYearYwListExcel")
    public ResponseEntity<byte[]> getInvPlanYearYwListExcel(@RequestBody InvPlanYearRequest request, @RequestHeader String token) {
        log.info("getInvPlanYearYwListExcel");
       return invFeign.getInvPlanYearYwListExcel(request, token);
    }

    @ApiOperation(value = "导出年度投资汇总申报(项目维度)-义乌")
    @PostMapping("/getInvPlanYearYwPreListExcel")
    public ResponseEntity<byte[]> getInvPlanYearYwPreListExcel(@RequestBody InvPlanYearRequest request, @RequestHeader String token) {
        log.info("getInvPlanYearYwPreListExcel");
        return invFeign.getInvPlanYearYwPreListExcel(request, token);
    }

    /**
     * 义乌 年度投资计划金额同步到投资跟踪
     *
     * @param ipvId
     * @return
     */
    @ApiOperation(value = "义乌年度投资计划金额同步到投资跟踪")
    @GetMapping("/syncToInvPlanYearYw")
    public BaseResponse syncToInvPlanYearYw(@RequestParam("ipvId") String ipvId) {
        return invFeign.syncToInvPlanYearYw(ipvId);
    }
}

package com.jpmsoft.projectProducer.controller.v1.investment;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvPlanYearRequest;
import com.jpmsoft.common.model.request.InvestmentControl.ProjectPresetYearRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.investment.InvPlanYearYwService;
import com.jpmsoft.projectProducer.service.investment.ProjectPresetYearService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/v1/projectPresetYear")
public class ProjectPresetYearController {

    @Autowired
    private ProjectPresetYearService projectPresetYearService;

    @Autowired
    private InvPlanYearYwService invPlanYearYwService;


    /**
     * 保存或修改年度项目资金预设
     * @param presetYearVo
     * @return
     */
    @PostMapping("/saveOrUpdatePresetYear")
    public BaseResponse saveOrUpdatePresetYear(@RequestBody ProjectPresetYearRequest request, @RequestHeader String token){
       return projectPresetYearService.saveOrUpdatePresetYear(request,token);
    }

    /**
     * 查询年度项目资金预设
     * @param presetYearVo
     * @return
     */
    @PostMapping("/getProjectPresetYearList")
    public BaseResponse getProjectPresetYearList(@RequestBody ProjectPresetYearRequest request){
        return projectPresetYearService.getProjectPresetYearList(request);
    }

    /**
     * id查询年度项目资金预设
     * @param
     * @return
     */
    @GetMapping("/getProjectPresetYearId")
    public BaseResponse getProjectPresetYearId(@RequestParam String id){
        return projectPresetYearService.getProjectPresetYearId(id);
    }


    /**
     * 义乌年度投资计划申报
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/createOrUpdateInvPlanYearYw")
    public BaseResponse createOrUpdateInvPlanYearYw(@RequestBody InvPlanYearRequest request) {
        log.info("createOrUpdateInvPlanYearYw");
        return invPlanYearYwService.createOrUpdateInvPlanYearYw(request);
    }

    /**
     * id查询年度投资计划申报
     * @param ipvId
     * @return
     */
    @GetMapping("/getInvPlanYearYwByIpvId")
    public BaseResponse getInvPlanYearYwByIpvId(@RequestParam String ipvId) {
        log.info("getInvPlanYearYwByIpvId");
        return invPlanYearYwService.getInvPlanYearYwByIpvId(ipvId);
    }

    /**
     * 查询年度投资计划申报
     * @param request
     * @param token
     * @return
     */
    @TenantId
    @PostMapping("/getInvPlanYearYwList")
    public BaseResponse getInvPlanYearYwList(@RequestBody InvPlanYearRequest request, @RequestHeader String token) {
        log.info("getInvPlanYearList");
        return invPlanYearYwService.getInvPlanYearYwList(request, token);
    }


    /**
     * 项目id查询已填写计划申报且审批通过的
     */
    @GetMapping("/getInvPlanYearYwBypId")
    public BaseResponse getInvPlanYearYwBypId(@RequestParam String pId,@RequestParam(required = false) String year) {
        log.info("getInvPlanYearYwBypId");
        return invPlanYearYwService.getInvPlanYearYwBypId(pId,year);
    }


    /**
     * 查询年底投资计划申报excel导出
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/getInvPlanYearYwListExcel")
    public ResponseEntity<byte[]> getInvPlanYearYwListExcel(@RequestBody InvPlanYearRequest request, @RequestHeader String token) throws IOException {
        log.info("getInvPlanYearYwListExcel");
        return   invPlanYearYwService.getInvPlanYearYwListExcel(request, token);
    }

    @PostMapping("/getInvPlanYearYwPreListExcel")
    public ResponseEntity<byte[]> getInvPlanYearYwPreListExcel(@RequestBody InvPlanYearRequest request, @RequestHeader String token) throws IOException {
        log.info("getInvPlanYearYwPreListExcel");
        return   invPlanYearYwService.getInvPlanYearYwPreListExcel(request, token);
    }

    /**
     * 查询年度投资汇总申报列表
     */
    @PostMapping("/getInvPlanYearSummaryYwList")
    public BaseResponse  getInvPlanYearSummaryYwList(@RequestBody InvPlanYearRequest request, @RequestHeader String token){
        log.info("getInvPlanYearSummaryYwList");
        return invPlanYearYwService.getInvPlanYearSummaryYwList(request, token);
    }





}

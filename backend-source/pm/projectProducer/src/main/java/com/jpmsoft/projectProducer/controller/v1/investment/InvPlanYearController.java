package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.request.InvestmentControl.InvPlanYearRequest;
import com.jpmsoft.common.model.request.InvestmentControl.InvPlanYearSearchRequest;
import com.jpmsoft.common.model.response.InvestmentControl.InvPlanYearResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.config.CommonResponse;
import com.jpmsoft.projectProducer.config.PageResult;
import com.jpmsoft.projectProducer.service.investment.IInvPlanYearService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/v1/invPlanYear")
public class InvPlanYearController {

    @Resource
    private IInvPlanYearService invPlanYearService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateInvPlanYear")
    public BaseResponse createOrUpdateInvPlanYear(@RequestBody InvPlanYearRequest request) {
        log.info("createOrUpdateInvPlanYear");
        return invPlanYearService.createOrUpdateInvPlanYear(request);
    }

    @TenantId
    @PostMapping("/getInvPlanYearList")
    public BaseResponse getInvPlanYearList(@RequestBody InvPlanYearRequest request, @RequestHeader String token) {
        log.info("getInvPlanYearList");
        return invPlanYearService.getInvPlanYearList(request, redisUtils.getUid(token));
    }

    @PostMapping("/getInvPlanYearSearchList")
    public CommonResponse<PageResult<InvPlanYearResponse>> getInvPlanYearSearchList(@RequestBody InvPlanYearSearchRequest request, @RequestHeader String token) {
        return CommonResponse.ok(invPlanYearService.getInvPlanYearSearchList(request, redisUtils.getUid(token)));
    }

    @PostMapping("/getSearchTableList")
    public CommonResponse<List<ClassInfoAnnotation>> getSearchTableList() {
        return CommonResponse.ok(invPlanYearService.getSearchTableList());
    }

    @GetMapping("/getInvPlanYearByIpvId")
    public BaseResponse getInvPlanYearByIpvId(@RequestParam String ipvId) {
        log.info("getInvPlanYearByIpvId");
        return invPlanYearService.getInvPlanYearByIpvId(ipvId);
    }

    @PostMapping("/getInvPlanYearByPIdAndYear")
    public BaseResponse getInvPlanYearByPIdAndYear(@RequestBody InvPlanYearRequest request) {
        log.info("getInvPlanYearByIpvId");
        return invPlanYearService.getInvPlanYearByPIdAndYear(request);
    }

    @PostMapping("/canToCreateInvPlanYear")
    public BaseResponse canToCreateInvPlanYear(@RequestBody InvPlanYearRequest request) {
        log.info("canToCreateInvPlanYear");
        return invPlanYearService.canToCreateInvPlanYear(request);
    }

    @PostMapping("/getInvPlanYearByPDYear")
    public BaseResponse getInvPlanYearByPDYear(@RequestBody InvPlanYearRequest request) {
        log.info("getInvPlanYearByPDYear");
        return invPlanYearService.getInvPlanYearByPDYear(request);
    }

    @GetMapping("/getPlanOverview")
    public BaseResponse getPlanOverview(@RequestParam String pName,@RequestParam String strYear,@RequestParam Integer page,@RequestParam Integer size,
                                        @RequestParam String pCompetentDepartment,@RequestParam String pSecType,@RequestParam String projectState){
       return invPlanYearService.getPlanOverview(pName,strYear,page,size,pCompetentDepartment,pSecType,projectState);
    };


    @TenantId
    @PostMapping("/createOrUpdateInvPlanYearXz")
    public BaseResponse createOrUpdateInvPlanYearXz(@RequestBody InvPlanYearRequest request) {
        log.info("createOrUpdateInvPlanYearXz");
        return invPlanYearService.createOrUpdateInvPlanYearXz(request);
    }

    //月度投资计划
    @PostMapping("/getInvPlanYearByPDYearXz")
    public BaseResponse getInvPlanYearByPDYearXz(@RequestBody InvPlanYearRequest request) {
        log.info("getInvPlanYearByPDYearXz");
        return invPlanYearService.getInvPlanYearByPDYearXz(request);
    }


    //修改项目状态
    @GetMapping("/updatePreState")
    public BaseResponse updatePreState(@RequestParam String pid,@RequestParam String preState,@RequestParam String icmId){
        return invPlanYearService.updatePreState(pid,preState,icmId);
    }

    @GetMapping("/getInvPlanYearByIpvIdXz")
    public BaseResponse getInvPlanYearByIpvIdXz(@RequestParam String ipvId) {
        log.info("getInvPlanYearByIpvIdXz");
        return invPlanYearService.getInvPlanYearByIpvIdXz(ipvId);
    }


    //判断年度计划是否存在
    @GetMapping("/determineAnnualPlan")
    public BaseResponse determineAnnualPlan(@RequestParam String pId){
        return  invPlanYearService.determineAnnualPlan(pId);
    }
    @GetMapping("/determinemonthlyPlan")
    //判断月度计划
    public BaseResponse   determinemonthlyPlan(@RequestParam String pId, @RequestParam Integer year, @RequestParam Integer month){
        return  invPlanYearService.determinemonthlyPlan(pId,year,month);

    }

    /**
     * 义乌 年度投资计划金额同步到投资跟踪
     *
     * @param ipvId
     * @return
     */
    @GetMapping("/syncToInvPlanYearYw")
    public BaseResponse syncToInvPlanYearYw(@RequestParam("ipvId") String ipvId) {
        return invPlanYearService.syncToInvPlanYearYw(ipvId);
    }
}

package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecBiddingPlanRequest;
import com.jpmsoft.common.model.request.SecInfo.SecBiddingPlanTZRequest;
import com.jpmsoft.common.model.request.SecInfo.SecInfoRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.secInfo.ISecBiddingPlanService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;

@Slf4j
@RestController
@RequestMapping("/v1/biddingPlan")
public class SecBiddingPlanController {

    @Resource
    private ISecBiddingPlanService secBiddingPlanService;

    @Resource
    private RedisUtils redisUtils;


    @GetMapping("/refreshSecBiddingPlanSign")
    public BaseResponse refreshSecBiddingPlanSign() {
        log.info("refreshSecBiddingPlanSign");
        return secBiddingPlanService.refreshSecBiddingPlanSign();
    }

    @TenantId
    @PostMapping("/createOrUpdateSecBiddingPlan")
    public BaseResponse createOrUpdateSecBiddingPlan(@RequestBody SecBiddingPlanRequest request) {
        log.info("createOrUpdateSecBiddingPlan");
        return secBiddingPlanService.createOrUpdateSecBiddingPlan(request);
    }

    @TenantId
    @PostMapping("/createOrUpdateFromOldSecBiddingPlan")
    public BaseResponse createOrUpdateFromOldSecBiddingPlan(@RequestBody SecBiddingPlanRequest request) {
        log.info("createOrUpdateFromOldSecBiddingPlan");
        return secBiddingPlanService.createOrUpdateFromOldSecBiddingPlan(request);
    }

    @TenantId
    @PostMapping("/getSecBiddingPlanList")
    public BaseResponse getSecBiddingPlanList(@RequestBody SecBiddingPlanRequest request, @RequestHeader String token) {
        log.info("getSecBiddingPlanList(getSecBiddingPlanListCjdz)");
        return secBiddingPlanService.getSecBiddingPlanListCjdz(request, redisUtils.getUserInfoByToken(token));
    }

    @GetMapping("/getSecBiddingPlanById")
    public BaseResponse getSecBiddingPlanById(@RequestParam String bpId) {
        log.info("getSecBiddingPlanById");
        return secBiddingPlanService.getSecBiddingPlanById(bpId);
    }

    @GetMapping("/getSecBiddingPlanByPIdNJCJ")
    public BaseResponse getSecBiddingPlanByPIdNJCJ(@RequestParam String pId) {
        log.info("getSecBiddingPlanByPIdNJCJ");
        return secBiddingPlanService.getSecBiddingPlanByPIdNJCJ(pId);
    }

    @PostMapping("/appoval2CreateSecInfoSysFile")
    public BaseResponse appoval2CreateSecInfoSysFile(@RequestBody SecInfoRequest request) {
        log.info("appoval2CreateSecInfoSysFile");
        return secBiddingPlanService.appoval2CreateSecInfoSysFile(request);
    }

    @TenantId
    @PostMapping("/getSecBiddingPlanGroupByProject")
    public BaseResponse getSecBiddingPlanGroupByProject(@RequestBody SecBiddingPlanRequest request, @RequestHeader String token) {
        log.info("getSecBiddingPlanGroupByProject");
        return secBiddingPlanService.getSecBiddingPlanGroupByProject(request, redisUtils.getUid(token));
    }


    @PostMapping("/getTZClassInfo")
    public BaseResponse getTZClassInfo(@RequestBody SecBiddingPlanTZRequest request, @RequestHeader String token) {
        log.info("getTZClassInfo");
        return secBiddingPlanService.getTZClassInfo(request, redisUtils.getUid(token));
    }


    @GetMapping("/getSecBiddingPlanByPId")
    public BaseResponse getSecBiddingPlanByPId(@RequestParam String pId) {
        log.info("getSecBiddingPlanByPId");
        return secBiddingPlanService.getSecBiddingPlanByPId(pId);
    }

    @GetMapping("/judgmentSecBiddingPlanByPId")
    public BaseResponse judgmentSecBiddingPlanByPId(@RequestParam("pId") String pId) {
        log.info("judgmentSecBiddingPlanByPId");
        return secBiddingPlanService.judgmentSecBiddingPlanByPId(pId);
    }

    @GetMapping("/judgmentSecBiddingPlanAmount")
    public BaseResponse judgmentSecBiddingPlanAmount(@RequestParam("type") String type, @RequestParam("id") String id, @RequestParam("money") BigDecimal money) {
        log.info("judgmentSecBiddingPlanAmount");
        return secBiddingPlanService.judgmentSecBiddingPlanAmount(type, id, money);
    }

    @GetMapping("/getSecBiddingPlanSubjectListByPId")
    public BaseResponse getSecBiddingPlanSubjectListByPId(
            @RequestParam("pId") String pId,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "excludeExtra", required = false) String excludeExtra,
            @RequestParam(value = "bpcId", required = false) String bpcId
    ) {
        log.info("getSecBiddingPlanSubjectListByPId");
        return secBiddingPlanService.getSecBiddingPlanSubjectListByPId(pId, type, excludeExtra, bpcId);
    }

    @GetMapping("/getSecBiddingPlanSubjectListByPIdTree")
    public BaseResponse getSecBiddingPlanSubjectListByPIdTree(
            @RequestParam("pId") String pId,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "excludeExtra", required = false) String excludeExtra,
            @RequestParam(value = "bpcId", required = false) String bpcId
    ) {
        log.info("getSecBiddingPlanSubjectListByPIdTree");
        return secBiddingPlanService.getSecBiddingPlanSubjectListByPIdTree(pId, type, excludeExtra, bpcId);
    }

    @GetMapping("/getSecFileRelationIdByPId")
    public BaseResponse getSecFileRelationIdByPId(@RequestParam("pId") String pId) {
        log.info("getSecFileRelationIdByPId");
        return secBiddingPlanService.getSecFileRelationIdByPId(pId);
    }

    /**
     * 通过项目获取估算概算总金额
     *
     * @param pId
     * @param type
     * @return
     */
    @GetMapping("/getEstimatedBudgetAmountByPId")
    public BaseResponse getEstimatedBudgetAmountByPId(@RequestParam("pId") String pId, @RequestParam(value = "type", required = false) String type) {
        log.info("getEstimatedBudgetAmountByPId");
        return secBiddingPlanService.getEstimatedBudgetAmountByPId(pId, type);
    }

    /**
     * 招标计划信息导出EXCEL
     *
     * @param response
     * @param bpId
     */
    @GetMapping("/getSecBiddingPlanDetailExportExcel")
    public void getSecBiddingPlanDetailExportExcel(HttpServletResponse response, @RequestParam("bpId") String bpId) {
        log.info("getSecBiddingPlanDetailExportExcel");
        try {
            Workbook workbook = secBiddingPlanService.getSecBiddingPlanDetailExportExcel(bpId);
            String fileName = URLEncoder.encode("招标计划信息.xls", "UTF-8");
            // 告诉浏览器用什么软件可以打开此文件
            response.setHeader("content-Type", "application/vnd.ms-excel");
            // 下载文件的默认名称
            response.setHeader("Content-Disposition", fileName);
            // 编码
            response.setCharacterEncoding("UTF-8");
            // 多sheet
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (IOException e) {
            log.error("招标计划信息导出EXCEL导出出错{}", e.getMessage(), e);
        }
    }

    /**
     * 前置校验
     *
     * @param tCode
     * @param secId
     * @return
     */
    @GetMapping("/getSecPrecheck")
    public BaseResponse getSecPrecheck(@RequestParam("tCode") String tCode, @RequestParam("secId") String secId) {
        log.info("getSecPrecheck");
        return secBiddingPlanService.getSecPrecheck(tCode, secId);
    }

    /**
     * 更新指定计划的项目分管领导
     *
     * @param bpId
     * @param responsibleLeader
     * @return
     */
    @GetMapping("/updateSecBiddingPlanResponsibleLeader")
    public BaseResponse updateSecBiddingPlanResponsibleLeader(@RequestParam("bpId") String bpId, @RequestParam("responsibleLeader") String responsibleLeader) {
        log.info("updateSecBiddingPlanResponsibleLeader");
        return secBiddingPlanService.updateSecBiddingPlanResponsibleLeader(bpId, responsibleLeader);
    }
}

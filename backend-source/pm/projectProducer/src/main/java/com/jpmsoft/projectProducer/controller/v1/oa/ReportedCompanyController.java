package com.jpmsoft.projectProducer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.oa.ReportedCompanyDto;
import com.jpmsoft.projectProducer.service.oa.IReportedCompanyService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/21 15:23
 */
@Slf4j
@RestController
@RequestMapping("/v1/OA/repCompany")
public class ReportedCompanyController {

    @Resource
    private IReportedCompanyService reportedCompanyService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateReportedCompany")
    public BaseResponse createOrUpdateReportedCompany(@RequestBody ReportedCompanyDto request) {
        log.info("createOrUpdateReportedCompany");
        return reportedCompanyService.createOrUpdateReportedCompany(request);
    }

    @TenantId
    @PostMapping("/getReportedCompanyList")
    public BaseResponse getReportedCompanyList(@RequestBody ReportedCompanyDto request, @RequestHeader String token) {
        log.info("getReportedCompanyList");
        return reportedCompanyService.getReportedCompanyList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getReportedCompanyById")
    public BaseResponse getReportedCompanyById(@RequestParam String id) {
        log.info("getReportedCompanyById");
        return reportedCompanyService.getReportedCompanyById(id);
    }

    @GetMapping("/getReportedCompanyGroupTypeByPid")
    public BaseResponse getReportedCompanyGroupTypeByPid(@RequestParam String pId) {
        log.info("getReportedCompanyGroupTypeByPid");
        return reportedCompanyService.getReportedCompanyGroupTypeByPid(pId);
    }


    /**
     * 审批结束后同步单位信息以及项目单位信息
     *
     * @param id
     * @return
     */
    @GetMapping("/approved2SyncCompanyInfo")
    public BaseResponse approved2SyncCompanyInfo(@RequestParam String id) {
        log.info("approved2SyncCompanyInfo");
        return reportedCompanyService.approved2SyncCompanyInfo(id);
    }
}

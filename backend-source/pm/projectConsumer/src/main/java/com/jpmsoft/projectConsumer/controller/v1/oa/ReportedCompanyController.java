package com.jpmsoft.projectConsumer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.oa.ReportedCompanyDto;
import com.jpmsoft.projectConsumer.feign.OaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/21 15:23
 */
@Slf4j
@RestController
@Api(tags = "项目单位报备", value = "项目单位报备")
@RequestMapping("/OA/repCompany")
public class ReportedCompanyController {

    @Resource
    private OaFeign oaFeign;

    @ApiOperation(value = "新增或修改项目单位报备")
    @PostMapping("/createOrUpdateReportedCompany")
    public BaseResponse createOrUpdateReportedCompany(@RequestBody ReportedCompanyDto request) {
        log.info("createOrUpdateReportedCompany");
        return oaFeign.createOrUpdateReportedCompany(request);
    }

    @ApiOperation(value = "分页查询项目单位报备")
    @PostMapping("/getReportedCompanyList")
    public BaseResponse getReportedCompanyList(@RequestBody ReportedCompanyDto request, @RequestHeader String token) {
        log.info("getReportedCompanyList");
        return oaFeign.getReportedCompanyList(request, token);
    }

    @ApiOperation(value = "ID查询项目单位报备")
    @GetMapping("/getReportedCompanyById")
    public BaseResponse getReportedCompanyById(@RequestParam String id) {
        log.info("getReportedCompanyById");
        return oaFeign.getReportedCompanyById(id);
    }


    @ApiOperation(value = "项目ID查询项目下单位报备（cType分组取每组创建时间第一条）")
    @GetMapping("/getReportedCompanyGroupTypeByPid")
    public BaseResponse getReportedCompanyGroupTypeByPid(@RequestParam String pId) {
        log.info("getReportedCompanyGroupTypeByPid");
        return oaFeign.getReportedCompanyGroupTypeByPid(pId);
    }

    /**
     * 审批结束后同步单位信息以及项目单位信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "审批结束后同步单位信息以及项目单位信息")
    @GetMapping("/approved2SyncCompanyInfo")
    public BaseResponse approved2SyncCompanyInfo(@RequestParam String id) {
        log.info("approved2SyncCompanyInfo");
        return oaFeign.approved2SyncCompanyInfo(id);
    }
}

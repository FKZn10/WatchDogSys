package com.jpmsoft.userProducer.controller.v1;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.JimuReportDto;
import com.jpmsoft.userProducer.pojo.entity.JimuReport;
import com.jpmsoft.userProducer.service.IJimuReportService;
import com.jpmsoft.userProducer.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "积木报表管理", value = "积木报表管理")
@RequestMapping("/v1/JimuReport")
public class JimuReportController {

    @Resource
    private IJimuReportService jimuReportService;
    @Autowired
    private RedisUtils redisUtils;
    /**
     * 查询积木报表列表信息
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "查询积木报表列表信息")
    @PostMapping("/getJimuReportList")
    public BaseResponse getJimuReportList(@RequestBody JimuReport request,@RequestHeader(value ="token",required = false) String token) {
        log.info("getJimuReportList");
        String customerId=redisUtils.getTeId(token);
        request.setTenantId(customerId);
        return jimuReportService.getJimuReportList(request);
    }



    /**
     * 获取需要导出的积木报表信息
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "获取需要导出的积木报表信息")
    @PostMapping("/getJimuReportListByIds")
    public BaseResponse getJimuReportListByIds(@RequestBody List<String> ids,@RequestHeader(value ="token",required = false) String token) {
        log.info("getJimuReportListByIds");
        String customerId=redisUtils.getTeId(token);
        return jimuReportService.getJimuReportListByIds(ids,customerId);
    }


    /**
     * 批量导入积木报表信息
     *
     * @param data
     * @return
     */
    @ApiOperation(value = "批量导入积木报表信息")
    @PostMapping("/importJimuReportList")
    public BaseResponse importJimuReportList(@RequestBody JimuReportDto data, @RequestHeader(value ="token",required = false) String token) {
        log.info("importJimuReportList");
        String customerId=redisUtils.getTeId(token);
        return jimuReportService.importJimuReportList(data,customerId);
    }

    /**
     * 根据id删除积木报表信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id删除积木报表信息")
    @GetMapping("/deleteJimuReportById")
    public BaseResponse deleteJimuReportById(@RequestParam String  id) {
        log.info("deleteJimuReportById");
        return jimuReportService.deleteJimuReportById(id);
    }



    @ApiOperation(value = "表单文件导入")
    @PostMapping("/importSysFormInFile")
    public BaseResponse importSysFormInFile(@RequestParam MultipartFile request,@RequestHeader(value ="token",required = false) String token) {
        log.info("importJimuReportFile");
        String customerId=redisUtils.getTeId(token);
        return jimuReportService.importJimuReport(request,customerId);
    }
    @ApiOperation(value = "刷新积木数据字典编码")
    @PostMapping("/refreshJimuDictItem")
    public BaseResponse refreshJimuDictItem(@RequestHeader(value ="token",required = false) String token) {
        log.info("refreshJimuDictItem");
        String customerId=redisUtils.getTeId(token);
        return jimuReportService.refreshJimuDictItem(customerId);
    }
}

package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.FullProcessConsultServiceProtocolMeterageCreateOrUpdateRequest;
import com.jpmsoft.common.model.request.Contract.FullProcessConsultServiceProtocolMeteragePageRequest;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
@RestController
@RequestMapping("/fullProcessConsultServiceProtocolMeterageApply")
public class FullProcessConsultServiceProtocolMeterageApplyController {

    @Resource
    private ConFeign conFeign;

    /**
     * 通过项目id查询服务协议内容及概算金额
     *
     * @param projectId
     * @return
     */
    @GetMapping("/getServiceProtocolContentItemByProjectId")
    private BaseResponse getServiceProtocolContentItemByProjectId(@RequestParam String projectId) {
        return conFeign.getServiceProtocolContentItemByProjectId(projectId);
    }

    /**
     * 分页查询接口
     *
     * @return
     */
    @PostMapping("/page")
    public BaseResponse page(@RequestBody FullProcessConsultServiceProtocolMeteragePageRequest request, @RequestHeader String token) {
        return conFeign.pageFullProcessConsultServiceProtocolMeterageApply(request, token);
    }

    /**
     * 详情接口
     *
     * @param id
     * @return
     */
    @GetMapping("/getById")
    public BaseResponse getById(@RequestParam String id) {
        return conFeign.getFullProcessConsultServiceProtocolMeterageApplyById(id);
    }

    /**
     * 创建或更新接口
     *
     * @param fullProcessConsultServiceProtocolMeterageCreateOrUpdateRequest
     * @return
     */
    @PostMapping("/createOrUpdate")
    public BaseResponse createOrUpdate(@RequestBody FullProcessConsultServiceProtocolMeterageCreateOrUpdateRequest fullProcessConsultServiceProtocolMeterageCreateOrUpdateRequest) {
        return conFeign.createOrUpdateFullProcessConsultServiceProtocolMeterageApply(fullProcessConsultServiceProtocolMeterageCreateOrUpdateRequest);
    }

    /**
     * 删除接口
     *
     * @param id 主键id
     * @return
     */
    @PostMapping("/deleteById/{id}")
    public BaseResponse deleteById(@PathVariable("id") String id) {
        return conFeign.deleteFullProcessConsultServiceProtocolMeterageApplyById(id);
    }

    /**
     * 获取合同最新审批通过的计量报审记录
     * @param contractId 合同id
     * @return
     */
    @GetMapping("/getLastFullProcessConsultServiceProtocolMeterageApply")
    public BaseResponse getLastFullProcessConsultServiceProtocolMeterageApply(@RequestParam String contractId){
        return conFeign.getLastFullProcessConsultServiceProtocolMeterageApply(contractId);
    }

    /**
     * 导出接口
     *
     * @return
     */
    @PostMapping("/export")
    public void export(@RequestBody FullProcessConsultServiceProtocolMeteragePageRequest request,HttpServletResponse httpServletResponse, @RequestHeader String token) throws IOException {
        Response feignResponse = conFeign.exportFullProcessConsultServiceProtocolMeterageApply(request, token);
        Response.Body body = feignResponse.body();
        try (InputStream inputStream = body.asInputStream();
             OutputStream outputStream = httpServletResponse.getOutputStream()) {
            String fileName = System.currentTimeMillis() +"-export.xlsx";
            httpServletResponse.setHeader("Content-disposition", "attachment;filename=" + fileName);
            httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            IOUtils.copy(inputStream, outputStream);
            feignResponse.close();
        } catch (IOException e) {
            log.error("exportContractStampDuty  error");
        }
    }

    @PostMapping("/getTZClassInfo")
    public BaseResponse getTZClassInfo(@RequestBody FullProcessConsultServiceProtocolMeteragePageRequest request, @RequestHeader String token) throws ClassNotFoundException {
        return conFeign.getFullProcessConsultServiceProtocolMeterageTZClassInfo(request, token);
    }

    @GetMapping("/getLastDeadline")
    public BaseResponse getLastDeadline(@RequestParam String pId,@RequestParam String conId){
        return conFeign.getLastDeadline(pId,conId);
    }

}

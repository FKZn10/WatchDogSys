package com.jpmsoft.projectProducer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.base.BatchhandlingTaskDto;
import com.jpmsoft.projectProducer.pojo.dto.other.FileStatusDto;
import com.jpmsoft.projectProducer.pojo.dto.other.SysCcSendDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * 文件服务
 *
 * @author: 李文涛 aaron
 * @date: 2022/11/8 9:28
 */
@FeignClient(name = "bpm-server", contextId = "bpm-server")
public interface BpmFeign {

    @PostMapping("/v1/flow/getBiddingProcedures")
    BaseResponse getBiddingProcedures(@RequestBody List<String> secIds);

    @PostMapping("/v1/task/batchSendTask")
    BaseResponse batchSendTask(@RequestBody BatchhandlingTaskDto request, @RequestHeader(value = "token", required = false) String token);

    @PostMapping("/v1/flow/cctransactionSend")
    BaseResponse cctransactionSend(@RequestBody SysCcSendDto request,@RequestHeader(value = "token", required = false) String token);

    @PostMapping("/v1/flow/Settingapprovalstatus")
    BaseResponse Settingapprovalstatus(@RequestBody FileStatusDto request, @RequestHeader(value = "token", required = false) String token);

    @PostMapping("/v1/carbonCopy/FreeCC")
    BaseResponse FreeCC(@RequestBody SysCcSendDto request, @RequestHeader(value = "token", required = false) String token);
}

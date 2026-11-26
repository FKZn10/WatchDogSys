package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;

import com.jpmsoft.common.model.requestAndResponse.Qs.QsExpertModel;
import com.jpmsoft.projectConsumer.pojo.dto.qualitySafety.QsExpertVacationDTO;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.ExtractExpertDto;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(name = "project-producer", contextId = "Qs")
public interface QsFeign {
    @PostMapping("/v1/QsExpert/createOrUpdateQsExpert")
    BaseResponse createOrUpdateQsExpert(@RequestBody QsExpertModel request);

    @PostMapping("/v1/QsExpert/createOrUpdateQsExpertCurrent")
    BaseResponse createOrUpdateQsExpertCurrent(@RequestBody QsExpertModel request);

    @PostMapping("/v1/QsExpert/createOrUpdateQsExpertVerify")
    BaseResponse createOrUpdateQsExpertVerify(@RequestBody QsExpertModel request);

    @GetMapping("/v1/QsExpert/resetPlaintextNum")
    BaseResponse resetPlaintextNum();

    @PostMapping("/v1/QsExpert/getQsExpertList")
    BaseResponse getQsExpertList(@RequestBody QsExpertModel request);

    @PostMapping("/v1/QsExpert/getQsExpertCjDzList")
    BaseResponse getQsExpertCjDzList(@RequestBody QsExpertModel request, @RequestHeader String token);

    @PostMapping("/v1/QsExpert/exportExcelQsExpertCjDzList")
    Response exportExcelQsExpertCjDzList(@RequestBody QsExpertModel request, @RequestHeader String token);

    @GetMapping("/v1/QsExpert/getQsExpertById")
    BaseResponse getQsExpertById(@RequestParam String exId);

    @GetMapping("/v1/QsExpert/deleteQsExpertById")
    BaseResponse deleteQsExpertById(@RequestParam String exId);

    @GetMapping("/v1/QsExpert/expertExists")
    BaseResponse expertExists(@RequestParam String idNum, @RequestParam(required = false) String id, @RequestHeader String token);

    @PostMapping("/v1/QsExpert/extractExpertInfo")
    BaseResponse extractExpertInfo(@RequestBody ExtractExpertDto request, @RequestHeader String token);

    @PostMapping("/v1/QsExpert/extractExpert")
    BaseResponse extractExpert(@RequestBody ExtractExpertDto request, @RequestHeader String token);

    @PostMapping("/v1/QsExpert/getQsExpertWhenVacation")
    BaseResponse getQsExpertWhenVacation(@RequestBody QsExpertVacationDTO request, @RequestHeader String token);

    @PostMapping("/v1/QsExpert/getQsExpertWhenVacationNoId")
    BaseResponse getQsExpertWhenVacationNoId(@RequestBody QsExpertVacationDTO request, @RequestHeader String token);

    @GetMapping("/v1/QsExpert/getQsExpertByCurrent")
    BaseResponse getQsExpertByCurrent(@RequestHeader String token);

    @PostMapping("/v1/QsExpert/updatePersonnel")
    BaseResponse updatePersonnel(@RequestBody QsExpertModel request);
}

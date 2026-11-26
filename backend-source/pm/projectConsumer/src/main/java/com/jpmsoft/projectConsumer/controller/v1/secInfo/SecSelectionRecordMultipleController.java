package com.jpmsoft.projectConsumer.controller.v1.secInfo;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSelectionRecordMultipleModel;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.ExtractExpertMultipleDto;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/v1/selectionRecordMultiple")
public class SecSelectionRecordMultipleController {

    @Autowired
    private SecFeign secFeign;

    @PostMapping("/createOrUpdateSecSelectionRecordMultiple")
    public BaseResponse createOrUpdateSecSelectionRecordMultiple(@RequestBody SecSelectionRecordMultipleModel request, @RequestHeader String token) {
        log.info("createOrUpdateSecSelectionRecordMultiple");
        return secFeign.createOrUpdateSecSelectionRecordMultiple(request, token);
    }


    @PostMapping("/getSecSelectionRecordMultipleList")
    public BaseResponse getSecSelectionRecordMultipleList(@RequestBody SecSelectionRecordMultipleModel request,@RequestHeader String token) {
        log.info("getSecSelectionRecordMultipleList");
        return secFeign.getSecSelectionRecordMultipleList(request,token);
    }

    @GetMapping("/getSecSelectionRecordMultipleById")
    public BaseResponse getSecSelectionRecordMultipleById(@RequestParam String id) {
        log.info("getSecSelectionRecordMultipleById");
        return secFeign.getSecSelectionRecordMultipleById(id);
    }

    @GetMapping("/deleteSecSelectionRecordMultipleById")
    public BaseResponse deleteSecSelectionRecordMultipleById(@RequestParam String id) {
        log.info("deleteSecSelectionRecordMultipleById");
        return secFeign.deleteSecSelectionRecordMultipleById(id);
    }



    /****************************************专家抽取***********************************************/

    @PostMapping("/createOrUpdateExpertExtractMultiple")
    public BaseResponse createOrUpdateExpertExtractMultiple(@RequestBody SecSelectionRecordMultipleModel request, @RequestHeader String token) {
        log.info("createOrUpdateExpertExtractMultiple");
        return secFeign.createOrUpdateExpertExtractMultiple(request, token);
    }

    @GetMapping("/getSecExpertExtractMultipleById")
    public BaseResponse getSecExpertExtractMultipleById(@RequestParam String id) {
        log.info("getSecExpertExtractMultipleById");
        return secFeign.getSecExpertExtractMultipleById(id);
    }

    @PostMapping("/extractJGExpertMultiple")
    public BaseResponse extractJGExpertMultiple(@RequestBody ExtractExpertMultipleDto request, @RequestHeader String token) {
        log.info("extractJGExpertMultiple");
        return secFeign.extractJGExpertMultiple(request, token);
    }

    @PostMapping("/approvalDataExpertMultiple")
    public BaseResponse approvalDataExpertMultiple(@RequestBody SecSelectionRecordMultipleModel request) {
        log.info("approvalDataExpertMultiple");
        return secFeign.approvalDataExpertMultiple(request);
    }


    @GetMapping("/getOtherExpertSecInfo")
    public BaseResponse getOtherExpertSecInfo(@RequestParam String secId) {
        log.info("getOtherExpertSecInfo");
        return secFeign.getOtherExpertSecInfo(secId);
    }

}

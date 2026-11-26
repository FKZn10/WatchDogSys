package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSelectionRecordModel;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.ExtractExpertDto;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecExpertSelectAndSelectionDTO;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecExpertSelectDto;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "专家选取申请", value = "专家选取申请")
@RequestMapping("/v1/secExpertSelect")
public class SecExpertSelectController {

    @Resource
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改专家选取申请")
    @PostMapping("/createOrUpdateSecExpertSelect")
    public BaseResponse createOrUpdateSecExpertSelect(@RequestBody SecExpertSelectDto request) {
        log.info("createOrUpdateSecExpertSelect");
        return secFeign.createOrUpdateSecExpertSelect(request);
    }

    @ApiOperation(value = "分页查询专家选取申请")
    @PostMapping("/getSecExpertSelectList")
    public BaseResponse getSecExpertSelectList(@RequestBody SecExpertSelectDto request, @RequestHeader String token) {
        log.info("getSecExpertSelectList");
        return secFeign.getSecExpertSelectList(request, token);
    }

    @ApiOperation(value = "选取时校验专家人数是否满足")
    @PostMapping("/checkExPeople")
    public BaseResponse checkExPeople(@RequestBody ExtractExpertDto request, @RequestHeader String token) {
        log.info("checkExPeople");
        return secFeign.checkExPeople(request, token);
    }

    @ApiOperation(value = "ID查询专家选取申请")
    @GetMapping("/getSecExpertSelectById")
    public BaseResponse getSecExpertSelectById(@RequestParam String id) {
        log.info("getSecExpertSelectById");
        return secFeign.getSecExpertSelectById(id);
    }


    @ApiOperation(value = "创建或修改专家选取申请同时创建新增抽取")
    @PostMapping("/createOrUpdateSecExpertSelectAndSelection")
    public BaseResponse createOrUpdateSecExpertSelectAndSelection(@RequestBody SecExpertSelectAndSelectionDTO request) {
        log.info("createOrUpdateSecExpertSelectAndSelection");
        if (request.getExpertSelect() == null || request.getSelectionRecord() == null)
            return BaseResponse.fail("缺少请求参数！");
        BaseResponse res = secFeign.createOrUpdateSecExpertSelect(request.getExpertSelect());
        if (res != null && res.getSuccess() && ValidateUtil.isNotNull(res.getData())) {
            SecSelectionRecordModel req = request.getSelectionRecord();
            req.setEsId(res.getData().toString());
            return secFeign.createOrUpdateSecSelectionRecord(req);
        } else return res;
    }
}

package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSelectionRecordDtlModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSelectionRecordModel;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecExpertSelectDto;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecSelectionRecordDtlDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(tags = "专家选取记录", value = "专家选取记录")
@RequestMapping("/v1/selectionRecord")
public class SecSelectionRecordController {
    @Autowired
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改专家选取记录")
    @PostMapping("/createOrUpdateSecSelectionRecord")
    public BaseResponse createOrUpdateSecSelectionRecord(@RequestBody SecSelectionRecordModel request) {
        log.info("createOrUpdateSecSelectionRecord");
        return secFeign.createOrUpdateSecSelectionRecord(request);
    }

    @ApiOperation(value = "分页查询专家选取记录")
    @PostMapping("/getSecSelectionRecordList")
    public BaseResponse getSecSelectionRecordList(@RequestBody SecSelectionRecordModel request,@RequestHeader String token) {
        log.info("getSecSelectionRecordList");
        return secFeign.getSecSelectionRecordList(request,token);
    }

    @ApiOperation(value = "多条件筛选专家选取记录")
    @PostMapping("/getSecSelectionRecordClassInfo")
    public BaseResponse getSecSelectionRecordClassInfo(@RequestBody SecSelectionRecordModel request,@RequestHeader String token) {
        log.info("getSecSelectionRecordClassInfo");
        return secFeign.getSecSelectionRecordClassInfo(request,token);
    }

    @ApiOperation(value = "ID查询专家选取记录")
    @GetMapping("/getSecSelectionRecordById")
    public BaseResponse getSecSelectionRecordById(@RequestParam String srId) {
        log.info("getSecSelectionRecordById");
        return secFeign.getSecSelectionRecordById(srId);
    }

    @ApiOperation(value = "ID删除专家选取记录")
    @GetMapping("/deleteSecSelectionRecordById")
    public BaseResponse deleteSecSelectionRecordById(@RequestParam String srId) {
        log.info("deleteSecSelectionRecordById");
        return secFeign.deleteSecSelectionRecordById(srId);
    }

    @ApiOperation(value = "查询专家参会次数")
    @GetMapping("/getSelectionRecordDtlCount")
    public BaseResponse getSelectionRecordDtlCount(@RequestParam String exId) {
        log.info("getSelectionRecordDtlCount");
        return secFeign.getSelectionRecordDtlCount(exId);
    }

    @ApiOperation(value = "是否回避，是否按时参加")
    @PostMapping("/updateSecSelectionRecordDtl")
    public BaseResponse updateSecSelectionRecordDtl(@RequestBody SecSelectionRecordDtlModel request) {
        log.info("updateSecSelectionRecordDtl");
        return secFeign.updateSecSelectionRecordDtl(request);
    }

    @ApiOperation(value = "获取专家的扣分情况")
    @PostMapping("/getSelectionRecordDtlListByExId")
    public BaseResponse getSelectionRecordDtlListByExId(@RequestBody SecSelectionRecordDtlDto request) {
        log.info("getSelectionRecordDtlListByExId");
        return secFeign.getSelectionRecordDtlListByExId(request);
    }

    /****************************************专家抽取***********************************************/
    @ApiOperation(value = "创建新增专家抽取")
    @PostMapping("/createOrUpdateExpertExtract")
    public BaseResponse createOrUpdateExpertExtract(@RequestBody SecSelectionRecordModel request) {
        log.info("createOrUpdateExpertExtract");
        return secFeign.createOrUpdateExpertExtract(request);
    }

    @ApiOperation(value = "ID查询专家抽取")
    @GetMapping("/getSecExpertExtractById")
    public BaseResponse getSecExpertExtractById(@RequestParam String srId) {
        log.info("getSecExpertExtractById");
        return secFeign.getSecExpertExtractById(srId);
    }

    @ApiOperation(value = "抽取申请ID查询专家抽取")
    @GetMapping("/getSecExpertExtractByEsId")
    public BaseResponse getSecExpertExtractByEsId(@RequestParam String esId) {
        log.info("getSecExpertExtractByEsId");
        return secFeign.getSecExpertExtractByEsId(esId);
    }


    @ApiOperation(value = "分页查询专家抽取")
    @PostMapping("/getSecExpertExtractList")
    public BaseResponse getSecExpertExtractList(@RequestBody SecExpertSelectDto request, @RequestHeader String token) {
        log.info("getSecExpertExtractList");
        return secFeign.getSecExpertExtractList(request, token);
    }

    @ApiOperation(value = "作废专家抽取同时作废对应专家选取信息")
    @GetMapping("/cancelExtract2cancelSelect")
    public BaseResponse cancelExtract2cancelSelect(@RequestParam String srId) {
        log.info("cancelExtract2cancelSelect");
        return secFeign.cancelExtract2cancelSelect(srId);
    }

    @ApiOperation(value = "查询同标段的招标文件审批结束记录数")
    @PostMapping("/getSectionDocumentCount")
    public BaseResponse getSectionDocumentCount(@RequestBody SecSelectionRecordModel request) {
        log.info("getSectionDocumentCount");
        return secFeign.getSectionDocumentCount(request);
    }

    @ApiOperation(value = "根据项目和标段查询评标方法")
    @GetMapping("/getTenderingMethodByProjectAndSection")
    public BaseResponse getTenderingMethodByProjectAndSection(@RequestParam("pId") String pId,
                                                              @RequestParam("secId") String secId) {
        log.info("getTenderingMethodByProjectAndSection");
        return secFeign.getTenderingMethodByProjectAndSection(pId, secId);
    }

    @ApiOperation(value = "查询标段下招标问价和答疑补疑的信息")
    @PostMapping("/getSecSelectionRecordInfoBySecId")
    public BaseResponse getSecSelectionRecordInfoBySecId(@RequestBody SecSelectionRecordModel request) {
        log.info("getSecSelectionRecordInfoBySecId");
        return secFeign.getSecSelectionRecordInfoBySecId(request);
    }
}

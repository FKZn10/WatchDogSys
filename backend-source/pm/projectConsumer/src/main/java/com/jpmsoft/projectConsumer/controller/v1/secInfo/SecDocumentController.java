package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecDocumentRequest;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Api(tags = "招标文件", value = "招标文件")
@RequestMapping("/v1/secDocument")
public class SecDocumentController {
    @Autowired
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改招标文件")
    @PostMapping("/createOrUpdateSecDocument")
    public BaseResponse createOrUpdateSecDocument(@RequestBody SecDocumentRequest request) {
        log.info("createOrUpdateSecDocument");
        return secFeign.createOrUpdateSecDocument(request);
    }

    @ApiOperation(value = "ID删除招标文件")
    @GetMapping("/deleteSecDocumentBydId")
    public BaseResponse deleteSecDocumentBydId(@RequestParam String dId) {
        log.info("deleteSecDocumentBydId");
        return secFeign.deleteSecDocumentBydId(dId);
    }

    @ApiOperation(value = "判断是否可办理招标文件（城建定制）")
    @GetMapping("/can2CreateSecDocument")
    public BaseResponse can2CreateSecDocument(@RequestParam String secId) {
        log.info("can2CreateSecDocument");
        return secFeign.can2CreateSecDocument(secId);
    }

    @ApiOperation(value = "ID查询招标文件")
    @GetMapping("/getSecDocumentBydId")
    public BaseResponse getSecDocumentBydId(@RequestParam String dId) {
        log.info("getSecDocumentBydId");
        return secFeign.getSecDocumentBydId(dId);
    }

    @ApiOperation(value = "标段ID查询招标文件")
    @GetMapping("/getSecDocumentBySecId")
    public BaseResponse getSecDocumentBySecId(@RequestParam String secId) {
        log.info("getSecDocumentBySecId");
        return secFeign.getSecDocumentBySecId(secId);
    }

    @PostMapping("/getpBusinessUnit")
    public BaseResponse getpBusinessUnit(@RequestBody List<String> pIds) {
        log.info("getpBusinessUnit");
        return secFeign.getpBusinessUnit(pIds);
    }

    @ApiOperation("多条件查询招标文件")
    @PostMapping("/getSecDocumentListClassInfo")
    public BaseResponse getSecDocumentListClassInfo(@RequestBody SecDocumentRequest request,@RequestHeader String token) {
        log.info("getSecDocumentListClassInfo");
        return secFeign.getSecDocumentListClassInfo(request,token);
    }
    @ApiOperation("多条件筛选查询招标文件")
    @PostMapping("/getSecDocumentListClass")
    public BaseResponse getSecDocumentListClass(@RequestBody SecDocumentRequest request,@RequestHeader String token) {
        log.info("getSecDocumentListClass");
        return secFeign.getSecDocumentListClass(request,token);
    }

    @ApiOperation(value = "分页查询招标文件")
    @PostMapping("/getSecDocumentList")
    public BaseResponse getSecDocumentList(@RequestBody SecDocumentRequest request) {
        log.info("getSecDocumentList");
        return secFeign.getSecDocumentList(request);
    }

    @ApiOperation(value = "分页查询招标文件")
    @PostMapping("/getSecDocumentListNJCJ")
    public BaseResponse getSecDocumentListNJCJ(@RequestBody SecDocumentRequest request) {
        log.info("getSecDocumentListNJCJ");
        return secFeign.getSecDocumentListNJCJ(request);
    }

    @ApiOperation(value = "修改招标文件开标时间地点")
    @PostMapping("/updateSecDocumentById")
    public BaseResponse updateSecDocumentById(@RequestBody SecDocumentRequest request) {
        log.info("updateSecDocumentById");
        return secFeign.updateSecDocumentById(request);
    }


    @ApiOperation(value = "分页查询招标文件(项目信息默认一个项目对应多个标段)")
    @PostMapping("/getSecDocumentListOne2Many")
    public BaseResponse getSecDocumentListOne2Many(@RequestBody SecDocumentRequest request) {
        log.info("getSecDocumentListOne2Many");
        return secFeign.getSecDocumentListOne2Many(request);
    }


    @ApiOperation(value = "分页查询招标文件,项目为主体分组查询")
    @PostMapping("/getSecDocumentGroupByProject")
    public BaseResponse getSecDocumentGroupByProject(@RequestBody SecDocumentRequest request, @RequestHeader String token) {
        log.info("getSecDocumentGroupByProject");
        return secFeign.getSecDocumentGroupByProject(request, token);
    }

    @ApiOperation("获取招标文件的费率")
    @GetMapping("/getDocumentRate")
    public BaseResponse getDocumentRate(@RequestParam String secId) {
        log.info("getDocumentRate");
        return secFeign.getDocumentRate(secId);
    }
}

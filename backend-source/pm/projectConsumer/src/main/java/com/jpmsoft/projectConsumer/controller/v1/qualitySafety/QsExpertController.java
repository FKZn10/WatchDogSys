package com.jpmsoft.projectConsumer.controller.v1.qualitySafety;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Qs.QsExpertModel;
import com.jpmsoft.projectConsumer.feign.QsFeign;
import com.jpmsoft.projectConsumer.pojo.dto.qualitySafety.QsExpertVacationDTO;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.ExtractExpertDto;
import feign.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
@RestController
@Api(tags = "专家库", value = "专家库")
@RequestMapping("/v1/QsExpert")
public class QsExpertController {
    @Resource
    private QsFeign qsFeign;

    @ApiOperation(value = "创建或修改专家库")
    @PostMapping("/createOrUpdateQsExpert")
    public BaseResponse createOrUpdateQsExpert(@RequestBody QsExpertModel request) {
        log.info("createOrUpdateQsExpert");
        return qsFeign.createOrUpdateQsExpert(request);
    }


    @ApiOperation(value = "创建或修改专家库(根据用户关联身份号)")
    @PostMapping("/createOrUpdateQsExpertCurrent")
    public BaseResponse createOrUpdateQsExpertCurrent(@RequestBody QsExpertModel request) {
        log.info("createOrUpdateQsExpertCurrent");
        return qsFeign.createOrUpdateQsExpertCurrent(request);
    }

    @ApiOperation(value = "创建或修改专家库(根据用户关联身份号，并且校验唯一性)")
    @PostMapping("/createOrUpdateQsExpertVerify")
    public BaseResponse createOrUpdateQsExpertVerify(@RequestBody QsExpertModel request) {
        log.info("createOrUpdateQsExpertVerify");
        return qsFeign.createOrUpdateQsExpertVerify(request);
    }


    @ApiOperation(value = "重置专家库明文身份证件")
    @PostMapping("/resetPlaintextNum")
    public BaseResponse resetPlaintextNum() {
        log.info("resetPlaintextNum");
        return qsFeign.resetPlaintextNum();
    }

    @ApiOperation(value = "分页查询专家库")
    @PostMapping("/getQsExpertList")
    public BaseResponse getQsExpertList(@RequestBody QsExpertModel request) {
        log.info("getQsExpertList");
        return qsFeign.getQsExpertList(request);
    }

    @ApiOperation(value = "分页查询专家库(城建定制专家鉴权逻辑)")
    @PostMapping("/getQsExpertCjDzList")
    public BaseResponse getQsExpertCjDzList(@RequestBody QsExpertModel request, @RequestHeader String token) {
        log.info("getQsExpertCjDzList");
        return qsFeign.getQsExpertCjDzList(request, token);
    }

    @ApiOperation(value = "专家库导出EXCEL")
    @PostMapping("/exportExcelQsExpertCjDzList")
    public void exportExcelQsExpertCjDzList(HttpServletResponse response, @RequestBody QsExpertModel request, @RequestHeader String token) {
        log.info("countInvCompleteMonthExportExcel");
        Response feignResponse = qsFeign.exportExcelQsExpertCjDzList(request, token);
        Response.Body body = feignResponse.body();
        try (InputStream inputStream = body.asInputStream();
             OutputStream outputStream = response.getOutputStream()) {
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, feignResponse.headers().get(HttpHeaders.CONTENT_DISPOSITION).stream().findFirst().get());
            response.setContentType(feignResponse.headers().get(HttpHeaders.CONTENT_TYPE).stream().findFirst().get());
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            log.error("exportExcelQsExpertCjDzList  error");
        }
    }

    @ApiOperation(value = "ID查询专家库")
    @GetMapping("/getQsExpertById")
    public BaseResponse getQsExpertById(@RequestParam String exId) {
        log.info("getQsExpertById");
        return qsFeign.getQsExpertById(exId);
    }

    @ApiOperation(value = "ID删除专家库")
    @GetMapping("/deleteQsExpertById")
    public BaseResponse deleteQsExpertById(@RequestParam String exId) {
        log.info("deleteQsExpertById");
        return qsFeign.deleteQsExpertById(exId);
    }

    @ApiOperation(value = "证件号校验专家是否已存在")
    @GetMapping("/expertExists")
    public BaseResponse expertExists(@RequestParam String idNum, @RequestParam(required = false) String id, @RequestHeader String token) {
        log.info("expertExists");
        return qsFeign.expertExists(idNum, id, token);
    }

    @ApiOperation(value = "抽取专家信息")
    @PostMapping("/extractExpertInfo")
    public BaseResponse extractExpertInfo(@RequestBody ExtractExpertDto request, @RequestHeader String token) {
        log.info("extractExpertInfo");
        return qsFeign.extractExpertInfo(request, token);
    }

    @ApiOperation(value = "抽取专家信息(限定技术类每个专家的专业随机抽取，经济类按人数随机抽取)")
    @PostMapping("/extractExpert")
    public BaseResponse extractExpert(@RequestBody ExtractExpertDto request, @RequestHeader String token) {
        log.info("extractExpert");
        return qsFeign.extractExpert(request, token);
    }

    @ApiOperation(value = "专家请假申请获取专家信息")
    @PostMapping("/getQsExpertWhenVacation")
    public BaseResponse getQsExpertWhenVacation(@RequestBody QsExpertVacationDTO request, @RequestHeader String token) {
        log.info("getQsExpertWhenVacation");
        return qsFeign.getQsExpertWhenVacation(request, token);
    }

    @ApiOperation(value = "专家请假申请获取专家信息(不根据当前操作人身份证排除)")
    @PostMapping("/getQsExpertWhenVacationNoId")
    public BaseResponse getQsExpertWhenVacationNoId(@RequestBody QsExpertVacationDTO request, @RequestHeader String token) {
        log.info("getQsExpertWhenVacationNoId");
        return qsFeign.getQsExpertWhenVacationNoId(request, token);
    }

    @ApiOperation(value = "获取当前登录用户对应的专家信息")
    @GetMapping("/getQsExpertByCurrent")
    public BaseResponse getQsExpertByCurrent(@RequestHeader String token) {
        log.info("getQsExpertByCurrent");
        return qsFeign.getQsExpertByCurrent(token);
    }

    @ApiOperation(value = "专家人员状态调整")
    @PostMapping("/updatePersonnel")
    public BaseResponse updatePersonnel(@RequestBody QsExpertModel request) {
        log.info("updatePersonnel");
        return qsFeign.updatePersonnel(request);
    }


}

package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGExpertModel;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.ExtractJGAgencyExpertDto;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.ExtractJGExpertDto;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecJGExpertVacationDTO;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import feign.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
@RestController
@Api(tags = "专家库（建管）", value = "专家库（建管）")
@RequestMapping("/v1/secJGExpert")
public class SecJGExpertController {
    @Resource
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改专家库")
    @PostMapping("/createOrUpdateSecJGExpert")
    public BaseResponse createOrUpdateSecJGExpert(@RequestBody SecJGExpertModel request) {
        log.info("createOrUpdateSecJGExpert");
        return secFeign.createOrUpdateSecJGExpert(request);
    }


    @ApiOperation(value = "创建或修改专家库(根据用户关联身份号)")
    @PostMapping("/createOrUpdateSecJGExpertCurrent")
    public BaseResponse createOrUpdateSecJGExpertCurrent(@RequestBody SecJGExpertModel request) {
        log.info("createOrUpdateSecJGExpertCurrent");
        return secFeign.createOrUpdateSecJGExpertCurrent(request);
    }

    @ApiOperation(value = "创建或修改专家库(根据用户关联身份号，并且校验唯一性)")
    @PostMapping("/createOrUpdateSecJGExpertVerify")
    public BaseResponse createOrUpdateSecJGExpertVerify(@RequestBody SecJGExpertModel request) {
        log.info("createOrUpdateSecJGExpertVerify");
        return secFeign.createOrUpdateSecJGExpertVerify(request);
    }


    @ApiOperation(value = "重置专家库明文身份证件")
    @PostMapping("/resetJGPlaintextNum")
    public BaseResponse resetJGPlaintextNum() {
        log.info("resetJGPlaintextNum");
        return secFeign.resetJGPlaintextNum();
    }

    @ApiOperation(value = "分页查询专家库")
    @PostMapping("/getSecJGExpertList")
    public BaseResponse getSecJGExpertList(@RequestBody SecJGExpertModel request) {
        log.info("getSecJGExpertList");
        return secFeign.getSecJGExpertList(request);
    }

    @ApiOperation(value = "分页查询专家库(城建定制专家鉴权逻辑)")
    @PostMapping("/getSecJGExpertCjDzList")
    public BaseResponse getSecJGExpertCjDzList(@RequestBody SecJGExpertModel request, @RequestHeader String token) {
        log.info("getSecJGExpertCjDzList");
        return secFeign.getSecJGExpertCjDzList(request, token);
    }

    @ApiOperation(value = "专家库导出EXCEL")
    @PostMapping("/exportExcelSecJGExpertCjDzList")
    public void exportExcelSecJGExpertCjDzList(HttpServletResponse response, @RequestBody SecJGExpertModel request, @RequestHeader String token) {
        log.info("exportExcelSecJGExpertCjDzList");
        Response feignResponse = secFeign.exportExcelSecJGExpertCjDzList(request, token);
        Response.Body body = feignResponse.body();
        try (InputStream inputStream = body.asInputStream();
            OutputStream outputStream = response.getOutputStream()) {
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, feignResponse.headers().get(HttpHeaders.CONTENT_DISPOSITION).stream().findFirst().get());
            response.setContentType(feignResponse.headers().get(HttpHeaders.CONTENT_TYPE).stream().findFirst().get());
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            log.error("exportExcelSecJGExpertCjDzList  error");
        }
    }

    @ApiOperation(value = "外部专家库模板")
    @PostMapping("/excelSecJGExpertTemplate")
    public void excelSecJGExpertTemplate(HttpServletResponse response, @RequestBody SecJGExpertModel request, @RequestHeader String token) {
        log.info("excelSecJGExpertTemplate");
        Response feignResponse = secFeign.excelSecJGExpertTemplate(request, token);
        Response.Body body = feignResponse.body();
        try (InputStream inputStream = body.asInputStream();
             OutputStream outputStream = response.getOutputStream()) {
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, feignResponse.headers().get(HttpHeaders.CONTENT_DISPOSITION).stream().findFirst().get());
            response.setContentType(feignResponse.headers().get(HttpHeaders.CONTENT_TYPE).stream().findFirst().get());
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            log.error("excelSecJGExpertTemplate  error");
        }
    }



    @ApiOperation(value = "专家库导入")
    @PostMapping("/importSecJGExpert")
    public BaseResponse importSecJGExpert(@RequestPart("files") MultipartFile files, @RequestHeader String token) throws IOException {
       return secFeign.importSecJGExpert(files,token);
    }

    @ApiOperation(value = "ID查询专家库")
    @GetMapping("/getSecJGExpertById")
    public BaseResponse getSecExpertById(@RequestParam String exId) {
        log.info("getSecJGExpertById");
        return secFeign.getSecJGExpertById(exId);
    }

    @ApiOperation(value = "ID删除专家库")
    @GetMapping("/deleteSecJGExpertById")
    public BaseResponse deleteSecJGExpertById(@RequestParam String exId) {
        log.info("deleteSecJGExpertById");
        return secFeign.deleteSecJGExpertById(exId);
    }

    @ApiOperation(value = "证件号校验专家是否已存在")
    @GetMapping("/expertJGExists")
    public BaseResponse expertJGExists(@RequestParam String idNum, @RequestParam(required = false) String id, @RequestHeader String token) {
        log.info("expertJGExists");
        return secFeign.expertJGExists(idNum, id, token);
    }

    @ApiOperation(value = "抽取专家信息")
    @PostMapping("/extractJGExpertInfo")
    public BaseResponse extractJGExpertInfo(@RequestBody ExtractJGExpertDto request, @RequestHeader String token) {
        log.info("extractJGExpertInfo");
        return secFeign.extractJGExpertInfo(request, token);
    }

    @ApiOperation(value = "抽取专家信息(限定技术类每个专家的专业随机抽取，经济类按人数随机抽取)")
    @PostMapping("/extractJGExpert")
    public BaseResponse extractJGExpert(@RequestBody ExtractJGExpertDto request, @RequestHeader String token) {
        log.info("extractJGExpert");
        return secFeign.extractJGExpert(request, token);
    }

    @ApiOperation(value = "抽取专家信息(限定技术类每个专家的专业随机抽取，经济类按人数随机抽取)")
    @PostMapping("/extractJGExpertCj")
    public BaseResponse extractJGExpertCj(@RequestBody ExtractJGExpertDto request, @RequestHeader String token) {
        log.info("extractJGExpertCj");
        return secFeign.extractJGExpertCj(request, token);
    }

    @PostMapping("/extractJGExpertJudge")
    public BaseResponse extractJGExpertJudge(@RequestBody ExtractJGExpertDto request, @RequestHeader String token) {
        log.info("extractJGExpertJudge");
        return secFeign.extractJGExpertJudge(request, token);
    }


    @PostMapping("/extractJGAgencyExpert")
    public BaseResponse extractAgencyExpert(@RequestBody ExtractJGAgencyExpertDto request, @RequestHeader String token) {
        log.info("extractJGAgencyExpert");
        return secFeign.extractJGAgencyExpert(request, token);
    }

    @ApiOperation(value = "专家请假申请获取专家信息")
    @PostMapping("/getSecJGExpertWhenVacation")
    public BaseResponse getSecJGExpertWhenVacation(@RequestBody SecJGExpertVacationDTO request, @RequestHeader String token) {
        log.info("getSecJGExpertWhenVacation");
        return secFeign.getSecJGExpertWhenVacation(request, token);
    }

    @ApiOperation(value = "专家请假申请获取专家信息(不根据当前操作人身份证排除)")
    @PostMapping("/getSecJGExpertWhenVacationNoId")
    public BaseResponse getSecJGExpertWhenVacationNoId(@RequestBody SecJGExpertVacationDTO request, @RequestHeader String token) {
        log.info("getSecJGExpertWhenVacationNoId");
        return secFeign.getSecJGExpertWhenVacationNoId(request, token);
    }

    @ApiOperation(value = "获取当前登录用户对应的专家信息")
    @GetMapping("/getSecJGExpertByCurrent")
    public BaseResponse getSecJGExpertByCurrent(@RequestHeader String token) {
        log.info("getSecJGExpertByCurrent");
        return secFeign.getSecJGExpertByCurrent(token);
    }

}

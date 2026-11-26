package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGExpertModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.ExtractJGAgencyExpertDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.ExtractJGExpertDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecJGExpertVacationDTO;
import com.jpmsoft.projectProducer.service.secInfo.ISecJGExpertService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Slf4j
@RestController
@RequestMapping("/v1/secJGExpert")
public class SecJGExpertController {

    @Resource
    private ISecJGExpertService secJGExpertService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateSecJGExpert")
    public BaseResponse createOrUpdateSecJGExpert(@RequestBody SecJGExpertModel request) {
        log.info("createOrUpdateSecJGExpert");
        return secJGExpertService.createOrUpdateSecJGExpert(request);
    }


    @TenantId
    @PostMapping("/createOrUpdateSecJGExpertCurrent")
    public BaseResponse createOrUpdateSecJGExpertCurrent(@RequestBody SecJGExpertModel request) {
        log.info("createOrUpdateSecJGExpertCurrent");
        return secJGExpertService.createOrUpdateSecJGExpertCurrent(request);
    }


    @TenantId
    @PostMapping("/createOrUpdateSecJGExpertVerify")
    public BaseResponse createOrUpdateSecJGExpertVerify(@RequestBody SecJGExpertModel request) {
        log.info("createOrUpdateSecJGExpertVerify");
        return secJGExpertService.createOrUpdateSecJGExpertVerify(request);
    }


    @GetMapping("/resetJGPlaintextNum")
    public BaseResponse resetJGPlaintextNum() {
        log.info("resetJGPlaintextNum");
        return secJGExpertService.resetJGPlaintextNum();
    }

    @TenantId
    @PostMapping("/getSecJGExpertList")
    public BaseResponse getSecJGExpertList(@RequestBody SecJGExpertModel request) {
        log.info("getSecJGExpertList");
        return secJGExpertService.getSecJGExpertList(request);
    }


    @TenantId
    @PostMapping("/getSecJGExpertCjDzList")
    public BaseResponse getSecExpertCjDzList(@RequestBody SecJGExpertModel request,@RequestHeader String token) {
        log.info("getSecJGExpertCjDzList");
        return secJGExpertService.getSecJGExpertCjDzList(request,redisUtils.getUid(token));
    }

    @PostMapping("/exportExcelSecJGExpertCjDzList")
    public void exportExcelSecJGExpertCjDzList(HttpServletResponse response,
                                             @RequestBody SecJGExpertModel request,
                                             @RequestHeader String token) {
        try {
            Workbook workbook = secJGExpertService.exportExcelSecJGExpertCjDzList(request, redisUtils.getUid(token));

            String fileName = URLEncoder.encode("专家库.xls", "UTF-8");
            // 告诉浏览器用什么软件可以打开此文件
            response.setHeader("content-Type", "application/vnd.ms-excel");
            // 下载文件的默认名称
            response.setHeader("Content-Disposition", fileName);
            //编码
            response.setCharacterEncoding("UTF-8");
            //多sheet
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (IOException e) {
            log.error("专家库导出EXCEL导出出错{}", e.getMessage(),e);
        }
    }


    @PostMapping("/excelSecJGExpertTemplate")
    public void excelSecJGExpertTemplate(HttpServletResponse response,
                                               @RequestBody SecJGExpertModel request,
                                               @RequestHeader String token) {
        try {
            Workbook workbook = secJGExpertService.excelSecJGExpertTemplate(request, redisUtils.getUid(token));

            String fileName = URLEncoder.encode("外部专家库模板.xls", "UTF-8");
            // 告诉浏览器用什么软件可以打开此文件
            response.setHeader("content-Type", "application/vnd.ms-excel");
            // 下载文件的默认名称
            response.setHeader("Content-Disposition", fileName);
            //编码
            response.setCharacterEncoding("UTF-8");
            //多sheet
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (IOException e) {
            log.error("专家库导出EXCEL导出出错{}", e.getMessage(),e);
        }
    }

    @PostMapping("/importSecJGExpert")
    public BaseResponse importSecJGExpert(@RequestPart("files") MultipartFile files, @RequestHeader String token) throws IOException {
        return secJGExpertService.importSecJGExpert(files,redisUtils.getUserInfoByToken(token));
    }

    @GetMapping("/getSecJGExpertById")
    public BaseResponse getSecJGExpertById(@RequestParam String exId) {
        log.info("getSecJGExpertById");
        return secJGExpertService.getSecJGExpertById(exId);
    }

    @GetMapping("/deleteSecJGExpertById")
    public BaseResponse deleteSecJGExpertById(@RequestParam String exId) {
        log.info("deleteSecJGExpertById");
        return secJGExpertService.deleteSecJGExpertById(exId);
    }


    @GetMapping("/expertJGExists")
    public BaseResponse expertJGExists(@RequestParam String idNum, @RequestParam(required = false) String id, @RequestHeader String token) {
        log.info("expertJGExists");
        return BaseResponse.ok(secJGExpertService.expertJGExists(idNum, id, redisUtils.getTeId(token)));
    }

    @PostMapping("/extractJGExpertInfo")
    public BaseResponse extractExpertInfo(@RequestBody ExtractJGExpertDto request, @RequestHeader String token) {
        log.info("extractJGExpertInfo");
        return secJGExpertService.extractJGExpertInfo(request, redisUtils.getTeId(token));
    }


    @PostMapping("/extractJGExpert")
    public BaseResponse extractExpert(@RequestBody ExtractJGExpertDto request, @RequestHeader String token) {
        log.info("extractJGExpert");
        return secJGExpertService.extractJGExpert(request, redisUtils.getTeId(token));
    }

    @PostMapping("/extractJGExpertCj")
    public BaseResponse extractJGExpertCj(@RequestBody ExtractJGExpertDto request, @RequestHeader String token) {
        log.info("extractJGExpertCj");
        return secJGExpertService.extractJGExpertCj(request, redisUtils.getTeId(token));
    }

    //评委抽取
    @PostMapping("/extractJGExpertJudge")
    public BaseResponse extractJGExpertJudge(@RequestBody ExtractJGExpertDto request, @RequestHeader String token) {
        log.info("extractJGExpertJudge");
        return secJGExpertService.extractJGExpertJudge(request, redisUtils.getTeId(token));
    }



    @PostMapping("/extractJGAgencyExpert")
    public BaseResponse extractAgencyExpert(@RequestBody ExtractJGAgencyExpertDto request, @RequestHeader String token) {
        log.info("extractJGAgencyExpert");
        return secJGExpertService.extractJGAgencyExpert(request, redisUtils.getTeId(token));
    }


    @PostMapping("/getSecJGExpertWhenVacation")
    public BaseResponse getSecJGExpertWhenVacation(@RequestBody SecJGExpertVacationDTO request, @RequestHeader String token) {
        log.info("getSecJGExpertWhenVacation");
        return secJGExpertService.getSecJGExpertWhenVacation(request, redisUtils.getUid(token));
    }

    @PostMapping("/getSecJGExpertWhenVacationNoId")
    public BaseResponse getSecJGExpertWhenVacationNoId(@RequestBody SecJGExpertVacationDTO request, @RequestHeader String token) {
        log.info("getSecJGExpertWhenVacationNoId");
        return secJGExpertService.getSecJGExpertWhenVacationNoId(request, redisUtils.getUid(token));
    }

    @GetMapping("/getSecJGExpertByCurrent")
    public BaseResponse getSecJGExpertByCurrent(@RequestHeader String token) {
        return secJGExpertService.getSecJGExpertByCurrent(redisUtils.getUid(token));
    }



}

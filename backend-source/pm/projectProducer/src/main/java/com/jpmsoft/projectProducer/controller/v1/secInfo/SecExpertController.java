package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.ExtractExpertDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertVacationDTO;
import com.jpmsoft.projectProducer.pojo.request.ReturnInitiatorDto;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertService;
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
@RequestMapping("/v1/secExpert")
public class SecExpertController {

    @Resource
    private ISecExpertService secExpertService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateSecExpert")
    public BaseResponse createOrUpdateSecExpert(@RequestBody SecExpertModel request) {
        log.info("createOrUpdateSecExpert");
        return secExpertService.createOrUpdateSecExpert(request);
    }


    @TenantId
    @PostMapping("/createOrUpdateSecExpertCurrent")
    public BaseResponse createOrUpdateSecExpertCurrent(@RequestBody SecExpertModel request) {
        log.info("createOrUpdateSecExpertCurrent");
        return secExpertService.createOrUpdateSecExpertCurrent(request);
    }

    @TenantId
    @PostMapping("/createOrUpdateSecExpertVerify")
    public BaseResponse createOrUpdateSecExpertVerify(@RequestBody SecExpertModel request) {
        log.info("createOrUpdateSecExpertVerify");
        return secExpertService.createOrUpdateSecExpertVerify(request);
    }


    @GetMapping("/resetPlaintextNum")
    public BaseResponse resetPlaintextNum() {
        log.info("resetPlaintextNum");
        return secExpertService.resetPlaintextNum();
    }

    @TenantId
    @PostMapping("/getSecExpertList")
    public BaseResponse getSecExpertList(@RequestBody SecExpertModel request) {
        log.info("getSecExpertList");
        return secExpertService.getSecExpertList(request);
    }

    @TenantId
    @PostMapping("/getSecExpertCjDzList")
    public BaseResponse getSecExpertCjDzList(@RequestBody SecExpertModel request,@RequestHeader String token) {
        log.info("getSecExpertCjDzList");
        return secExpertService.getSecExpertCjDzList(request, token);
    }

    /**
     * 专家库多条件查询(城建定制专家鉴权逻辑)
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/getSecExpertCjDzListClass")
    public BaseResponse getSecExpertCjDzListClass(@RequestBody SecExpertModel request,@RequestHeader String token) {
        log.info("getSecExpertCjDzListClass");
        return secExpertService.getSecExpertCjDzListClass(request, token);
    }

    @PostMapping("/exportExcelSecExpertCjDzList")
    public void exportExcelSecExpertCjDzList(HttpServletResponse response,
                                             @RequestBody SecExpertModel request,
                                             @RequestHeader String token) {
        try {
            Workbook workbook = secExpertService.exportExcelSecExpertCjDzList(request, redisUtils.getUid(token));
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

    @PostMapping("/importExcelSecExpertCjDzList")
    public BaseResponse importExcelSecExpertCjDzList(@RequestPart("file") MultipartFile file, @RequestHeader String token) {
        log.info("importExcelSecExpertCjDzList");
        return secExpertService.importExcelSecExpertCjDzList(file, redisUtils.getUserInfoByToken(token));
    }

    @GetMapping("/getSecExpertById")
    public BaseResponse getSecExpertById(@RequestParam String exId) {
        log.info("getSecExpertById");
        return secExpertService.getSecExpertById(exId);
    }

    @GetMapping("/deleteSecExpertById")
    public BaseResponse deleteSecExpertById(@RequestParam String exId) {
        log.info("deleteSecExpertById");
        return secExpertService.deleteSecExpertById(exId);
    }


    @GetMapping("/expertExists")
    public BaseResponse expertExists(@RequestParam String idNum, @RequestParam(required = false) String id, @RequestHeader String token) {
        log.info("expertExists");
        return BaseResponse.ok(secExpertService.expertExists(idNum, id, redisUtils.getTeId(token)));
    }

    @PostMapping("/extractExpertInfo")
    public BaseResponse extractExpertInfo(@RequestBody ExtractExpertDto request, @RequestHeader String token) {
        log.info("extractExpertInfo");
        return secExpertService.extractExpertInfo(request, redisUtils.getTeId(token));
    }


    @PostMapping("/extractExpert")
    public BaseResponse extractExpert(@RequestBody ExtractExpertDto request, @RequestHeader String token) {
        log.info("extractExpert");
        return secExpertService.extractExpert(request, redisUtils.getTeId(token));
    }



    @PostMapping("/getSecExpertWhenVacation")
    public BaseResponse getSecExpertWhenVacation(@RequestBody SecExpertVacationDTO request, @RequestHeader String token) {
        log.info("getSecExpertWhenVacation");
        return secExpertService.getSecExpertWhenVacation(request, redisUtils.getUid(token));
    }

    @PostMapping("/getSecExpertWhenVacationNoId")
    public BaseResponse getSecExpertWhenVacationNoId(@RequestBody SecExpertVacationDTO request, @RequestHeader String token) {
        log.info("getSecExpertWhenVacationNoId");
        return secExpertService.getSecExpertWhenVacationNoId(request, redisUtils.getUid(token));
    }

    @GetMapping("/getSecExpertByCurrent")
    public BaseResponse getSecExpertByCurrent(@RequestHeader String token) {
        return secExpertService.getSecExpertByCurrent(redisUtils.getUid(token));
    }


    //专家库人员状态调整
    @PostMapping("/updatePersonnel")
    public BaseResponse updatePersonnel(@RequestBody SecExpertModel request){
        return  secExpertService.updatePersonnel(request);
    }

    /**
     * 根据用户ID获取专家信息
     *
     * @param uId
     * @param token
     * @return
     */
    @GetMapping("/getSecExpertByuId")
    public BaseResponse getSecExpertByuId(@RequestParam(required = false, value = "uId") String uId, @RequestHeader(required = false) String token){
        log.info("getSecExpertByuId");
        return secExpertService.getSecExpertByuId(uId, redisUtils.getUid(token));
    }

    /**
     * 评标专家申请驳回到发起人
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/returnInitiatorToTask")
    public BaseResponse returnInitiatorToTask(@RequestBody ReturnInitiatorDto request, @RequestHeader String token) {
        log.info("returnInitiatorToTask");
        return secExpertService.returnInitiatorToTask(request, token);
    }

    /**
     * 新的一年复制专家库
     *
     * @param year
     * @return
     */
    @GetMapping("/copyAllSecExpert")
    public BaseResponse copyAllSecExpert(@RequestParam(required = false) Integer year) {
        log.info("copyAllSecExpert");
        return secExpertService.copyAllSecExpert(year);
    }

    /**
     * 更新专家变更状态
     *
     * @param exId
     * @param changeStatus
     * @return
     */
    @GetMapping("/updateChangeStatus")
    public BaseResponse updateChangeStatus(@RequestParam String exId, @RequestParam String changeStatus) {
        log.info("updateChangeStatus");
        return secExpertService.updateChangeStatus(exId, changeStatus);
    }

    /**
     * 处理用户表与专家库禁用状态同步
     *
     * @return
     */
    @GetMapping("/handleDisableSecExpert")
    public BaseResponse handleDisableSecExpert() {
        log.info("handleDisableSecExpert");
        return secExpertService.handleDisableSecExpert();
    }

    /**
     * 获取可以抽取的专家数量
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/getExtractSecExpertCount")
    public BaseResponse getExtractSecExpertCount(@RequestBody ExtractExpertDto request, @RequestHeader String token) {
        log.info("getExtractSecExpertCount");
        return secExpertService.getExtractSecExpertCount(request, redisUtils.getTeId(token));
    }

    /**
     * 同步单位信息到专家库
     *
     * @param uId
     * @return
     */
    @GetMapping("/syncCompanyToSecExpert")
    public BaseResponse syncCompanyToSecExpert(@RequestParam String uId) {
        log.info("syncCompanyToSecExpert");
        return secExpertService.syncCompanyToSecExpert(uId);
    }
}

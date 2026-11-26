package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertModel;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.ExtractExpertDto;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecExpertVacationDTO;
import com.jpmsoft.projectConsumer.request.ReturnInitiatorDto;
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
@Api(tags = "专家库", value = "专家库")
@RequestMapping("/v1/secExpert")
public class SecExpertController {
    @Resource
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改专家库")
    @PostMapping("/createOrUpdateSecExpert")
    public BaseResponse createOrUpdateSecExpert(@RequestBody SecExpertModel request) {
        log.info("createOrUpdateSecExpert");
        return secFeign.createOrUpdateSecExpert(request);
    }


    @ApiOperation(value = "创建或修改专家库(根据用户关联身份号)")
    @PostMapping("/createOrUpdateSecExpertCurrent")
    public BaseResponse createOrUpdateSecExpertCurrent(@RequestBody SecExpertModel request) {
        log.info("createOrUpdateSecExpertCurrent");
        return secFeign.createOrUpdateSecExpertCurrent(request);
    }

    @ApiOperation(value = "创建或修改专家库(根据用户关联身份号，并且校验唯一性)")
    @PostMapping("/createOrUpdateSecExpertVerify")
    public BaseResponse createOrUpdateSecExpertVerify(@RequestBody SecExpertModel request) {
        log.info("createOrUpdateSecExpertVerify");
        return secFeign.createOrUpdateSecExpertVerify(request);
    }


    @ApiOperation(value = "重置专家库明文身份证件")
    @PostMapping("/resetPlaintextNum")
    public BaseResponse resetPlaintextNum() {
        log.info("resetPlaintextNum");
        return secFeign.resetPlaintextNum();
    }

    @ApiOperation(value = "分页查询专家库")
    @PostMapping("/getSecExpertList")
    public BaseResponse getSecExpertList(@RequestBody SecExpertModel request) {
        log.info("getSecExpertList");
        return secFeign.getSecExpertList(request);
    }

    @ApiOperation(value = "分页查询专家库(城建定制专家鉴权逻辑)")
    @PostMapping("/getSecExpertCjDzList")
    public BaseResponse getSecExpertCjDzList(@RequestBody SecExpertModel request, @RequestHeader String token) {
        log.info("getSecExpertCjDzList");
        return secFeign.getSecExpertCjDzList(request, token);
    }

    /**
     * 专家库多条件查询(城建定制专家鉴权逻辑)
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation(value = "专家库多条件查询(城建定制专家鉴权逻辑)")
    @PostMapping("/getSecExpertCjDzListClass")
    public BaseResponse getSecExpertCjDzListClass(@RequestBody SecExpertModel request,@RequestHeader String token) {
        log.info("getSecExpertCjDzListClass");
        return secFeign.getSecExpertCjDzListClass(request, token);
    }

    @ApiOperation(value = "专家库导出EXCEL")
    @PostMapping("/exportExcelSecExpertCjDzList")
    public void exportExcelSecExpertCjDzList(HttpServletResponse response, @RequestBody SecExpertModel request, @RequestHeader String token) {
        log.info("countInvCompleteMonthExportExcel");
        Response feignResponse = secFeign.exportExcelSecExpertCjDzList(request, token);
        Response.Body body = feignResponse.body();
        try (InputStream inputStream = body.asInputStream();
             OutputStream outputStream = response.getOutputStream()) {
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, feignResponse.headers().get(HttpHeaders.CONTENT_DISPOSITION).stream().findFirst().get());
            response.setContentType(feignResponse.headers().get(HttpHeaders.CONTENT_TYPE).stream().findFirst().get());
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            log.error("exportExcelSecExpertCjDzList  error");
        }
    }

    @PostMapping("/importExcelSecExpertCjDzList")
    public BaseResponse importExcelSecExpertCjDzList(@RequestPart("file") MultipartFile file, @RequestHeader String token) {
        log.info("importExcelSecExpertCjDzList");
        return secFeign.importExcelSecExpertCjDzList(file, token);
    }

    @ApiOperation(value = "ID查询专家库")
    @GetMapping("/getSecExpertById")
    public BaseResponse getSecExpertById(@RequestParam String exId) {
        log.info("getSecExpertById");
        return secFeign.getSecExpertById(exId);
    }

    @ApiOperation(value = "ID删除专家库")
    @GetMapping("/deleteSecExpertById")
    public BaseResponse deleteSecExpertById(@RequestParam String exId) {
        log.info("deleteSecExpertById");
        return secFeign.deleteSecExpertById(exId);
    }

    @ApiOperation(value = "证件号校验专家是否已存在")
    @GetMapping("/expertExists")
    public BaseResponse expertExists(@RequestParam String idNum, @RequestParam(required = false) String id, @RequestHeader String token) {
        log.info("expertExists");
        return secFeign.expertExists(idNum, id, token);
    }

    @ApiOperation(value = "抽取专家信息")
    @PostMapping("/extractExpertInfo")
    public BaseResponse extractExpertInfo(@RequestBody ExtractExpertDto request, @RequestHeader String token) {
        log.info("extractExpertInfo");
        return secFeign.extractExpertInfo(request, token);
    }

    @ApiOperation(value = "抽取专家信息(限定技术类每个专家的专业随机抽取，经济类按人数随机抽取)")
    @PostMapping("/extractExpert")
    public BaseResponse extractExpert(@RequestBody ExtractExpertDto request, @RequestHeader String token) {
        log.info("extractExpert");
        return secFeign.extractExpert(request, token);
    }

    @ApiOperation(value = "专家请假申请获取专家信息")
    @PostMapping("/getSecExpertWhenVacation")
    public BaseResponse getSecExpertWhenVacation(@RequestBody SecExpertVacationDTO request, @RequestHeader String token) {
        log.info("getSecExpertWhenVacation");
        return secFeign.getSecExpertWhenVacation(request, token);
    }

    @ApiOperation(value = "专家请假申请获取专家信息(不根据当前操作人身份证排除)")
    @PostMapping("/getSecExpertWhenVacationNoId")
    public BaseResponse getSecExpertWhenVacationNoId(@RequestBody SecExpertVacationDTO request, @RequestHeader String token) {
        log.info("getSecExpertWhenVacationNoId");
        return secFeign.getSecExpertWhenVacationNoId(request, token);
    }

    @ApiOperation(value = "获取当前登录用户对应的专家信息")
    @GetMapping("/getSecExpertByCurrent")
    public BaseResponse getSecExpertByCurrent(@RequestHeader String token) {
        log.info("getSecExpertByCurrent");
        return secFeign.getSecExpertByCurrent(token);
    }

    @ApiOperation(value = "专家人员状态调整")
    @PostMapping("/updatePersonnel")
    public BaseResponse updatePersonnel(@RequestBody SecExpertModel request) {
        log.info("updatePersonnel");
        return secFeign.updatePersonnel(request);
    }

    /**
     * 根据用户ID获取专家信息
     *
     * @param uId
     * @param token
     * @return
     */
    @ApiOperation(value = "根据用户ID获取专家信息")
    @GetMapping("/getSecExpertByuId")
    public BaseResponse getSecExpertByuId(@RequestParam(required = false, value = "uId") String uId, @RequestHeader(required = false) String token){
        return secFeign.getSecExpertByuId(uId, token);
    }

    /**
     * 评标专家申请驳回到发起人
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation(value = "评标专家申请驳回到发起人")
    @PostMapping("/returnInitiatorToTask")
    public BaseResponse returnInitiatorToTask(@RequestBody ReturnInitiatorDto request, @RequestHeader String token) {
        log.info("returnInitiatorToTaskExpert");
        return secFeign.returnInitiatorToTaskExpert(request, token);
    }

    /**
     * 新的一年复制专家库
     *
     * @param year
     * @return
     */
    @ApiOperation(value = "新的一年复制专家库")
    @GetMapping("/copyAllSecExpert")
    public BaseResponse copyAllSecExpert(@RequestParam(required = false) Integer year) {
        log.info("copyAllSecExpert");
        return secFeign.copyAllSecExpert(year);
    }

    /**
     * 更新专家变更状态
     *
     * @param exId
     * @param changeStatus
     * @return
     */
    @ApiOperation("更新专家变更状态")
    @GetMapping("/updateChangeStatus")
    public BaseResponse updateChangeStatus(@RequestParam String exId, @RequestParam String changeStatus) {
        log.info("updateChangeStatus");
        return secFeign.updateChangeStatus(exId, changeStatus);
    }

    /**
     * 处理用户表与专家库禁用状态同步
     *
     * @return
     */
    @ApiOperation("处理用户表与专家库禁用状态同步")
    @GetMapping("/handleDisableSecExpert")
    public BaseResponse handleDisableSecExpert() {
        log.info("handleDisableSecExpert");
        return secFeign.handleDisableSecExpert();
    }

    /**
     * 获取可以抽取的专家数量
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation("获取可以抽取的专家数量")
    @PostMapping("/getExtractSecExpertCount")
    public BaseResponse getExtractSecExpertCount(@RequestBody ExtractExpertDto request, @RequestHeader String token) {
        log.info("getExtractSecExpertCount");
        return secFeign.getExtractSecExpertCount(request, token);
    }

    /**
     * 同步单位信息到专家库
     *
     * @param uId
     * @return
     */
    @ApiOperation("同步单位信息到专家库")
    @GetMapping("/syncCompanyToSecExpert")
    public BaseResponse syncCompanyToSecExpert(@RequestParam String uId) {
        log.info("syncCompanyToSecExpert");
        return secFeign.syncCompanyToSecExpert(uId);
    }
}

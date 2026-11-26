package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.*;
import com.jpmsoft.common.model.response.FileTemplateTree;
import com.jpmsoft.userProducer.annotations.TenantId;
import com.jpmsoft.userProducer.pojo.dto.FileTemplateDto;
import com.jpmsoft.userProducer.pojo.dto.FlowDto;
import com.jpmsoft.userProducer.pojo.dto.FlowKeyWordDto;
import com.jpmsoft.userProducer.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "流程管理", value = "流程管理")
@RequestMapping("/v1/FlowManage")
public class FlowManageController {

    @Resource
    private IFlowKeyWordService flowKeyWordService;

    @Resource
    private IFileTemplateService fileTemplateService;

    @Resource
    private IFlowManageService flowManageService;

    @Resource
    private IFlowRestTemplateService flowRestTemplateService;
    @Resource
    private IFlowUserService flowUserService;

    /**
     * 获取流程关键字信息列表
     *
     * @param request
     * @return
     */
    @TenantId
    @ApiOperation(value = "获取流程关键字信息列表")
    @PostMapping("/GetFlowKeyWord")
    public BaseResponse GetFlowKeyWord(@RequestBody FlowKeyWordDto request) {
        log.info("GetFlowKeyWord");
        return flowKeyWordService.getFlowKeyWordList(request);
    }

    /**
     * 新增或修改流程关键字信息
     *
     * @param request
     * @return
     */
    @TenantId
    @ApiOperation(value = "新增或修改流程关键字信息")
    @PostMapping("/CUFlowKeyWord")
    public BaseResponse CUFlowKeyWord(@RequestBody FlowKeyWordDto request) {
        log.info("CUFlowKeyWord");
        return flowKeyWordService.CreateOrUpdateFlowKeyWord(request);
    }

    /**
     * 删除流程关键字信息
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "删除流程关键字信息")
    @PostMapping("/DFlowKeyWord")
    public BaseResponse DFlowKeyWord(@RequestBody FlowKeyWordDto request) {
        log.info("DFlowKeyWord");
        return flowKeyWordService.DeleteFlowKeyWord(request);
    }


    /**
     * 查询模板数据
     *
     * @param excludeCancel
     * @param tName
     * @param tCode
     * @param token
     * @return
     */
    @ApiOperation(value = "查询模板数据")
    @GetMapping("/getFileTemplateTreeList")
    public BaseResponse getFileTemplateTreeList(
            @RequestParam(required = false) Boolean excludeCancel,
            @RequestParam(value = "tName", required = false) String tName,
            @RequestParam(value = "tCode", required = false) String tCode,
            @RequestHeader(required = false) String token
    ) {
        log.info("getFileTemplateTreeList");
        if (excludeCancel == null || !excludeCancel)
            return fileTemplateService.getFlowFileTemplateByTree(tName, tCode, token);
        else return fileTemplateService.getFileTempByTreeExcludeCancel(tName, tCode, token);
    }


    /**
     * 查询模板数据
     *
     * @param request
     * @return
     */
    @TenantId
    @ApiOperation(value = "查询模板数据")
    @PostMapping("/getFileTemplateList")
    public BaseResponse getFileTemplateList(@RequestBody FileTemplateDto request) {
        log.info("getFileTemplateList");
        return fileTemplateService.getFileTemplateList(request);
    }

    @ApiOperation(value = "编码查询模板数据")
    @PostMapping("/getFileTemplateByCode")
    public FileTemplateTree getFileTemplateByCode(@RequestParam String tCode, @RequestHeader(required = false) String token) {
        log.info("getFileTemplateByCode");
        return fileTemplateService.getFileTemplateByCode(tCode, token);
    }

    @ApiOperation(value = "编码查询模板数据")
    @PostMapping("/getFileTemplateByCodes")
    public List<FileTemplateTree> getFileTemplateByCodes(@RequestBody List<String> tCodes, @RequestHeader(required = false) String token) {
        log.info("getFileTemplateByCodes");
        return fileTemplateService.getFileTemplateByCodes(tCodes, token);
    }

    @ApiOperation(value = "上级ID查询模板数据")
    @GetMapping("/getFileTemplateByParent")
    public BaseResponse getFileTemplateByParent(
            @RequestParam String id,
            @RequestParam(required = false) Boolean excludeCancel,
            @RequestParam(value = "tName", required = false) String tName,
            @RequestParam(value = "tCode", required = false) String tCode,
            @RequestHeader(required = false) String token) {
        log.info("getFileTemplateByParent");
        return fileTemplateService.getFileTemplateByParent(id, excludeCancel, tName, tCode, token);
    }

    /**
     * 新增、修改、删除流程模板信息
     *
     * @param request
     * @param token
     * @return
     */
    @TenantId
    @ApiOperation(value = "新增、修改、删除流程模板信息")
    @PostMapping("/CUDFileTemplate")
    public BaseResponse CUDFileTemplate(@RequestBody @Validated FileTemplateDto request, @RequestHeader String token) {
        log.info("CUDFileTemplate");
        return fileTemplateService.CreateOrUpdateOrDeleteFileTemplateWord(request, token);
    }

    @ApiOperation(value = "上移或下移流程模板")
    @GetMapping("/upOrDownFileTemplateSort")
    public BaseResponse upOrDownFileTemplateSort(@RequestParam String tId, @RequestParam String action, @RequestHeader String token) {
        log.info("upOrDownFileTemplateSort");
        return fileTemplateService.upOrDownFileTemplateSort(tId, action, token);
    }


    /**
     * 获取流程信息列表
     *
     * @param request
     * @return
     */
    @TenantId
    @ApiOperation(value = "获取流程信息列表")
    @PostMapping("/getFlowListManage")
    public BaseResponse getFlowListManage(@RequestBody FlowDto request) {
        log.info("getFlowListManage");
        return flowManageService.getFlowtList(request);
    }

    @TenantId
    @ApiOperation(value = "设置流程启用禁用")
    @GetMapping("/setEnableDisable")
    public BaseResponse setEnableDisable(@RequestParam String flowId, @RequestParam String state, @RequestHeader String token) {
        log.info("setEnableDisable");
        return flowManageService.setEnableDisable(flowId, state, token);
    }

    /**
     * 新增或修改流程信息
     *
     * @param request
     * @return
     */
    @TenantId
    @ApiOperation(value = "新增或修改流程信息")
    @PostMapping("/CreateOrUpdateFlow")
    public BaseResponse CreateOrUpdateFlow(@RequestBody FlowDto request) {
        log.info("CreateOrUpdateFlow");
        return flowManageService.CreateOrUpdateFlow(request);
    }

    /**
     * 删除流程信息
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "删除流程信息")
    @PostMapping("/DeleteFlow")
    public BaseResponse DeleteFlow(@RequestBody @Validated FlowDto request) {
        log.info("DeleteFlow");
        return flowManageService.DeleteFlow(request);
    }


    /************************************* 流程管理 *********************************************/
    /**
     * 流程管理
     * 抄送记录
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "抄送记录", httpMethod = "POST")
    @PostMapping("/getCarbonCopyList")
    public BaseResponse getCarbonCopyList(@RequestBody SysCarbonCopyRequest request, @RequestHeader String token) {
        log.info("getCarbonCopyList");
        return flowRestTemplateService.getCarbonCopyList(request, token);
    }

    /**
     * 流程管理
     * 流程实例
     * reuqest.started ==True 则为已启动流程
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "流程实例,reuqest.started为true则为已启动流程", httpMethod = "POST")
    @PostMapping("/getFileList")
    public BaseResponse getFileList(@RequestBody SysFileRequest request, @RequestHeader String token) {
        log.info("getFileList");
        return flowRestTemplateService.getFileList(request, token);
    }

    /**
     * 流程管理
     * 已部署流程
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "已部署流程", httpMethod = "POST")
    @PostMapping("/getFlowList")
    public BaseResponse getFlowList(@RequestBody SysFlowRequest request, @RequestHeader String token) {
        log.info("getFlowList");
        return flowRestTemplateService.getFlowList(request, token);
    }

    /**
     * 流程管理
     * 沟通任务
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "沟通任务", httpMethod = "POST")
    @PostMapping("/getCoordinationTaskList")
    public BaseResponse getCoordinationTaskList(@RequestBody SysCoordinationTaskRequest request, @RequestHeader String token) {
        log.info("getCoordinationTaskList");
        return flowRestTemplateService.getCoordinationTaskList(request, token);
    }

    /**
     * 流程管理
     * 进行中流程
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "进行中流程", httpMethod = "POST")
    @PostMapping("/getTaskList")
    public BaseResponse getTaskList(@RequestBody SysTaskRequest request, @RequestHeader String token) {
        log.info("getTaskList");
        return flowRestTemplateService.getTaskList(request, token);
    }

    /**
     * 流程管理
     * 沟通记录
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "沟通记录", httpMethod = "POST")
    @PostMapping("/getCoordinationProcessList")
    public BaseResponse getCoordinationProcessList(@RequestBody SysCoordinationProcessRequest request, @RequestHeader String token) {
        log.info("getCoordinationProcessList");
        return flowRestTemplateService.getCoordinationProcessList(request, token);
    }


    @ApiOperation(value = "根据流程id获取流程图")
    @GetMapping("/getFlowXmlByFlowId")
    public BaseResponse getFlowXmlByFlowId(@RequestParam String flowId) {
        log.info("getFlowXmlByFlowId");
        return flowManageService.getFlowXmlByFlowId(flowId);
    }


    @ApiOperation(value = "根据FlowUserRoleid获取")
    @GetMapping("/getRoleUserFlowByUId")
    public BaseResponse getRoleUserFlowByUId(@RequestParam String rid) {
        log.info("getRoleUserFlowByUId");
        return BaseResponse.ok(flowUserService.getRoleUserFlowByUId(rid));
    }

    @GetMapping("/getFileTemplateTableById")
    public  String   getFileTemplateTableById(@RequestParam String id){
       return fileTemplateService.getFileTemplateTableById(id);
    }
}

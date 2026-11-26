package com.jpmsoft.jpbpm.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.UserTaskProcessinRequest;
import com.jpmsoft.common.model.response.InvestmentControl.TransferStatusUpdateRequest;
import com.jpmsoft.jpbpm.pojo.dto.ApprovalTimelimitDto;
import com.jpmsoft.jpbpm.pojo.dto.SysTaskProcessinDto;
import com.jpmsoft.jpbpm.pojo.dto.SysTaskProcessinUpdateDto;
import com.jpmsoft.jpbpm.pojo.entity.TaskProcessin;
import com.jpmsoft.jpbpm.pojo.vo.TaskCoordinationTaskVo;
import com.jpmsoft.jpbpm.service.FileService;
import com.jpmsoft.jpbpm.service.SysTaskProcessinTimeService;
import com.jpmsoft.jpbpm.service.TaskProcessinService;
import com.jpmsoft.jpbpm.util.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "已办事务", value = "已办事务")
@RequestMapping("/v1/taskProcessin")
public class TaskProcessinController {
    @Autowired
    private TaskProcessinService taskProcessinService;
    @Autowired
    SysTaskProcessinTimeService sysTaskProcessinTimeService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private FileService fileService;

    /**
     * 获取用户已办信息
     *
     * @param token   用户ID
     * @param request 请求参数
     * @return
     */
    @PostMapping("/getUserTaskProcessList")
    public BaseResponse getUserTaskProcessList(@RequestHeader(value ="token",required = false) String token, @RequestBody UserTaskProcessinRequest request) {
        Object uId = redisUtils.get(token);
        if (uId == null)
            return new BaseResponse("Token失效！");
        log.info("getUserTaskProcessList");
        String customerId=redisUtils.getCustomerId(token);
        return taskProcessinService.getUserTaskProcessList(uId.toString(), request,customerId);
    }

    /**
     * 获取用户已办信息（去重版）
     * 对于同一个tp_user和tp_entityid，只保留最新的一条办理记录
     *
     * @param token   用户Token
     * @param request 请求参数
     * @return 去重后的已办任务列表
     */
    @ApiOperation(value = "获取用户已办信息（去重版）", httpMethod = "POST")
    @PostMapping("/getUserTaskProcessListDistinct")
    public BaseResponse getUserTaskProcessListDistinct(@RequestHeader(value ="token",required = false) String token, @RequestBody UserTaskProcessinRequest request) {
        Object uId = redisUtils.get(token);
        if (uId == null)
            return new BaseResponse("Token失效！");
        log.info("getUserTaskProcessListDistinct");
        String customerId = redisUtils.getCustomerId(token);
        return taskProcessinService.getUserTaskProcessListDistinct(uId.toString(), request, customerId);
    }

    /**
     * 获取已办信息
     *
     * @param request 请求参数
     * @return
     */
    @PostMapping("/getTaskProcessList")
    public BaseResponse getTaskProcessList(@RequestBody SysTaskProcessinDto request,@RequestHeader(value ="token",required = false) String token) {
        log.info("getTaskProcessList");
        String customerId=redisUtils.getCustomerId(token);
        return taskProcessinService.getTaskProcessList(request,customerId);
    }


    /**
     * 修改已办信息（部分字段）
     *
     * @param request 请求参数
     * @return
     */
    @PostMapping("/updateTaskProcess")
    public BaseResponse updateTaskProcess(@RequestBody SysTaskProcessinUpdateDto request,@RequestHeader(value ="token",required = false) String token) {
        log.info("updateTaskProcess");
        return taskProcessinService.updateTaskProcess(request);
    }

    /**
     * 删除代办信息
     *
     * @param tpId 请求参数
     * @return
     */
    @GetMapping("/deleteTaskProcessById")
    public BaseResponse deleteTaskProcessById(@RequestParam String tpId,@RequestParam(value ="tCode",required = false) String tCode,@RequestParam(value ="year",required = false) String year,@RequestHeader(value ="token",required = false) String token) {
        log.info("deleteTaskProcessById");
        return taskProcessinService.deleteTaskProcessById(tpId,tCode,year);
    }

    /**
     * 获取代办时间范围
     *
     * @return
     */
    @GetMapping("/getMinTimeAndMaxTime")
    public BaseResponse getMinTimeAndMaxTime(@RequestParam(value ="tCode",required = false) String tCode,@RequestParam(value ="year",required = false) String year,@RequestHeader(value ="token",required = false) String token) {
        log.info("getMinTimeAndMaxTime");
        String customerId=redisUtils.getCustomerId(token);
        return fileService.getMinTimeAndMaxTime(customerId,tCode,year);
    }



    @ApiOperation(value = "驳回信息统计（部门）")
    @PostMapping("/getRejectStatisticsD")
    public BaseResponse getRejectStatisticsD(@RequestBody  ApprovalTimelimitDto request ,@RequestHeader(value ="token",required = false) String token) {
        log.info("getRejectStatisticsD");
        String customerId=redisUtils.getCustomerId(token);
        return  sysTaskProcessinTimeService.getRejectStatisticsD(request,customerId);
    }


    @ApiOperation(value = "驳回信息统计（项目部门）")
    @PostMapping("/getRejectStatisticsPD")
    public BaseResponse getRejectStatisticsPD(@RequestBody  ApprovalTimelimitDto request ,@RequestHeader(value ="token",required = false) String token) {
        log.info("getRejectStatisticsPD");
        String customerId=redisUtils.getCustomerId(token);
        return  sysTaskProcessinTimeService.getRejectStatisticsPD(request,customerId);
    }


    @ApiOperation(value = "驳回信息统计（驳回人员）")
    @PostMapping("/getRejectStatisticsUD")
    public  BaseResponse getRejectStatisticsUD(@RequestBody  ApprovalTimelimitDto request ,@RequestHeader(value ="token",required = false) String token) {
        log.info("getRejectStatisticsUD");
        String customerId=redisUtils.getCustomerId(token);
        return  sysTaskProcessinTimeService.getRejectStatisticsUD(request,customerId);
    }

    @ApiOperation(value = "驳回信息统计（被驳回人员）")
    @PostMapping("/getRejectStatisticsRUD")
    public  BaseResponse getRejectStatisticsRUD(@RequestBody  ApprovalTimelimitDto request ,@RequestHeader(value ="token",required = false) String token) {
        log.info("getRejectStatisticsRUD");
        String customerId=redisUtils.getCustomerId(token);
        return  sysTaskProcessinTimeService.getRejectStatisticsRUD(request,customerId);
    };


    @ApiOperation(value = "创建审批记录信息")
    @PostMapping("/createTaskProcess")
    public  BaseResponse createTaskProcess(@RequestBody TaskProcessin request ,@RequestParam(value ="tCode",required = false) String tCode,@RequestParam(value ="year",required = false) String year, @RequestHeader(value ="token",required = false) String token) {
        log.info("createTaskProcess");
        String customerId=redisUtils.getCustomerId(token);
        if(!"All".equals(customerId)){
            request.setTeId(customerId);
        }
        return  taskProcessinService.createTaskProcess(request,tCode,year);
    };

    @PostMapping("/getUserTaskProcessListCJ")
    public BaseResponse getUserTaskListCJ(@RequestHeader(value ="token",required = false) String token, @RequestBody TaskCoordinationTaskVo request) {
        Object uId = redisUtils.get(token);
        if (uId == null)
            return new BaseResponse("Token失效！");
        log.info("getUserTaskProcessListCJ");
        String customerId=redisUtils.getCustomerId(token);
        return taskProcessinService.getUserTaskProcessListCJ(uId.toString(), request,customerId);
    }

    /**
     * 查询用户是否在审批流程中
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation(value = "查询用户是否在审批流程中")
    @PostMapping("/checkUserInApprovalProcess")
    public BaseResponse checkUserInApprovalProcess(@RequestBody TransferStatusUpdateRequest request, @RequestHeader String token) {
        log.info("checkUserInApprovalProcess");
        return taskProcessinService.checkUserInApprovalProcess(request.getIds(), redisUtils.getUserInfoByToken(token));
    }
}

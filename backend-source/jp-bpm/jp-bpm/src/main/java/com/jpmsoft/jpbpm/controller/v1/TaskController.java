package com.jpmsoft.jpbpm.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SysFileRequest;
import com.jpmsoft.common.model.request.SysTaskRequest;
import com.jpmsoft.common.model.request.UserTaskRequest;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.jpbpm.constants.BpmMsg;
import com.jpmsoft.jpbpm.pojo.dto.BatchhandlingTaskDto;
import com.jpmsoft.jpbpm.pojo.entity.Task;
import com.jpmsoft.jpbpm.pojo.vo.TaskCoordinationTaskVo;
import com.jpmsoft.jpbpm.service.ISysTaskMassageService;
import com.jpmsoft.jpbpm.service.TaskService;
import com.jpmsoft.jpbpm.util.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@RestController
@Api(tags = "待办事务", value = "待办事务")
@RequestMapping("/v1/task")
public class TaskController {
    @Resource
    private TaskService taskService;

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ISysTaskMassageService sysTaskMassageService;

    /**
     * 第三方使用我临时token查询待办
     *
     * @param token
     * @param request
     * @return
     */
    @PostMapping("/third/selectTaskPage")
    public BaseResponse selectTaskPage(@RequestHeader String token, @RequestBody UserTaskRequest request) {
        log.info("getUserTaskList");
        Object redisData = redisUtils.get("OtherAuth_" + token);
        if (redisData == null) return BaseResponse.fail(BpmMsg.thirdTokenInvalid);
        Map<String, String> datas = JacksonUtils.parseObject(redisData.toString(), Map.class);
        redisUtils.delete("OtherAuth_" + token);
        String uId = datas.get("uId");
        if (ValidateUtil.isAllEmpty(request.getSortFiled(), request.getSortType(), request.getSortSideFiled())) {
            request.setSortFiled("tasksendtime");
            request.setSortType("desc");
        }
        return taskService.getUserTaskList(uId, request, "All", Boolean.TRUE);
    }


    /**
     * 获取用户代办信息
     *
     * @param token   用户ID
     * @param request 请求参数
     * @return
     */
    @PostMapping("/getUserTaskList")
    public BaseResponse getUserTaskList(@RequestHeader(value = "token", required = false) String token, @RequestBody UserTaskRequest request) {
        log.info("getUserTaskList");
        String customerId = redisUtils.getCustomerId(token);
        return taskService.getUserTaskList(redisUtils.getUid(token), request, customerId, Boolean.FALSE);
    }
    @PostMapping("/getUserTaskListCJ")
    public BaseResponse getUserTaskListCJ(@RequestHeader(value = "token", required = false) String token, @RequestBody TaskCoordinationTaskVo request) {
        log.info("getUserTaskListCJ");
        String customerId = redisUtils.getCustomerId(token);
        return taskService.getUserTaskListCJ(redisUtils.getUid(token), request,customerId);
    }
    /**
     * 流程管理
     * 进行中流程
     *
     * @param request
     * @return
     */
    @PostMapping("/getTaskList")
    public BaseResponse getTaskList(@RequestBody SysTaskRequest request, @RequestHeader(value = "token", required = false) String token) {
        log.info("getTaskList");
        String customerId = redisUtils.getCustomerId(token);
        return taskService.getTaskList(request, customerId);
    }

    /**
     * 流程管理
     * 进行中流程
     *
     * @param taskId
     * @return
     */
    @GetMapping("/deleteTaskById")
    public BaseResponse deleteTaskById(@RequestParam String taskId, @RequestParam String fileId, @RequestHeader(value = "token", required = false) String token) {
        log.info("deleteTaskById");
        return taskService.deleteTaskById(taskId, fileId, redisUtils.getUserInfoByToken(token));
    }


    /**
     * 新增代办信息
     *
     * @param request
     * @return
     */
    @PostMapping("/createTask")
    public BaseResponse createTask(@RequestBody Task request, @RequestParam(value ="tCode",required = false) String tCode,@RequestParam(value ="year",required = false) String year, @RequestHeader(value = "token", required = false) String token) {
        log.info("createTask");
        String customerId = redisUtils.getCustomerId(token);
        return taskService.createTask(request, redisUtils.getUserInfoByToken(token), customerId, token, tCode,year);
    }

    /**
     * 批量生成代办
     *
     * @param request
     * @return
     */
    @PostMapping("/batchSendTask")
    public BaseResponse batchSendTask(@RequestBody BatchhandlingTaskDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("batchSendTask");
        String customerId = redisUtils.getCustomerId(token);
        return taskService.batchSendTask(request);
    }

    /**
     * 获取用户代办信息
     *
     * @param token   用户ID
     * @param request 请求参数
     * @return
     */
    @PostMapping("/getTaskListByUserId")
    public BaseResponse getTaskListByUserId(@RequestHeader(value = "token", required = false) String token, @RequestBody UserTaskRequest request) {
        log.info("getTaskListByUserId");
        String customerId = redisUtils.getCustomerId(token);
        return taskService.getTaskListByUserId(redisUtils.getUid(token), request, customerId, Boolean.FALSE);
    }

    @ApiOperation(value = "生成待办超时平台消息")
    @GetMapping("/generateReminder")
    public BaseResponse generateReminder() {
        log.info("generateReminder");
        taskService.generateReminder("All");
        return BaseResponse.ok();
    }

    @ApiOperation(value = "设置代办超时阅读状态")
    @GetMapping("/setSysTaskMassageReadType")
    public   BaseResponse   setSysTaskMassageReadType(@RequestHeader(value = "token", required = false) String token,String id){
        String  uId=redisUtils.getUid(token);
        return   sysTaskMassageService.setSysTaskMassageReadType(id,uId);
    }

    @ApiOperation(value = "删除待办信息(支持通过taskId或TaskEntityID删除)")
    @GetMapping("/deleteTaskByIdOrTaskEntityID")
    public BaseResponse deleteTaskByIdOrTaskEntityID(@RequestParam(value ="taskId",required = false) String taskId, @RequestParam(value ="TaskEntityID",required = false) String TaskEntityID, @RequestParam("customerId") String customerId) {
        log.info("deleteTaskByIdOrTaskEntityID");
        taskService.deleteTaskByIdOrTaskEntityID(taskId, TaskEntityID, customerId);
        return BaseResponse.ok();
    }

    @ApiOperation("获取用户待办列表")
    @PostMapping("/getUserTodoList")
    public BaseResponse getUserTodoList(@RequestHeader(value = "token", required = false) String token, @RequestBody SysFileRequest request) {
        log.info("getUserTaskLigetUserTodoListstCJ");
        String customerId = redisUtils.getCustomerId(token);
        return taskService.getUserTodoList(redisUtils.getUid(token), request, customerId);
    }

    @ApiOperation("获取用户待办数量")
    @GetMapping("/getUserTodoCount")
    public BaseResponse getUserTodoCount(@RequestHeader(value = "token", required = false) String token) {
        log.info("getUserTodoCount");
        String customerId = redisUtils.getCustomerId(token);
        return taskService.getUserTodoCount(redisUtils.getUid(token), customerId);
    }

    @ApiOperation("获取无数据流程列表")
    @GetMapping("/getNoDataFlow")
    public BaseResponse getNoDataFlow(@RequestParam(value = "state", required = false) Integer state, @RequestParam(value = "voidData", required = false) Boolean voidData) {
        return taskService.getNoDataFlow(state, voidData);
    }
}

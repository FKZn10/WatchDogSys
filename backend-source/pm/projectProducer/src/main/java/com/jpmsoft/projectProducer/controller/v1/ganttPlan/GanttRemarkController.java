package com.jpmsoft.projectProducer.controller.v1.ganttPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ganttPlan.GanttRemarkRequest;
import com.jpmsoft.projectProducer.service.ganttPlan.GanttRemarkService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author tianhelin
 * @date 2025/5/26
 */
@Slf4j
@RestController
@RequestMapping("/v1/ganttRemark")
public class GanttRemarkController {
    @Resource
    private GanttRemarkService ganttRemarkService;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 获取备注历史记录列表
     *
     * @param request
     * @return
     */
    @PostMapping("/getGanttRemarkList")
    public BaseResponse getGanttRemarkList(@RequestBody GanttRemarkRequest request) {
        log.info("getGanttRemarkList");
        return ganttRemarkService.getGanttRemarkList(request);
    }

    /**
     * 新增备注
     *
     * @param request
     * @return
     */
    @PostMapping("/addGanttRemark")
    public BaseResponse addGanttRemark(@RequestBody GanttRemarkRequest request, @RequestHeader String token) {
        log.info("addGanttRemark");
        return ganttRemarkService.addGanttRemark(request, redisUtils.getUserInfoByToken(token));
    }
}

package com.jpmsoft.projectConsumer.controller.v1.ganttPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ganttPlan.GanttRemarkRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author tianhelin
 * @date 2025/5/26
 */
@Slf4j
@RestController
@Api(tags = "里程碑计划汇总", value = "里程碑计划汇总")
@RequestMapping("/v1/ganttRemark")
public class GanttRemarkController {
    @Resource
    private BaseFeign baseFeign;

    /**
     * 获取备注历史记录列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "计划管理备注分页查询")
    @PostMapping("/getGanttRemarkList")
    public BaseResponse getGanttRemarkList(@RequestBody GanttRemarkRequest request) {
        log.info("getGanttRemarkList");
        return baseFeign.getGanttRemarkList(request);
    }

    /**
     * 新增备注
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "计划管理备注新增")
    @PostMapping("/addGanttRemark")
    public BaseResponse addGanttRemark(@RequestBody GanttRemarkRequest request, @RequestHeader String token) {
        log.info("addGanttRemark");
        return baseFeign.addGanttRemark(request, token);
    }
}

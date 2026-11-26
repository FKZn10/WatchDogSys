package com.jpmsoft.projectConsumer.controller.v1.project;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.project.PreProjectDeviceCoordinateCreateOrUpdateRequest;
import com.jpmsoft.common.model.request.project.PreProjectDeviceCoordinateRequest;
import com.jpmsoft.projectConsumer.feign.PreFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/v1/projectDeviceCoordinate")
public class PreProjectDeviceCoordinateController {

    @Resource
    PreFeign preFeign;

    /**
     * 创建修改
     *
     * @param request
     * @return
     */
    @PostMapping("/createOrUpdate")
    public BaseResponse createOrUpdate(@RequestBody PreProjectDeviceCoordinateCreateOrUpdateRequest request) {
        return preFeign.projectDeviceCoordinateCreateOrUpdate(request);
    }

    /**
     * 标记过的项目列表
     *
     * @return
     */
    @GetMapping("/list")
    public BaseResponse list(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return preFeign.projectDeviceCoordinateList(pageNum, pageSize);
    }

    /**
     * 获取项目点坐标列表
     *
     * @return
     */
    @PostMapping("/getList")
    public BaseResponse getList(@RequestBody PreProjectDeviceCoordinateRequest request) {
        return preFeign.getProjectDeviceCoordinateList(request);
    }

    /**
     * 根据项目id筛选
     *
     * @param projectId
     * @return
     */
    @GetMapping("/listByProjectId")
    public BaseResponse listByProjectId(@RequestParam String projectId) {
        return preFeign.listByProjectId(projectId);
    }

    /**
     * 根据主键uuid删除
     * @param uuid
     * @return
     */
    @PostMapping("/deleteOne/{uuid}")
    public BaseResponse deleteOne(@PathVariable("uuid") String uuid) {
        preFeign.projectDeviceCoordinateDeleteOne(uuid);
        return BaseResponse.ok();
    }

}

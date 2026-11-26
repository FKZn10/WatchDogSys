package com.jpmsoft.projectProducer.controller.v1.project;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.project.PreProjectDeviceCoordinateCreateOrUpdateRequest;
import com.jpmsoft.common.model.request.project.PreProjectDeviceCoordinateRequest;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectDeviceCoordinate;
import com.jpmsoft.projectProducer.service.project.IPreProjectDeviceCoordinateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 项目设备坐标 控制器
 */
@Slf4j
@RestController
@RequestMapping("/v1/projectDeviceCoordinate")
public class PreProjectDeviceCoordinateController {

    @Resource
    IPreProjectDeviceCoordinateService preProjectDeviceCoordinateService;

    /**
     * 创建修改
     *
     * @param request
     * @return
     */
    @PostMapping("/createOrUpdate")
    public BaseResponse createOrUpdate(@RequestBody PreProjectDeviceCoordinateCreateOrUpdateRequest request) {
        List<PreProjectDeviceCoordinate> preProjectDeviceCoordinateList = BeanUtils.copyListData(request.getPreProjectDeviceCoordinateModelList(), PreProjectDeviceCoordinate.class);
        preProjectDeviceCoordinateService.createOrUpdate(preProjectDeviceCoordinateList);
        return BaseResponse.ok();
    }


    /**
     * 页面列表接口
     *
     * @return
     */
    @GetMapping("/list")
    public BaseResponse list(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        Page<PreProjectDeviceCoordinate> page = preProjectDeviceCoordinateService
                .getPage(new Page<>(pageNum, pageSize));
        return BaseResponse.ok(page);
    }

    /**
     * 根据项目id筛选
     *
     * @param projectId
     * @return
     */
    @GetMapping("/listByProjectId")
    public BaseResponse listByProjectId(@RequestParam String projectId) {
        List<PreProjectDeviceCoordinate> projectDeviceCoordinateList = preProjectDeviceCoordinateService.list(
                new LambdaQueryWrapper<PreProjectDeviceCoordinate>()
                        .eq(PreProjectDeviceCoordinate::getProjectId, projectId)
                        .orderByAsc(PreProjectDeviceCoordinate::getPointOrder));
        return BaseResponse.ok(projectDeviceCoordinateList);
    }

    /**
     * 获取项目点坐标列表
     *
     * @return
     */
    @PostMapping("/getList")
    public BaseResponse getList(@RequestBody PreProjectDeviceCoordinateRequest request) {
        return BaseResponse.ok(preProjectDeviceCoordinateService.getList(request));
    }


    /**
     * 根据主键uuid删除
     *
     * @param uuid
     * @return
     */
    @PostMapping("/deleteOne/{uuid}")
    public BaseResponse deleteOne(@PathVariable("uuid") String uuid) {
        preProjectDeviceCoordinateService.removeById(uuid);
        return BaseResponse.ok();
    }

}

package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProjectProfileModel;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author jjz
 * 河北项目形象进度
 */
@Slf4j
@RestController
@Api(tags = "项目形象进度", value = "项目形象进度")
@RequestMapping("/v1/projectProfile")
public class ProjectProfileController {

    @Resource
    private BaseFeign baseFeign;

    @PostMapping("/getProjectProfileList")
    public BaseResponse getProjectProfileList(@RequestBody ProjectProfileModel request) {
        log.info("获取形象进度列表展示");
        return baseFeign.getProjectProfileList(request);
    }

    // 根据pId获取对应形象进度列表
    @GetMapping("/getProjectProfileListByPid")
    public BaseResponse getProjectProfileListByPid(@RequestParam String pId) {
        log.info("根据pId获取对应形象进度列表");
        return baseFeign.getProjectProfileListByPid(pId);
    }

    // 根据id获取对应形象进度详细信息
    @GetMapping("/getProjectProfileById")
    public BaseResponse getProjectProfileById(@RequestParam String id) {
        log.info("根据id获取对应形象进度详细信息");
        return baseFeign.getProjectProfileById(id);
    }
}

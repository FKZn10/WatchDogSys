package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProjectProfileModel;
import com.jpmsoft.projectProducer.service.project.IProjectProfileService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author jjz
 * 河北项目形象进度
 */
@Slf4j
@RestController
@RequestMapping("/v1/projectProfile")
public class ProjectProfileController {

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private IProjectProfileService projectProfileService;

    // 获取形象进度列表展示
    @PostMapping("/getProjectProfileList")
    public BaseResponse getProjectProfileList(@RequestBody ProjectProfileModel request) {
        log.info("获取形象进度列表展示");
        return projectProfileService.getProjectProfileList(request);
    }

    // 根据pId获取对应形象进度列表
    @GetMapping("/getProjectProfileListByPid")
    public BaseResponse getProjectProfileListByPid(@RequestParam String pId) {
        log.info("根据pId获取对应形象进度列表");
        return projectProfileService.getProjectProfileListByPid(pId);
    }

    // 根据id获取对应形象进度详细信息
    @GetMapping("/getProjectProfileById")
    public BaseResponse getProjectProfileById(@RequestParam String id) {
        log.info("根据id获取对应形象进度详细信息");
        return projectProfileService.getProjectProfileById(id);
    }
}

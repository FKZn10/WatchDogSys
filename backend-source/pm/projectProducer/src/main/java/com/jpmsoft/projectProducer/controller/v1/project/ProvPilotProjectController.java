package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotProjectImportRequest;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotProjectModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.project.IProvPilotProjectService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 省试点项目 Controller（生产端 PP）
 *
 * @author whjp
 * @since 2026-09-16
 */
@Slf4j
@RestController
@RequestMapping("/v1/provPilotProject")
public class ProvPilotProjectController {

    @Resource
    private IProvPilotProjectService provPilotProjectService;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 编辑（新增/修改）
     */
    @TenantId
    @PostMapping("/createOrUpdate")
    public BaseResponse createOrUpdate(@RequestBody ProvPilotProjectModel request, @RequestHeader String token) {
        log.info("createOrUpdateProvPilotProject");
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        return provPilotProjectService.createOrUpdate(request, user);
    }

    /**
     * 列表查询
     */
    @TenantId
    @PostMapping("/getList")
    public BaseResponse getList(@RequestBody ProvPilotProjectModel request, @RequestHeader String token) {
        log.info("getProvPilotProjectList");
        return provPilotProjectService.getList(request, redisUtils.getUid(token));
    }

    /**
     * 详情
     */
    @GetMapping("/getById")
    public BaseResponse getById(@RequestParam("id") String id) {
        log.info("getProvPilotProjectById");
        return provPilotProjectService.getById(id);
    }

    /**
     * 真删除（批量），同时删除 sys_file
     */
    @PostMapping("/deleteBatch")
    public BaseResponse deleteBatch(@RequestBody List<String> ids) {
        log.info("deleteProvPilotProject");
        return provPilotProjectService.deleteBatch(ids);
    }

    /**
     * 补充导入（File 服务 Excel 解析后调用）
     */
    @PostMapping("/importProvPilotProject")
    public BaseResponse importProvPilotProject(@RequestBody ProvPilotProjectImportRequest request, @RequestHeader String token) {
        log.info("importProvPilotProject");
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        return provPilotProjectService.importProvPilotProject(request, user);
    }

    /**
     * 导出数据（File 服务导出时调用）
     */
    @TenantId
    @PostMapping("/getExportList")
    public List<ProvPilotProjectModel> getExportList(@RequestBody ProvPilotProjectModel request, @RequestHeader String token) {
        log.info("getExportProvPilotProjectList");
        return provPilotProjectService.getExportList(request, redisUtils.getUid(token));
    }
}
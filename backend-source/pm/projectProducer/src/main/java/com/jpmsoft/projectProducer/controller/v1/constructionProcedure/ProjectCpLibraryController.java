package com.jpmsoft.projectProducer.controller.v1.constructionProcedure;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.proCp.ProjectCpLibraryRequset;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.construction.LibraryFileTypeDTO;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpLibraryService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/ConstructionProcedure/proCpLibrary")
public class ProjectCpLibraryController {
    @Resource
    private IProjectCpLibraryService projectCpLibraryService;
    @Resource
    private RedisUtils redisUtils;


    /**
     * 新增或修改
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/createOrUpdateProCpLibrary")
    public BaseResponse createOrUpdateProCpLibrary(@RequestBody ProjectCpLibraryRequset request) {
        log.info("createOrUpdateProCpLibrary");
        return projectCpLibraryService.createOrUpdateProCpLibrary(request);
    }

    /**
     * ID删除
     *
     * @param cplId
     * @return
     */
    @GetMapping("/deleteProCpLibraryById")
    public BaseResponse deleteProCpLibraryById(@RequestParam String cplId) {
        log.info("deleteProCpLibraryById");
        return projectCpLibraryService.deleteProCpLibraryById(cplId);
    }

    /**
     * 获取List集合
     *
     * @return
     */
    @TenantId
    @PostMapping("/getProCpLibraryTreeList")
    public BaseResponse getProCpLibraryTreeList(@RequestBody ProjectCpLibraryRequset request) {
        log.info("getProCpLibraryTreeList");
        return projectCpLibraryService.getProCpLibraryTreeList(request);
    }

    @PostMapping("/updateLibraryFileTypeBath")
    public BaseResponse updateLibraryFileTypeBath(@RequestBody  LibraryFileTypeDTO request){
        log.info("updateLibraryFileTypeBath");
        return projectCpLibraryService.updateLibraryFileTypeBath(request);
    }

    @GetMapping("/getProCpChangeDtlLibraryTreeList")
    public BaseResponse getProCpChangeDtlLibraryTreeList(@RequestParam String id, @RequestHeader String token) {
        log.info("getProCpChangeDtlLibraryTreeList");
        return projectCpLibraryService.getProCpChangeDtlLibraryTreeList(id, redisUtils.getTeId(token));
    }

    @GetMapping("/upOrDownProCpLibrary")
    public BaseResponse upOrDownProCpLibrary(@RequestParam String cplId, @RequestParam String action, @RequestHeader String token) {
        log.info("getProCpLibraryTreeList");
        return projectCpLibraryService.upOrDownProCpLibrary(cplId, action, redisUtils.getTeId(token));
    }
}

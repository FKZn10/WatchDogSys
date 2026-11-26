package com.jpmsoft.projectConsumer.controller.v1.constructionProcedure;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.proCp.ProjectCpLibraryRequset;
import com.jpmsoft.projectConsumer.pojo.dto.construction.LibraryFileTypeDTO;
import com.jpmsoft.projectConsumer.feign.CpFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "项目建设程序库", value = "项目建设程序库")
@RequestMapping("/v1/ConstructionProcedure/proCpLibrary")
public class ProjectCpLibraryController {
    @Autowired
    private CpFeign cpFeign;


    /**
     * 新增或修改
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "新增或修改项目建设程序库")
    @PostMapping("/createOrUpdateProCpLibrary")
    public BaseResponse createOrUpdateProCpLibrary(@RequestBody ProjectCpLibraryRequset request) {
        log.info("createOrUpdateProCpLibrary");
        return cpFeign.createOrUpdateProCpLibrary(request);
    }

    /**
     * ID删除
     *
     * @param cplId
     * @return
     */
    @ApiOperation(value = "ID删除项目建设程序库")
    @GetMapping("/deleteProCpLibraryById")
    public BaseResponse deleteProCpLibraryById(@RequestParam String cplId) {
        log.info("deleteProCpLibraryById");
        return cpFeign.deleteProCpLibraryById(cplId);
    }

    /**
     * 获取List集合
     *
     * @return
     */
    @ApiOperation(value = "获取项目建设程序库树形集合")
    @PostMapping("/getProCpLibraryTreeList")
    public BaseResponse getProCpLibraryTreeList(@RequestBody ProjectCpLibraryRequset request) {
        log.info("getProCpLibraryTreeList");
        return cpFeign.getProCpLibraryTreeList(request);
    }

    @ApiOperation(value = "批量修改建设程序库成果上传附件类型(同步更新对应模板详情的成果附件类型)")
    @PostMapping("/updateLibraryFileTypeBath")
    public BaseResponse updateLibraryFileTypeBath(@RequestBody LibraryFileTypeDTO request){
        log.info("updateLibraryFileTypeBath");
        return cpFeign.updateLibraryFileTypeBath(request);
    }

    @ApiOperation(value = "建设程序变更时获取程序库树(新增时id=项目建设程序Id,修改时id=项目建设程序变更Id)")
    @GetMapping("/getProCpChangeDtlLibraryTreeList")
    public BaseResponse getProCpChangeDtlLibraryTreeList(@RequestParam String id, @RequestHeader String token) {
        log.info("getProCpChangeDtlLibraryTreeList");
        return cpFeign.getProCpChangeDtlLibraryTreeList(id, token);
    }

    @ApiOperation(value = "上移或者下移排序")
    @GetMapping("/upOrDownProCpLibrary")
    public BaseResponse upOrDownProCpLibrary(@RequestParam String cplId, @RequestParam String action) {
        log.info("getProCpLibraryTreeList");
        return cpFeign.upOrDownProCpLibrary(cplId, action);
    }
}

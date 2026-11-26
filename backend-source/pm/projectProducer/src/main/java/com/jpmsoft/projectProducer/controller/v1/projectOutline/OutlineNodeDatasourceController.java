package com.jpmsoft.projectProducer.controller.v1.projectOutline;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineNodeDatasourceRequest;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineNodeDatasourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/projectOutline/nodeDatasource")
public class OutlineNodeDatasourceController {
    @Resource
    private IOutlineNodeDatasourceService OutlineNodeDatasourceService;

    @PostMapping("/createOutlineNodeDatasource")
    public BaseResponse createOutlineNodeDatasource(@RequestBody OutlineNodeDatasourceRequest request, @RequestHeader String token) {
        log.info("createOutlineNodeDatasource");
        return OutlineNodeDatasourceService.createOutlineNodeDatasource(request, token);
    }

    @PostMapping("/getOutlineNodeDatasourceList")
    public BaseResponse getOutlineNodeDatasourceList(@RequestBody OutlineNodeDatasourceRequest request) {
        log.info("getOutlineNodeDatasourceList");
        return OutlineNodeDatasourceService.getOutlineNodeDatasourceList(request);
    }

    @GetMapping("/deleteOutlineNodeDatasourceById")
    public BaseResponse deleteOutlineNodeDatasourceById(@RequestParam String id, @RequestHeader String token) {
        log.info("deleteOutlineNodeDatasourceById");
        return OutlineNodeDatasourceService.deleteOutlineNodeDatasourceById(id, token);
    }

    @PostMapping("/updateOutlineNodeDatasource")
    public BaseResponse updateOutlineNodeDatasource(@RequestBody OutlineNodeDatasourceRequest request, @RequestHeader String token) {
        log.info("updateOutlineNodeDatasource");
        return OutlineNodeDatasourceService.updateOutlineNodeDatasource(request, token);
    }

    @PostMapping("/autoSaveOutlineNodeDatasource")
    public BaseResponse autoSaveOutlineNodeDatasource(@RequestBody OutlineNodeDatasourceRequest request, @RequestHeader String token) {
        log.info("autoSaveOutlineNodeDatasource");
        if (ValidateUtil.isEmpty(request.getId())) {
            return OutlineNodeDatasourceService.createOutlineNodeDatasource(request, token);
        } else {
            return OutlineNodeDatasourceService.updateOutlineNodeDatasource(request, token);
        }
    }
}

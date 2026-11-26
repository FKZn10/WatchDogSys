package com.jpmsoft.userProducer.controller.v1;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.OutlineDatasourceDto;
import com.jpmsoft.userProducer.service.OutlineDatasourceService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/OutlineDatasource")
public class OutlineDatasourceController {
    @Autowired
    private OutlineDatasourceService OutlineDatasourceService;

    @ApiOperation(value = "创建数据源")
    @PostMapping("/createOrUpdateOutlineDatasource")
    public BaseResponse createOrUpdateOutlineDatasource(@RequestBody OutlineDatasourceDto request, @RequestHeader String token) {
        log.info("createOrUpdateOutlineDatasource");
        return OutlineDatasourceService.createOrUpdateOutlineDatasource(request, token);
    }

    @ApiOperation(value = "获取数据源列表")
    @PostMapping("/getOutlineDatasourceList")
    public BaseResponse getOutlineDatasourceList(@RequestBody OutlineDatasourceDto request) {
        log.info("getOutlineDatasourceList");
        return OutlineDatasourceService.getOutlineDatasourceList(request);
    }

    @ApiOperation(value = "根据id获取数据源")
    @GetMapping("/getOutlineDatasourceById")
    public BaseResponse getOutlineDatasourceById(@RequestParam String id) {
        log.info("getOutlineDatasourceById");
        return OutlineDatasourceService.getOutlineDatasourceById(id);
    }

}

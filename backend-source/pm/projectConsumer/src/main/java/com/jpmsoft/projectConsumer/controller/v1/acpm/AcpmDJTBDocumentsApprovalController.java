package com.jpmsoft.projectConsumer.controller.v1.acpm;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.acpm.AcpmDJTBDocumentsApprovalDto;
import com.jpmsoft.projectConsumer.feign.AcpmFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 代建投标文件审批
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 16:49
 */
@Slf4j
@RestController
@Api(tags = "代建投标文件审批", value = "代建投标文件审批")
@RequestMapping("/acpm/v1/DJTBDocuApproval")
public class AcpmDJTBDocumentsApprovalController {


    @Resource
    private AcpmFeign acpmFeign;


    @ApiOperation(value = "新增或修改代建投标文件审批")
    @PostMapping("/createOrUpdate")
    public BaseResponse createOrUpdate(@RequestBody AcpmDJTBDocumentsApprovalDto request) {
        return acpmFeign.createOrUpdate(request);
    }

    @ApiOperation(value = "分页查询代建投标文件审批")
    @PostMapping("/getList")
    public BaseResponse getList(@RequestBody AcpmDJTBDocumentsApprovalDto request) {
        return acpmFeign.getList(request);
    }

    @ApiOperation(value = "ID查询代建投标文件审批")
    @GetMapping("/getDJTBDocuApprovalById")
    public BaseResponse getDJTBDocuApprovalById(@RequestParam String id) {
        return acpmFeign.getDJTBDocuApprovalById(id);
    }
}

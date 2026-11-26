package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.project.ProProjectRegisterRequest;
import com.jpmsoft.projectConsumer.feign.ProProjectRegisterFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 项目登记增删改查接口。
 */
@RestController
@Api(tags = "项目登记")
@RequestMapping("/v1/proProjectRegister")
public class ProProjectRegisterController {

    @Resource
    private ProProjectRegisterFeign proProjectRegisterFeign;

    /**
     * 新增或修改项目登记，同时保存参建单位和附件。
     *
     * @param request 整表单保存参数
     * @param token   登录令牌
     * @return 保存后的登记ID
     */
    @ApiOperation("新增或修改项目登记")
    @PostMapping("/createOrUpdateProProjectRegister")
    public BaseResponse createOrUpdateProProjectRegister(@RequestBody ProProjectRegisterRequest request,
                                                         @RequestHeader("token") String token) {
        return proProjectRegisterFeign.createOrUpdateProProjectRegister(request, token);
    }

    /**
     * 分页查询项目登记。
     *
     * @param request 筛选及分页参数
     * @return 当前页记录及总条数
     */
    @ApiOperation("分页查询项目登记")
    @PostMapping("/getProProjectRegisterList")
    public BaseResponse getProProjectRegisterList(@RequestBody ProProjectRegisterRequest request) {
        return proProjectRegisterFeign.getProProjectRegisterList(request);
    }

    /**
     * 根据ID查询登记主表、参建单位和附件。
     *
     * @param id 登记ID
     * @return 登记详情
     */
    @ApiOperation("查询项目登记详情")
    @GetMapping("/getProProjectRegisterById")
    public BaseResponse getProProjectRegisterById(@RequestParam("id") String id) {
        return proProjectRegisterFeign.getProProjectRegisterById(id);
    }

    /**
     * 删除项目登记及其参建单位、单据信息。
     *
     * @param id 登记ID
     * @return 删除结果
     */
    @ApiOperation("删除项目登记")
    @PostMapping("/deleteProProjectRegister")
    public BaseResponse deleteProProjectRegister(@RequestParam("id") String id) {
        return proProjectRegisterFeign.deleteProProjectRegister(id);
    }

    /**
     * 统计当前用户可见的全部年度项目登记。
     *
     * @return 总数、审核中和已审核数量
     */
    @PostMapping("/getRegistrationStatistics")
    public BaseResponse getRegistrationStatistics() {
        return proProjectRegisterFeign.getRegistrationStatistics();
    }
}

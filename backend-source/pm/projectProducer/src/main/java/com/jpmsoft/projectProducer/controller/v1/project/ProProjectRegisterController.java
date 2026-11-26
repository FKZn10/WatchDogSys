package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.project.ProProjectRegisterRequest;
import com.jpmsoft.projectProducer.service.project.IProProjectRegisterService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 项目登记增删改查接口。
 */
@RestController
@RequestMapping("/v1/proProjectRegister")
public class ProProjectRegisterController {

    @Resource
    private IProProjectRegisterService proProjectRegisterService;

    @Resource
    private RedisUtils redisUtils;

    /**
     * 新增或修改项目登记，同时保存参建单位和附件。
     *
     * @param request 整表单保存参数
     * @param token   登录令牌
     * @return 保存后的登记ID
     */
    @PostMapping("/createOrUpdateProProjectRegister")
    public BaseResponse createOrUpdateProProjectRegister(@RequestBody ProProjectRegisterRequest request,
                                                         @RequestHeader("token") String token) {
        return proProjectRegisterService.createOrUpdateProProjectRegister(request, redisUtils.getUserInfoByToken(token));
    }

    /**
     * 分页查询项目登记。
     *
     * @param request 筛选及分页参数
     * @return 当前页记录及总条数
     */
    @PostMapping("/getProProjectRegisterList")
    public BaseResponse getProProjectRegisterList(@RequestBody ProProjectRegisterRequest request) {
        return proProjectRegisterService.getProProjectRegisterList(request);
    }

    /**
     * 根据ID查询登记主表、参建单位和附件。
     *
     * @param id 登记ID
     * @return 登记详情
     */
    @GetMapping("/getProProjectRegisterById")
    public BaseResponse getProProjectRegisterById(@RequestParam("id") String id) {
        return proProjectRegisterService.getProProjectRegisterById(id);
    }

    /**
     * 删除项目登记及其参建单位、单据信息。
     *
     * @param id 登记ID
     * @return 删除结果
     */
    @PostMapping("/deleteProProjectRegister")
    public BaseResponse deleteProProjectRegister(@RequestParam("id") String id) {
        return proProjectRegisterService.deleteProProjectRegister(id);
    }

    /**
     * 统计当前用户可见的全部年度项目登记。
     *
     * @return 总数、审核中和已审核数量
     */
    @PostMapping("/getRegistrationStatistics")
    public BaseResponse getRegistrationStatistics() {
        return proProjectRegisterService.getRegistrationStatistics();
    }
}

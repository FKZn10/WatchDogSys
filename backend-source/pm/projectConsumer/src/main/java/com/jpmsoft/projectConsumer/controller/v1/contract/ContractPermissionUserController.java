package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import com.jpmsoft.projectConsumer.pojo.dto.contract.ContractPermissionUserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 合同权限配置-合同参与人员
 *
 * @Author: 李文涛 aaron
 * @date: 2025/2/6 16:03
 */
@Slf4j
@RestController
@Api(tags = "合同权限配置-合同参与人员", value = "合同权限配置-合同参与人员")
@RequestMapping("/v1/contractPermissionUser")
public class ContractPermissionUserController {

    @Resource
    private ConFeign conFeign;

    @ApiOperation(value = "添加一个合同权限配置信息")
    @GetMapping("/addContractOne")
    public BaseResponse addContractOne(@RequestParam String uid, @RequestParam String conId, @RequestHeader String token) {
        log.info("addContractOne");
        return conFeign.addContractOne(uid, conId, token);
    }


    @ApiOperation(value = "添加一个合同权限配置信息批量")
    @PostMapping("/addContractUserBatch")
    public BaseResponse addContractUserBatch(@RequestBody List<String> userIds, @RequestParam String conId, @RequestHeader String token) {
        log.info("addContractUserBatch");
        return conFeign.addContractUserBatch(userIds, conId, token);
    }


    @ApiOperation(value = "给用户添加所有合同权限配置")
    @GetMapping("/addContractAll")
    public BaseResponse addContractAll(@RequestParam String uid, @RequestHeader String token) {
        log.info("addContractAll");
        return conFeign.addContractAll(uid, token);
    }

    @ApiOperation(value = "删除用户下所有关联合同")
    @GetMapping("/deleteContractAll")
    public BaseResponse deleteContractAll(@RequestParam String uid) {
        log.info("deleteContractAll");
        return conFeign.deleteContractAll(uid);
    }

    @ApiOperation(value = "根据合同ID和用户ID删除数据")
    @GetMapping("/deleteByConIdAndUid")
    public BaseResponse deleteByConIdAndUid(@RequestParam String uid, @RequestParam String conId) {
        log.info("deleteByConIdAndUid");
        return conFeign.deleteByConIdAndUid(uid, conId);
    }

    @ApiOperation(value = "ID删除")
    @GetMapping("/deletePermissionUserById")
    public BaseResponse deletePermissionUserById(@RequestParam String id) {
        log.info("deletePermissionUserById");
        return conFeign.deletePermissionUserById(id);
    }

    @ApiOperation(value = "分页查询合同权限配置合同下所有用户")
    @PostMapping("/selectUserPage")
    public BaseResponse selectUserPage(@RequestBody ContractPermissionUserDTO request) {
        log.info("selectUserPage");
        return conFeign.selectUserPage(request);
    }

    @ApiOperation(value = "分页查询合同权限配置用户下所有合同信息")
    @PostMapping("/selectContractPage")
    public BaseResponse selectContractPage(@RequestBody ContractPermissionUserDTO request) {
        log.info("selectContractPage");
        return conFeign.selectContractPage(request);
    }

    @ApiOperation(value = "获取当前用户的合同ID列表")
    @GetMapping("/getCurrentUserContractIdList")
    public BaseResponse getCurrentUserContractIdList(@RequestHeader String token) {
        log.info("getCurrentUserContractIdList");
        return conFeign.getCurrentUserConIdList(token);
    }
}

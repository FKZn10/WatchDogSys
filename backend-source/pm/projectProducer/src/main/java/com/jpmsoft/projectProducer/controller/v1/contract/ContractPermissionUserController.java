package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.contract.ContractPermissionUserDTO;
import com.jpmsoft.projectProducer.service.contract.IContractPermissionUserService;
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
@RequestMapping("/v1/contractPermissionUser")
public class ContractPermissionUserController {

    @Resource
    private IContractPermissionUserService contractPermissionUserService;


    @GetMapping("/addContractOne")
    public BaseResponse addContractOne(@RequestParam String uid, @RequestParam String conId, @RequestHeader String token) {
        log.info("addContractOne");
        return contractPermissionUserService.addContractOne(uid, conId, token);
    }

    @PostMapping("/addContractUserBatch")
    public BaseResponse addContractUserBatch(@RequestBody List<String> userIds, @RequestParam String conId, @RequestHeader String token) {
        log.info("addContractUserBatch");
        return contractPermissionUserService.addContractUserBatch(userIds, conId, token);
    }

    @GetMapping("/addContractAll")
    public BaseResponse addContractAll(@RequestParam String uid, @RequestHeader String token) {
        log.info("addContractAll");
        return contractPermissionUserService.addContractAll(uid, token);
    }

    @GetMapping("/deleteContractAll")
    public BaseResponse deleteContractAll(@RequestParam String uid) {
        log.info("deleteContractAll");
        return contractPermissionUserService.deleteContractAll(uid);
    }

    @GetMapping("/deleteByConIdAndUid")
    public BaseResponse deleteByConIdAndUid(@RequestParam String uid, @RequestParam String conId) {
        log.info("deleteByConIdAndUid");
        return contractPermissionUserService.deleteByConIdAndUid(uid, conId);
    }

    @GetMapping("/deletePermissionUserById")
    public BaseResponse deletePermissionUserById(@RequestParam String id) {
        log.info("deletePermissionUserById");
        return contractPermissionUserService.deleteById(id);
    }

    @PostMapping("/selectUserPage")
    public BaseResponse selectUserPage(@RequestBody ContractPermissionUserDTO request) {
        log.info("selectUserPage");
        return contractPermissionUserService.selectUserPage(request);
    }

    @PostMapping("/selectContractPage")
    public BaseResponse selectContractPage(@RequestBody ContractPermissionUserDTO request) {
        log.info("selectContractPage");
        return contractPermissionUserService.selectContractPage(request);
    }

    @GetMapping("/getCurrentUserContractIdList")
    public BaseResponse getCurrentUserContractIdList(@RequestHeader String token) {
        log.info("getCurrentUserContractIdList");
        return contractPermissionUserService.getCurrentUserConIdList(token);
    }
}

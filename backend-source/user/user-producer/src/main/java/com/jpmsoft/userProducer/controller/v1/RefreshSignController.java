package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.model.request.SignAndVerifyDTO;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.pojo.entity.SysRole;
import com.jpmsoft.userProducer.pojo.entity.SysUser;
import com.jpmsoft.userProducer.service.IApiUtilsService;
import com.jpmsoft.userProducer.service.ISysConfigEntityService;
import com.jpmsoft.userProducer.service.ISysRoleService;
import com.jpmsoft.userProducer.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/8/8 14:43
 */
@Slf4j
@RestController
@ApiIgnore
@RequestMapping("/v1/refreshSign")
public class RefreshSignController {

    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysRoleService sysRoleService;
    @Resource
    private ISysConfigEntityService sysConfigEntityService;
    @Resource
    private IApiUtilsService apiUtilsService;

    @ApiIgnore
    @GetMapping("/refreshUser")
    public BaseResponse refreshUser() {
        InterfaceEncDecDTO ied = sysConfigEntityService.getInterfaceEncDec();
        if (ied.getUserSign()) {
            List<SysUser> users = sysUserService.list();
            List<SysUser> nosign = users.stream().filter(f -> ValidateUtil.isEmpty(f.getSign())).collect(Collectors.toList());
            nosign.forEach(n -> {
                if (ValidateUtil.isNotEmpty(ied.getSignUrl())) {
                    SignAndVerifyDTO sg = new SignAndVerifyDTO();
                    sg.setContent(n.signContent());
                    BaseResponse res = apiUtilsService.sendPost(ied.getSignUrl(), sg, null);
                    if (ValidateUtil.isNotNull(res) && ValidateUtil.isNotNull(res.getData())) {
                        n.setSign(res.getData().toString());
                    }
                }
            });
            sysUserService.updateBatchById(nosign);
        }
        return BaseResponse.ok();
    }


    @ApiIgnore
    @GetMapping("/refreshRole")
    public BaseResponse refreshRole() {
        InterfaceEncDecDTO ied = sysConfigEntityService.getInterfaceEncDec();
        if (ied.getRoleSign()) {
            List<SysRole> roles = sysRoleService.list();
            List<SysRole> nosign = roles.stream().filter(f -> ValidateUtil.isEmpty(f.getSign())).collect(Collectors.toList());
            nosign.forEach(n -> {
                if (ValidateUtil.isNotEmpty(ied.getSignUrl())) {
                    SignAndVerifyDTO sg = new SignAndVerifyDTO();
                    sg.setContent(n.signContent());
                    BaseResponse res = apiUtilsService.sendPost(ied.getSignUrl(), sg, null);
                    if (ValidateUtil.isNotNull(res) && ValidateUtil.isNotNull(res.getData())) {
                        n.setSign(res.getData().toString());
                    }
                }
            });
            sysRoleService.updateBatchById(nosign);
        }
        return BaseResponse.ok();
    }
}

package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.RoleDto;
import com.jpmsoft.userProducer.pojo.entity.SysRole;

import java.util.List;


public interface ISysRoleService extends IService<SysRole> {

    BaseResponse GetRoleList(RoleDto request);

    BaseResponse CreateOrUpdateRole(RoleDto request);

    BaseResponse DeleteRole(RoleDto request);

    BaseResponse GetRoleCheckBox(String powerId,String cId,String token);

    /**
     * 查询权限菜单下所有角色
     *
     * @param poweId
     * @return
     */
    List<SysRole> getRoleByPower(String poweId);

    List<SysRole> GetRoleList(String teId);
}

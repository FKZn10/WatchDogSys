package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.RoleDto;
import com.jpmsoft.userProducer.pojo.entity.SysRolePower;
import com.jpmsoft.userProducer.pojo.vo.RolePowerVo;

import java.util.List;


public interface ISysRolePowerService extends IService<SysRolePower> {

    void CreateRolePower(String roleId, String powerId,String teId);

    void deleteRolePower(String roleId);

    BaseResponse getPowerByRole(RoleDto request);

    List<RolePowerVo> getRoleNames(String teId);


    /**
     * 删除角色关联删除
     *
     * @param roleId
     */
    void deleteRolePowerByRoleId(String roleId);


    void deleteRolePowerByPowerId(String powerId);

    /**
     * 批量刷新角色权限关系
     * roleIds==null 不执行任何操作
     * roleIds=[] 或者有数据 则先删除所有角色权限再重新创建
     *
     * @param roleIds
     * @param powerId
     */
    void updatePositionRoleBath(List<String> roleIds, String powerId);
}

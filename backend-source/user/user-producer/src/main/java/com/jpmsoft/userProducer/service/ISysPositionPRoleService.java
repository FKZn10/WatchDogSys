package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.userProducer.pojo.entity.SysPositionPRole;

import java.util.List;


public interface ISysPositionPRoleService extends IService<SysPositionPRole> {

    void CreatePositionPRole(String ppId, String roleIds);

    void insert(SysPositionPRole sysPositionPRole);

    /**
     * 删除角色 关联删除
     *
     * @param roleId
     */
    void deletePositionPRoleByRoleId(String roleId);

    List<SysPositionPRole> getPositionPRoleByPpId(String ppId);
}

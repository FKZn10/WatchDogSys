package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.TPositionDto;
import com.jpmsoft.userProducer.pojo.entity.SysTPositionRole;

import java.util.List;


public interface ISysTPositionRoleService extends IService<SysTPositionRole> {

    /**
     * 根据项目岗位模板获取角色
     *
     * @param request
     * @return
     */
    BaseResponse getRoleByT_Position(TPositionDto request);

    /**
     * 新增岗位模板角色
     *
     * @param tpId
     * @param roleIds
     */
    void createTPositionRole(String tpId, String roleIds);

    /**
     * 删除岗位模板角色
     *
     * @param tpId
     */
    void deleteTPositionRole(String tpId);

    List<SysTPositionRole> getTPositionRoleByTpId(String tpId);

    /**
     * 删除角色关联删除
     *
     * @param roleId
     */
    void deleteTPositionRoleByRole(String roleId);
}

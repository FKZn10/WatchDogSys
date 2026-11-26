package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.PositionPDto;
import com.jpmsoft.userProducer.pojo.dto.PositionRolePDto;
import com.jpmsoft.userProducer.pojo.entity.SysPositionRoleP;


public interface ISysPositionRolePService extends IService<SysPositionRoleP> {

    BaseResponse getRoleByPositionP(PositionPDto request);

    /**
     * 删除角色关联删除
     *
     * @param roleId
     */
    void deletePositionRolePByRoleId(String roleId);

    /**
     * 获取项目下所有岗位的角色信息
     * @param request
     * @return
     */
    BaseResponse getPositionRolePByPId(PositionRolePDto request);
}

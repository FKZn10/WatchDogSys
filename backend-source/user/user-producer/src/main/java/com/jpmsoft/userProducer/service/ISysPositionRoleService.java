package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.userProducer.pojo.dto.PositionDto;
import com.jpmsoft.userProducer.pojo.dto.UserPDPDto;
import com.jpmsoft.userProducer.pojo.entity.SysPositionRole;
import com.jpmsoft.userProducer.pojo.entity.SysUser;
import com.jpmsoft.userProducer.pojo.vo.LogInfoVo;

import java.util.List;


public interface ISysPositionRoleService extends IService<SysPositionRole> {

    List<SysPositionRole> getPositionRoleByPId(String pId);

    /**
     * 根据岗位获取角色
     *
     * @param request
     * @return
     */
    BaseResponse getRoleByPosition(PositionDto request);


    void CreatePositionRoles(String roleIds, String positionId);

    void deletePositionRoles(String pId);

    /**
     * 登录获取用户角色
     * <p>
     * 组织机构岗位角色以及项目组织机构岗位角色合并结果
     *
     * @param uId
     * @return
     */
    List<String> loginToGetUserRoleName(String uId, InterfaceEncDecDTO ied);

    /**
     * 组织机构岗位角色以及项目组织机构岗位角色合并结果
     * 以及角色对应按钮权限编码
     *
     * @param user
     * @return
     */
    LogInfoVo setUserRoleNameAndBtnRole(LogInfoVo logInfo, SysUser user);

    /**
     * 删除角色关联删除
     *
     * @param roleId
     */
    void deletePositionRoleByRoleId(String roleId);

    /**
     * 获取用户角色的来源
     *
     * @param request
     * @param token
     * @return
     */
    BaseResponse getUserRoleSource(UserPDPDto request, String token);
}

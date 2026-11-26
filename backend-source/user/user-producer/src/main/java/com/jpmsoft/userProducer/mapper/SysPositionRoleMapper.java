package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.userProducer.pojo.dto.BtnPowerRoleDto;
import com.jpmsoft.userProducer.pojo.dto.UserPDPDto;
import com.jpmsoft.userProducer.pojo.entity.SysPositionRole;
import com.jpmsoft.userProducer.pojo.entity.SysRole;
import com.jpmsoft.userProducer.pojo.vo.SysRoleSourceVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SysPositionRoleMapper extends BaseMapper<SysPositionRole> {

    /**
     * 登录时获取用户角色
     *
     * @param uId
     * @return
     */
    List<SysRole> loginToGetUserRoleName(@Param("uId") String uId);

    /**
     * 获取所有角色对应按钮权限
     *
     * @return
     */
    List<BtnPowerRoleDto> getAllBtnPowerRole(@Param("userRoles") List<String> userRoles);

    /**
     * 获取用户角色的来源
     *
     * @param page
     * @param request
     * @return
     */
    List<SysRoleSourceVo> getUserRoleSource(@Param("page") Page<SysRoleSourceVo> page, @Param("params") UserPDPDto request);

    /**
     * 获取用户岗位名称
     *
     * @param uId
     * @return
     */
    List<String> getUserPositionNamesByUid(String uId);
}

package com.jpmsoft.fileUpload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.fileUpload.pojo.dto.easyexcel.DepartmentPSysUserDTO;
import com.jpmsoft.fileUpload.pojo.dto.easyexcel.DepartmentSysUserDTO;
import com.jpmsoft.fileUpload.pojo.dto.easyexcel.SysUserVo;
import com.jpmsoft.fileUpload.pojo.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface SysUserMapper extends BaseMapper<SysUser> {

    List<SysUser> getUsersByNamesIgnoreSpace(@Param("userNames") Set<String> userNames);

    List<DepartmentSysUserDTO> getUserListByMoreCondition(@Param("params") Map<String, Object> params);


    List<DepartmentPSysUserDTO> GetUserListByDPId(@Param("params") Map<String, Object> params);

    /**
     * 分页查询用户信息
     *
     * @param params
     * @return
     */
    List<SysUserVo> GetUserList(@Param("params") Map<String, Object> params);
}

package com.jpmsoft.fileUpload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.fileUpload.pojo.dto.SysUserManualDTO;
import com.jpmsoft.fileUpload.pojo.entity.SysUserManual;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/12/26 9:07
 */
public interface SysUserManualMapper extends BaseMapper<SysUserManual> {

    @Select("select   " +
            "id," +
            "Um_Title," +
            "Um_Keys," +
            "Um_Parent_Id," +
            "Um_State," +
            "Um_Sort," +
            "Create_Uname," +
            "Create_Uid," +
            "Create_Time" +
            " from Sys_User_Manual  order by Um_Sort asc")
    List<SysUserManualDTO> selectAll();
}

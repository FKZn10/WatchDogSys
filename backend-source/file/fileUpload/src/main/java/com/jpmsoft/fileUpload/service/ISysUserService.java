package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.fileUpload.pojo.dto.UserDTO;
import com.jpmsoft.fileUpload.pojo.entity.SysUser;

import javax.servlet.http.HttpServletResponse;

public interface ISysUserService extends IService<SysUser> {

    String getUserNameByUId(String uId);

    SysUser getUserByAccount(String account);

    void exportUserWithDepartment(UserDTO user, HttpServletResponse response);


    void exportUserWithDepartmentP(UserDTO user, HttpServletResponse response);

    void exportUserList(UserDTO user, String uId, HttpServletResponse response);
}

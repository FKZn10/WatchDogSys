package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.fileUpload.pojo.entity.SysDepartment;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/7/3 9:40
 */
public interface ISysDepartmentService extends IService<SysDepartment> {

    /**
     * 获取所有单位部门以及其下级部门ID
     *
     * @param cId
     * @return
     */
    List<String> getAllChildDepartmentByCid(String cId);
}

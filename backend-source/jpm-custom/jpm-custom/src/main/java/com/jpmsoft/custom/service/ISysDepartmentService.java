package com.jpmsoft.custom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.custom.pojo.entity.SysDepartment;

import java.util.List;

public interface ISysDepartmentService extends IService<SysDepartment> {


    List<String> getUserDepIdsByUid(String uId);

    ;
}

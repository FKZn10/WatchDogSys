package com.jpmsoft.projectProducer.service.oa;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.entity.oa.OASupervisionTaskDepartment;

import java.util.List;

public interface IOaSupervisionTaskDepartmentService extends IService<OASupervisionTaskDepartment> {
    List<OASupervisionTaskDepartment> GetOASupervisionTaskDepartmentPojoList(String tid);
}

package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.jpbpm.pojo.entity.Department;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface DepartmentService  extends IService<Department> {
    List<Department> getDepartmentAll(String customerId);
}

package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.jpbpm.pojo.entity.SysRole;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysRoleService extends IService<SysRole> {

    List<SysRole>  getRoleAll(String customerId);
}

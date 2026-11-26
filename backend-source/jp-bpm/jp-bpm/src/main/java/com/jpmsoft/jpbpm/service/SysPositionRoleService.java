package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.jpbpm.pojo.entity.SysPositionRole;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysPositionRoleService extends IService<SysPositionRole> {

    List<SysPositionRole> getPositionRoleAll(String customerId);
}

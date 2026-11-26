package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.jpbpm.pojo.entity.SysPosition;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysPositionService extends IService<SysPosition> {
    List<SysPosition> getPositionAll(String customerId);
}

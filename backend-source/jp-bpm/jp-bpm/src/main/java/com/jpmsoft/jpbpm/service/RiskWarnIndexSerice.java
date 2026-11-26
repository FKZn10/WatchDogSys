package com.jpmsoft.jpbpm.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.jpbpm.pojo.entity.RiskWarnIndex;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RiskWarnIndexSerice extends IService<RiskWarnIndex> {
    List<RiskWarnIndex> selectListByRwiId(List<String> rwiId,String customerId);
}

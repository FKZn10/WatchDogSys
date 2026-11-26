package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.jpbpm.mapper.RiskWarnIndexMapper;
import com.jpmsoft.jpbpm.pojo.entity.RiskWarnIndex;
import com.jpmsoft.jpbpm.service.RiskWarnIndexSerice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Transactional
@Service
public class RiskWarnIndexImpl extends ServiceImpl<RiskWarnIndexMapper, RiskWarnIndex> implements RiskWarnIndexSerice {
    @Override
    public List<RiskWarnIndex> selectListByRwiId(List<String> rwiId,String customerId) {
        if (rwiId.size() > 0) {
            String rwiIdstr = rwiId.stream().map(String::valueOf).collect(Collectors.joining("','"));
            return baseMapper.selectListByRwiId(rwiIdstr);
        } else {
            return null;
        }
    }
}

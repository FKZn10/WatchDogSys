package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.jpbpm.mapper.FlowUserMapper;
import com.jpmsoft.jpbpm.pojo.entity.FlowUser;
import com.jpmsoft.jpbpm.service.FlowUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class FlowUserImpl extends ServiceImpl<FlowUserMapper, FlowUser> implements FlowUserService {
    @Override
    public List<FlowUser> getFlowUserInFlowUserActS(List<String> flowUserActs,String customerId) {
        if(CollectionUtils.isEmpty(flowUserActs)){
            return  new ArrayList<>();
        }
        QueryWrapper<FlowUser> wrapper=new QueryWrapper<>();
        wrapper.in("flowUserAct",flowUserActs);
        if(!"All".equals(customerId) ){
            wrapper.eq("Te_Id", customerId);
        }
        return baseMapper.selectList(wrapper);
    }
}

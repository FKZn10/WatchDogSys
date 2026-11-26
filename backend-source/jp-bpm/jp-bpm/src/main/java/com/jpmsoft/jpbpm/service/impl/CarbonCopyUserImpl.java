package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.jpbpm.mapper.CarbonCopyUserMapper;
import com.jpmsoft.jpbpm.pojo.entity.CarbonCopyUser;
import com.jpmsoft.jpbpm.service.CarbonCopyUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CarbonCopyUserImpl extends ServiceImpl<CarbonCopyUserMapper, CarbonCopyUser> implements CarbonCopyUserService {
    @Override
    public List<CarbonCopyUser> getCarbonCopyUserListByActId(String ccActid,String customerId) {
        QueryWrapper<CarbonCopyUser> wrapper = new QueryWrapper<>();
        wrapper.eq("CC_ActID", ccActid);
        if(!"All".equals(customerId) ){
            wrapper.eq("Te_Id", customerId);
        }
        return baseMapper.selectList(wrapper);
    }
}

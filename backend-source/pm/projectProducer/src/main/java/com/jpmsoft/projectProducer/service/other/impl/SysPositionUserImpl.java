package com.jpmsoft.projectProducer.service.other.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.projectProducer.mapper.other.SysPositionUserMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysPositionUser;
import com.jpmsoft.projectProducer.service.other.ISysPositionUserService;
import org.springframework.stereotype.Service;

@Service
public class SysPositionUserImpl extends ServiceImpl<SysPositionUserMapper, SysPositionUser>
        implements ISysPositionUserService {
}

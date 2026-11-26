package com.jpmsoft.projectProducer.service.other.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.projectProducer.mapper.other.SysMessageReceiveMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysMessageReceive;
import com.jpmsoft.projectProducer.service.other.ISysMessageReceiveService;
import org.springframework.stereotype.Service;

@Service
public class SysMessageReceiveImpl extends ServiceImpl<SysMessageReceiveMapper, SysMessageReceive>
        implements ISysMessageReceiveService {
}

package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.jpbpm.mapper.SysMessageReceiveMapper;
import com.jpmsoft.jpbpm.pojo.entity.SysMessageReceive;
import com.jpmsoft.jpbpm.service.ISysMessageReceiveService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysMessageReceiveImpl  extends ServiceImpl<SysMessageReceiveMapper, SysMessageReceive> implements ISysMessageReceiveService {
}

package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.userProducer.mapper.JimuDictMapper;
import com.jpmsoft.userProducer.pojo.entity.JimuDict;
import com.jpmsoft.userProducer.service.IJimuDictService;
import org.springframework.stereotype.Service;


@Service
public class JimuDictImpl extends ServiceImpl<JimuDictMapper, JimuDict> implements IJimuDictService {
}

package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.userProducer.mapper.JimuDictItemMapper;
import com.jpmsoft.userProducer.pojo.entity.JimuDictItem;
import com.jpmsoft.userProducer.service.IJimuDictItemService;
import org.springframework.stereotype.Service;


@Service
public class JimuDictItemImpl extends ServiceImpl<JimuDictItemMapper, JimuDictItem> implements IJimuDictItemService {
}

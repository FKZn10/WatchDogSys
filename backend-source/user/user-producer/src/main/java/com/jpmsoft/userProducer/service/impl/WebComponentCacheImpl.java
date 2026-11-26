package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.userProducer.mapper.WebComponentCacheMapper;
import com.jpmsoft.userProducer.pojo.entity.WebComponentCache;
import com.jpmsoft.userProducer.service.IWebComponentCacheService;
import org.springframework.stereotype.Service;

@Service
public class WebComponentCacheImpl extends ServiceImpl<WebComponentCacheMapper, WebComponentCache> implements IWebComponentCacheService {
}

package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.projectProducer.mapper.secInfo.SecFocusExpertMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecFocusExpert;
import com.jpmsoft.projectProducer.service.secInfo.ISecFocusExpertService;
import org.springframework.stereotype.Service;

/**
 * 重点专家库使用申请
 */
@Service
public class SecFocusExpertServiceImpl extends ServiceImpl<SecFocusExpertMapper, SecFocusExpert> implements ISecFocusExpertService {
}

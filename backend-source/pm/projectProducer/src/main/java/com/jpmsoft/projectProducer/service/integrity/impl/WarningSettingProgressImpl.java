package com.jpmsoft.projectProducer.service.integrity.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.projectProducer.mapper.integrity.WarningSettingProgressMapper;
import com.jpmsoft.projectProducer.pojo.entity.integrity.WarningSettingProgress;
import com.jpmsoft.projectProducer.service.integrity.IWarningSettingProgressService;
import org.springframework.stereotype.Service;

/**
 * 预警提醒设置
 */
@Service
public class WarningSettingProgressImpl extends ServiceImpl<WarningSettingProgressMapper, WarningSettingProgress> implements IWarningSettingProgressService {
}

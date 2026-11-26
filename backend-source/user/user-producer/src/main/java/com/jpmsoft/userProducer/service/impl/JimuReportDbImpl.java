package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.userProducer.mapper.JimuReportDbMapper;
import com.jpmsoft.userProducer.pojo.entity.JimuReportDb;
import com.jpmsoft.userProducer.service.IJimuReportDbService;
import org.springframework.stereotype.Service;

@Service
public class JimuReportDbImpl extends ServiceImpl<JimuReportDbMapper, JimuReportDb> implements IJimuReportDbService {
}

package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.mapper.SysSequenceLogMapper;
import com.jpmsoft.userProducer.pojo.dto.SysSequenceLogDTO;
import com.jpmsoft.userProducer.pojo.entity.SysSequenceLog;
import com.jpmsoft.userProducer.pojo.entity.SysSequenceLog;
import com.jpmsoft.userProducer.service.ISysSequenceLogService;
import com.jpmsoft.userProducer.utils.CheckNullUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/15 9:39
 */
@Service
public class SysSequenceLogImpl extends ServiceImpl<SysSequenceLogMapper, SysSequenceLog> implements ISysSequenceLogService {

    @Value("${system.sequenceLog}")
    private Boolean sequenceLog;

    @Override
    public void createSequenceLog(String code, String tCode, Object request) {
        if (sequenceLog != null && sequenceLog) {
            SysSequenceLog log = new SysSequenceLog();
            log.setId(GenerateUUID.getUUID());
            log.setSslRule(tCode);
            log.setSslCode(code);
            log.setSslBody(JacksonUtils.toJSONString(request));
            log.setSslTime(new Date());
            baseMapper.insert(log);
        }
    }

    @Override
    public BaseResponse getSequenceLogList(SysSequenceLogDTO request) {
        QueryWrapper<SysSequenceLog> wrapper = new QueryWrapper<>();
        if (!ValidateUtil.isEmpty(request.getSslRule()))
            wrapper.like("Ssl_Rule", request.getSslRule());
        if (!ValidateUtil.isEmpty(request.getSslCode()))
            wrapper.like("Ssl_Code", request.getSslCode());
        wrapper.orderByDesc("Ssl_Time");
        IPage<SysSequenceLog> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), wrapper);
        Long total = iPage.getTotal();
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }
}

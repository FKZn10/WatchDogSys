package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysSequenceLogDTO;
import com.jpmsoft.userProducer.pojo.entity.SysSequenceLog;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/15 9:38
 */
public interface ISysSequenceLogService extends IService<SysSequenceLog> {

    void createSequenceLog(String code, String tCode, Object request);

    BaseResponse getSequenceLogList(SysSequenceLogDTO request);
}

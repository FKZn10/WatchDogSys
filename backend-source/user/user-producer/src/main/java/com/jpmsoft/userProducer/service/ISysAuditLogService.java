package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysAuditLogQueryDto;
import com.jpmsoft.userProducer.pojo.entity.SysAuditLog;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/25 20:54
 */
public interface ISysAuditLogService extends IService<SysAuditLog> {

    BaseResponse getSysAuditLogList(SysAuditLogQueryDto query);
}

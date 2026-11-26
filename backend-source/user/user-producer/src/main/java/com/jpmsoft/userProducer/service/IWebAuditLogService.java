package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysAuditLogQueryDto;
import com.jpmsoft.userProducer.pojo.entity.WebAuditLog;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/25 20:54
 */
public interface IWebAuditLogService extends IService<WebAuditLog> {

    BaseResponse getWebAuditLogList(SysAuditLogQueryDto query);
}

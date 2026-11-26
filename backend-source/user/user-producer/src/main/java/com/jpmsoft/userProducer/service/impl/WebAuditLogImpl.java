package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.userProducer.mapper.WebAuditLogMapper;
import com.jpmsoft.userProducer.pojo.dto.SysAuditLogQueryDto;
import com.jpmsoft.userProducer.pojo.entity.WebAuditLog;
import com.jpmsoft.userProducer.pojo.vo.SysAuditLogVo;
import com.jpmsoft.userProducer.service.IWebAuditLogService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/25 20:54
 */
@Service
public class WebAuditLogImpl extends ServiceImpl<WebAuditLogMapper, WebAuditLog> implements IWebAuditLogService {

    @Override
    public BaseResponse getWebAuditLogList(SysAuditLogQueryDto query) {
        QueryMap<SysAuditLogQueryDto> queryMap = new QueryMap<>(query);
        queryMap.put("salFormId").put("createUname").put("salType").put("fileTitle");
        IPage<SysAuditLogVo> page = new Page<>(query.getPage(), query.getSize());
        List<SysAuditLogVo> data = baseMapper.getWebAuditLogList(page, queryMap.getQuery());
        return BaseResponse.ok(data, Long.valueOf(page.getTotal()).intValue());
    }
}

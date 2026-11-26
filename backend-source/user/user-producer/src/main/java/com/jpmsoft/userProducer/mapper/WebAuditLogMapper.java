package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.userProducer.pojo.entity.WebAuditLog;
import com.jpmsoft.userProducer.pojo.vo.SysAuditLogVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WebAuditLogMapper extends BaseMapper<WebAuditLog> {

    List<SysAuditLogVo> getWebAuditLogList(@Param("page") IPage<SysAuditLogVo> page, @Param("params") Map<String, Object> params);

}

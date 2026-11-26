package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.userProducer.pojo.entity.SysAuditLog;
import com.jpmsoft.userProducer.pojo.vo.SysAuditLogVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/25 20:54
 */
public interface SysAuditLogMapper extends BaseMapper<SysAuditLog> {


    List<SysAuditLogVo> getSysAuditLogList(@Param("page") IPage<SysAuditLogVo> page,@Param("params") Map<String, Object> params);

}

package com.jpmsoft.fileUpload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.fileUpload.pojo.entity.SysLoginLog;
import com.jpmsoft.fileUpload.pojo.vo.SysLoginLogVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/15 10:26
 */

public interface SysLoginLogMapper extends BaseMapper<SysLoginLog> {

    List<SysLoginLogVo> getSysLoginLogList(@Param("params") Map<String, Object> params);

    Integer getSysLoginLogListCount(@Param("params") Map<String, Object> params);


}

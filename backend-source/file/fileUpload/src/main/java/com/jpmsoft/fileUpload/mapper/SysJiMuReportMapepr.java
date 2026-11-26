package com.jpmsoft.fileUpload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.fileUpload.pojo.entity.SysJiMuReport;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/3/10 16:47
 */
public interface SysJiMuReportMapepr extends BaseMapper<SysJiMuReport> {

    @Select(" select * from Sys_Ji_Mu_Report where  jm_id is not NULL and file_id is not NULL  limit #{size}")
    List<SysJiMuReport> selectBySize(@Param("size") int size);
}

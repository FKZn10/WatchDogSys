package com.jpmsoft.fileUpload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Archive.ArchiveFilingApplyModel;
import com.jpmsoft.fileUpload.pojo.entity.SysDownloadLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/9/28 9:29
 */
public interface SysDownloadLogMapper extends BaseMapper<SysDownloadLog> {


    List<SysDownloadLog> getDownloadLogList(@Param("page") IPage<SysDownloadLog> page, @Param("params") Map<String, Object> params);

}

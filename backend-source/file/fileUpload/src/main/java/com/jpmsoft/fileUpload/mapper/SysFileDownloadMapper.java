package com.jpmsoft.fileUpload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.fileUpload.pojo.entity.SysFileDownload;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SysFileDownloadMapper extends BaseMapper<SysFileDownload> {

    List<SysFileDownload> getSysFileDownload(@Param("ids") String ids);

    List<SysFileDownload> getSysFileDownloadBath(@Param("ids") List<String> ids);

}

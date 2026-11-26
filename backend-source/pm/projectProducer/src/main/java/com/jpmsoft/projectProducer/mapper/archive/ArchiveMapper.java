package com.jpmsoft.projectProducer.mapper.archive;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Archive.ArchiveResponse;
import com.jpmsoft.projectProducer.pojo.entity.archive.Archive;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ArchiveMapper extends BaseMapper<Archive> {

    List<ArchiveResponse> getArchiveList(@Param("page") IPage<ArchiveResponse> page, @Param("params") Map<String, Object> params);

}

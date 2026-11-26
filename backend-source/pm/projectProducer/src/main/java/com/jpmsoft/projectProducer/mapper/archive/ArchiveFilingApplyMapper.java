package com.jpmsoft.projectProducer.mapper.archive;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Archive.ArchiveFilingApplyModel;
import com.jpmsoft.projectProducer.pojo.entity.archive.ArchiveFilingApply;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ArchiveFilingApplyMapper extends BaseMapper<ArchiveFilingApply> {


    List<ArchiveFilingApplyModel> getArchiveFilingApplyList(@Param("page") IPage<ArchiveFilingApplyModel> page, @Param("params") Map<String, Object> params);

    ArchiveFilingApplyModel getArchiveFilingApplyById(@Param("faId") String faId);
}

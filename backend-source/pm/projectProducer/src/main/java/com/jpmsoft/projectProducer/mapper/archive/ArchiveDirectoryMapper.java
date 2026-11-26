package com.jpmsoft.projectProducer.mapper.archive;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.request.Archive.ArchiveDirectoryRequest;
import com.jpmsoft.common.model.response.Archive.ArchiveDirectoryResponse;
import com.jpmsoft.projectProducer.pojo.entity.archive.ArchiveDirectory;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ArchiveDirectoryMapper extends BaseMapper<ArchiveDirectory> {

    List<ArchiveDirectoryResponse> getAllArchiveDirectory(@Param("params") ArchiveDirectoryRequest params);

    List<ArchiveDirectoryResponse> getArchiveDirectoryByadIdOrpId(@Param("adId") String adId, @Param("pId") String pId, @Param("adType") String adType,@Param("adState")String adState);

    /**
     * 查询所有归档目录
     * 每个节点归档情况
     *
     * @param params
     * @return
     */
    List<ArchiveDirectoryResponse> getAllArchiveDirectoryByPid(@Param("params") ArchiveDirectoryRequest params);
}

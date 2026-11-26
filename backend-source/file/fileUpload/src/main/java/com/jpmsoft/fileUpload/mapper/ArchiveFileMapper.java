package com.jpmsoft.fileUpload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.Archive.ArchiveStatisticsModel;
import com.jpmsoft.common.model.response.Archive.ArchiveFileResponse;
import com.jpmsoft.fileUpload.pojo.dto.LuceneFileDTO;
import com.jpmsoft.fileUpload.pojo.entity.ArchiveFile;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface ArchiveFileMapper extends BaseMapper<ArchiveFile> {

    @Select("select Ad_Link from Archive_Directory where Ad_Id=#{adId}")
    String getAdLinkByAdId(@Param("adId") String adId);

    /**
     * 查询未创建索引且已归档的文件信息
     * 100条
     *
     * @return
     */
    List<LuceneFileDTO> getUnIndexArchiveFile(@Param("sfx")  List<String> sfx);

    List<ArchiveStatisticsModel> getArchiveFileStatistics(@Param("afIds") List<String> afIds);

    Integer updateArchiveFileIndex(@Param("afIds") List<String> afIds, @Param("afIndex") Boolean afIndex);

    List<ArchiveFile> getProjectArchiveUpFile(@Param("pId") String pId);

    List<ArchiveFileResponse> getByAdIds(@Param("ids") List<String> ids, @Param("pid") String pid);
}

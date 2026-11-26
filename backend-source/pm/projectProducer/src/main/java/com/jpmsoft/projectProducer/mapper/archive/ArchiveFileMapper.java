package com.jpmsoft.projectProducer.mapper.archive;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Archive.ArchiveStatisticsModel;
import com.jpmsoft.common.model.response.Archive.ArchiveFileResponse;
import com.jpmsoft.projectProducer.pojo.entity.archive.ArchiveFile;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import lombok.Data;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ArchiveFileMapper extends BaseMapper<ArchiveFile> {

    /**
     * 读取目录父子关系和当前用户在各目录上的配置状态。
     *
     * @param user 当前登录用户
     * @return 目录及本级访问状态
     */
    List<DirectoryAccess> getWzDirectoryAccess(@Param("user") LogUserInfoVo user);

    /**
     * WZ 目录的本级访问状态。
     */
    @Data
    class DirectoryAccess {
        public static final int INHERIT = 0;
        public static final int GRANTED = 2;

        private String adId;
        private String parentId;
        private int accessState;
    }

    List<ArchiveFileResponse> getArchiveFileByApIds(@Param("apIds") List<String> apIds);

    /**
     * 按文件权限、去重及筛选条件统计总数，供分页列表显式使用。
     *
     * @param params 权限与筛选参数
     * @return 符合条件的文件总数
     */
    Long getArchiveFileStatisticsCount(@Param("params") Map<String, Object> params);

    /**
     * 查询文件统计分页数据，分页总数由独立计数查询取得。
     *
     * @param page   分页信息
     * @param params 权限与筛选参数
     * @return 当前页文件
     */
    List<ArchiveStatisticsModel> getArchiveFileStatistics(@Param("page") IPage<ArchiveStatisticsModel> page, @Param("params") Map<String, Object> params);

}

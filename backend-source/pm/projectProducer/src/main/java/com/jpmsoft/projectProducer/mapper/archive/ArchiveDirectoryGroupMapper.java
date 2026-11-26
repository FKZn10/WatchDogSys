package com.jpmsoft.projectProducer.mapper.archive;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.dto.other.DirPowerRoleDTO;
import com.jpmsoft.projectProducer.pojo.entity.archive.ArchiveDirectoryGroup;
import com.jpmsoft.projectProducer.pojo.vo.archive.ArchiveDirectoryGroupVo;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/9 17:18
 */
public interface ArchiveDirectoryGroupMapper extends BaseMapper<ArchiveDirectoryGroup> {

    List<ArchiveDirectoryGroupVo> getArchiveDirectoryGroupList(@Param("page") IPage<ArchiveDirectoryGroupVo> page, @Param("params") Map<String, Object> params);

    List<ArchiveDirectoryGroupVo> getArchiveDirectoryGroupByAdId(@Param("adId") String adId);

    ArchiveDirectoryGroupVo getArchiveDirectoryGroupById(@Param("id") String id);

    @Select("select id from Archive_Directory_Group where Ad_Id=#{adId}")
    List<String> getIdListByAdId(@Param("adId") String adId);

    List<ArchiveDirectoryGroupVo>  getUserDirectoryPower(@Param("userInfo") LogUserInfoVo userInfo);

    List<DirPowerRoleDTO> getDirPowerRole(@Param("roleIds") List<String> roleIds);
}

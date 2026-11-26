package com.jpmsoft.projectProducer.service.archive;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Archive.ArchiveDirectoryGroupDto;
import com.jpmsoft.projectProducer.pojo.dto.archive.DirectoryRoleDTO;
import com.jpmsoft.projectProducer.pojo.entity.archive.ArchiveDirectoryGroup;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/9 17:18
 */
public interface IArchiveDirectoryGroupService extends IService<ArchiveDirectoryGroup> {

    BaseResponse createOrUpdateArchiveDirectoryGroupBath(List<ArchiveDirectoryGroupDto> request,String teId);

    BaseResponse getArchiveDirectoryGroupList(ArchiveDirectoryGroupDto request);

    BaseResponse createOrUpdateArchiveDirectoryGroup(ArchiveDirectoryGroupDto request);

    BaseResponse getArchiveDirectoryGroupByAdId(String adId);

    BaseResponse getArchiveDirectoryGroupById(String id);

    BaseResponse delArchiveDirectoryGroupById(String id);

    void delArchiveDirectoryGroupByAdIds(List<String> adIds);

    List<DirectoryRoleDTO> getUserDirectoryPower(LogUserInfoVo user);
}

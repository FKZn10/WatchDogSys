package com.jpmsoft.projectProducer.service.archive;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Archive.ArchiveDirectoryRequest;
import com.jpmsoft.projectProducer.pojo.entity.archive.ArchiveDirectory;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;


public interface IArchiveDirectoryService extends IService<ArchiveDirectory> {

    BaseResponse createOrUpdateArchiveDirectory(ArchiveDirectoryRequest request);

    BaseResponse getArchiveDirectoryList(ArchiveDirectoryRequest request, LogUserInfoVo user);

    BaseResponse upOrDownArchiveDirectory(String adId, String action,String teId);

    BaseResponse getArchiveDirectoryByadIdOrpId(String adId, String pId, String adType,String adState,LogUserInfoVo user);

    BaseResponse getArchiveDirectoryTreeByPId(String pId, String adType,LogUserInfoVo user);


    /**
     * 查询档案目录(项目筛选，统计已归档数量)，
     * 还可根据归档目录名筛选，排除无归档数量节点
     *
     * @param request
     * @return
     */
    BaseResponse getAllArchiveDirectoryByPid(ArchiveDirectoryRequest request);

    /**
     * 刷新目录链路
     *
     * @return
     */
    BaseResponse refershDirectoryLink(String teId);

    /**
     * 删除节点
     *
     * @param adId
     * @return
     */
    BaseResponse delDirectoryByAdId(String adId,String teId);
}


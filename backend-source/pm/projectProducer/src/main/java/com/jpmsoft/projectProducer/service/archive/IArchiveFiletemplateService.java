package com.jpmsoft.projectProducer.service.archive;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Archive.ArchiveFiletemplateRequest;
import com.jpmsoft.projectProducer.pojo.entity.archive.ArchiveFiletemplate;

import java.util.List;


public interface IArchiveFiletemplateService extends IService<ArchiveFiletemplate> {

    void createOrUpdateArchiveFiletemplate(List<ArchiveFiletemplateRequest> datas, String adId);

    BaseResponse getArchiveFiletemplateByAdId(String adId);

    /**
     * 判断当前文档编码是否已关联了归档目录
     *
     * @param datas
     * @param adId
     */
    void checkAlreadyInDiDirectory(List<ArchiveFiletemplateRequest> datas, String adId);


    /**
     * 目录是否关联模板
     *
     * @param adIds
     * @return
     */
    boolean directoryHavingTemplate(List<String> adIds);
}

package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.fileUpload.pojo.dto.ArchiveFileNameRuleDTO;
import com.jpmsoft.fileUpload.pojo.entity.ArchiveFileNameRule;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/8/29 14:08
 */
public interface IArchiveFileNameRuleService extends IService<ArchiveFileNameRule> {

    BaseResponse createOrUpdate(ArchiveFileNameRuleDTO request);

    BaseResponse getArchiveFileNameRuleList(ArchiveFileNameRuleDTO request);

    BaseResponse deleteById(String id);


    List<ArchiveFileNameRule>  selectRules(String afnId);


    void deleteByAfnId(String id);

}

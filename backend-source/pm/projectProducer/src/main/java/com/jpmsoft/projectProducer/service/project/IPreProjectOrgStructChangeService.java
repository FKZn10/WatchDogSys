package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.Project.PreProjectOrgStructResponse;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectOrgStructChange;

import java.util.List;

/**
 * 项目组织机构（变更表）(PreProjectOrgStructChange)表服务接口
 *
 * @author c
 * @since 2024-12-11 17:39:11
 */
public interface IPreProjectOrgStructChangeService extends IService<PreProjectOrgStructChange> {


    BaseResponse getDatapId(String pId, int page, int size);

    PreProjectOrgStructResponse getPreProjectOrgStructChangeById(String id);

    List<PreProjectOrgStructChange> getdataBypId(String pId);
}

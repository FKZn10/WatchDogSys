package com.jpmsoft.projectProducer.service.project;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.response.Project.PreProjectOrgStructUnitResponse;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectOrgStructUnitChange;

import java.util.List;

/**
 * 项目组织机构单位变更表(PreProjectOrgStructUnitChange)表服务接口
 *
 * @author c
 * @since 2024-12-11 17:39:11
 */
public interface IPreProjectOrgStructUnitChangeService extends IService<PreProjectOrgStructUnitChange> {


    List<PreProjectOrgStructUnitResponse> getByOrgStructId(String id);
}

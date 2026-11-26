package com.jpmsoft.projectProducer.service.project;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.response.Project.PreProjectOrgStructUserResponse;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectOrgStructUserChange;

import java.util.List;

/**
 * 项目组织机构的单位人员变更(PreProjectOrgStructUserChange)表服务接口
 *
 * @author c
 * @since 2024-12-11 17:39:12
 */
public interface IPreProjectOrgStructUserChangeService extends IService<PreProjectOrgStructUserChange>{


     List<PreProjectOrgStructUserResponse> getByOrgStructUnitIdList(List<String> orgStructUnitIdList) ;

     List<PreProjectOrgStructUserResponse> getByOrgStructUnitIdsList(List<String> orgStructUnitIdList, String id);
}

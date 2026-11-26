package com.jpmsoft.projectProducer.service.project;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.response.Project.PreProjectOrgStructUserResponse;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectOrgStructUser;

import java.util.List;

/**
 * 项目组织机构的单位人员(PreProjectOrgStructUser)表服务接口
 *
 * @author c
 * @since 2024-12-11 17:39:12
 */
public interface IPreProjectOrgStructUserService extends IService<PreProjectOrgStructUser> {


    List<PreProjectOrgStructUserResponse> getByOrgStructUnitIdList(List<String> orgStructUnitIdList);

    void deletepId(List<String> ids);

}

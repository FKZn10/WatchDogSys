package com.jpmsoft.projectProducer.service.project;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.project.PreProjectOrgStructRequest;
import com.jpmsoft.common.model.response.Project.PreProjectOrgStructResponse;
import com.jpmsoft.common.model.response.Project.PreProjectOrgStructUnitResponse;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectOrgStructUnit;

import java.util.List;

/**
 * 项目组织机构单位表(PreProjectOrgStructUnit)表服务接口
 *
 * @author c
 * @since 2024-12-11 17:39:11
 */
public interface IPreProjectOrgStructUnitService extends IService<PreProjectOrgStructUnit> {


    List<PreProjectOrgStructUnitResponse> getByOrgStructId(String orgStructId);

    BaseResponse saveSorganization(PreProjectOrgStructRequest request, String uId);

    BaseResponse changeSorganization(PreProjectOrgStructRequest request, String uId);

    void saveProjectOrganization(PreProjectOrgStructResponse pre);
}

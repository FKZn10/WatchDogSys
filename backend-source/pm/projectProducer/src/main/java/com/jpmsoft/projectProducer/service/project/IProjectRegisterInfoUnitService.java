package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProjectRegisterInfoUnitModel;
import com.jpmsoft.projectProducer.pojo.entity.project.ProjectRegisterInfoUnit;

import java.util.List;

/**
 * 项目申报-联合申报单位子表 服务类
 */
public interface IProjectRegisterInfoUnitService extends IService<ProjectRegisterInfoUnit> {
    BaseResponse saveOrUpdateProjectRegisterInfoUnit(List<ProjectRegisterInfoUnitModel> unitList, String primaryId);
}

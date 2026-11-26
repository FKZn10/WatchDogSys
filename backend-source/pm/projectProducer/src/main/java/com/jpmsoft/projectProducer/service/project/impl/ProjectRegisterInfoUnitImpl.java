package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProjectRegisterInfoUnitModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.project.ProjectRegisterInfoUnitMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.ProjectRegisterInfoUnit;
import com.jpmsoft.projectProducer.service.project.IProjectRegisterInfoUnitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目申报-联合申报单位子表 服务实现类
 */
@Service
public class ProjectRegisterInfoUnitImpl extends ServiceImpl<ProjectRegisterInfoUnitMapper, ProjectRegisterInfoUnit> implements IProjectRegisterInfoUnitService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse saveOrUpdateProjectRegisterInfoUnit(List<ProjectRegisterInfoUnitModel> unitList, String primaryId) {
        if (ValidateUtil.isEmpty(unitList) || ValidateUtil.isEmpty(primaryId)) {
            return BaseResponse.ok();
        }
        this.remove(Wrappers.<ProjectRegisterInfoUnit>lambdaQuery().eq(ProjectRegisterInfoUnit::getPriId, primaryId));
        List<ProjectRegisterInfoUnit> entities = new ArrayList<>();
        for (ProjectRegisterInfoUnitModel model : unitList) {
            ProjectRegisterInfoUnit entity = new ProjectRegisterInfoUnit();
            BeanUtils.copyProperties(model, entity);
            entity.setId(GenerateUUID.getUUID());
            entity.setPriId(primaryId);
            entities.add(entity);
        }
        this.saveBatch(entities);
        return BaseResponse.ok();
    }
}

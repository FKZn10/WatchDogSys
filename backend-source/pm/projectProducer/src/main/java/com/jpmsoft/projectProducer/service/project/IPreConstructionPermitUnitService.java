package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.Project.PreConstructionPermitUnitModel;
import com.jpmsoft.projectProducer.pojo.entity.project.PreConstructionPermitUnit;

import java.util.List;

public interface IPreConstructionPermitUnitService extends IService<PreConstructionPermitUnit> {


    void createOrUpdatePreConstructionPermitUnit(List<PreConstructionPermitUnitModel> datas, String cpId);

    void deletePreConstructionPermitUnitByComId(String cpId);
}

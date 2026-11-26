package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.project.PreOwnerUnitDTO;
import com.jpmsoft.projectProducer.pojo.entity.project.PreOwnerUnit;

import java.util.List;

public interface IPreOwnerUnitService extends IService<PreOwnerUnit> {
    /**
     * 保存业主单位人员详情
     *
     * @param pprId
     * @param ownerUnits
     */
    void saveOwnerUnit(String pprId, List<PreOwnerUnitDTO> ownerUnits);

    /**
     * 获取业主单位人员详情列表
     *
     * @param pprId
     * @return
     */
    List<PreOwnerUnitDTO> getPreOwnerUnitByPprId(String pprId);
}

package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.project.PreChangeOwnerUnitDTO;
import com.jpmsoft.projectProducer.pojo.entity.project.PreChangeOwnerUnit;

import java.util.List;

/**
 * @author tianhelin
 * @date 2025/05/13
 */
public interface IPreChangeOwnerUnitService extends IService<PreChangeOwnerUnit> {
    /**
     * 保存变更业主单位
     *
     * @param pprcId
     * @param ownerUnits
     */
    void saveChangeOwnerUnit(String pprcId, List<PreChangeOwnerUnitDTO> ownerUnits);

    /**
     * 根据pprcId获取变更业主单位详情
     *
     * @param pprcId
     * @return
     */
    List<PreChangeOwnerUnitDTO> getChangeOwnerUnitByPprcId(String pprcId);
}

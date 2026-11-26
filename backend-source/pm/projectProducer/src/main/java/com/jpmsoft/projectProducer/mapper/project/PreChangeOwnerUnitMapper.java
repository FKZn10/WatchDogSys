package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.request.project.PreChangeOwnerUnitDTO;
import com.jpmsoft.projectProducer.pojo.entity.project.PreChangeOwnerUnit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author tianhelin
 * @date 2025/05/13
 */
@Mapper
public interface PreChangeOwnerUnitMapper extends BaseMapper<PreChangeOwnerUnit> {
    /**
     * 根据pprcId获取变更业主单位详情
     *
     * @param pprcId
     * @return
     */
    List<PreChangeOwnerUnitDTO> getChangeOwnerUnitByPprcId(String pprcId);
}

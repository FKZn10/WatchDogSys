package com.jpmsoft.projectProducer.mapper.fund;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.fund.GroupFundPlanDetailSubjectDTO;
import com.jpmsoft.projectProducer.pojo.entity.fund.GroupFundPlanDetailSubject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 资金计划明细-概算关联表 Mapper 接口
 */
@Mapper
public interface GroupFundPlanDetailSubjectMapper extends BaseMapper<GroupFundPlanDetailSubject> {
    /**
     * 根据资金计划明细ID获取关联的概算信息
     *
     * @param id
     * @return
     */
    List<GroupFundPlanDetailSubjectDTO> getByGfpId(@Param("id") String id);
}

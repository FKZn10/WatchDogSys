package com.jpmsoft.projectProducer.mapper.design;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignInitialBudgetDtl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DesignInitialBudgetDtlMapper extends BaseMapper<DesignInitialBudgetDtl> {


    @Select("SELECT * FROM Design_Initial_Budget_Dtl WHERE di_id = #{diId}")
    List<DesignInitialBudgetDtl> getBudgetDtlListByDiId(@Param("diId") String diId);


}

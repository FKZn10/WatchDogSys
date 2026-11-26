package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvAdjustmentTemplateDtl;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvBudgetDeclareDtl;
import com.jpmsoft.projectProducer.pojo.vo.investment.BudgetConPayVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface InvAdjustmentTemplateDtlMapper extends BaseMapper<InvAdjustmentTemplateDtl> {

    @Select("Select * from Inv_Adjustment_Template_Dtl where At_Id=(select At_Id from Inv_Adjustment_Template_Dtl where Atd_Id=#{atdId})")
    List<InvAdjustmentTemplateDtl> getAllByAtdId(@Param("atdId") String atdId);
}

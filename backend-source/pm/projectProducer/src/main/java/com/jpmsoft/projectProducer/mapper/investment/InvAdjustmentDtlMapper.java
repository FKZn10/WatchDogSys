package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvAdjustmentDtl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface InvAdjustmentDtlMapper extends BaseMapper<InvAdjustmentDtl> {

    @Select("Select * from Inv_Adjustment_Dtl where Ia_Id=(select Ia_Id from Inv_Adjustment_Dtl where Ad_Id=#{adId})")
    List<InvAdjustmentDtl> getAllByAtdId(@Param("adId") String adId);
}

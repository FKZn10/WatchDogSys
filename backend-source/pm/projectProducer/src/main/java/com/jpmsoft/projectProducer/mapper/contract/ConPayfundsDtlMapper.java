package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayFundsDetails;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayfundsDtl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ConPayfundsDtlMapper extends BaseMapper<ConPayfundsDtl> {
    @Select("select * from Con_Payfunds_Dtl where PayDtlId = #{payId}")
    List<ConPayfundsDtl> getConPayfundsDtlListByPayId(@Param("payId")String id);
    @Select("select * from Con_Payfunds_Details where Pay_Id = #{payId}")
    List<ConPayFundsDetails> getConPayfundsDetailsListByPayId(@Param("payId")String id);
}

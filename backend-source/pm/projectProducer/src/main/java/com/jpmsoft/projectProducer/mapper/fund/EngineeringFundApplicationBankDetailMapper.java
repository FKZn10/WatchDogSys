package com.jpmsoft.projectProducer.mapper.fund;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.fund.EngineeringFundApplicationBankDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EngineeringFundApplicationBankDetailMapper extends BaseMapper<EngineeringFundApplicationBankDetail> {
    void addBatch(@Param("list")List<EngineeringFundApplicationBankDetail> details);

    List<EngineeringFundApplicationBankDetail> getDetailByEfaId(@Param("efaId")String efaId);
}

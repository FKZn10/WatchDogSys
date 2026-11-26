package com.jpmsoft.projectProducer.mapper.fund;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.fund.EngineeringFundApplicationFundNumberDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EngineeringFundApplicationFundNumberDetailMapper extends BaseMapper<EngineeringFundApplicationFundNumberDetail> {

    List<EngineeringFundApplicationFundNumberDetail> getDetailByEfaId(@Param("efaId")String efaId);

    void addBatch(@Param("list")List<EngineeringFundApplicationFundNumberDetail> details);

    List<EngineeringFundApplicationFundNumberDetail> selectByFnId(@Param("fnId")String id);
}

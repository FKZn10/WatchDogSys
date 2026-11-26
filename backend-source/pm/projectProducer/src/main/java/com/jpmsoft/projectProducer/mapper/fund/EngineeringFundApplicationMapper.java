package com.jpmsoft.projectProducer.mapper.fund;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.fund.EngineeringFundApplication;
import com.jpmsoft.projectProducer.pojo.vo.fund.EngineeringFundApplicationVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Mapper
public interface EngineeringFundApplicationMapper extends BaseMapper<EngineeringFundApplication> {
    List<EngineeringFundApplicationVo> getEngineeringFundApplicationList(@Param("params")Map<String, Object> query,@Param("page")IPage<EngineeringFundApplicationVo> page);

    EngineeringFundApplicationVo getEngineeringFundApplicationById(@Param("id")String id);

    List<EngineeringFundApplicationVo> getEngineeringFundApplicationByIds(@Param("ids") Collection<String> ids);

    String getDhBankAccount(@Param("pId")String pId,@Param("cId")String cId,@Param("type")String type,@Param("account")String account);

}

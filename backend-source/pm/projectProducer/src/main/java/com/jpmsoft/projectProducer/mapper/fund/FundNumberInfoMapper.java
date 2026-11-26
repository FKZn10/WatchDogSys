package com.jpmsoft.projectProducer.mapper.fund;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.fund.FundNumberInfo;
import com.jpmsoft.projectProducer.pojo.vo.fund.FundNumberInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface FundNumberInfoMapper extends BaseMapper<FundNumberInfo> {

    List<FundNumberInfoVo> getFundNumberInfoList(@Param("page") IPage<FundNumberInfoVo> page,@Param("params")Map<String, Object> query);

    FundNumberInfoVo selectFundNumberInfoById(@Param("id")String id);

    List<FundNumberInfoVo> selectByPId(@Param("pId")String pId);
}

package com.jpmsoft.projectProducer.mapper.fund;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.fund.PayAmountInit;
import com.jpmsoft.projectProducer.pojo.vo.fund.PayAmountInitVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PayAmountInitMapper extends BaseMapper<PayAmountInit> {
    List<PayAmountInit> getPayAmountInit(@Param("pId")String pId);

    PayAmountInit getByPId(@Param("pId")String pId);

    List<PayAmountInitVo> getPayAmountInitList(@Param("params")Map<String, Object> query,@Param("page")IPage<PayAmountInitVo> page);
}

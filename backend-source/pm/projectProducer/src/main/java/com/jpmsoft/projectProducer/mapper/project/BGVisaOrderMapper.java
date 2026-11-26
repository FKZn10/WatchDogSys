package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.BGVisaOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface BGVisaOrderMapper extends BaseMapper<BGVisaOrder> {
    BigDecimal getBGVisaOrderMoney(@Param("conId") String conId);

}

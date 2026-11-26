package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvInfrastructureMonth;
import com.jpmsoft.projectProducer.pojo.vo.investment.InvInfrastructureMonthVo;
import org.apache.ibatis.annotations.Param;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/5 9:06
 */
public interface InvInfrastructureMonthMapper extends BaseMapper<InvInfrastructureMonth> {

    InvInfrastructureMonthVo getInvInfrastructureMonthById(@Param("id") String id);
}

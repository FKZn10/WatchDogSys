package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvSettlementSecond;
import com.jpmsoft.projectProducer.pojo.vo.investment.InvSettlementSecondVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/21 9:59
 */
public interface InvSettlementSecondMapper extends BaseMapper<InvSettlementSecond> {

    List<InvSettlementSecondVo> getInvSettlementSecondList(@Param("page") IPage<InvSettlementSecondVo> page,
                                                           @Param("params") Map<String, Object> params);

    InvSettlementSecondVo getInvSettlementSecondById(@Param("id") String id);
}

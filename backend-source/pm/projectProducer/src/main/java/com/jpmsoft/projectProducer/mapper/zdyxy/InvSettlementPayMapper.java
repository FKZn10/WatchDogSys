package com.jpmsoft.projectProducer.mapper.zdyxy;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.InvSettlementPayModel;
import com.jpmsoft.projectProducer.pojo.entity.zdyxy.InvSettlementPay;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface InvSettlementPayMapper extends BaseMapper<InvSettlementPay> {
    List<InvSettlementPayModel> getList(@Param("params")Map<String, Object> query, @Param("page")IPage<InvSettlementPayModel> page);

    InvSettlementPayModel getDetail(@Param("id")String id);

    List<InvSettlementPayModel> getNotReturnedInvSettlementPay();
}
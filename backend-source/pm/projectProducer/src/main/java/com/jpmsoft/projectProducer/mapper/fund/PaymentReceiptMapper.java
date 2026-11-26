package com.jpmsoft.projectProducer.mapper.fund;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.projectProducer.pojo.entity.fund.PaymentReceipt;
import com.jpmsoft.projectProducer.pojo.vo.fund.PaymentReceiptVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PaymentReceiptMapper extends BaseMapper<PaymentReceipt> {
    PaymentReceiptVo selectPaymentReceiptById(@Param("id")String id);

    List<PaymentReceiptVo> getPaymentReceiptList(@Param("page") Page<PaymentReceiptVo> page, @Param("params") Map<String, Object> query);

    List<PaymentReceipt> getReceipt(@Param("pId")String pId);


    List<PaymentReceiptVo> getPaymentReceiptList2(@Param("page")IPage<PaymentReceiptVo> page,@Param("params")Map<String, Object> query);
}

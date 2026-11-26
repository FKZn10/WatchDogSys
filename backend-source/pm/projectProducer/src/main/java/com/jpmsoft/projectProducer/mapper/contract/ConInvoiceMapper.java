package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConInvoiceDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConInvoice;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/19 8:58
 */
public interface ConInvoiceMapper extends BaseMapper<ConInvoice> {

    List<ConInvoiceDTO> getInvoiceList(@Param("page") IPage<ConInvoiceDTO> page, @Param("params") Map<String, Object> params);


    ConInvoiceDTO getInvoiceById(@Param("id") String id);
}

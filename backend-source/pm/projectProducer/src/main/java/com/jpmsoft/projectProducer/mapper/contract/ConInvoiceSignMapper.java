package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConInvoiceSignDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConInvoiceSign;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 发票签收
 *
 * @Author: 李文涛 aaron
 * @date: 2024/3/28 15:14
 */
public interface ConInvoiceSignMapper extends BaseMapper<ConInvoiceSign> {

    List<ConInvoiceSignDTO> getConInvoiceSignList(@Param("page") IPage<ConInvoiceSignDTO> page, @Param("params") Map<String, Object> params);


    ConInvoiceSignDTO getConInvoiceSignById(@Param("id") String id);
}

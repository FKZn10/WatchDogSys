package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConInvoiceInfoDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConInvoiceInfo;

import java.util.List;

/**
 * 发票签收-发票信息
 * @Author: 李文涛 aaron
 * @date: 2024/3/28 15:32
 */
public interface IConInvoiceInfoService extends IService<ConInvoiceInfo> {

    void createOrUpdate(List<ConInvoiceInfoDTO> data, String isId);

    void deleteByIsId(String isId);
}

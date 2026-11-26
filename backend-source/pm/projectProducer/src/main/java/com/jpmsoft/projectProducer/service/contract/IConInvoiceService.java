package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConInvoiceDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConInvoice;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/19 8:58
 */
public interface IConInvoiceService extends IService<ConInvoice> {

    BaseResponse createOrUpdateInvoice(ConInvoiceDTO request);

    BaseResponse getInvoiceList(ConInvoiceDTO request, String uId);

    BaseResponse getInvoiceById(String id);
}

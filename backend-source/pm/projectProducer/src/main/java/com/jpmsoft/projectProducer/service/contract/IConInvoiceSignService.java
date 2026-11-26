package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConInvoiceSignDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConInvoiceSign;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/28 15:14
 */
public interface IConInvoiceSignService extends IService<ConInvoiceSign> {


    BaseResponse createOrUpdateConInvoiceSign(ConInvoiceSignDTO request);

    BaseResponse getConInvoiceSignList(ConInvoiceSignDTO request,String uid);

    BaseResponse getConInvoiceSignById(String id);

}

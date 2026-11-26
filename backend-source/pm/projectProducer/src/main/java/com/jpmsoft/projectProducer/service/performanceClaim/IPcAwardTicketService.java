package com.jpmsoft.projectProducer.service.performanceClaim;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Claim.PcAwardTicketRequest;
import com.jpmsoft.common.model.response.Claim.PcAwardTicketResponse;
import com.jpmsoft.projectProducer.pojo.entity.performanceClaim.PcAwardTicket;
import com.jpmsoft.common.model.request.Contract.BindAwardTicketRequest;

import java.util.List;

public interface IPcAwardTicketService extends IService<PcAwardTicket> {

    BaseResponse createOrUpdatePcAwardTicket(PcAwardTicketRequest request);

    BaseResponse getPcAwardTicketList(PcAwardTicketRequest request, String uId);

    BaseResponse getPcAwardTicketById(String atId);

    void refreshConPayId(List<String> atIds, String payId);

    List<PcAwardTicketResponse> getPcAwardTicketByConPay(String payId);

    void bindPay(BindAwardTicketRequest bindAwardTicketRequest);
}

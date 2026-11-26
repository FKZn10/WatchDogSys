package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.TrackAuditMonth;
import com.jpmsoft.projectProducer.pojo.entity.investment.TrackAuditMonthDtl;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;


/**
 * @author: shanzhi
 * @date: 2024/06/28 17:10
 */
public interface ITrackAuditMonthService extends IService<TrackAuditMonthDtl> {

    BaseResponse getTrackAuditMonthByConId(String conId, String month);

    BaseResponse getTrackAuditMonthListByPId(String pId);

    BaseResponse approvalTrackAuditMonth(String tamId);
}

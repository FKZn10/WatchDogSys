package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.entity.investment.FinalAccountingReportDetail;
import com.jpmsoft.projectProducer.pojo.vo.investment.FinalAccountingReportDetailVo;

import java.util.List;

public interface FinalAccountingReportDetailService extends IService<FinalAccountingReportDetail> {
    void saveReportDetail(List<FinalAccountingReportDetailVo> detailList, String id, int type);

    void deleteReportId(String id);

    List<FinalAccountingReportDetail> getReportDetailId(String id);
}

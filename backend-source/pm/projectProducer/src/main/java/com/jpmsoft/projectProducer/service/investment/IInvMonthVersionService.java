package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvMonthVersionRequest;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvMonthVersion;

import java.util.List;

public interface IInvMonthVersionService extends IService<InvMonthVersion> {
    /**
     * 根据id集合保存版本
     *
     * @param ids
     */
    void saveInvMonthVersionByIcmIds(List<String> ids);

    /**
     * 根据id获取版本信息
     *
     * @param id
     * @return
     */
    BaseResponse getInvMonthVersionById(String id);

    /**
     * 处理版本
     *
     * @param request
     * @return
     */
    BaseResponse createInvMonthVersion(InvMonthVersionRequest request);
}

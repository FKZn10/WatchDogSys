package com.jpmsoft.projectProducer.service.fund;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.fund.ClaimIncomingFundsRequest;
import com.jpmsoft.projectProducer.pojo.entity.fund.ClaimIncomingFunds;

/**
 * 来款认领 服务类接口
 *
 * @author tianhelin
 * @date 2025/11/17
 */
public interface IClaimIncomingFundsService extends IService<ClaimIncomingFunds> {
    /**
     * 创建或更新来款认领
     *
     * @param request 来款认领请求对象
     * @return 操作结果
     */
    BaseResponse createOrUpdateClaimIncomingFunds(ClaimIncomingFundsRequest request);

    /**
     * 来款认领同步到收款收据
     *
     * @param request
     * @return
     */
    BaseResponse syncToPaymentReceipt(ClaimIncomingFundsRequest request);

    /**
     * 获取来款认领分页数据
     *
     * @param request 来款认领请求对象
     * @return 分页数据
     */
    BaseResponse getClaimIncomingFundsPage(ClaimIncomingFundsRequest request);

    /**
     * 根据主键ID获取来款认领详情
     *
     * @param request
     * @return
     */
    BaseResponse getClaimIncomingFundsByPid(ClaimIncomingFundsRequest request);

    /**
     * 获取集团已到位资金累计金额
     *
     * @param pId 集团ID
     * @return
     */
    BaseResponse getGroupFundsCumulative(String pId);
}
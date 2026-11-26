package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBiddingPlanGm;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/12/26 13:50
 */
public interface ISecBiddingPlanGmService extends IService<SecBiddingPlanGm> {


    BaseResponse selectLastApprovedMoney(String secId);
}

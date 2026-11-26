package com.jpmsoft.projectProducer.service.xz;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.xz.FundApprovalCreateRequest;
import com.jpmsoft.common.model.request.xz.FundApprovalQueryRequest;
import com.jpmsoft.common.model.response.xz.FundApprovalVO;
import com.jpmsoft.projectProducer.config.PageResult;
import com.jpmsoft.projectProducer.pojo.entity.xz.FundApproval;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/8/29 15:52
 * @Description:
 * @Version: 1.0
 */
public interface FundApprovalService extends IService<FundApproval> {

    PageResult<FundApprovalVO> page(FundApprovalQueryRequest request);

    String create(FundApprovalCreateRequest request);

    String update(FundApprovalCreateRequest request);

    FundApprovalVO detail(FundApprovalCreateRequest request);

    void syncApproveState(FundApprovalCreateRequest request);

    void remove(FundApprovalCreateRequest request);
}

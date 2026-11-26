package com.jpmsoft.projectProducer.service.xz;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.xz.FundApplicationCreateRequest;
import com.jpmsoft.common.model.request.xz.FundApplicationQueryRequest;
import com.jpmsoft.common.model.response.xz.FundApplicationVO;
import com.jpmsoft.projectProducer.config.PageResult;
import com.jpmsoft.projectProducer.pojo.entity.xz.FundApplication;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/8/29 15:52
 * @Description:
 * @Version: 1.0
 */
public interface FundApplicationService extends IService<FundApplication> {

    PageResult<FundApplicationVO> page(FundApplicationQueryRequest request);

    String create(FundApplicationCreateRequest request);

    String update(FundApplicationCreateRequest request);

    FundApplicationVO detail(FundApplicationCreateRequest request);

    void syncApproveState(FundApplicationCreateRequest request);

    void remove(FundApplicationCreateRequest request);
}

package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.pay.ConFundNoDto;
import com.jpmsoft.common.model.response.pay.ConFundNoVo;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConFundNo;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/15 14:34
 */
public interface IConFundNoService extends IService<ConFundNo> {

    void createOrUpdateConFundNo(List<ConFundNoDto> datas, String payId);

    void delConFundNoByPayId(String payId);

    List<ConFundNoVo> getConFundByConIds(List<String> payIds);
}

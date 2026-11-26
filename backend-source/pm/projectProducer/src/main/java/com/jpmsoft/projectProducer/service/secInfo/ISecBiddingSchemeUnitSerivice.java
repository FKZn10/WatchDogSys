package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecBiddingSchemeUnitDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBiddingSchemeUnit;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/25 15:11
 */
public interface ISecBiddingSchemeUnitSerivice extends IService<SecBiddingSchemeUnit> {

    void createOrUpdateSecBiddingSchemeUnit(List<SecBiddingSchemeUnitDto> datas, String bsId);

    void delSecBiddingSchemeUnitByBsId(String bsId);
}

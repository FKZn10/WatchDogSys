package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.SecInfo.SecFundsDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecFunds;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/2/19 8:42
 */
public interface ISecFundsService extends IService<SecFunds> {

    void createOrUpdate(List<SecFundsDTO> data, String secId);

    List<SecFundsDTO> selectBySecId(String secId);

    void deleteBySecId(String secId);

    void deleteByBpId(String bpId);
}

package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecPurchaserDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecPurchaser;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/10/8 16:56
 */
public interface ISecPurchaserService extends IService<SecPurchaser> {

    void createOrUpdateSecPurchaser(List<SecPurchaserDTO> data, String sopId);

    void deleteSecPurchaserBySopId(String sopId);

    List<SecPurchaserDTO> selectBySopId(String sopId);
}

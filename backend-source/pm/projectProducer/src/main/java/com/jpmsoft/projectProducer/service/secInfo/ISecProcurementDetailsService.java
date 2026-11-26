package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.SecInfo.SecProcurementDetailsDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecProcurementDetails;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/17 13:49
 */
public interface ISecProcurementDetailsService extends IService<SecProcurementDetails> {

    void createOrUpdate(List<SecProcurementDetailsDTO> data, String secId);

    List<SecProcurementDetailsDTO> selectBySecId(String secId);

    void deleteBySecId(String secId);

    void deleteByBpId(String bpId);
}

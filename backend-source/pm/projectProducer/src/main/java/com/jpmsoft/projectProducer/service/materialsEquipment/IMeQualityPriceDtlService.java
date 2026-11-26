package com.jpmsoft.projectProducer.service.materialsEquipment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.dto.materialsEquipment.MeQualityPriceDtlDto;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeQualityPriceDtl;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 13:50
 */
public interface IMeQualityPriceDtlService extends IService<MeQualityPriceDtl> {

    void createOrUpdateMeQualityPriceDtl(List<MeQualityPriceDtlDto> datas,String qpId);

    void delMeQualityPriceDtlByQpId(String qpId);
}

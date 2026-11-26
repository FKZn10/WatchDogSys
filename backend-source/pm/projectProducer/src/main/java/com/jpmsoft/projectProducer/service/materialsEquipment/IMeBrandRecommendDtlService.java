package com.jpmsoft.projectProducer.service.materialsEquipment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.dto.materialsEquipment.MeBrandRecommendDtlDto;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeBrandRecommendDtl;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 9:46
 */
public interface IMeBrandRecommendDtlService extends IService<MeBrandRecommendDtl> {

    void createOrUpdateMeBrandRecommendDtl(List<MeBrandRecommendDtlDto> datas,String brId);

    void delMeBrandRecommendDtlByBrId(String brId);
}

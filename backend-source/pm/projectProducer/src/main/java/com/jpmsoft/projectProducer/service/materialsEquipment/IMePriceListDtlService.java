package com.jpmsoft.projectProducer.service.materialsEquipment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.dto.materialsEquipment.MePriceListDtlDto;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MePriceListDtl;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/4 14:33
 */
public interface IMePriceListDtlService extends IService<MePriceListDtl> {

    void createOrUpdateMePriceListDtl(List<MePriceListDtlDto> datas, String plId);

    void delMePriceListDtlByPlId(String plId);
}

package com.jpmsoft.projectProducer.service.materialsEquipment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.dto.materialsEquipment.MeBrandApprovalDtlDto;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeBrandApprovalDtl;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 10:28
 */
public interface IMeBrandApprovalDtlService extends IService<MeBrandApprovalDtl> {

    void createOrUpdateMeBrandApprovalDtl(List<MeBrandApprovalDtlDto> datas, String baId);

    void delMeBrandApprovalDtlByBaId(String baId);
}

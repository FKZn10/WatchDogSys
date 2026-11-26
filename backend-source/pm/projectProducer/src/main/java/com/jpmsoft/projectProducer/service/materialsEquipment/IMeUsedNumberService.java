package com.jpmsoft.projectProducer.service.materialsEquipment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.MeEnter.MeEnterApplyDtlRequest;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeUsedNumber;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/2/4 9:50
 */
public interface IMeUsedNumberService extends IService<MeUsedNumber> {

    void createOrUpdateMeUsedNumber(List<MeEnterApplyDtlRequest> applyDtls,String uaId);
}

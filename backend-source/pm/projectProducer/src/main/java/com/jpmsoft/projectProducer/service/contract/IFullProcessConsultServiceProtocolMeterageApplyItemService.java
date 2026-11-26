package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.entity.contract.FullProcessConsultServiceProtocolMeterageApplyItem;

import java.util.List;

public interface IFullProcessConsultServiceProtocolMeterageApplyItemService extends IService<FullProcessConsultServiceProtocolMeterageApplyItem> {
    List<FullProcessConsultServiceProtocolMeterageApplyItem> getAppIdList(String id);
}

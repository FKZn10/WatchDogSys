package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertShieldDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertShield;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/10 14:02
 */
public interface ISecExpertShieldService extends IService<SecExpertShield> {

    void createOrUpdateSecExpertShield(List<SecExpertShieldDto> datas, String srId);

    void delSecExpertShieldBySrId(String srId);
}

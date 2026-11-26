package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.PositionlvDto;
import com.jpmsoft.userProducer.pojo.entity.SysPositionlv;


public interface ISysPositionlvService extends IService<SysPositionlv> {

    BaseResponse GetPositionLVList(PositionlvDto positionlv);

    BaseResponse GetPositionLVSelect(String teId);

    BaseResponse CreateOrUpdatePositionLV(PositionlvDto positionlv);

    BaseResponse DeletePositionLV(PositionlvDto positionlv);
}

package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysMessageTempDto;
import com.jpmsoft.userProducer.pojo.entity.SysMessageTemp;
import com.jpmsoft.userProducer.pojo.vo.SysMessageTempVo;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/11 13:58
 */
public interface ISysMessageTempService extends IService<SysMessageTemp> {

    BaseResponse createOrUpdateSysMessageTemp(SysMessageTempDto request, String uId);

    BaseResponse getSysMessageTempList(SysMessageTempDto request);

    BaseResponse getSysMessageTempById(String id);

    SysMessageTempVo getSysMessageTempByCode(String code);

    BaseResponse delSysMessageTempById(String id);
}

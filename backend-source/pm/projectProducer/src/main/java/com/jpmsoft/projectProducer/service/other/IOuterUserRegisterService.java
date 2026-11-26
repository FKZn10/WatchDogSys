package com.jpmsoft.projectProducer.service.other;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.other.OuterUserRegisterDTO;
import com.jpmsoft.projectProducer.pojo.entity.other.OuterUserRegister;

public interface IOuterUserRegisterService extends IService<OuterUserRegister> {
    /**
     * 系统外部发起用户注册
     *
     * @param request
     * @return
     */
    BaseResponse registerUser(OuterUserRegisterDTO request);

    /**
     * 审批通过创建用户数据
     *
     * @param id
     * @return
     */
    BaseResponse approve2CreateUser(String id);
}

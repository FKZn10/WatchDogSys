package com.jpmsoft.projectProducer.service.qualitySafety;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SignSafetyResponDTO;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.SignSafetyRespon;

/**
 * 安全责任书签订
 */
public interface ISignSafetyResponService extends IService<SignSafetyRespon> {

    /**
     * 根据人员申报表发起安全责任书签订并发送待办
     *
     * @param rpId  人员申报表主键
     * @param token 登录token
     * @return 安全责任书主键
     */
    BaseResponse createSignSafetyResponByReported(String rpId, String token);

    /**
     * 获取安全责任书签订列表
     *
     * @param request 筛选条件
     * @return 安全责任书签订列表
     */
    BaseResponse getSignSafetyResponList(SignSafetyResponDTO request);

    /**
     * 获取安全责任书签订详情
     *
     * @param id 主键
     * @return 安全责任书签订详情
     */
    BaseResponse getSignSafetyResponById(String id);
}

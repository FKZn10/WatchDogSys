package com.jpmsoft.projectProducer.service.riskWarn;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.riskWarn.RiskPreventionMeasureDTO;
import com.jpmsoft.projectProducer.pojo.entity.riskWarn.RiskPreventionMeasure;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/29 11:31
 */
public interface IRiskPreventionMeasureService extends IService<RiskPreventionMeasure> {

    BaseResponse createOrUpdate(RiskPreventionMeasureDTO request);

    BaseResponse delete(String id);

    BaseResponse enable(String id,String status);

    /**
     * 获取整表树结构
     *
     * @return
     */
    BaseResponse getTree(LogUserInfoVo user);

    /**
     * 同级节点上下移动序号
     *
     * @param id     主键ID
     * @param action up上移 down 下移
     * @return
     */
    BaseResponse upOrDown(String id, String action, String teId);



}

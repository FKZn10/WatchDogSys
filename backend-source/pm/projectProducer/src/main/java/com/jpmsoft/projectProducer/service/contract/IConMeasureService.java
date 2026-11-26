package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConMeasureDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConMeasure;

/**
 * @author 李文涛 aaron
 * @date 2022/07/13 08:29
 */
public interface IConMeasureService extends IService<ConMeasure> {

    BaseResponse createOrUpdateConMeasure(ConMeasureDto request);

    BaseResponse createConMeasure(ConMeasureDto request);

    BaseResponse updateConMeasure(ConMeasureDto request);

    BaseResponse updateConMeasureOnly(ConMeasureDto request);

    BaseResponse getConMeasureList(ConMeasureDto request,String uId);

    BaseResponse getConMeasureById(String id);

    /**
     * 获取合同下最新审批通过的一条数据
     *
     * @param conId
     * @return
     */
    ConMeasure getLastApproveMeasureByConId(String conId);

    /**
     * 退回发起人时
     * 1.修改Con_Measure_Record表
     * Mear_Final_Amount=Mear_Declare_Amount
     * Mear_Final_Total=Mear_Declared_Total
     * <p>
     * 2.删除Con_Measure_Record_Check表相关记录
     *
     * @param id
     * @return
     */
    BaseResponse return2Initiator(String id);


    /**
     * 查询
     * 1.已申报的清单的总数
     * 2.已审核的总数
     * 3.申报金额统计
     * 4.审核金额统计
     */
    BaseResponse statisticsApplyAndAmt(String id,String conId);

    /**
     * 根据合同统计审批通过的本期审定产值
     * @param conId
     * @return
     */
    BaseResponse  statisticsAmtByConId(String conId);

    BaseResponse getConMeasureClassInfo(ConMeasureDto request);

    BaseResponse getConMeasureInfo(ConMeasureDto request);
}

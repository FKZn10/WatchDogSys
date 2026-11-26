package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.pay.PayFieldDtlDto;
import com.jpmsoft.common.model.response.pay.ConPayResponse;
import com.jpmsoft.common.model.response.pay.PayFieldDtlVo;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPay;
import com.jpmsoft.projectProducer.pojo.entity.contract.PayFieldDtl;

import java.util.List;
import java.util.Map;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/5 14:53
 */
@Deprecated
public interface IPayFieldDtlService extends IService<PayFieldDtl> {

    /**
     * ConPayRes转Map
     * Map内put key:Pay_Field_Dtl链表Sys_Pay_Field的Pf_Code值   value:Pay_Field_Dtl表Pf_Value值
     *
     * @param con
     * @return
     */
    @Deprecated
    Map<String, Object> getConPayAndFiledDtl(ConPayResponse con);

    List<PayFieldDtlVo> getPayFieldDtlByPayId(String payId);

    void createOrUpdatePayFieldDtl(List<PayFieldDtlDto> datas, ConPay pay);

    void delPayFieldDtlByPayId(String payId);

    /**
     * 执行字段的计算规则
     *
     * @param datas
     * @return
     */
    BaseResponse calculationValue(List<PayFieldDtlDto> datas);

    /**
     * 校验字段的校验规则
     *
     * @param datas
     * @return
     */
    BaseResponse validValue(List<PayFieldDtlDto> datas);
}

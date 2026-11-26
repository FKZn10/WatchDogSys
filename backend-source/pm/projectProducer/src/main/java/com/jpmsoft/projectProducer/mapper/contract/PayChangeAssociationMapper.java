package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.Contract.PayChangeAssociationResponse;
import com.jpmsoft.projectProducer.pojo.entity.contract.PayChangeAssociation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 支付变更关联
 */
@Mapper
public interface PayChangeAssociationMapper extends BaseMapper<PayChangeAssociation> {
    /**
     * 根据payId获取支付变更关联列表
     *
     * @param payId
     * @return
     */
    List<PayChangeAssociationResponse> getAssociationListByPayId(@Param("payId") String payId);


    @Select("select pca.* from Pay_Change_Association pca left join con_pay pay on pay.pay_id=pca.pay_id where pay.con_id = #{conId} and pay.pay_state!=3")
    List<PayChangeAssociation> selectCountByConId(@Param("conId") String conId);
}

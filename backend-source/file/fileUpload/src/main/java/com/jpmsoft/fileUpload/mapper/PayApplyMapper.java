package com.jpmsoft.fileUpload.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * 付款申请书打印 - 直查数据库补充数据源
 * <p>
 * 用于获取 projectProducer 现有打印接口未返回的字段(如合同签订日期),
 * 本 mapper 为新增, 不影响任何原逻辑.
 *
 * @author 付款打印
 */
@Mapper
public interface PayApplyMapper {

    /**
     * 按支付ID查询合同签订日期(Con_Contract.Con_Date)
     * 链路: Con_Pay(Con_Id) -> Con_Contract(Con_Id)
     *
     * @param payId 支付ID
     * @return 合同签订日期
     */
    @Select("select c.Con_Date from Con_Pay p " +
            "left join Con_Contract c on c.Con_Id = p.Con_Id " +
            "where p.Pay_Id = #{payId}")
    Date getConDateByPayId(@Param("payId") String payId);
}
package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecProcurementDetails;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/17 13:49
 */
public interface SecProcurementDetailsMapper extends BaseMapper<SecProcurementDetails> {


    @Delete("delete from Sec_Procurement_Details where Sec_Id in (Select Sec_Id from Sec_Info where Bp_Id=#{bpId})")
    Integer deleteByBpId(@Param("bpId") String bpId);
}

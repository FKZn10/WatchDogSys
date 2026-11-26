package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.QuotationRequest;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * 标段询价调研明细数据访问。
 */
public interface QuotationRequestMapper extends BaseMapper<QuotationRequest> {

    /**
     * 删除招标计划下所有标段的询价调研明细。
     *
     * @param bpId 招标计划ID
     * @return 删除条数
     */
    @Delete("delete from quotation_request where Sec_Id in (select Sec_Id from Sec_Info where Bp_Id=#{bpId})")
    int deleteByBpId(@Param("bpId") String bpId);
}

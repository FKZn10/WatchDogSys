package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecInquiryRecordDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInquiryRecord;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecInquiryRecordVo;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecQuotationUnitVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/22 15:48
 */
public interface SecInquiryRecordMapper extends BaseMapper<SecInquiryRecord> {

    SecInquiryRecordVo getSecInquiryRecordById(@Param("id") String id);

    /**
     * 标段ID查询最新审批通过的询价记录
     *
     * @param secId
     * @return
     */
    SecInquiryRecordVo getSecInquiryRecordBySecId(@Param("secId") String secId);

    List<SecInquiryRecordVo> getSecInquiryRecordList(@Param("page") IPage<SecInquiryRecordVo> page, @Param("params") Map<String, Object> params);


    List<SecInquiryRecordVo> getSecInquiryRecordBySecIds(@Param("secIds") List<String> secIds);

    List<SecQuotationUnitVo> getSecQuotationUnitBySirId(@Param("sirId") String sirId);

    List<Map<String, Object>> getAllValues(@Param("authSql") String authSql, @Param("selectClause") String selectClause,@Param("params") Map<String, Object> query);

    List<SecInquiryRecordVo> getSecInquiryRecordListClass(@Param("authSql") String authSql, @Param("page") IPage<SecInquiryRecordDto> page, @Param("params") Map<String, Object> query);
}

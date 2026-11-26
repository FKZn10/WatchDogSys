package com.jpmsoft.projectProducer.mapper.subcontract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Subcontract.SubcontractorRecordResponse;
import com.jpmsoft.projectProducer.pojo.entity.subcontract.SubcontractorRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SubcontractorRecordMapper extends BaseMapper<SubcontractorRecord> {

    List<SubcontractorRecordResponse> getSubcontractorRecordList(@Param("page") IPage<SubcontractorRecordResponse> page, @Param("params") Map<String, Object> params);


    SubcontractorRecordResponse getSubcontractorRecordBySrId(@Param("srId") String srId);
}

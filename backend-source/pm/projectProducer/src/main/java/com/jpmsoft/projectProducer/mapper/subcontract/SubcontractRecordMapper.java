package com.jpmsoft.projectProducer.mapper.subcontract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Subcontract.SubcontractRecordResponse;
import com.jpmsoft.projectProducer.pojo.entity.subcontract.SubcontractRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SubcontractRecordMapper extends BaseMapper<SubcontractRecord> {

    List<SubcontractRecordResponse> getSubcontractRecordList(@Param("page") IPage<SubcontractRecordResponse> page, @Param("params") Map<String, Object> params);

    SubcontractRecordResponse getSubcontractRecordByScId(@Param("scId") String scId);
}

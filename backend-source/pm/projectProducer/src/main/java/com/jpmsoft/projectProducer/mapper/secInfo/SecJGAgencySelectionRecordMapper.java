package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGAgencySelectionRecordModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecJGAgencySelectionRecord;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecJGAgencyExpertExtractVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SecJGAgencySelectionRecordMapper extends BaseMapper<SecJGAgencySelectionRecord> {


    List<SecJGAgencySelectionRecordModel> getSecSelectionRecordList(@Param("page") IPage<SecJGAgencySelectionRecordModel> page, @Param("params") Map<String, Object> params);


    SecJGAgencySelectionRecordModel getSecSelectionRecordById(@Param("srId") String srId);


    SecJGAgencyExpertExtractVo getSecExpertExtractById(@Param("srId") String srId);



}

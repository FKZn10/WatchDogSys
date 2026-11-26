package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGAgencySelectionRecordDtlModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecJGAgencySelectionRecordDtl;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SecJGAgencySelectionRecordDtlMapper extends BaseMapper<SecJGAgencySelectionRecordDtl> {


    List<SecJGAgencySelectionRecordDtlModel> getSelectionRecordDtlList(@Param("params") Map<String, Object> params);


}

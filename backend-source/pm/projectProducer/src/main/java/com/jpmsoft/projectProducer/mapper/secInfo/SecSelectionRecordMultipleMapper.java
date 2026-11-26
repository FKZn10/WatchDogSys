package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSelectionRecordMultipleModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecSelectionRecordMultiple;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecExpertExtractMultipleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SecSelectionRecordMultipleMapper extends BaseMapper<SecSelectionRecordMultiple> {


    List<SecSelectionRecordMultipleModel> getSecSelectionRecordList(@Param("page") IPage<SecSelectionRecordMultipleModel> page, @Param("params") Map<String, Object> params);


    SecSelectionRecordMultiple getSecSelectionRecordById(@Param("id") String id);


    SecExpertExtractMultipleVo getSecExpertExtractById(@Param("id") String id);

    SecSelectionRecordMultiple getLatestData(@Param("secId") String secId);

}

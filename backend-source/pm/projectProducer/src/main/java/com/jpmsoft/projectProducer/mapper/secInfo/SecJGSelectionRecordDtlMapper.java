package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGSelectionRecordDtlModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecJGSelectionRecordDtl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface SecJGSelectionRecordDtlMapper extends BaseMapper<SecJGSelectionRecordDtl> {

    List<SecJGSelectionRecordDtlModel> getSelectionRecordDtlList(@Param("params") Map<String, Object> params);

    @Select("select count(1) from Sec_JG_Selection_Record_Dtl srd " +
            " left join Sec_JG_Selection_Record sr on sr.id=srd.Sr_Id" +
            " where sr.State=7 and srd.Ex_Id=#{exId}")
    Integer getJGSelectionRecordDtlCount(@Param("exId") String exId);

}

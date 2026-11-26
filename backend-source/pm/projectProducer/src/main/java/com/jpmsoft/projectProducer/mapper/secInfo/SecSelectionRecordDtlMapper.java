package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSelectionRecordDtlModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecSelectionRecordDtl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;


public interface SecSelectionRecordDtlMapper extends BaseMapper<SecSelectionRecordDtl> {


    @Select("select count(1) from Sec_Selection_Record_Dtl srd " +
            " left join Sec_Selection_Record sr on sr.Sr_Id=srd.Sr_Id" +
            " where sr.State=7 and srd.Ex_Id=#{exId}")
    Integer getSelectionRecordDtlCount(@Param("exId") String exId);

    @Select("select count(1) from Sec_Selection_Record_Dtl where Ex_Id=#{exId}")
    Integer getExpertCount(@Param("exId") String exId);

    @Select("select count(*) from Sec_Selection_Record_Dtl mn left join Sec_Selection_Record sr " +
            " on sr.Sr_Id=mn.Sr_Id where mn.Ex_Id=#{exId} and sr.Sr_Start<#{time} ")
    Integer expertSelectionWithTime(@Param("exId") String exId, @Param("time") String time);


    @Update("update Sec_Selection_Record_Dtl set Srd_Attend=#{attend} where Ex_Id=#{exId} and Sr_Id=#{srId}")
    Integer updateAttend(@Param("exId") String exId, @Param("srId") String srId, @Param("attend") Integer attend);

    @Update("update Sec_Selection_Record_Dtl set Srd_Attend=#{attend},Srd_Vacation=#{vacation} where Ex_Id=#{exId} and Sr_Id=#{srId}")
    Integer updateVacation(@Param("exId") String exId, @Param("srId") String srId, @Param("attend") Integer attend, @Param("vacation") Integer vacation);


    List<SecSelectionRecordDtlModel> getSelectionRecordDtlList(@Param("params") Map<String,Object> params);

    List<SecSelectionRecordDtlModel> getSelectionRecordDtlListByExId(@Param("page") Page<SecSelectionRecordDtlModel> page, @Param("params") Map<String, Object> query);

    List<SecSelectionRecordDtl> getListByExIds(@Param("exIds") List<String> exIds);
}

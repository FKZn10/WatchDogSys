package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConEcFeeRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ConEcFeeRecordMapper extends BaseMapper<ConEcFeeRecord> {

    @Update("update Con_Measure_Record set Mear_Final_Amount=#{amount} where id=#{id}")
    Integer updateFinalAmount(@Param("id") String id, @Param("amount") Float amount);

    @Update("update Con_Measure_Record set Mear_Final_Amount=null,Mear_Final_Total=null,State=0 where Mea_Id=#{meaId}")
    Integer return2InitiatorRestoreAmount(@Param("meaId") String meaId);

    @Select("select id from Con_Measure_Record where Mea_Id=#{meaId}")
    List<String> getAllIdByMeaId(@Param("meaId") String meaId);

}

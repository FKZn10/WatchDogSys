package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConMearDeclareDto;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConMearUpperAmtDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConMeasureRecord;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConMeasureRecordVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author 李文涛 aaron
 * @date 2022/07/13 08:29
 */
public interface ConMeasureRecordMapper extends BaseMapper<ConMeasureRecord> {

    @Update("update Con_Measure_Record set Mear_Final_Amount=#{amount} where id=#{id}")
    Integer updateFinalAmount(@Param("id") String id, @Param("amount") Float amount);


    ConMearUpperAmtDto getConMearUpperAmt(@Param("conId") String conId, @Param("boqdCode") String boqdCode);

    List<ConMearDeclareDto> getConMearDeclareByConId(@Param("conId") String conId);


    List<ConMeasureRecordVo> getAllConMearByConIdAndCode(@Param("conId") String conId, @Param("boqdCode") String boqdCode);


    @Update("update Con_Measure_Record set Mear_Final_Amount=null,Mear_Final_Total=null,State=0 where Mea_Id=#{meaId}")
    Integer return2InitiatorRestoreAmount(@Param("meaId") String meaId);

    @Select("select id from Con_Measure_Record where Mea_Id=#{meaId}")
    List<String> getAllIdByMeaId(@Param("meaId") String meaId);


    List<ConMeasureRecordVo> getApprovedData(List<String> boqdIds);
}





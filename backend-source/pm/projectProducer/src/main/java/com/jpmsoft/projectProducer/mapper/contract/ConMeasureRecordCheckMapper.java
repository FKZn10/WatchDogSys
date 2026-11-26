package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConMeasureRecordCheck;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConMeasureRecordCheckVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 李文涛 aaron
 * @date 2022/07/13 08:29
 */
public interface ConMeasureRecordCheckMapper extends BaseMapper<ConMeasureRecordCheck> {

    ConMeasureRecordCheckVo getConMeasureRecordCheckById(@Param("id") String id);

    List<ConMeasureRecordCheckVo> getConMeasureRecordCheckList(@Param("page") IPage<ConMeasureRecordCheckVo> page,@Param("params") Map<String, Object> params);

    @Delete("delete from Con_Measure_Record_Check where Mear_Id in (select Mear_Id from Con_Measure_Record where Mea_Id=#{meaId})")
    Integer deleteConMeasureRecordCheckByMeaId(@Param("meaId") String meaId);
}





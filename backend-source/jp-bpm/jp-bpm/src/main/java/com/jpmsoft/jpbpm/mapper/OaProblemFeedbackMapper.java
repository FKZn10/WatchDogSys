package com.jpmsoft.jpbpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.jpbpm.pojo.entity.OaProblemFeedback;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OaProblemFeedbackMapper extends BaseMapper<OaProblemFeedback> {


    @Update("update OA_Problem_Dtl set Pmd_Status=#{status} where Pmd_Id=#{pmdId}")
    Integer sncUpdateOAProblemDtlStatus(@Param("status") Integer status,@Param("pmdId") String pmdId);
}

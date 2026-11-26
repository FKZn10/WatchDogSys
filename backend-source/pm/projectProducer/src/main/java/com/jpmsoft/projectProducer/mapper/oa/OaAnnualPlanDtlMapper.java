package com.jpmsoft.projectProducer.mapper.oa;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAnnualPlanDtl;
import com.jpmsoft.projectProducer.pojo.vo.oa.OaAnnualPlanDtlVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/12/2 15:04
 */
public interface OaAnnualPlanDtlMapper extends BaseMapper<OaAnnualPlanDtl> {

    @Select("select MAX(Apd_Sort) from Oa_Annual_Plan_Dtl where Ap_Id=#{apId}")
    Integer getCurrentSort(@Param("apId") String apId);


    List<OaAnnualPlanDtlVo> getOaAnnualPlanDtlList(
            //@Param("page") IPage<OaAnnualPlanDtlVo> page,
            @Param("params") Map<String, Object> params);


    List<OaAnnualPlanDtl> getOaAnnualPlanDtlByYearAndPids(@Param("pIds") List<String> pIds, @Param("year") Integer year);
}

package com.jpmsoft.projectProducer.mapper.oa;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaProblemDtlModel;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaProblemDtl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 项目问题详情（清单）
 */

public interface OaProblemDtlMapper extends BaseMapper<OaProblemDtl> {

    List<OaProblemDtlModel> getOaProblemDtlList(@Param("page") IPage<OaProblemDtlModel> page, @Param("params") Map<String, Object> params);

    @Update("update OA_Problem_Dtl set Pmd_Status=#{pmdStatus} where Pmd_Id=#{pmdId}")
    Integer refreshProblemDtlStatus(@Param("pmdId") String pmdId, @Param("pmdStatus") int pmdStatus);
}

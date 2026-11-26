package com.jpmsoft.projectProducer.mapper.oa;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.ReportedPersonnelDtl;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ReportedPersonnelDtlMapper extends BaseMapper<ReportedPersonnelDtl> {

    /**
     * 获取项目现有的项目岗位名称
     *
     * @param pId
     * @return
     */
    List<String> getProjPositionPName(@Param("pId") String pId, @Param("uIds") List<String> uIds);
}

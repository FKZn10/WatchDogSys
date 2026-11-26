package com.jpmsoft.projectProducer.mapper.constructionProcedure;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCplTemplateDtl;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ProjectCplTemplateDtlMapper extends BaseMapper<ProjectCplTemplateDtl> {
    List<ProjectCplTemplateDtl> getProCplTemplateDtlByCpltId(@Param("cpltId") String cpltId);


    Integer updateLibraryFileTypeBath(@Param("cplIds") List<String> cplIds,@Param("type") String type);
}

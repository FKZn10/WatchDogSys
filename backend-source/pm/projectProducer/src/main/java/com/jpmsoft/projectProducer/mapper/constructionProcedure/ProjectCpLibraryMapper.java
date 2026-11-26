package com.jpmsoft.projectProducer.mapper.constructionProcedure;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.request.proCp.ProjectCpLibraryRequset;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpLibrary;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface ProjectCpLibraryMapper extends BaseMapper<ProjectCpLibrary> {

    @Select("select * from  Project_Cp_Library where Cpl_Parent_Id=#{parentId} and Cpl_Sort>#{cplSort}")
    List<ProjectCpLibrary> selectSameLevelBySort(@Param("parentId") String parentId, @Param("cplSort") Integer cplSort);


    Integer updateLibraryFileTypeBath(@Param("cplIds") List<String> cplIds,@Param("type") String type);

    List<ProjectCpLibrary> getList(@Param("params") ProjectCpLibraryRequset request);
}

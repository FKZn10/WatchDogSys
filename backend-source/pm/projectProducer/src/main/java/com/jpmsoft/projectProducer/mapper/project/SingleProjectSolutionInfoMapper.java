package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.SingleProjectSolutionInfo;
import com.jpmsoft.projectProducer.pojo.vo.project.SingleProjectSolutionInfoVo;

import java.util.List;

/**
* @author Dell
* @description 针对表【single_project_solution_info】的数据库操作Mapper
* @createDate 2025-09-05 08:16:46
*/
public interface SingleProjectSolutionInfoMapper extends BaseMapper<SingleProjectSolutionInfo> {
    List<SingleProjectSolutionInfoVo> selectSingleProjectSolutionInfoVo();
}





package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectSolutionInfoModel;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectSolutionInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PreProjectSolutionInfoMapper extends BaseMapper<PreProjectSolutionInfo> {
    List<PreProjectSolutionInfoModel> getPreProjectSolutionInfoList(@Param("params")Map<String, Object> query,@Param("page") IPage<PreProjectSolutionInfoModel> page);

    PreProjectSolutionInfoModel getPreProjectSolutionInfoById(@Param("id")String id);

    List<PreProjectSolutionInfo> selectLists();
}

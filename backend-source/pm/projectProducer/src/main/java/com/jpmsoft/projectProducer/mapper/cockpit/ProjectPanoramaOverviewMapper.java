package com.jpmsoft.projectProducer.mapper.cockpit;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.SmartSite.ProjectPanoramaOverviewModel;
import com.jpmsoft.projectProducer.pojo.entity.cockpit.ProjectPanoramaOverview;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ProjectPanoramaOverviewMapper extends BaseMapper<ProjectPanoramaOverview> {

    List<ProjectPanoramaOverviewModel> getProjectPanoramaOverviewList(@Param("page") IPage<ProjectPanoramaOverviewModel> page, @Param("params") Map<String, Object> params);

    List<ProjectPanoramaOverviewModel> getProjectPanoramaList(@Param("teId")  String teId);


    ProjectPanoramaOverviewModel getProjectPanoramaOverviewById(@Param("ppoId") String ppoId);

    List<ProjectPanoramaOverviewModel> getProjectPanoramaOverview(@Param("params") ProjectPanoramaOverviewModel request);
}

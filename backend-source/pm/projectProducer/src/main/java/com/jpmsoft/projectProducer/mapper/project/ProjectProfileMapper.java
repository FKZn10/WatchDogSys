package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Project.ProjectProfileModel;
import com.jpmsoft.projectProducer.pojo.entity.project.ProjectProfile;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author jjz
 */
public interface ProjectProfileMapper extends BaseMapper<ProjectProfile> {

    List<ProjectProfileModel> getProjectProfileList(@Param("page") IPage<ProjectProfileModel> page,
                                                    @Param("params") Map<String, Object> params);

    List<ProjectProfileModel> getProjectProfileListByPid(@Param("pId") String pId);

    ProjectProfileModel getProjectProfileById(@Param("id") String id);
}

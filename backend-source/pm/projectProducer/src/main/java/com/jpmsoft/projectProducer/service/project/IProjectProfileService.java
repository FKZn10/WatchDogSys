package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProjectProfileModel;
import com.jpmsoft.projectProducer.pojo.entity.project.ProjectProfile;

/**
 * @author jjz
 *
 */
public interface IProjectProfileService extends IService<ProjectProfile> {

    BaseResponse getProjectProfileList(ProjectProfileModel request);

    BaseResponse getProjectProfileListByPid(String pId);

    BaseResponse getProjectProfileById(String id);
}

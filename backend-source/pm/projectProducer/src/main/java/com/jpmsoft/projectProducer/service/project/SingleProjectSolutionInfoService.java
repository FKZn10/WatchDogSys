package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.entity.project.SingleProjectSolutionInfo;
import com.jpmsoft.projectProducer.pojo.request.project.SingleProjectSolutionInfoRequest;

/**
* @author Dell
* @description 针对表【single_project_solution_info】的数据库操作Service
* @createDate 2025-09-05 08:16:46
*/
public interface SingleProjectSolutionInfoService extends IService<SingleProjectSolutionInfo> {

    BaseResponse getSingleProjectSolutionInfo(SingleProjectSolutionInfoRequest request);
}

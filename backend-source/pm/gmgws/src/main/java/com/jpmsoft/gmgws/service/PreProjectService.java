package com.jpmsoft.gmgws.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.gmgws.pojo.entity.PaymentStatistics;
import com.jpmsoft.gmgws.pojo.entity.PreProject;

import java.util.List;

public interface PreProjectService extends IService<PreProject> {

    List<PreProject> getProjectMame(List<String> name );
}

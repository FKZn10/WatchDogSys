package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.entity.project.ProWnDetail;

public interface IProWnDetailService extends IService<ProWnDetail> {


    BaseResponse getProWnDetailById(String pwId);

    BaseResponse getProWnDetailByPid(String pwPid);
}
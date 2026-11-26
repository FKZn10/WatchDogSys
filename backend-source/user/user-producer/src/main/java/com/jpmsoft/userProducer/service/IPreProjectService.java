package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.userProducer.pojo.entity.PreProject;

import java.util.List;

public interface IPreProjectService extends IService<PreProject> {

    List<PreProject> getProjectList(List<String> idlist);


    String  getDidByPid(String pId);
}

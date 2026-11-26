package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.fileUpload.pojo.entity.SecProject;

import java.util.List;

public interface ISecProjectService extends IService<SecProject> {

    List<String> getSecIdsByPid(String pId);
}

package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.jpbpm.pojo.entity.PreProject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PreProjectService extends IService<PreProject> {
    List<PreProject> getPreProjectList(List<String> pIds, String customerId);

    String getDidByPid(String pId);
}

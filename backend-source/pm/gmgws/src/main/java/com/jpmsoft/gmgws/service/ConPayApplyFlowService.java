package com.jpmsoft.gmgws.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.gmgws.pojo.entity.ConPayApplyFlow;

import java.util.List;

public interface ConPayApplyFlowService   extends IService<ConPayApplyFlow> {
    List<ConPayApplyFlow> getProjectDataList(List<String> collect);

    List<ConPayApplyFlow> getProjectData(String conId);

    List<ConPayApplyFlow> getProjectPidList(List<String> collect);
}

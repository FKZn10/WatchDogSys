package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.SecInfo.SecBiddingProjectRequest;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBiddingProject;

import java.util.List;
import java.util.Map;

public interface ISecBiddingProjectService extends IService<SecBiddingProject> {


    void createOrUpdateSecBiddingProject(List<String> pIds, List<SecBiddingProjectRequest> secBiddingProjectList , String bpId, String createUid);

    /**
     * 判断项目是否已存在未结束的招标计划
     *
     * @param bpId
     * @return
     */
    Boolean haveSBPCount(List<String> pIds, String bpId);


    List<String> getSecBiddingProjectPids(String bpId);

    Map<String,Object> getSecBiddingProject(String bpId);

    List<SecBiddingProject> getSecBiddingPidData(String projectId);
}

package com.jpmsoft.projectProducer.service.leaderBoard;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.yw.GroupProjectIssueModel;
import com.jpmsoft.projectProducer.pojo.entity.yw.GroupProjectIssue;

public interface GroupProjectIssueService extends IService<GroupProjectIssue> {
    BaseResponse saveOrUpdateGroupProjectIssue(GroupProjectIssueModel request);

    BaseResponse getGroupProjectIssueList(GroupProjectIssueModel request, String token);

    BaseResponse getGroupProjectIssueDetail(String id);

    BaseResponse approvalData(GroupProjectIssueModel request);
}
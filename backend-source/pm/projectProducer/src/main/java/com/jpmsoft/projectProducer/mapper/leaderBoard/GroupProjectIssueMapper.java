package com.jpmsoft.projectProducer.mapper.leaderBoard;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.yw.GroupProjectIssueModel;
import com.jpmsoft.projectProducer.pojo.entity.yw.GroupProjectIssue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface GroupProjectIssueMapper extends BaseMapper<GroupProjectIssue> {
    List<GroupProjectIssueModel> getGroupProjectIssueList(@Param("params")Map<String, Object> query,
                                                          @Param("page")IPage<GroupProjectIssueModel> page);

    GroupProjectIssueModel getGroupProjectIssueById(@Param("id")String id);
}
package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectApprovalModel;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectApproval;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PreProjectApprovalMapper extends BaseMapper<PreProjectApproval> {
    List<PreProjectApprovalModel> getPreProjectApprovalModelList(@Param("page")Map<String, Object> query,
                                                                 @Param("params")IPage<PreProjectApprovalModel> page);

    PreProjectApprovalModel getPreProjectApprovalById(@Param("id")String id);
}

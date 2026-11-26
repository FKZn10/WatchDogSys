package com.jpmsoft.projectProducer.mapper.design;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.jpmsoft.common.model.request.Design.DesignResultsIssuedRequest;
import com.jpmsoft.common.model.request.ganttPlan.GanttMilestoneStatisticsRequest;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignResultsIssuedModel;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignResultsIssued;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DesignResultsIssuedMapper extends BaseMapper<DesignResultsIssued> {
    List<DesignResultsIssuedModel> getDesignResultsIssuedList(
            @Param("page") Page<DesignResultsIssuedRequest> page,
            @Param("params") DesignResultsIssuedRequest params,
            @Param("authSql") String authSql,
            @Param("ids") List<String> ids);

    List<Map<String, Object>> getAllValues(
            @Param("selectClause") String selectClause,
            @Param("params") DesignResultsIssuedRequest params,
            @Param("authSql") String authSql,
            @Param("ids") List<String> ids
    );

    DesignResultsIssuedModel getDesignResultsIssuedById(
            @Param("id") String id,
            @Param("authSql") String authsql);

    List<Map<String, Object>> getAllValuesCJTZ(
            @Param("selectClause") String selectClause,
            @Param("params") DesignResultsIssuedRequest params,
            @Param("authSql") String authSql,
            @Param("ids") List<String> ids
    );


    List<DesignResultsIssuedModel> getDesignResultsIssuedListCJTZ(
            @Param("page") Page<DesignResultsIssuedRequest> page,
            @Param("params") DesignResultsIssuedRequest params,
            @Param("authSql") String authSql,
            @Param("ids") List<String> ids);


    List<DesignResultsIssuedModel> getDownloadLogListForDesign(
            @Param("page") IPage<DesignResultsIssuedModel> page,
            @Param("params") DesignResultsIssuedModel params);
}

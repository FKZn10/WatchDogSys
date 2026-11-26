package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotProjectModel;
import com.jpmsoft.projectProducer.pojo.entity.project.ProvPilotProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 省试点项目 Mapper
 *
 * @author whjp
 * @since 2026-09-16
 */
@Mapper
public interface ProvPilotProjectMapper extends BaseMapper<ProvPilotProject> {

    /**
     * 分页列表查询
     *
     * @param params QueryMap 包装后的查询参数
     * @param page   分页参数
     */
    List<ProvPilotProjectModel> getList(@Param("params") Map<String, Object> params,
                                        @Param("page") IPage<ProvPilotProjectModel> page);

    /**
     * 详情查询
     */
    ProvPilotProjectModel getById(@Param("id") String id);

    /**
     * 查询指定前缀（SX-年度-批次-）下已存在的最大项目编码，用于续号
     *
     * @param prefix 编码前缀，如 SX-2024-01-
     */
    String selectMaxProjectCode(@Param("prefix") String prefix);
}
package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotEnterpriseModel;
import com.jpmsoft.projectProducer.pojo.entity.project.ProvPilotEnterprise;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 省试点企业 Mapper
 *
 * @author whjp
 * @since 2026-09-17
 */
@Mapper
public interface ProvPilotEnterpriseMapper extends BaseMapper<ProvPilotEnterprise> {

    /**
     * 分页列表查询
     */
    List<ProvPilotEnterpriseModel> getList(@Param("params") Map<String, Object> params,
                                           @Param("page") IPage<ProvPilotEnterpriseModel> page);

    /**
     * 详情查询
     */
    ProvPilotEnterpriseModel getById(@Param("id") String id);

    /**
     * 查询指定前缀（SQ+年度+批次）下已存在的最大企业编号，用于续号
     */
    String selectMaxEnterpriseCode(@Param("prefix") String prefix);

    /**
     * 按租户统计试点企业全部记录，空表返回零。
     *
     * @param teId 已认证的租户ID，未启用租户时为null
     * @return 企业记录数量
     */
    Long countEnterpriseStatistics(@Param("teId") String teId);
}
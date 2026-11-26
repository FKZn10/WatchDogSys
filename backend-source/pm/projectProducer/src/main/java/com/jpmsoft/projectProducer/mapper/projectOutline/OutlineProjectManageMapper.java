package com.jpmsoft.projectProducer.mapper.projectOutline;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.request.projectOutline.OutlineProjectManageRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjectManageResponse;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineProjectManage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author tianhelin
 * @date 2025/6/10
 */
@Mapper
public interface OutlineProjectManageMapper extends BaseMapper<OutlineProjectManage> {
    /**
     * 获取项目管理大纲管理分页
     *
     * @param page
     * @param query
     * @return
     */
    List<OutlineProjectManageResponse> getOutlineProjectManagePage(@Param("page") Page<OutlineProjectManageRequest> page, @Param("params") Map<String, Object> query);

    /**
     * 获取项目管理详情
     *
     * @param id
     * @return
     */
    OutlineProjectManageResponse getOutlineProjectManageById(String id);

    /**
     * 获取项目对应的项目管理大纲
     *
     * @param pId
     * @param opmCode
     * @return
     */
    OutlineProjectManageResponse getOutlineProjectManageByPid(@Param("pId") String pId, @Param("opmCode") String opmCode);

    /**
     * 获取项目对应的项目管理大纲列表
     *
     * @param pId
     * @return
     */
    List<OutlineProjectManageResponse> getOutlineProjectManageByPidList(@Param("pId") String pId);

    List<String> getTNamesByTCodes(@Param("tCodes") Set<String> tCodes);

    /**
     * 获取大纲台账统计
     *
     * @return
     */
    Map<String, Integer> getOutlineProjectLedgerCount();

    /**
     * 获取大纲台账分页
     *
     * @param page
     * @param request
     * @return
     */
    List<OutlineProjectManageResponse> getOutlineProjectLedgerPage(@Param("page") Page<OutlineProjectManageResponse> page, @Param("params") OutlineProjectManageRequest request);
}

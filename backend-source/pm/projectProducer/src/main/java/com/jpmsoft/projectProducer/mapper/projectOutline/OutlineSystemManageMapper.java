package com.jpmsoft.projectProducer.mapper.projectOutline;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.request.projectOutline.OutlineSystemManageRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineSystemManageResponse;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineSystemManage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author tianhelin
 * @date 2025/6/10
 */
@Mapper
public interface OutlineSystemManageMapper extends BaseMapper<OutlineSystemManage> {
    /**
     * 获取体系模板分页
     *
     * @param page
     * @param query
     * @return
     */
    List<OutlineSystemManageResponse> getOutlineSystemManagePage(@Param("page") Page<OutlineSystemManageRequest> page, @Param("params") Map<String, Object> query);

    /**
     * 获取体系模板详情
     *
     * @param id
     * @return
     */
    OutlineSystemManageResponse getOutlineSystemManageById(String id);
}

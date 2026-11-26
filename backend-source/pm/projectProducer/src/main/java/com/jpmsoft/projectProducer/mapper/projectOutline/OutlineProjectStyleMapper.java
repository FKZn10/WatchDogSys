package com.jpmsoft.projectProducer.mapper.projectOutline;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.request.projectOutline.OutlineProjectStyleRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjectStyleResponse;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineProjectStyle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 管理规划子系统 样式
 *
 * @author tianhelin
 * @date 2025/8/18
 */
@Mapper
public interface OutlineProjectStyleMapper extends BaseMapper<OutlineProjectStyle> {
    /**
     * 获取体系模板样式分页列表
     *
     * @param request
     * @return
     */
    List<OutlineProjectStyleResponse> getOutlineStylePage(@Param("page") Page<OutlineProjectStyleResponse> page, @Param("params") OutlineProjectStyleRequest request);
}

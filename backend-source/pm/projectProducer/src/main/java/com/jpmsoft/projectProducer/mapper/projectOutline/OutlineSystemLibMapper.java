package com.jpmsoft.projectProducer.mapper.projectOutline;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.request.projectOutline.OutlineSystemLibRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineSystemLibResponse;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineSystemLib;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author tianhelin
 * @date 2025/6/10
 */
@Mapper
public interface OutlineSystemLibMapper extends BaseMapper<OutlineSystemLib> {
    /**
     * 获取体系库
     *
     * @param request
     * @return
     */
    List<OutlineSystemLibResponse> getOutlineSystemLibList(@Param("params") OutlineSystemLibRequest request);

    @Select("select max(Osl_Sort) from Outline_System_Lib where osm_id=#{osmId} and Osl_Parent_Id=#{parentId}")
    Integer selectSort(@Param("osmId") String osmId, @Param("parentId") String parentId);

    /**
     * 检查模板叶子节点是否为空
     *
     * @param osmId
     * @return
     */
    List<OutlineSystemLibResponse> checkEmptyOutlineSystemLib(String osmId);

    /**
     * 根据osmId获取有关联业务功能的体系库节点
     *
     * @param osmId
     * @return
     */
    List<OutlineSystemLibResponse> getLibWithTempByOsmId(String osmId);
}

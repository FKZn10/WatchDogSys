package com.jpmsoft.projectProducer.mapper.projectOutline;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.request.projectOutline.OutlineProjectTempRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjectTempResponse;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineProjectTemp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author tianhelin
 * @date 2025/6/17
 */
@Mapper
public interface OutlineProjectTempMapper extends BaseMapper<OutlineProjectTemp> {
    @Select("select max(Opd_Sort) from Outline_Project_Temp where Opl_Id=#{oplId}")
    Integer selectSort(@Param("oplId") String oplId);

    /**
     * 分页查询项目大类
     *
     * @param page
     * @param request
     * @return
     */
    List<OutlineProjectTempResponse> getOutlineProjectTempPage(@Param("page") Page<OutlineProjectTemp> page, @Param("params") OutlineProjectTempRequest request);

    List<OutlineProjectTempResponse> getOutlineProjectTempByOplIds(@Param("oplIds") List<String> oplIds);

    List<String> getOdIdsByTCodes(@Param("tCodes") List<String> tCodes);
}

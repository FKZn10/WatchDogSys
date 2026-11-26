package com.jpmsoft.projectProducer.mapper.projectOutline;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.request.projectOutline.OutlineProjectLibRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjectLibResponse;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineProjectLib;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author tianhelin
 * @date 2025/6/10
 */
@Mapper
public interface OutlineProjectLibMapper extends BaseMapper<OutlineProjectLib> {
    /**
     * 获取大纲体系树形结构
     *
     * @param request
     * @return
     */
    List<OutlineProjectLibResponse> getOutlineProjectLibTree(@Param("params") OutlineProjectLibRequest request);

    List<OutlineProjectLibResponse> getOutlineProjectLibListByOpmId(@Param("opmId") String opmId);

    @Select("select max(Opl_Sort) from Outline_Project_Lib where Opm_Id=#{opmId} and Opl_Parent_Id=#{parentId}")
    Integer selectSort(@Param("opmId") String opmId, @Param("parentId") String parentId);

    List<String> getAllProjectName(@Param("pId") String pId);

    List<SysFile> getFileIdByTCodes(@Param("pId") String pId, @Param("tCodes") List<String> tCodes);

    /**
     * 检查大纲叶子节点是否为空
     *
     * @param opmId
     * @return
     */
    List<OutlineProjectLibResponse> checkEmptyOutlineProjectLib(@Param("opmId") String opmId);

    List<OutlineProjectLibResponse> getOplIdByPidAndTCode(@Param("opmIds") List<String> opmIds);
}

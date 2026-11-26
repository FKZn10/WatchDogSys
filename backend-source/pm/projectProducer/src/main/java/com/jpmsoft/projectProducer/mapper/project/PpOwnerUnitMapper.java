package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.request.project.PpOwnerUnitDTO;
import com.jpmsoft.projectProducer.pojo.entity.project.PpOwnerUnit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface PpOwnerUnitMapper extends BaseMapper<PpOwnerUnit> {
    /**
     * 根据项目id查询项目所有业主单位人员详情
     *
     * @param pId
     * @return
     */
    List<PpOwnerUnitDTO> getPpOwnerUnitByPId(String pId);

    List<PpOwnerUnitDTO> listPpOwnerUnitByPIds(@Param("pIds") ArrayList<String> pIds);
}

package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.dto.project.PreProjectReserveChangeDTO;
import com.jpmsoft.projectProducer.pojo.dto.project.PreProjectReserveDTO;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectReserve;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectReserveChange;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/3/6 14:21
 */
public interface PreProjectReserveChangeMapper extends BaseMapper<PreProjectReserveChange> {

    List<PreProjectReserveChangeDTO> getProjectReserveChangeList(@Param("page") IPage<PreProjectReserveChangeDTO> page, @Param("params") PreProjectReserveChangeDTO params);

    PreProjectReserveChangeDTO getProjectReserveChangeById(@Param("id") String  id);
}

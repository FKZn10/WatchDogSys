package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.dto.project.ProWnDetailDTO;
import com.jpmsoft.projectProducer.pojo.entity.project.ProWnDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProWnDetailMapper extends BaseMapper<ProWnDetail> {


    ProWnDetailDTO getProWnDetailById(@Param("pwId") String pwId);

    List<ProWnDetailDTO> getProWnDetailByPid(@Param("pwPid") String pwPid);
}
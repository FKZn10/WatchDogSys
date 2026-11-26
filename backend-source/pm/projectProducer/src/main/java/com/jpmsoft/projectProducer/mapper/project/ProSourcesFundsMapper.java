package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.ProSourcesFunds;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ProSourcesFundsMapper extends BaseMapper<ProSourcesFunds> {
    List<Map<String, Object>> getSourcesFundsSumByPids(@Param("pIds") List<String> pIds);
}

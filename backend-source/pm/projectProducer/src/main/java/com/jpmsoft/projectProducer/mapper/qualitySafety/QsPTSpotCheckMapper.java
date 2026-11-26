package com.jpmsoft.projectProducer.mapper.qualitySafety;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.QsPTSpotCheckDto;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsPTSpotCheck;
import com.jpmsoft.projectProducer.pojo.vo.qualitySafety.QsPTSpotCheckVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface QsPTSpotCheckMapper extends BaseMapper<QsPTSpotCheck> {


    List<QsPTSpotCheckDto> getQsPTSpotCheckList(@Param("page") IPage<QsPTSpotCheckDto> page, @Param("params") Map<String, Object> params);


    QsPTSpotCheckVo getQsPTSpotCheckById(@Param("id") String id);

}

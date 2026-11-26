package com.jpmsoft.projectProducer.mapper.construction;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.dto.construction.NightConstructionFilingDTO;
import com.jpmsoft.projectProducer.pojo.entity.construction.NightConstructionFiling;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/10/24 10:50
 */
public interface NightConstructionFilingMapper extends BaseMapper<NightConstructionFiling> {

    List<NightConstructionFilingDTO> getNightConstructionFilingList(@Param("page") IPage<NightConstructionFilingDTO> page, @Param("params") Map<String, Object> params);


    NightConstructionFilingDTO getNightConstructionFilingById(@Param("id") String id);
}

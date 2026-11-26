package com.jpmsoft.projectProducer.mapper.visaChange;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConDccs;
import com.jpmsoft.projectProducer.pojo.vo.visaChange.ConDccsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/4/6 17:48
 */
public interface ConDccsMapper extends BaseMapper<ConDccs> {


    ConDccsVO getConDccsById(@Param("id") String id);

    List<ConDccsVO> getConDccsList(@Param("page") IPage<ConDccsVO> page, @Param("params") Map<String, Object> params);

}

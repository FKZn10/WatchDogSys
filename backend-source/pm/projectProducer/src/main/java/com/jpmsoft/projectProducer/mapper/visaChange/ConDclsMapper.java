package com.jpmsoft.projectProducer.mapper.visaChange;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConDcls;
import com.jpmsoft.projectProducer.pojo.vo.visaChange.ConDclsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/4/7 8:36
 */
public interface ConDclsMapper extends BaseMapper<ConDcls> {

    ConDclsVO getConDclsById(@Param("id") String id);

    List<ConDclsVO> getConDclsList(@Param("page") IPage<ConDclsVO> page, @Param("params") Map<String, Object> params);

}

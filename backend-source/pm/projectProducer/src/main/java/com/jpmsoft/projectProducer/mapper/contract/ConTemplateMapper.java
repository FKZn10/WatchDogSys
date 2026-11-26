package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConTemplate;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConTemplateVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 李文涛 aaron
 * @date 2022/07/08 10:06
 */
public interface ConTemplateMapper extends BaseMapper<ConTemplate> {

    List<ConTemplateVo> getConTemplateList(@Param("page") IPage<ConTemplateVo> page, @Param("params") Map<String, Object> params);


    ConTemplateVo getConTemplateById(@Param("id") String id);
}





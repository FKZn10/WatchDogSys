package com.jpmsoft.projectProducer.mapper.conBoq;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jpmsoft.projectProducer.pojo.entity.conBoq.ConBoq;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConBoqVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ConBoqMapper extends BaseMapper<ConBoq> {

    IPage<ConBoq> getBoqList(IPage<ConBoq> page, @Param(Constants.WRAPPER) Wrapper<ConBoq> queryWrapper);


    ConBoqVo getConBoqById(@Param("boqId") String boqId);


    List<ConBoqVo> getConBoqList(@Param("page") IPage<ConBoqVo> page, @Param("params") Map<String, Object> params);
}

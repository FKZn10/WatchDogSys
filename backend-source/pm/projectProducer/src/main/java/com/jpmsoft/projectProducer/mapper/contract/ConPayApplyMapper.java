package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConPayApplyDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayApply;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ConPayApplyMapper extends BaseMapper<ConPayApply> {
    List<ConPayApplyDto> getConPayApplyList(@Param("params") Map<String, Object> params);

    Integer getConPayApplyCount(@Param("params") Map<String, Object> params);

    ConPayApplyDto getConPayApplyById(@Param("cpaId") String id);
}

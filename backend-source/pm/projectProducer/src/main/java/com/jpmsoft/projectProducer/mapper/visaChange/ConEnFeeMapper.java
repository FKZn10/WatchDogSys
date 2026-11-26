package com.jpmsoft.projectProducer.mapper.visaChange;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.dto.visaChange.ConEnFeeDTO;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConEnFee;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/12/21 17:06
 */
public interface ConEnFeeMapper extends BaseMapper<ConEnFee> {

    List<ConEnFeeDTO> getConEnFeeList(@Param("page") IPage<ConEnFeeDTO> page,
                                      @Param("params") Map<String, Object> params);


    ConEnFeeDTO getConEnFeeById(@Param("id") String id);
}

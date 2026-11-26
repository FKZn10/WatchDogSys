package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractChange;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConContractChangeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/9/18 13:40
 */
public interface ConContractChangeMapper extends BaseMapper<ConContractChange> {
    List<ConContractChangeVO> getContractChangeList(@Param("page") IPage<ConContractChangeVO> page, @Param("params") Map<String, Object> params);

    ConContractChangeVO getContractChangeById(@Param("id") String id);
}

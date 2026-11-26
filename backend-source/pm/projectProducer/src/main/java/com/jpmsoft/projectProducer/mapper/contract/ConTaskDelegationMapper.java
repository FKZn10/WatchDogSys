package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConTaskDelegation;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConTaskDelegationVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/5 13:57
 */
public interface ConTaskDelegationMapper extends BaseMapper<ConTaskDelegation> {


    List<ConTaskDelegationVo> getConTaskDelegationList(@Param("page") IPage<ConTaskDelegationVo> page,
                                                       @Param("params") Map<String, Object> params);

    ConTaskDelegationVo getConTaskDelegationById(@Param("id") String id);

   List<ConTaskDelegationVo> getConTaskDelegationByConId(@Param("conId") String conId);
}

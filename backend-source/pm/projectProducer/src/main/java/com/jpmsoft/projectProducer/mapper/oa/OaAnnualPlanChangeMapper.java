package com.jpmsoft.projectProducer.mapper.oa;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAnnualPlanChange;
import com.jpmsoft.projectProducer.pojo.vo.oa.OaAnnualPlanChangeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/12/2 15:04
 */
public interface OaAnnualPlanChangeMapper extends BaseMapper<OaAnnualPlanChange> {

    List<OaAnnualPlanChangeVo> getOaAnnualPlanChangeList(@Param("page") IPage<OaAnnualPlanChangeVo> page,
                                                         @Param("params") Map<String, Object> params);

    OaAnnualPlanChangeVo getOaAnnualPlanChangeById(@Param("id") String id);
}

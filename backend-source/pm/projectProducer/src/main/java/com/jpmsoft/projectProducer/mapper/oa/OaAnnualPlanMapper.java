package com.jpmsoft.projectProducer.mapper.oa;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAnnualPlan;
import com.jpmsoft.projectProducer.pojo.vo.oa.OaAnnualPlanVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/12/2 15:04
 */
public interface OaAnnualPlanMapper extends BaseMapper<OaAnnualPlan> {

    List<OaAnnualPlanVo> getOaAnnualPlanList(@Param("page") IPage<OaAnnualPlanVo> page,
                                             @Param("params") Map<String, Object> params);

    OaAnnualPlanVo getOaAnnualPlanById(@Param("id") String id);
}

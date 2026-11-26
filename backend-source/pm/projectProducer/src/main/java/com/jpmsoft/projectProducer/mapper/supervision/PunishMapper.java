package com.jpmsoft.projectProducer.mapper.supervision;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Supervisor.ConSupPlanResponse;
import com.jpmsoft.common.model.response.Supervisor.PunishResponse;
import com.jpmsoft.projectProducer.pojo.entity.supervision.ConSupPlan;
import com.jpmsoft.projectProducer.pojo.entity.supervision.Punish;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface PunishMapper extends BaseMapper<Punish> {


    List<PunishResponse> getPunishList(@Param("page") IPage<PunishResponse> page,@Param("params") Map<String, Object> params);
}

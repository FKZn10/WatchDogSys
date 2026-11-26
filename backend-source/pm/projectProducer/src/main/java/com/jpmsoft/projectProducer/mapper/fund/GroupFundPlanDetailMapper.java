package com.jpmsoft.projectProducer.mapper.fund;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.fund.GroupFundPlanDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GroupFundPlanDetailMapper extends BaseMapper<GroupFundPlanDetail> {
    void addBatch(@Param("list")List<GroupFundPlanDetail> details);


    List<GroupFundPlanDetail> getDetailByGfpId(@Param("gfpId")String gfpId);
}

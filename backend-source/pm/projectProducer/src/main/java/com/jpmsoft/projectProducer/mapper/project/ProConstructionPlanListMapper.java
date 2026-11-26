package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Project.ProConstructionPlanListModel;
import com.jpmsoft.projectProducer.pojo.entity.project.ProConstructionPlanList;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ProConstructionPlanListMapper extends BaseMapper<ProConstructionPlanList> {

    List<ProConstructionPlanListModel> getProConstructionPlanList(@Param("page") IPage<ProConstructionPlanListModel> page, @Param("params") Map<String, Object> params);

    ProConstructionPlanListModel getProConstructionPlanListById(@Param("cplId") String cplId);

    List<ProConstructionPlanListModel> getProConstructionPlanListByPcpId(@Param("pcpId") String pcpId);

}

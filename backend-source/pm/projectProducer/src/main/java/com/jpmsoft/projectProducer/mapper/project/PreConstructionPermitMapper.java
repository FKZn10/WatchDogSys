package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Project.PreConstructionPermitModel;
import com.jpmsoft.projectProducer.pojo.entity.project.PreConstructionPermit;
import com.jpmsoft.projectProducer.pojo.vo.project.PreConstructionPermitStatisticsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface PreConstructionPermitMapper extends BaseMapper<PreConstructionPermit> {

    List<PreConstructionPermitModel> getPreConstructionPermitList(@Param("page") IPage<PreConstructionPermitModel> page, @Param("params") Map<String, Object> params);

    PreConstructionPermitStatisticsVo getCpContractPriceSum(@Param("params") Map<String, Object> params);

    PreConstructionPermitModel getPreConstructionPermitById(@Param("cpId") String cpId);

    List<PreConstructionPermitModel> getPreConstructionPermitListByPid(@Param("pId") String pId);
}

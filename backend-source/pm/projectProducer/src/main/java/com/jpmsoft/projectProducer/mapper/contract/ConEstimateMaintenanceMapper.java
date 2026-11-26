package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.request.Contract.ConEstimateMaintenanceRequest;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConContractModel;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConEstimateModel;
import com.jpmsoft.common.model.requestAndResponse.Contract.EstimateListModel;
import com.jpmsoft.common.model.response.Contract.ConContractResponse;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConEcoDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContract;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConEstimate;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConEstimateMaintenance;
import com.jpmsoft.projectProducer.pojo.entity.multiproject.MpcFunds;
import com.jpmsoft.projectProducer.pojo.vo.contract.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public interface ConEstimateMaintenanceMapper extends BaseMapper<ConEstimateMaintenance> {
    List<ConEstimateMaintenanceRequest> getConEstimateMaintenanceList(@Param("page") IPage<ConEstimateMaintenanceRequest> page, @Param("params") Map<String, Object> params);

    ConEstimateMaintenanceRequest getConEstimateMaintenanceById(String cemId);

    List<ConEstimateModel> getConEstimateMaintenanceByConIdAndSecId(String conId, String secId);


}

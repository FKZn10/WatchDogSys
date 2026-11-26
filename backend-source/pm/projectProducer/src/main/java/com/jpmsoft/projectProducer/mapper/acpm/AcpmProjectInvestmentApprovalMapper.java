package com.jpmsoft.projectProducer.mapper.acpm;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.acpm.AcpmProjectInvestmentApproval;
import com.jpmsoft.projectProducer.pojo.vo.acpm.AcpmProjectInvestmentApprovalSelectVo;
import com.jpmsoft.projectProducer.pojo.vo.acpm.AcpmProjectInvestmentApprovalVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 14:40
 */

public interface AcpmProjectInvestmentApprovalMapper extends BaseMapper<AcpmProjectInvestmentApproval> {

    List<AcpmProjectInvestmentApprovalVo> getList(@Param("params") Map<String, Object> params);

    List<AcpmProjectInvestmentApprovalSelectVo> getSelectList(@Param("params") Map<String, Object> params);

    Integer getCount(@Param("params") Map<String, Object> params);

    AcpmProjectInvestmentApprovalVo getById(@Param("id") String id);

}

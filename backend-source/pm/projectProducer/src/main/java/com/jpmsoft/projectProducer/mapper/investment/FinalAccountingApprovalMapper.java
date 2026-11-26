package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.projectProducer.pojo.entity.investment.FinalAccountingApproval;
import com.jpmsoft.projectProducer.pojo.vo.investment.FinalAccountingApprovalVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FinalAccountingApprovalMapper extends BaseMapper<FinalAccountingApproval> {
    FinalAccountingApprovalVo getfinalApprovalById(@Param("id") String id);

    Page<FinalAccountingApprovalVo> getfinalApprovalList(@Param("page")Page<FinalAccountingApproval> page,@Param("request") FinalAccountingApprovalVo request);

    List<Object> getpNameValueByCode(@Param("value")String dbValue);

    List<Object> getValueByCode(@Param("value")String dbValue);
}

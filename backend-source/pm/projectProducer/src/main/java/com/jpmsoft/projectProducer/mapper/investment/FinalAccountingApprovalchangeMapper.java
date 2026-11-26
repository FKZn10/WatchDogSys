package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.projectProducer.pojo.entity.investment.FinalAccountingApprovalChange;
import com.jpmsoft.projectProducer.pojo.vo.investment.FinalAccountingApprovalChangeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FinalAccountingApprovalchangeMapper extends BaseMapper<FinalAccountingApprovalChange> {
    FinalAccountingApprovalChangeVo getfinalApprovalChangeById(@Param("id") String id );

    Page<FinalAccountingApprovalChangeVo> getfinalApprovalChangeList(@Param("page") Page<FinalAccountingApprovalChangeVo> page, @Param("request")FinalAccountingApprovalChangeVo request);
}

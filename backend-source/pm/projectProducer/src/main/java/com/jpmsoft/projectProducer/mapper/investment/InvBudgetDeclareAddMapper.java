package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.dto.investment.InvBudgetDeclareAddDTO;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvBudgetDeclareAdd;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/29 15:13
 */
public interface InvBudgetDeclareAddMapper extends BaseMapper<InvBudgetDeclareAdd> {


    List<InvBudgetDeclareAddDTO> getInvBudgetDeclareAddList(@Param("page") IPage<InvBudgetDeclareAddDTO> page, @Param("params") Map<String, Object> params);


    InvBudgetDeclareAddDTO getInvBudgetDeclareAddById(String id);
}

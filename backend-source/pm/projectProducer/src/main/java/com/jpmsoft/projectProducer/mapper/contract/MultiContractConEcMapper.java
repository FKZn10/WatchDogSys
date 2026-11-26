package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.projectProducer.pojo.entity.contract.MultiContractConEc;
import com.jpmsoft.projectProducer.pojo.vo.contract.MultiContractConEcPageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

@Mapper
public interface MultiContractConEcMapper extends BaseMapper<MultiContractConEc> {
    Page<MultiContractConEcPageVO> page(
            @Param("page") Page<Object> page,
            @Param("projectName") String projectName,
            @Param("pId") String pId,
            @Param("ecChangeType") String ecChangeType,
            @Param("authSql")String authSql);

    BigDecimal getFinishedDesignChangeApplyMoneyByProjectId(
            @Param("projectId") String projectId,
            @Param("finishedState") Integer finishedState,
            @Param("authSql") String authSql);
}

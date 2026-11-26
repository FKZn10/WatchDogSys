package com.jpmsoft.gmgws.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.gmgws.pojo.vo.ContractTotlaVo;
import com.jpmsoft.gmgws.pojo.vo.ContractVo;
import com.jpmsoft.gmgws.pojo.vo.StatisticsVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface InfrastrExcelMapper {


    IPage<ContractTotlaVo> getContractList(IPage<ContractTotlaVo> page,@Param("statisticsVo") StatisticsVo statisticsVo);


    IPage<ContractTotlaVo> getProjectList(IPage<ContractTotlaVo> page,@Param("statisticsVo") StatisticsVo statisticsVo);

    IPage<ContractTotlaVo> getContractLedgerList(IPage<ContractTotlaVo> page,@Param("statisticsVo") StatisticsVo statisticsVo);

    IPage<ContractTotlaVo> getCapitalPaymentList(IPage<ContractTotlaVo> page,@Param("statisticsVo") StatisticsVo statisticsVo);

    List<ContractVo> getContractAdopt();

    List<ContractVo> getContract();


    IPage<ContractTotlaVo> getProjectAuthenticationList(@Param("page") IPage<ContractTotlaVo> page,@Param("statisticsVo") StatisticsVo statisticsVo,@Param("authSql")  String authSql);
    //新 查询合同支付统计
    IPage<ContractTotlaVo> getContractStatisticsList(@Param("page") IPage<ContractTotlaVo> page,@Param("statisticsVo") StatisticsVo statisticsVo,@Param("authSql") String authSql);

}

package com.jpmsoft.gmgws.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import com.jpmsoft.gmgws.pojo.entity.ConContract;
import com.jpmsoft.gmgws.pojo.vo.ContractTotlaVo;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConContractMapper extends BaseMapper<ConContract> {
    List<ContractTotlaVo> getPayList(@Param("ids") List<String> ids);


    List<ConContract> selectConContract(@org.apache.ibatis.annotations.Param("page") IPage<ConContract> page,@org.apache.ibatis.annotations.Param("params") ConContractRequest params);
}

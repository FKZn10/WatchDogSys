package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.Contract.conConsortiumModel;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConConsortiumDtl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ConConsortiumDtlMapper extends BaseMapper<ConConsortiumDtl> {

    List<conConsortiumModel>  slectConConsortiumDtlListByConId(@Param("conId") String conId);
}

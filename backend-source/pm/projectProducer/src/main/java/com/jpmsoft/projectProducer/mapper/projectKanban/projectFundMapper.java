package com.jpmsoft.projectProducer.mapper.projectKanban;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface projectFundMapper {


    Double getInvesToral(String pId);

    Double getContractTotal(String pId);

    Double getPayTotal(String pId);

    Double getRealPatTotal(String pId);
}

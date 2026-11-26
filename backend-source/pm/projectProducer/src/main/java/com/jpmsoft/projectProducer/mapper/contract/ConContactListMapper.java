package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Contract.ConContactListResponse;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContactList;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ConContactListMapper extends BaseMapper<ConContactList> {

    List<ConContactListResponse> getConContactList(@Param("page") IPage<ConContactListResponse> page, @Param("params") Map<String, Object> params);


    ConContactListResponse getContactLisByClId(@Param("clId") String clId);
}

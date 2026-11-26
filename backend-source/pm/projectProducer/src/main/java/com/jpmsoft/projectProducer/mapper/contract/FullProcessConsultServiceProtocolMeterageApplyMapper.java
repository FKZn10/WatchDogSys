package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.request.Contract.FullProcessConsultServiceProtocolMeteragePageRequest;
import com.jpmsoft.projectProducer.pojo.entity.contract.FullProcessConsultServiceProtocolMeterageApply;
import com.jpmsoft.projectProducer.pojo.vo.contract.FullProcessConsultServiceProtocolMeterageApplyVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FullProcessConsultServiceProtocolMeterageApplyMapper extends BaseMapper<FullProcessConsultServiceProtocolMeterageApply> {
    Page<FullProcessConsultServiceProtocolMeterageApplyVO> pageFullProcessConsultServiceProtocolMeterageApply(@Param("objectPage") Page<Object> objectPage, @Param("request") FullProcessConsultServiceProtocolMeteragePageRequest request, @Param("uId") String uId);

    List<FullProcessConsultServiceProtocolMeterageApplyVO> listFullProcessConsultServiceProtocolMeterageApply(@Param("request") FullProcessConsultServiceProtocolMeteragePageRequest request, @Param("uId") String uId);
}

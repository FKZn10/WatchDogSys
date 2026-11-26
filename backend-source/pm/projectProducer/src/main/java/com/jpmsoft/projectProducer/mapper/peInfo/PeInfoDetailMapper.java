package com.jpmsoft.projectProducer.mapper.peInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.PeInfo.PeInfoDetailModel;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeInfoDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface PeInfoDetailMapper extends BaseMapper<PeInfoDetail> {

    void deletePeInfoDetailByPeId(@Param("peId") String peId);

    void insertBatchs(@Param("pojos") List<PeInfoDetailModel> pojos);
}

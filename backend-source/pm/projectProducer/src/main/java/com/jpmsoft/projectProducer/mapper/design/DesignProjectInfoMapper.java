package com.jpmsoft.projectProducer.mapper.design;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignProjectInfoModel;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignProjectInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface DesignProjectInfoMapper extends BaseMapper<DesignProjectInfo> {


    List<DesignProjectInfoModel> getDesignProjectInfoList(@Param("params") Map<String,Object> params);

    void delDesignProjectInfoByBusinessId(@Param("businessId") String businessId, @Param("businessType") Integer businessType);

}

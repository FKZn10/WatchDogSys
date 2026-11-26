package com.jpmsoft.projectProducer.mapper.maintenance;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Maintenance.MaintenanceContactResponse;
import com.jpmsoft.projectProducer.pojo.entity.maintenance.MaintenanceContact;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface MaintenanceContactMapper extends BaseMapper<MaintenanceContact> {

    List<MaintenanceContactResponse> getMaintenanceContactList(@Param("page") IPage<MaintenanceContactResponse> page, @Param("params") Map<String, Object> params);


    MaintenanceContactResponse getMaintenanceContactById(@Param("mcId") String mcId);
}

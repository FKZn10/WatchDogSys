package com.jpmsoft.projectProducer.mapper.maintenance;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Maintenance.MaintenanceWarrantyResponse;
import com.jpmsoft.projectProducer.pojo.entity.maintenance.MaintenanceWarranty;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface MaintenanceWarrantyMapper extends BaseMapper<MaintenanceWarranty> {

    List<MaintenanceWarrantyResponse> getMaintenanceWarrantyList(@Param("page") IPage<MaintenanceWarrantyResponse> page, @Param("params") Map<String, Object> params);

    MaintenanceWarrantyResponse getMaintenanceWarrantyById(@Param("mwId") String mwId);
}

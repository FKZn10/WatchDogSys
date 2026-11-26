package com.jpmsoft.projectProducer.mapper.cockpit;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.SmartSite.SmartSiteCameraModel;
import com.jpmsoft.projectProducer.pojo.entity.cockpit.SmartSiteCamera;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SmartSiteCameraMapper extends BaseMapper<SmartSiteCamera> {

    List<SmartSiteCameraModel> getSmartSiteCameraList(IPage<SmartSiteCameraModel> page, @Param("params") Map<String, Object> params);


    SmartSiteCameraModel getSmartSiteCameraById(@Param("sscId") String sscId);

    SmartSiteCamera getAuthorizedPlaybackCamera(@Param("sscId") String sscId, @Param("teId") String teId,
                                                @Param("authSql") String authSql);

    List<SmartSiteCamera> getAuthorizedStatusCameras(@Param("pId") String pId, @Param("teId") String teId,
                                                    @Param("authSql") String authSql);

    List<SmartSiteCameraModel> getSmartSiteCameraByPid(@Param("pIds") List<String> pIds, @Param("sod") String sod);
}

package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.project.PreProjectDeviceCoordinateRequest;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectDeviceCoordinate;
import com.jpmsoft.projectProducer.pojo.vo.project.PreProjectDeviceCoordinateVo;

import java.util.List;

public interface IPreProjectDeviceCoordinateService extends IService<PreProjectDeviceCoordinate> {

    /**
     * 创建或更新
     * @param preProjectDeviceCoordinateModelList
     */
    void createOrUpdate(List<PreProjectDeviceCoordinate> preProjectDeviceCoordinateModelList);

    /**
     * 获取项目点位列表
     * @param request
     * @return
     */
    List<PreProjectDeviceCoordinateVo> getList(PreProjectDeviceCoordinateRequest request);


    /**
     * 页面列表接口
     * @param page
     * @return
     */
    Page<PreProjectDeviceCoordinate> getPage(Page<Object> page);
}

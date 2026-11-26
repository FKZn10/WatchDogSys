package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.fileUpload.pojo.entity.Watermark;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/3/10 13:41
 */
public interface IWatermarkService extends IService<Watermark> {

    String getWatermarkPath(String root,String id);
    Watermark getByWatermarkId(String root,String id);
    Watermark getByWatermarkId(String root,String id,Boolean isPdf);

    BaseResponse getWatermarkList();
}

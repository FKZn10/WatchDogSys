package com.jpmsoft.projectProducer.service.construction;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.construction.BuildQualitySafetyLogDto;
import com.jpmsoft.projectProducer.pojo.entity.construction.BuildQualitySafetyLog;

/**
 * @author 李文涛 aaron
 * @date 2022/07/08 17:48
 */
public interface IBuildQualitySafetyLogService extends IService<BuildQualitySafetyLog> {

    BaseResponse createBuildQualitySafetyLog(BuildQualitySafetyLogDto request);

    BaseResponse updateBuildQualitySafetyLog(BuildQualitySafetyLogDto request);

    BaseResponse getBuildQualitySafetyLogList(BuildQualitySafetyLogDto request, String uId);

    BaseResponse getBuildQualitySafetyLogById(String id);
}

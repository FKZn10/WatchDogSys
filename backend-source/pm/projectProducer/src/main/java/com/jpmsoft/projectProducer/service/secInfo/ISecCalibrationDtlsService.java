package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecCalibrationDtlsDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecCalibrationDtls;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:49
 */
public interface ISecCalibrationDtlsService extends IService<SecCalibrationDtls> {

    void createOrUpdateSecCalibrationDtls(List<SecCalibrationDtlsDTO> data,String scsId);

    void deleteByScsId(String scsId);
}

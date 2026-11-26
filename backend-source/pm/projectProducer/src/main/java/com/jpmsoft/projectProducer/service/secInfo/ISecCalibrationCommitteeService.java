package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecCalibrationCommitteeDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecCalibrationCommittee;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:49
 */
public interface ISecCalibrationCommitteeService extends IService<SecCalibrationCommittee> {

    void createOrUpdateSecCalibrationCommittee(List<SecCalibrationCommitteeDTO> data, String sccId);

    void deleteBySccId(String sccId);
}

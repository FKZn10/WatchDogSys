package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecSupervisionCommitteeDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecSupervisionCommittee;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:49
 */
public interface ISecSupervisionCommitteeService extends IService<SecSupervisionCommittee> {

    void createOrUpdateSecSupervisionCommittee(List<SecSupervisionCommitteeDTO> data, String sccId);

    void deleteBySccId(String sccId);
}

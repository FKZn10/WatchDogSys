package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecDefenseSupervisionDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecDefenseSupervision;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:49
 */
public interface ISecDefenseSupervisionService extends IService<SecDefenseSupervision> {

    void createOrUpdateSecDefenseSupervision(List<SecDefenseSupervisionDTO> data,String sqId);

    void deleteBySqId(String sqId);
}

package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecClearTeamDtlDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecClearTeamDtl;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:49
 */
public interface ISecClearTeamDtlService extends IService<SecClearTeamDtl> {

    void createOrUpdateSecClearTeamDtl(List<SecClearTeamDtlDTO> data,String sctId);

    void deleteBySctId(String sctId);
}

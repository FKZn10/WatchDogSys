package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecClearTeamDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecClearTeam;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:50
 */
public interface ISecClearTeamService extends IService<SecClearTeam> {

    BaseResponse createOrUpdateSecClearTeam(SecClearTeamDTO request);

    BaseResponse getSecClearTeamById(String id);
}

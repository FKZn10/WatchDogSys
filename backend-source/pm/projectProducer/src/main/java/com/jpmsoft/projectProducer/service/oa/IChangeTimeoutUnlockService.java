package com.jpmsoft.projectProducer.service.oa;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.entity.oa.ChangeTimeoutUnlock;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/10/29 14:36
 */
public interface IChangeTimeoutUnlockService extends IService<ChangeTimeoutUnlock> {

    BaseResponse approve2UnlockMeeting(String id);
}

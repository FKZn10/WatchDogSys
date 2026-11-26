package com.jpmsoft.projectProducer.service.oa.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.oa.ChangeTimeoutUnlockMapper;
import com.jpmsoft.projectProducer.pojo.entity.oa.ChangeTimeoutUnlock;
import com.jpmsoft.projectProducer.service.oa.IChangeMeetingNotificationService;
import com.jpmsoft.projectProducer.service.oa.IChangeTimeoutUnlockService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/10/29 14:37
 */
@Service
public class ChangeTimeoutUnlockImpl extends ServiceImpl<ChangeTimeoutUnlockMapper, ChangeTimeoutUnlock>
            implements IChangeTimeoutUnlockService {
    @Resource
    private IChangeMeetingNotificationService changeMeetingNotificationService;

    @Override
    public BaseResponse approve2UnlockMeeting(String id) {
        ChangeTimeoutUnlock entity=baseMapper.selectById(id);
        if (entity==null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (ValidateUtil.isNull(entity.getState())||!entity.getState().equals(ApprovalStatusEnum.Already_Over.getIndex()))
            return BaseResponse.fail(ResMsg.stateCannotAct);
        //解锁会议
        changeMeetingNotificationService.apply2UnlockMeeting(entity.getCmnId(),3);
        return BaseResponse.ok();
    }
}

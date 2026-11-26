package com.jpmsoft.projectProducer.service.oa;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.entity.oa.ChangeMeetingNotification;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/10/29 14:01
 */
public interface IChangeMeetingNotificationService extends IService<ChangeMeetingNotification> {


    BaseResponse  timeOut2LockMeeting(Integer day);

    void  apply2UnlockMeeting(String id, Integer scState);



    BaseResponse queryDataList(Integer page,Integer size);
}

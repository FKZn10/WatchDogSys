package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.jpbpm.pojo.entity.SysTaskFirstRead;
import com.jpmsoft.jpbpm.pojo.entity.Task;

/**
 * 待办首次阅读信息
 */
public interface ISysTaskFirstReadService extends IService<SysTaskFirstRead> {
    /**
     * 在待办阅读时存储首次阅读的时间
     *
     * @param taskentity 待办信息
     * @return
     */
    BaseResponse createFirstRead(Task taskentity);
}

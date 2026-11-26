package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.jpbpm.mapper.SysTaskFirstReadMapper;
import com.jpmsoft.jpbpm.pojo.entity.FlowActivity;
import com.jpmsoft.jpbpm.pojo.entity.SysTaskFirstRead;
import com.jpmsoft.jpbpm.pojo.entity.Task;
import com.jpmsoft.jpbpm.service.FlowActivityService;
import com.jpmsoft.jpbpm.service.ISysTaskFirstReadService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 待办首次阅读信息
 */
@Service
public class SysTaskFirstReadImpl extends ServiceImpl<SysTaskFirstReadMapper, SysTaskFirstRead> implements ISysTaskFirstReadService {
    @Resource
    private FlowActivityService flowActivityService;

    /**
     * 在待办阅读时存储首次阅读的时间
     *
     * @param taskentity 待办信息
     * @return
     */
    @Override
    public BaseResponse createFirstRead(Task taskentity) {
        if (ValidateUtil.isNull(taskentity)) {
            return BaseResponse.fail("待办信息为空！");
        }
        // 首先查询是否重复
        long count = this.count(Wrappers.<SysTaskFirstRead>lambdaQuery()
                .eq(SysTaskFirstRead::getTaskId, taskentity.getTaskid()));
        if (count > 0) {
            // 已阅读过不处理
            return BaseResponse.ok();
        }
        SysTaskFirstRead read = createRead(taskentity);
        this.save(read);
        return BaseResponse.ok(read.getId());
    }

    /**
     * 创建阅读记录
     *
     * @param taskentity 待办信息
     * @return
     */
    @NonNull
    private SysTaskFirstRead createRead(Task taskentity) {
        SysTaskFirstRead read = new SysTaskFirstRead();
        read.setId(GenerateUUID.getUUID());
        read.setTaskId(taskentity.getTaskid());
        read.setPId(taskentity.getProjectId());
        read.setConId(taskentity.getContractId());
        read.setEntityId(taskentity.getTaskentityid());
        read.setFlowId(taskentity.getTaskflow());
        read.setActId(taskentity.getTaskcurrentactid());
        FlowActivity activity = flowActivityService.getById(taskentity.getTaskcurrentactid());
        if (ValidateUtil.isNotNull(activity)) {
            read.setActCode(activity.getActcode());
        }
        read.setReadTime(ValidateUtil.isNotNull(taskentity.getTaskreadtime()) ? taskentity.getTaskreadtime() : new Date());
        read.setReadUser(taskentity.getTaskuser());
        read.setTCode(taskentity.getTCode());
        return read;
    }
}

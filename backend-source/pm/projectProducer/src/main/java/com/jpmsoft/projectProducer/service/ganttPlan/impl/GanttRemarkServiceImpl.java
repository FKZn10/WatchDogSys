package com.jpmsoft.projectProducer.service.ganttPlan.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ganttPlan.GanttRemarkRequest;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.ganttPlan.GanttRemarkMapper;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttRemark;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.ganttPlan.GanttRemarkService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author tianhelin
 * @date 2025/5/23
 */
@Service
public class GanttRemarkServiceImpl extends ServiceImpl<GanttRemarkMapper, GanttRemark> implements GanttRemarkService {
    /**
     * 获取备注历史记录列表
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse getGanttRemarkList(GanttRemarkRequest request) {
        Page<GanttRemarkRequest> page = new Page<>(request.getPage(), request.getSize());
        return BaseResponse.ok(baseMapper.getGanttRemarkList(page, request));
    }

    /**
     * 新增备注
     *
     * @param request
     * @param userInfo
     * @return
     */
    @Override
    public BaseResponse addGanttRemark(GanttRemarkRequest request, LogUserInfoVo userInfo) {
        if (StrUtil.isNotBlank(request.getRemark()) && StrUtil.isNotBlank(request.getGmsId())) {
            GanttRemark remark = new GanttRemark();
            remark.setId(GenerateUUID.getUUID());
            remark.setGmsId(request.getGmsId());
            remark.setWriteTime(new Date());
            remark.setRemark(request.getRemark());
            remark.setCreateUser(userInfo.getId());
            remark.setCreateUserName(userInfo.getName());
            boolean save = this.save(remark);
            return save ? BaseResponse.ok() : BaseResponse.fail();
        }
        return BaseResponse.fail();
    }
}

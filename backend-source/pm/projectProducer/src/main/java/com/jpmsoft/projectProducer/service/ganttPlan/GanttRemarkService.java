package com.jpmsoft.projectProducer.service.ganttPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ganttPlan.GanttRemarkRequest;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttRemark;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

/**
 * @author tianhelin
 * @date 2025/5/23
 */
public interface GanttRemarkService extends IService<GanttRemark> {
    /**
     * 获取备注历史记录列表
     *
     * @param request
     * @return
     */
    BaseResponse getGanttRemarkList(GanttRemarkRequest request);

    /**
     * 新增备注
     *
     * @param request
     * @param userInfo
     * @return
     */
    BaseResponse addGanttRemark(GanttRemarkRequest request, LogUserInfoVo userInfo);
}

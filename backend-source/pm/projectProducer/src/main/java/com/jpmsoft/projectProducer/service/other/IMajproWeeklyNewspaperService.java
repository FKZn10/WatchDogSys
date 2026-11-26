package com.jpmsoft.projectProducer.service.other;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.NoMajproWeeklyNewspaperRequest;
import com.jpmsoft.projectProducer.pojo.entity.other.MajproWeeklyNewspaper;

public interface IMajproWeeklyNewspaperService extends IService<MajproWeeklyNewspaper> {
    /**
     * 获取没在指定时间内填写周报的重点项目
     *
     * @param request
     * @return
     */
    BaseResponse getNoMajproWeeklyNewspaperList(NoMajproWeeklyNewspaperRequest request,String token);
}

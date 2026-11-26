package com.jpmsoft.projectProducer.service.riskWarn;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.RiskWarn.RiskWarnRequest;
import com.jpmsoft.projectProducer.pojo.entity.riskWarn.RiskWarn;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

public interface IRiskWarnService extends IService<RiskWarn> {

    BaseResponse getRiskWarnList(RiskWarnRequest request, LogUserInfoVo user);

    BaseResponse getRiskWarnById(String rwId);

    BaseResponse getRiskWarnListOutline(RiskWarnRequest request, LogUserInfoVo user);

    /**
     * 按预警指标统计数量并倒序返回名称与数量，仅使用 size 限制排名条数。
     *
     * @param request 筛选条件和排名条数
     * @param user 当前用户，用于数据权限过滤
     * @return 预警名称与数量的有序 Map
     */
    BaseResponse getRiskWarnRanking(RiskWarnRequest request, LogUserInfoVo user);
}

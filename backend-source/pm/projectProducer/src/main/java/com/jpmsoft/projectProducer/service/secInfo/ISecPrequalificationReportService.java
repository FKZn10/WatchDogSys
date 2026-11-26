package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecPrequalificationReportRequest;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecPrequalificationReport;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

/**
 * 资格预审情况报告 服务类
 */
public interface ISecPrequalificationReportService extends IService<SecPrequalificationReport> {
    BaseResponse getSecPrequalificationReportList(SecPrequalificationReportRequest request, LogUserInfoVo user);

    BaseResponse getSecPrequalificationReportClassInfo(SecPrequalificationReportRequest request, LogUserInfoVo user);
}

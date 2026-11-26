package com.jpmsoft.projectProducer.service.oa;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.oa.ReportedCompanyDto;
import com.jpmsoft.projectProducer.pojo.entity.oa.ReportedCompany;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/21 14:40
 */
public interface IReportedCompanyService extends IService<ReportedCompany> {

    BaseResponse createOrUpdateReportedCompany(ReportedCompanyDto request);

    BaseResponse getReportedCompanyList(ReportedCompanyDto request, String uId);

    BaseResponse getReportedCompanyById(String id);

    BaseResponse  getReportedCompanyGroupTypeByPid(String pId);

    /**
     * 审批结束后同步单位信息以及项目单位信息
     *
     * @param id
     * @return
     */
    BaseResponse approved2SyncCompanyInfo(String id);
}

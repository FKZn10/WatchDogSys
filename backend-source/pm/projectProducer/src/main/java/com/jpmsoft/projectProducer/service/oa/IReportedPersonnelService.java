package com.jpmsoft.projectProducer.service.oa;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.ReportedPersonnelModel;
import com.jpmsoft.projectProducer.pojo.entity.project.ReportedPersonnel;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

public interface IReportedPersonnelService extends IService<ReportedPersonnel> {

    BaseResponse createOrUpdateReportedPersonnel(ReportedPersonnelModel request);

    BaseResponse createOrUpdateReportedPersonnelCj(ReportedPersonnelModel request);

    BaseResponse getReportedPersonnelList(ReportedPersonnelModel request, String uId);

    BaseResponse getReportedPersonnelById(String rpId);

    BaseResponse approveReportedPersonnelById(String rpId);

    /**
     * 北林项目人员变更
     *
     * @param rpId
     * @return
     */
    BaseResponse approve2SyncDepartmentP(String rpId, LogUserInfoVo user);
}

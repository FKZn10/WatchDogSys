package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.jpbpm.pojo.dto.ApprovalTimelimitDto;
import com.jpmsoft.jpbpm.pojo.entity.SysTaskProcessinTime;
import com.jpmsoft.jpbpm.pojo.entity.TaskProcessUser;
import com.jpmsoft.jpbpm.pojo.entity.TaskProcessin;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface SysTaskProcessinTimeService  extends IService<SysTaskProcessinTime> {
    BaseResponse synchronizationTaskProcessinTime(String startDate,String endDate,String customerId,String tCode,String year);

    BaseResponse getPrescription(ApprovalTimelimitDto request,String customerId);
    BaseResponse getPrescriptionDetails(ApprovalTimelimitDto request,String customerId);
    BaseResponse getPrescriptionD(ApprovalTimelimitDto request,String customerId);
    int TaskProcessinTimeCreate(TaskProcessin request, String activityType,String  nextActId,String  nextActName,String prId,String customerId,String tCode);

    BaseResponse updateSysTaskProcessinTimeByTpId(String tpId,String ageing,String tCode,String customerId,String year);

    BaseResponse getSysTaskProcessinTimeByTpId(String tpId,String customerId);

    BaseResponse getSysTaskProcessinTimeByTpIds(List<String> tpId,String customerId);

    Integer updateTaskProcessinTime(Date approvalTime, String state, String fileId,String customerId);

    BaseResponse getRejectStatisticsD(ApprovalTimelimitDto request,String customerId);
    BaseResponse getRejectStatisticsPD(ApprovalTimelimitDto request,String customerId);


    BaseResponse getRejectStatisticsUD(ApprovalTimelimitDto request,String customerId);
    BaseResponse getRejectStatisticsRUD(ApprovalTimelimitDto request,String customerId);

    BaseResponse updateFilecodeByFiles(String startDate, String endDate, String customerId);
    BaseResponse updateFilecodeByFile(String code, String fileid, String customerId,String tCode,String year);

    BaseResponse rejectInnformationCorrection(String startDate, String endDate, String customerId);
}

package com.jpmsoft.projectProducer.service.qualitySafety;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Qs.QsPlanTaskQsRecordTypeRequest;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.ProSafetyLedgerDtlDTO;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.QsPlanTaskDto;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.QsRecordDto;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsPlanTask;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/20 14:41
 */
public interface IQsPlanTaskService extends IService<QsPlanTask> {
    /**
     * 新增修改
     *
     * @param request
     * @return
     */
    BaseResponse createOrUpdateQsPlanTask(QsPlanTaskDto request);

    /**
     * 维护部分指定信息
     *
     * @param request
     * @return
     */
    BaseResponse updateQsPlanTaskPartialInfo(QsPlanTaskDto request);

    BaseResponse getQsPlanTaskList(QsPlanTaskDto request, String uId);

    BaseResponse getQsPlanTaskListNoAuth(QsPlanTaskDto request);

    BaseResponse getQsPlanTaskById(String id);

    /**
     * 更新要求整改时间
     *
     * @param request
     * @return
     */
    BaseResponse updateQsPlanTaskRequireTime(QsPlanTaskDto request);

    BaseResponse getQsPlanTaskPage(QsPlanTaskDto request, String uId);

    /**
     * 项目质量安全检查台账
     *
     * @param request
     * @param uId
     * @return
     */
    BaseResponse getProSafetyLedgerPage(ProSafetyLedgerDtlDTO request, String uId);

    /**
     * 项目质量安全检查台账(义乌)
     *
     * @param request
     * @param uid
     * @return
     */
    BaseResponse getProSafetyLedgerPageYW(ProSafetyLedgerDtlDTO request, String uid);

    /**
     * 安全质量检查统计、高频问题统计
     *
     * @param request
     * @param uid
     * @return
     */
    BaseResponse qualitySafetyStatistics(QsRecordDto request, String uid);

    /**
     * 获取高频问他列表
     *
     * @param request
     * @param uid
     * @return
     */
    BaseResponse getHighQsRecordList(QsRecordDto request, String uid);

    BaseResponse updateQsPlanTaskQsRecordType(QsPlanTaskQsRecordTypeRequest request);

    BaseResponse getQsPlanTaskListExport(QsPlanTaskDto request, String uId);

    BaseResponse copyHighQsRecord(String qstId, String qsrId, String ptId, String uId);

    List<ProSafetyLedgerDtlDTO> getProSafetyLedgerPageYWExport(ProSafetyLedgerDtlDTO request, String uId);

    BaseResponse verifyqsPlanTask(String pId, String ptType, String token);

    void generateSms();

    BaseResponse getProSafetyLedgerPageYWNew(ProSafetyLedgerDtlDTO request, String token);

    List<ProSafetyLedgerDtlDTO> getProSafetyLedgerPageYWExportNew(ProSafetyLedgerDtlDTO request, String uid);

    BaseResponse approveData(QsPlanTaskDto request);

    BaseResponse refreshQsPlanTask(QsPlanTaskDto request);

    /**
     * 城建定时刷新超期状态
     * 🟢绿色：所有问题按时整改完成（闭合率100%且无超期）
     * 🟡黄色：存在临期整改项目（距离整改期限≤3天）
     * 红色：存在超期未整改项目或未整改项目
     * <p>
     * 同时给指定监督员生成平台消息
     *
     * 接口不对外使用定时任务管理定时调用
     * @return
     */
    BaseResponse timingRefreshOutTimeStatus();

    BaseResponse generateSubFileRecordsAndNotifyAssignees(QsPlanTaskDto request,String token);

    BaseResponse delegateTodoAndNotifyCcUsers(String id, String uid);

    BaseResponse syncQsRecordState(String id);

    BaseResponse getQsPlanTaskListSH(QsPlanTaskDto request, String uid);
}
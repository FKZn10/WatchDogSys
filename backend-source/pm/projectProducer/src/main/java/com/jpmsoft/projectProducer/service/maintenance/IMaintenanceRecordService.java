package com.jpmsoft.projectProducer.service.maintenance;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Maintenance.MaintenanceRecordRequest;
import com.jpmsoft.projectProducer.pojo.entity.maintenance.MaintenanceRecord;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import org.apache.poi.ss.usermodel.Workbook;

public interface IMaintenanceRecordService extends IService<MaintenanceRecord> {


    BaseResponse createOrUpdateMaintenanceRecord(MaintenanceRecordRequest request);

    BaseResponse getMaintenanceRecordList(MaintenanceRecordRequest request, String uId);

    BaseResponse getMaintenanceRecordListNoAuth(MaintenanceRecordRequest request);

    BaseResponse getMaintenanceRecordGgList(MaintenanceRecordRequest request, LogUserInfoVo user);

    BaseResponse getMaintenanceRecordById(String mrId);

    BaseResponse getRecordsByRepairState(int pageNum, int size);

    Workbook exportMaintenanceRecordGgExcel(MaintenanceRecordRequest request, LogUserInfoVo user);

    /**
     * 刷新单条广工零星维修状态，审批状态为3时直接将维修状态同步为7。
     *
     * @param mrId 维修记录ID
     * @return 刷新后的维修状态或失败结果
     */
    BaseResponse refreshRepairStateGg(String mrId);

    BaseResponse getRepairPersonByMrId(String mrId);

    /**
     * 按流程节点全量刷新广工零星维修状态。
     * 跳过审批状态为0的记录，审批状态为3时直接将维修状态同步为7。
     * 维修状态已为7时保持不变，已完工记录再按结算单同步为4、8或3。
     * 已结算记录再按支付单同步为6、9或4。
     *
     * @return 全量刷新结果
     */
    BaseResponse refreshRepairStateGgAll();
}

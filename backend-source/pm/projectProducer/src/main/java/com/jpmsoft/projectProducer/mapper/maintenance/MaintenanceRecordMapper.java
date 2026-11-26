package com.jpmsoft.projectProducer.mapper.maintenance;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.MaintenanceRecordModel;
import com.jpmsoft.common.model.response.Maintenance.MaintenanceRecordResponse;
import com.jpmsoft.common.model.response.TaskResponse;
import com.jpmsoft.projectProducer.pojo.entity.maintenance.MaintenanceRecord;
import com.jpmsoft.projectProducer.pojo.entity.other.TaskProcessin;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface MaintenanceRecordMapper extends BaseMapper<MaintenanceRecord> {

    List<MaintenanceRecordResponse> getMaintenanceRecordList(@Param("page") IPage<MaintenanceRecordResponse> page,
                                                             @Param("params") Map<String, Object> params);


    MaintenanceRecordResponse getMaintenanceRecordById(@Param("mrId") String mrId);

    TaskResponse getTask(@Param("mrId") String mrId);

    TaskProcessin getTaskProcessin(@Param("mrId") String mrId);

    List<MaintenanceRecordModel> getList(@Param("params")Map<String, Object> query,
                                         @Param("page") IPage<MaintenanceRecordModel> page);
    List<MaintenanceRecordModel> getList2(@Param("params")Map<String, Object> query,
                                         @Param("page") IPage<MaintenanceRecordModel> page);
    MaintenanceRecordModel getDetail(@Param("mrId")String mrId);

    Integer getApplicationWarrantyStatusByMrId(@Param("mrId") String mrId);

    int updateRepairRecordStatusAllInOne();

    List<MaintenanceRecordModel> getMaintenanceRecordListByAwId(@Param("id") String id);

    String getRepairPersonByMrId(@Param("mrId") String mrId, @Param("actCode") String actCode);

    /**
     * 查询维修记录对应的结算维修状态，已结算优先于结算中和已作废结算单。
     *
     * @param mrIds 非空的维修记录ID列表
     * @return 维修记录ID及目标维修状态，结算草稿和空状态不参与同步
     */
    List<MaintenanceRecord> getSettlementRepairStates(@Param("mrIds") List<String> mrIds);

    /**
     * 查询已结算维修记录的支付状态，已支付优先于支付中和作废支付单。
     *
     * @param mrIds 非空的已结算维修记录ID列表
     * @return 维修记录ID及目标维修状态，仅关联审批通过的结算单
     */
    List<MaintenanceRecord> getPaymentRepairStates(@Param("mrIds") List<String> mrIds);
}
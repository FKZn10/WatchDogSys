package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSelectionRecordDtlModel;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecSelectionRecordDtlDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecSelectionRecordDtl;

import java.util.Date;
import java.util.List;

public interface ISecSelectionRecordDtlService extends IService<SecSelectionRecordDtl> {

    void createOrUpdateSecSelectionRecord(List<SecSelectionRecordDtlModel> datas, String srId);


    void createOrUpdateSecSelectionRecordDtl(List<SecSelectionRecordDtlModel> secSelectionRecordDtls, String ecId, String srId);

    void deleteAllBySrId(String srId);

    /**
     * 查询专家参会次数
     *
     * @param exId
     * @return
     */
    BaseResponse getSelectionRecordDtlCount(String exId);

    BaseResponse updateSecSelectionRecordDtl(SecSelectionRecordDtlModel request);

    Integer getExpertCount(String exId);

    /**
     * 指定专家在指定时间之前是否有会议
     * 判断专家是否可以暂停（请假）
     *
     * @return
     */
    boolean expertSelectionWithTime(String exId, Date time);

    void updateAttend(String exId, String srId, Integer attend);

    /**
     * 修改是否按时参加以及是否请假标记
     *
     * @param exId
     * @param srId
     * @param vacation
     */
    void updateVacation(String exId, String srId, Integer attend, Integer vacation);

    List<SecSelectionRecordDtl> getBySrId(String srId);


    List<SecSelectionRecordDtlModel>  getSelectionRecordDtlList(SecSelectionRecordDtlDto request);

    BaseResponse getSelectionRecordDtlListByExId(SecSelectionRecordDtlDto request);

    List<SecSelectionRecordDtl> getListByExIds(List<String> exIds);
}

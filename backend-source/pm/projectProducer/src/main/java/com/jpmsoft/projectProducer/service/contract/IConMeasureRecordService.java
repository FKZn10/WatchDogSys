package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConMearDeclareDto;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConMearUpperAmtDto;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConMeasureRecordDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConMeasureRecord;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConMeasureRecordCheck;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConMeasureRecordVo;

import java.util.List;

/**
 * @author 李文涛 aaron
 * @date 2022/07/13 08:29
 */
public interface IConMeasureRecordService extends IService<ConMeasureRecord> {

    void createOrUpdateConMeasureRecord(List<ConMeasureRecordDto> datas, String meaId);

    BaseResponse webAutoSaveConMeasureRecord(ConMeasureRecordDto request,String token);

    void updateFinalAmount(ConMeasureRecordCheck check);

    /**
     * 获取清单号对应的上次计量审核数据
     *
     * @param conId
     * @param boqdCode
     * @return
     */
    ConMearUpperAmtDto getConMearUpperAmt(String conId, String boqdCode);

    /**
     * 合同+清单号
     * 获取历史所有审批通过的计量单
     *
     * @param conId
     * @param boqdCode
     * @return
     */
    List<ConMeasureRecordVo> getAllConMearByConIdAndCode(String conId, String boqdCode);

    /**
     * 合同ID获取计量清单（累计申报情况）
     *
     * @param conId
     * @return
     */
    List<ConMearDeclareDto> getConMearDeclareByConId(String conId);

    /**
     * 计量报审退回发起人
     * 修改Con_Measure_Record表
     * Mear_Final_Amount=null
     * Mear_Final_Total=null
     *
     * @param meaId
     */
    void return2InitiatorRestoreAmount(String meaId);

    /**
     * 根据计量单判断是否可删除合同工程量清单列表
     *
     * @param boqdIds
     * @return
     */
    boolean can2deleteBoqd(List<String> boqdIds);

    ConMeasureRecord getConMeasureRecordByMeaBoqd(String meaId,String boqdId);


    List<ConMeasureRecordVo> getApprovedData(List<String> boqdIds);
}

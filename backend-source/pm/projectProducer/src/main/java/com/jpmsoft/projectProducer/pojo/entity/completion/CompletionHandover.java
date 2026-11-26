package com.jpmsoft.projectProducer.pojo.entity.completion;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 项目移交
 */
@Data
@TableName("Completion_Handover")
public class CompletionHandover {

    @TableId(type = IdType.INPUT)
    private String chId;

    private String pId;
    private String caId;
    private String chSupervisorId;
    private String chUserUnitId;
    private String chContractorId;
    private String chRemark;
    private String chEntityList;
    private String chDataList;
    private String chProductDesc;
    private String chQualityDesc;
    private String chWarrantyDesc;
    private String chCreateUid;
    private String chCreateUname;
    private Date chCreateTime;
    private Integer chState;
    private Date chStateTime;
    private String chContactTel;
    private String teId;

    private String chBlueprint;
    private String chDevice;


    //新
    //建成年份
    private String chYear;
    //竣工结算时间
    private String chSettlementTime;
    //移交验收时间
    private String chAcceptanceTime;
    //移交人
    private String chContact;
    //质保起始时间
    private String chWarrantyStart;
    //质保结束时间
    private String chWarrantyEnd;
    //厂商单位
    private String chFirmUnit;
    //厂商联系
    private String chFirmContact;
    //厂商联系电话
    private String chFirmPhone;
    //建筑信息库ID
    private String birId;

    //同步状态标记
    private Integer birState;


}

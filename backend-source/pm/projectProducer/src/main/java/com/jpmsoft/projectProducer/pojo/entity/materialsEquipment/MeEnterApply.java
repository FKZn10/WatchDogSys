package com.jpmsoft.projectProducer.pojo.entity.materialsEquipment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 材料设备进场报审
 */
@Data
@TableName("Me_Enter_Apply")
public class MeEnterApply {

    @TableId(type = IdType.INPUT)
    private String eaId;
    private String pId;
    private String conId;
    private String eaCode;
    private Date eaDate;
    private String eaSupervisorId;
    private String eaDesignId;
    private String eaBrandApply;
    private String eaPurchaseContract;
    private String eaProjectLocation;
    private String eaScCategory;
    private Integer eaWitness;
    private Integer eaProjectTeam;
    private String eaCreateUid;
    private String eaCreateUname;
    private Date eaCreateTime;
    private Integer eaState;
    private Date eaStateTime;
    private String eaFilePc;
    private String eaFilePtr;
    private String eaFileNmc;
    private String eaFileInvoice;
    private String eaFileCd;
    private String eaFileMas;
    private String eaFileAp;
    private Date eaAcceptTime;
    private String eaAcceptorId;
    private String eaAcceptorName;
    private String teId;
}

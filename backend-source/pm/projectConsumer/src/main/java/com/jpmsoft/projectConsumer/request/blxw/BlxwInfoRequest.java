package com.jpmsoft.projectConsumer.request.blxw;

import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;

import java.util.List;

@Data
public class BlxwInfoRequest extends BasePageEntity {
    private String id;
        //编号
    private String blxwNumber;
    //项目名称
    private String blxwProjectId;
    private String blxwProjectName;
    //违规标段
    private String blxwSectionId;
    private String blxwSectionName;
    //违规合同
    private String blxwContractId;
    private String blxwContractName;
        //责任单位
    private String blxwResponsibleUnitId;
    private String blxwResponsibleUnitName;
    //责任单位负责人
    private String blxwResponsibleUnitLeaderId;
    private String blxwResponsibleUnitLeaderName;
    //处理状态 0 未处理 1 已处理
    private Integer blxwState;
    private String noticeInfoCode;
    private String closeStatus;
    private String isRectifyTime;
    private String blxwHandleType;
    private Integer state;
    // 是否历史记录
    private Boolean isHistory;
    //不良行为等级
    private List<String> blxwLevels;
}
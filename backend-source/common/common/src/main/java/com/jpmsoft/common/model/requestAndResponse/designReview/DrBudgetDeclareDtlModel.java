package com.jpmsoft.common.model.requestAndResponse.designReview;

import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class DrBudgetDeclareDtlModel  implements Serializable {

    private String id;
    //概算id
    private String dbdId;

    //序号
    private String bddNumber;

    //父级节点id
    private String bddParentId;

    //清单编码
    private String bddCode;

    //上级清单编码
    private String bddParentCode;

    //项目及费用名称
    private String bddName;

    //建筑工程费
    private BigDecimal bddConstructionCost;

    //设备购置费
    private BigDecimal bddDeviceCost;

    //安装工程费
    private BigDecimal bddInstallCost;

    //其他费用
    private BigDecimal bddOtherCost;

    //合计
    private BigDecimal bddTotalCost;

    //单位
    private String bddUnit;

    //数量
    private Float bddCount;

    //单位造价
    private BigDecimal bddUnitCost;

    //排序
    private Integer bddOrder;

    //层级
    private Integer bddLevel;

    //全路径
    private String bddPath;

    //备注
    private String remark;

}

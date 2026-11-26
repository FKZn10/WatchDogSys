package com.jpmsoft.projectProducer.pojo.vo.contract;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ProgressDeclarationVo  implements Serializable {

    private static final long serialVersionUID = 441519531234326796L;

    private String id;  //id

    private String conId;

    private String pName;  //项目名

    private String conName;  //合同名

    private String conCode; //编号

    private String contrPartyAName; //合同甲方

    private String contrPartyBName;//合同乙方

    private BigDecimal conAmtStr;  //合同金额str
    private BigDecimal conAmtEnd;  //合同金额
    private List<String> conAmts;  //合同金额

    private List<String> settlementAmts; //结算金额
    private BigDecimal   settlementAmtStr; //结算金额
    private BigDecimal   settlementAmtEnd; //结算金额

    private List<String> progressDates;  //进度日期
    private String progressDateStr;  //进度日期开始
    private String progressDateEnd;  //进度日期结束

    private List<String> progress;  //合同进度
    private BigDecimal progressStr;
    private BigDecimal progressEnd;


    private List<String> declareDates;
    private String declareDateStr; //申报日期开始
    private String declareDateEnd; //申报日期结束

    private String declarePeople;  //申报人

    private String state;  //状态

    private String pdRemark;  //说明

    private Integer page;

    private Integer size;
}

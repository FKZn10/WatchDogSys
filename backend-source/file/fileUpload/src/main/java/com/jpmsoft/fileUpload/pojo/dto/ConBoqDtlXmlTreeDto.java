package com.jpmsoft.fileUpload.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/19 14:12
 */
@Data
public class ConBoqDtlXmlTreeDto extends ConBoqDtlDto implements Serializable {

    private static final long serialVersionUID = -4702023026418084944L;
    private Integer boqdSort;

    //编号序号
    private String boqdXh;
    //安全文明施工费
    private BigDecimal boqdAqsg;
    //规费
    private BigDecimal boqdGf;
    //计算基础
    private String boqdJsjc;
    //计算规则
    private String boqdJsgz;
    //取费基数
    private BigDecimal boqdQfjs;
    //人工费
    private BigDecimal boqdRgf;
    //主材费
    private BigDecimal boqdZcf;
    //设备费
    private BigDecimal boqdSbf;
    //辅材费
    private BigDecimal boqdFcf;
    //材料费
    private BigDecimal boqdClf;
    //机械费
    private BigDecimal boqdJxf;
    //管理费
    private BigDecimal boqdGlf;
    //利润
    private BigDecimal boqdLr;
    //综合人工工日
    private BigDecimal boqdZgr;
    //人材机含量
    private BigDecimal boqdRcjhl;
    //人材机ID
    private String boqdRcjId;
    //基准单价
    private BigDecimal boqdJzDj;
    //投标单价
    private BigDecimal boqdTbDj;
    //风险系数
    private BigDecimal boqdFxxs;
    List<ConBoqDtlXmlTreeDto> childNodes;

}

package com.jpmsoft.fileUpload.pojo.dto.beijing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MaterialDeviceSamplingInspectionDTO {

    // 标题
    String title;

    // 资料编码
    String code;

    // 项目名称
    String projectName;

    // 合同名称
    String contractName;

    // 试件名称
    String testPieceName;

    // 生产厂家
    String manufacturerName;

    // 试件品种
    String testPieceVariety;

    // 材料出厂编号
    String materialFactoryCode;

    // 试件规格型号
    String testPieceFlavorModel;

    // 材料进场时间
    Date materialEnterTime;

    // 材料进场数量
    Double materialEnterNumber;

    // 代表数量
    Double representativeNumber;

    // 试样编号
    String testPieceCode;

    // 取样组数
    Double samplingGroupNumber;

    // 抽样时间
    Date samplingTime;

    // 取样地点
    String samplingLocation;

    // "使用部位（取样部位）"
    String samplingPart;

    // 检测项目（设计要求)
    String testProjectDesignRequirement;

    // 见证记录
    String witnessRecord;

    // 产品标准
    String productStandard;

    // 验收规范
    String acceptanceSpecification;

    // 设计要求
    String designRequirement;

    // 抽样人签字
    String samplerSignature;

    // 抽样人签字日期
    Date samplerSignatureDate;

    // 见证人签字
    String witnessSignature;

    // 见证人签字日期
    Date witnessSignatureDate;

    // 检测单位
    String testUnitName;

    // 送检时间
    Date inspectionSubmitTime;

    // 附件
    String filesRelationId;

    // 附件链接地址
    String filesLink;
    
}

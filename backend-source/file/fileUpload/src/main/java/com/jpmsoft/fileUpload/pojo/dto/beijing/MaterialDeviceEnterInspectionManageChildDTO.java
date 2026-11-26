package com.jpmsoft.fileUpload.pojo.dto.beijing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MaterialDeviceEnterInspectionManageChildDTO implements Serializable {

    // 名称
    String name;

    // 规格
    String flavor;

    // 进场数量
    Double enterNumber;

    // 生产厂家
    String manufacturerName;

    // 质量证明文件核查
    String qualityCertificationFileVerification;

    // 外观检验结果
    String appearanceInspectionResult;

    // 复验情况
    String retestSituation;
}

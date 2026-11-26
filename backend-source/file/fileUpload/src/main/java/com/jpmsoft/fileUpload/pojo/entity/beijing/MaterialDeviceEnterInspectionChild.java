package com.jpmsoft.fileUpload.pojo.entity.beijing;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("material_device_enter_inspection_child")
@Data
public class MaterialDeviceEnterInspectionChild implements Serializable {

    // 主键ID
    @TableId(type = IdType.INPUT)
    String uuid;

    // 材料设备进场验货id
    String materialDeviceEnterInspectionId;

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

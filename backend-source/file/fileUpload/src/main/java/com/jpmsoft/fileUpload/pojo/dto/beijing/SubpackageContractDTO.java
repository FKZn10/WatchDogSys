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
public class SubpackageContractDTO {

    // 项目名称
    String projectName;

    // 总包合同名称
    String mainContractName;

    // 总包合同编码
    String mainContractCode;

    // 分包合同名称
    String subpackageContractName;

    // 分包合同编码
    String subpackageContractCode;

    // 合同金额
    Double contractAmount;

    // 甲方单位
    String partyAsUnitName;

    // 签订日期
    Date signDate;

    // 驻场负责人
    String residentDirectorName;

    // 联系电话
    String residentDirectorPhone;

    // 收款账户
    String acceptanceMoneyHolderName;

    // 收款账号
    String receiveAccount;

    // 合同类型
    String contractType;

    // 附件上传
    String filesLink;

    // 乙方单位名称
    String partyBsUnitName;

    // 乙方单位类型
    String partyBsUnitType;

}

package com.jpmsoft.fileUpload.pojo.dto;

import lombok.Data;

import java.util.Date;

/**
 * 付款申请书-审批流程记录（直查 Sys_Task_Processin join SYS_User）
 * <p>
 * 用于支付确认表按节点名(actName)通用匹配填充 意见/签名/日期.
 *
 * @author 付款打印
 */
@Data
public class PayApplyApproveDto {

    /**
     * 审批节点名
     */
    private String actName;

    /**
     * 审批意见
     */
    private String tpExplain;

    /**
     * 审批人姓名
     */
    private String uName;

    /**
     * 电子签名图片附件ID(U_Signature, 对应 SysFileAnnex.fileId)
     */
    private String uSignature;

    /**
     * 审批时间
     */
    private Date tpDotime;

    /**
     * 签名图片下载URL(拼接 downloadUrl)
     */
    private String imgUrl;
}

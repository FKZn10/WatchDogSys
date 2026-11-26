package com.jpmsoft.common.model.response.InvestmentControl;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/4/1 15:38
 * @Description:
 * @Version: 1.0
 */
@Data
public class TransferStatusUpdateRequest implements Serializable {

    private static final long serialVersionUID = 4057506543163734717L;

    private List<String> ids;

    /**
     * 单据流转状态：01-第一节点未提交(第一节点可编辑)；02-被驳回到第一节点(第一节点可编辑)；03-提交到第二节点(第二节点可编辑)；04被驳回到第二节点(第二节点可编辑)；05-提交到第三节点(第三节点可编辑)；06完结(全部不可编辑)
     */
    private String transferStatus;
    private String orRemarks;
}

package com.jpmsoft.common.model.requestAndResponse.InvestmentControl;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

@Data
public class TreeNode extends BaseModeRequest {
    private String id;        // 唯一标识
    private String parentId;  // 父节点ID
    private String name;      // 节点名称
    private String serial;    // 序号列内容（如"一、(一)1.1"）
    private Integer level;        // 层级深度（根节点为0）
    private String sheetName;
    private Integer rowNum;
}

package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 根据项目ID集合批量查询项目组织机构的人员
 *
 * @Author: 李文涛 aaron
 * @date: 2023/11/9 14:50
 */
@Data
public class DepPUserBatchSelectDTO extends BaseModeRequest implements Serializable {

    private List<String> projectIds;
    private List<String> ppNames;
}

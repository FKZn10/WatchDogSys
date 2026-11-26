package com.jpmsoft.projectProducer.pojo.entity.bim;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2022/6/10 10:03
 * @Description: PROJECT_BIM_TREE_MAP 项目与BIM模型树关联表
 * @Version: 1.0
 */
@Data
@TableName("PROJECT_BIM_TREE_MAP")
public class ProjectBimTreeMap {

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * bim模型树id
     */
    private String bimTreeId;

    private String createId;

    private String createName;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
}

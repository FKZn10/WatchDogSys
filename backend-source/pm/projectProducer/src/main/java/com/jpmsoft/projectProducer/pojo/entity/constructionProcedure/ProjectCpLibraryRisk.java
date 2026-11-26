package com.jpmsoft.projectProducer.pojo.entity.constructionProcedure;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 项目建设程序库-风险点防控措施关联表
 */
@Data
@TableName("Project_Cp_Library_Risk")
public class ProjectCpLibraryRisk {
    @TableId(type = IdType.INPUT)
    private String id;
    private String cplId;
    private String rpmId;
}

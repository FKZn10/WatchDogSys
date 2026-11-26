package com.jpmsoft.projectProducer.pojo.entity.design;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 图纸管理
 */
@Data
@TableName("Design_Drawing")
public class DesignDrawing {

    @TableId(type = IdType.INPUT)
    private String ddId;
    //项目ID
    private String pId;
    //设计图名称
    private String ddName;
    //设计图类型（字典编码）
    private String ddType;
    //版本号
    private String ddVersionNumber;
    //单体
    private String ddMonomer;
    //图纸专业
    private String ddProfessional;
    //图纸文件格式
    private String ddFileFormat;
    //图纸文件上传
    private String ddFileRelationId;
    //图纸备注
    private String ddRemark;
    private String ddCreateUid;
    private String ddCreateUname;
    private Date ddCreateTime;
    private Integer ddState;
    private Date ddStateTime;
    private String teId;

    private String ddCategory;
}

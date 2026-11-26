package com.jpmsoft.projectProducer.pojo.entity.enghonorpunish;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 工程荣誉	(EngHonor)实体类
 *
 * @author c
 * @since 2024-12-03 10:40:10
 */
@Data
@TableName("Eng_Honor")
public class EngHonor implements Serializable {
    private static final long serialVersionUID = -68573904147611542L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 奖项名称
     */
    private String awardName;
    /**
     * 奖项类别
     */
    private String awardType;
    /**
     * 奖项级别
     */
    private String awardLevel;
    /**
     * 报奖项目
     */
    private String awardWinningProject;
    /**
     * 获奖单位
     */
    private String winningUnit;
    /**
     * 颁奖部门
     */
    private String awardDepartment;
    /**
     * 获奖时间
     */
    private Date winningTime;
    /**
     * 获奖文件
     */
    private String winningFile;
    /**
     * 创建人id
     */
    private String createUid;
    /**
     * 创建人姓名
     */
    private String createUname;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 审批状态
     */
    private Integer state;
    /**
     * 审批时间
     */
    private Date stateTime;


}


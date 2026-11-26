package com.jpmsoft.projectProducer.pojo.entity.qualitySafety;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 安全责任书签订
 */
@Data
@TableName("sign_safety_respon")
public class SignSafetyRespon {

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private String saId;

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
     * 流程状态
     */
    private Integer state;

    /**
     * 审批时间
     */
    private Date stateTime;

    /**
     * 姓名，取人员申报表的申报人
     */
    private String saName;

    /**
     * 责任单位id，取人员申报表的申报单位
     */
    private String companyId;

    /**
     * 岗位id
     */
    private String saPostId;

    /**
     * 项目id
     */
    private String pId;

    /**
     * 合同id
     */
    private String conId;

    /**
     * 签订时间
     */
    private Date saSignTime;

    /**
     * 签订附件
     */
    private String saSignAttach;

    /**
     * 岗位（冗余岗位名称），取人员申报表的变更后岗位
     */
    private String saPost;

    /**
     * 关联的人员申报表id
     */
    private String repPersonId;

    /**
     * 删除标识，0未删除1已删除
     */
    private Integer deleted;
}

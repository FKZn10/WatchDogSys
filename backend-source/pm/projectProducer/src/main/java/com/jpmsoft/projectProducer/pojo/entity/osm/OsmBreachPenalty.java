package com.jpmsoft.projectProducer.pojo.entity.osm;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
 * 违约处罚(OsmBreachPenalty)实体类
 *
 * @since 2024-12-05 11:54:58
 */
@Data
@TableName("osm_breach_penalty")
public class OsmBreachPenalty implements Serializable {
    private static final long serialVersionUID = 514627689941054333L;
    /**
     * id
     */
    private String id;
    /**
     * 处罚事项名称
     */
    private String punishItemName;
    /**
     * 事项类别
     */
    private String itemType;

    /**
     * 项目ID
     */
    private String pId;
    /**
     * 合同id
     */
    private String conId;
    /**
     * 乙方单位
     */
    private String unitId;
    /**
     * 处罚金额
     */
    private Double punishMoney;
    /**
     * 办理人id
     */
    private String handlerUserId;
    /**
     * 办理人姓名
     */
    private String handlerUserName;
    /**
     * 办理时间
     */
    private Date handlerTime;
    /**
     * 是否需要用印
     */
    private Integer usingSeals;
    /**
     * 用印类型
     */
    private String sealsType;
    /**
     * 用印个数
     */
    private String sealsNum;
    /**
     * 用印文件份数
     */
    private Integer sealsFileNum;
    /**
     * 处罚事项描述
     */
    private String remark;
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
     * 处理描述
     */
    private String 	obpClms;

    /**
     * 处理相关文件
     */
    private String handlerFile;

    /**
     * 处理类型
     */
    private String handlerType;

    /**
     * actualPaymentAmount
     */
    private BigDecimal actualPaymentAmount;



}


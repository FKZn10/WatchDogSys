package com.jpmsoft.projectProducer.pojo.entity.zdyxy;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("T_GXXX_YXSDWJBSJZLB")
public class TGxxxYxsdwjbsjzlb implements Serializable {

    private static final long serialVersionUID = 9141771978189632186L;
    /**
     * 主键ID
     */
    private String globalId;

    /**
     * 单位代码
     */
    private String dwdm;

    /**
     * 单位名称
     */
    private String dwmc;

    /**
     * 单位简称
     */
    private String dwjc;

    /**
     * 单位别名
     */
    private String dwbm;

    /**
     * 隶属单位号
     */
    private String lsdwh;

    /**
     * 单位类别码
     */
    private String dwlbm;

    /**
     * 单位类型码
     */
    private String dwlxm;

    /**
     * 单位办别码
     */
    private String dwbbm;

    /**
     * 办公地点
     */
    private String bgdd;
}
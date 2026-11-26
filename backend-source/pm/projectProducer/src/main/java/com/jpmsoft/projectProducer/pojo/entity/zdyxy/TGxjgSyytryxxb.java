package com.jpmsoft.projectProducer.pojo.entity.zdyxy;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_gxjg_syytryxxb")
public class TGxjgSyytryxxb implements Serializable {
    private static final long serialVersionUID = -3049554156024833999L;
    /**
     * 主键
     */
    private String globalId;

    /**
     * 工号
     */
    private String gh;

    /**
     * 姓名
     */
    private String xm;

    /**
     * 性别
     */
    private String xb;

    /**
     * 单位代码
     */
    private String dwdm;

    /**
     * 单位名称
     */
    private String dwmc;
}
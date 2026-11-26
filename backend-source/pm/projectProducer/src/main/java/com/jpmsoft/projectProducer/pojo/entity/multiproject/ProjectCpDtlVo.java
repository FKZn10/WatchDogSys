package com.jpmsoft.projectProducer.pojo.entity.multiproject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 项目进度
 * </p>
 *
 * @author 刘凯锋
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProjectCpDtlVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 项目id
     */
    private String pId;

    /**
     * 项目名称
     */
    private String PName;

    /**
     * 建设程序编码
     */
    private String CpdCode;

    /**
     * 建设程序编码名称
     */
    private String CpdName;

}

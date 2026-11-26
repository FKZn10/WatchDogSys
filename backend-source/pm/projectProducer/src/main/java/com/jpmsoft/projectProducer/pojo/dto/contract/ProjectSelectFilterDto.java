package com.jpmsoft.projectProducer.pojo.dto.contract;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 基础配置cjProjectSelectFilter
 *
 * @author: 李文涛 aaron
 * @date: 2022/7/29 9:56
 */
@Data
public class ProjectSelectFilterDto implements Serializable {

    private static final long serialVersionUID = -721951268379806355L;
    //合同类型
    private List<String> conType;
    //建设程序编码
    private List<String> cpdCode;
}

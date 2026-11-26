package com.jpmsoft.userProducer.pojo.vo;

import com.jpmsoft.userProducer.pojo.entity.SysTOrganization;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/29 9:58
 */
@Data
public class SysTOrganizationVo extends SysTOrganization implements Serializable {
    private static final long serialVersionUID = 5831533788431051168L;
    private String toCName;
    private String toCAbbName;
}

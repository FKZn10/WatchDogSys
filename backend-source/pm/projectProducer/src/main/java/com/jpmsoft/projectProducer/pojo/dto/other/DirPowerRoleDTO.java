package com.jpmsoft.projectProducer.pojo.dto.other;

import lombok.Data;

import java.io.Serializable;

/**
 * 目录权限配置
 *
 * @Author: 李文涛 aaron
 * @date: 2023/7/26 9:08
 */
@Data
public class DirPowerRoleDTO implements Serializable {
    private String roleId;
    private String powerCode;
}

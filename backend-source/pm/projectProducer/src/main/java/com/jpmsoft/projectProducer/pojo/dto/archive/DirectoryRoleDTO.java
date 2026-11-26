package com.jpmsoft.projectProducer.pojo.dto.archive;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 当前操作人具备的档案目录以及对应的权限编码
 *
 * @Author: 李文涛 aaron
 * @date: 2023/7/25 10:40
 */
@Data
public class DirectoryRoleDTO implements Serializable {

    private String adId;
    private List<String> powerCodes;
}

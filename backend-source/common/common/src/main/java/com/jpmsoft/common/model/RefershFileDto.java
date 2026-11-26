package com.jpmsoft.common.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/8 9:06
 */
@Data
public class RefershFileDto implements Serializable {

    private List<String> relationIds;
    private List<String> fileIds;
    private String entityId;
}

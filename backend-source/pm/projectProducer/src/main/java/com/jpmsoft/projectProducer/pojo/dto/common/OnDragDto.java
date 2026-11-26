package com.jpmsoft.projectProducer.pojo.dto.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/6 17:28
 */
@Data
public class OnDragDto implements Serializable {

    private String moveId;
    private String targetId;
    private Integer moveSort;
    private Integer targetSort;
}

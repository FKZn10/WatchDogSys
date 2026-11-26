package com.jpmsoft.projectProducer.pojo.request.thirdDock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/5/22 14:10
 * @Description:
 * @Version: 1.0
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MainDataRequest implements Serializable {
    private static final long serialVersionUID = 4057506543556324717L;

    private String fieldName;

    private Object fieldValue;
}

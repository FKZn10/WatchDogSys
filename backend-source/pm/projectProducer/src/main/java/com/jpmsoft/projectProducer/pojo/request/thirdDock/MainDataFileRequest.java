package com.jpmsoft.projectProducer.pojo.request.thirdDock;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/5/22 14:10
 * @Description:
 * @Version: 1.0
 */
@Data
public class MainDataFileRequest implements Serializable {
    private static final long serialVersionUID = 4057506543556324127L;

    private String fileName;

    private Object filePath;
}

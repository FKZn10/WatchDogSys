package com.jpmsoft.projectConsumer.pojo.dto.construction;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 建设程序成果附件类型批量修改
 * @Author: 李文涛 aaron
 * @date: 2024/9/12 15:26
 */
@Data
public class LibraryFileTypeDTO implements Serializable {

    private List<String> ids;
    private String fileType;
}

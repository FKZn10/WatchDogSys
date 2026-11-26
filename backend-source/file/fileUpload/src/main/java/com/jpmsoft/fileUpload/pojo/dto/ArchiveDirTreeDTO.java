package com.jpmsoft.fileUpload.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/4/17 9:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArchiveDirTreeDTO implements Serializable {

    private static final long serialVersionUID = -998327786698452392L;
    private String adId;
    private String adName;
    private List<ArchiveDirTreeDTO> childNodes;
}

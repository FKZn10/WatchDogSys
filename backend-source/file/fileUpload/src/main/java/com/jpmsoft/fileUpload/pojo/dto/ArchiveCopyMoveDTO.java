package com.jpmsoft.fileUpload.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 归档文件复制迁移
 *
 * @Author: 李文涛 aaron
 * @date: 2024/5/10 15:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArchiveCopyMoveDTO implements Serializable {

    private static final long serialVersionUID = -5244137360360263692L;
    //需要复制的归档文件记录
    private List<String> afIds;
    //目标归档目录
    private String adId;
}

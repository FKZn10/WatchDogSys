package com.jpmsoft.fileUpload.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

/**
 * 全文检索入参
 *
 * @Author: 李文涛 aaron
 * @date: 2023/7/24 13:35
 */
@Data
public class FullTextDTO extends BaseModeRequest {
    private String text;
}

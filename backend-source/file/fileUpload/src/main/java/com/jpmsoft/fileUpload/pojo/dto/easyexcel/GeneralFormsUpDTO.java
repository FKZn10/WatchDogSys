package com.jpmsoft.fileUpload.pojo.dto.easyexcel;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/8 14:08
 */
@Data
@Accessors(chain = true)
public class GeneralFormsUpDTO implements Serializable {

    /**
     * 字段数据
     */
    private List<Map<String, String>> strData;
    /**
     * 附件数据
     */
    private List<Map<String, String>> fileData;
    /**
     * 字典字段字段对应字典编码
     */
    private Map<String, String> dictionaryMp;
    /**
     * 链表字段对应SQL
     */
    private Map<String, String> keySql;
    /**
     * 关联为项目表的字段名
     */
    private String projectKey;
    /**
     * 额外附件
     */
    private List<String> otherFiles;
}

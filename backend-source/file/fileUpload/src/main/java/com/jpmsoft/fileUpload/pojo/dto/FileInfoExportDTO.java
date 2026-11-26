package com.jpmsoft.fileUpload.pojo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@TableName("SYS_FileAnnex")
@Accessors(chain = true)
public class FileInfoExportDTO {

    /**
     * 文件路径
     */
    @ExcelProperty(index = 0, value = "附件路径")
    private String filePath;
    @ExcelProperty(index = 1, value = "附件名称")
    private String fileName;
    @ExcelProperty(index = 2, value = "上传时间")
    private Date fileUploadTime;
    @ExcelProperty(index = 3, value = "上传人")
    private String fileCreateUName;
    @ExcelProperty(index = 4, value = "文档模板编码")
    private String fileCode;
    @ExcelProperty(index = 5, value = "业务模块")
    private String fileCodeName;
    /**
     * 是否已归档
     * 0 未归档
     * 1 已归档
     */
    @ExcelProperty(index = 6, value = "是否已归档（1已归档0未归档）")
    private Integer archive;
    /**
     * 附件上传位置
     * 0：普通上传路径
     * 1：归档上传路径
     */
    @ExcelProperty(index = 7, value = "存储路径（1归档路径0普通上传路径）")
    private Integer fileLocation;
    /**
     * 附件来源
     * 01：用户上传
     * 02：系统关联（历史上传文件）
     */
    @ExcelProperty(index = 8, value = "任务编码")
    private String fileSourceType;
}

package com.jpmsoft.fileUpload.pojo.entity.beijing;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("auto_import_file_record")
public class AutoImportFileRecord {

    public static final Integer HANDLE_SUCCESS = 1;

    public static final Integer HANDLE_FAILED = 0;

    // 主键id
    @TableId(value = "uuid",type = IdType.INPUT)
    private String uuid;

    // 文件名
    private String fileName;

    // md5值
    private String md5;

    // 处理时间
    private Date handleTime;

    // 处理成功，1成功 ，0失败
    private Integer handleSuccess;

    // 处理结果
    private String handleResult;

    // 记录创建时间
    private Date createTime;

}

package com.jpmsoft.fileUpload.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 专家选取记录
 */
@Data
@TableName("Sec_Selection_Record")
public class SecSelectionRecord {
    @TableId(type = IdType.INPUT)
    private String srId;
    private String srType;
    private Date srAccessTime;
    private String srAbout;
    private Date srStart;
    private String srCompetent;
    private String srItems;
    private String srDataLists;
    private String srNotes;
    private String srContact;
    private String srPhone;

}

package com.jpmsoft.fileUpload.pojo.dto.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.jpmsoft.fileUpload.easyexcel.annotations.ExcelValid;
import lombok.Data;

import java.util.Date;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/2 10:45
 */
@Data
public class SecExpertDto {

    @ExcelValid
    @ExcelProperty(index = 1)
    private String exName;

    @ExcelValid
    @ExcelProperty(index = 2)
    private String exSex;

    @ExcelValid
    @DateTimeFormat("yyyy-MM-dd")
    @ExcelProperty(index = 3)
    private Date exBirthday;

    @ExcelValid
    @ExcelProperty(index = 4)
    private String exPolitics;

    @ExcelValid
    @ExcelProperty(index = 5)
    private String exFamily;

    @ExcelValid
    @ExcelProperty(index = 6)
    private String exCategory;

    @ExcelValid
    @ExcelProperty(index = 7)
    private String exType;

    @ExcelValid
    @ExcelProperty(index = 8)
    private String exEducation;

    @ExcelValid
    @ExcelProperty(index = 9)
    private String exCellphoneNumber1;

    @ExcelValid
    @ExcelProperty(index = 10)
    private String exIdNumber;

    @ExcelValid
    @ExcelProperty(index = 11)
    private String exWorkAddress;

    @ExcelValid
    @ExcelProperty(index = 12)
    private String exPost;

    @ExcelValid
    @ExcelProperty(index = 13)
    private String exProfessionalCategory;

    @ExcelValid
    @DateTimeFormat("yyyy-MM-dd")
    @ExcelProperty(index = 14)
    private Date exStartDate;

    @ExcelProperty(index = 15)
    private String exTitle;

    @DateTimeFormat("yyyy-MM-dd")
    @ExcelProperty(index = 16)
    private Date exTilteDate;

    @ExcelProperty(index = 17)
    private String exTitleNumber;

    @ExcelProperty(index = 18)
    private String exPracticeQualification;

    @ExcelProperty(index = 19)
    private String exQualification;

    @ExcelProperty(index = 20)
    private String exPracticeCode;

    @ExcelValid
    @ExcelProperty(index = 21)
    private String exMainWork;

    @ExcelValid
    @ExcelProperty(index = 22)
    private String exPersonalResult;
}

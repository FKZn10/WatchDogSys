package com.jpmsoft.fileUpload.pojo.dto.jaxbxml;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.Date;

@Data
@XmlType(name = "column")
@XmlRootElement(name = "column")
@XmlAccessorType(XmlAccessType.FIELD)
public class SysTableColumnXmlDTO {
    @XmlAttribute(name = "columnId")
    public String columnId;
    @XmlElement(name = "tableId")
    public String tableId;
    @XmlElement(name = "columnName")
    public String columnName;
    @XmlElement(name = "columnCode")
    public String columnCode;
    @XmlElement(name = "columnType")
    public String columnType;
    @XmlElement(name = "columnRemark")
    public String columnRemark;
    @XmlElement(name = "columnNull")
    public String columnNull;
    @XmlElement(name = "columnPk")
    public String columnPk;
    @XmlElement(name = "columnLength")
    public Integer columnLength;
    @XmlElement(name = "columnCsharptype")
    public String columnCsharptype;
    @XmlElement(name = "columnCsharpcode")
    public String columnCsharpcode;
    @XmlElement(name = "columnFormvisible")
    public String columnFormvisible;
    @XmlElement(name = "columnFormtype")
    public String columnFormtype;
    @XmlElement(name = "columnTime")
    public Date columnTime;
    @XmlElement(name = "columnNumber")
    public Integer columnNumber;
    @XmlElement(name = "columnJavaSmallHump")
    public String columnJavaSmallHump;
    @XmlElement(name = "tableCode")
    private String tableCode;
    @XmlElement(name = "columnFile")
    private Boolean columnFile;
    @XmlElement(name = "filterType")
    private Integer filterType;
    @XmlElement(name = "assocTableCode")
    private String assocTableCode;
    @XmlElement(name = "assocColumnCode")
    private String assocColumnCode;
    @XmlElement(name = "assocColumnPk")
    private String assocColumnPk;
    @XmlElement(name = "assocShowCode")
    private String assocShowCode;
    @XmlElement(name = "assocShowName")
    private String assocShowName;
    @XmlElement(name = "columnOnly")
    private Boolean columnOnly;
}

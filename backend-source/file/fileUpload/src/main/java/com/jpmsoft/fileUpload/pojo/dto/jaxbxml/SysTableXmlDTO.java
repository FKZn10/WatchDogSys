package com.jpmsoft.fileUpload.pojo.dto.jaxbxml;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.*;
import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@XmlRootElement(name = "table")
@XmlType(name="table")
@XmlAccessorType(XmlAccessType.FIELD)
public class SysTableXmlDTO {

    @XmlAttribute(name = "tableId")
    public String tableId;
    @XmlElement(name="tableName")
    public String tableName;
    @XmlElement(name="tableCode")
    public String tableCode;
    @XmlElement(name="tableRemark")
    public String tableRemark;
    @XmlElement(name="tableCreatetime")
    public Date tableCreatetime;
    @XmlElement(name="tableCreateuser")
    public String tableCreateuser;
    @XmlElement(name="tableCreateusername")
    public String tableCreateusername;
    @XmlElement(name="tableStats")
    public String tableStats;
    @XmlElement(name="tablePk")
    public String tablePk;
    @XmlElement(name="tableClassname")
    public String tableClassname;
    @XmlElement(name="tableDb")
    public String tableDb;
    @XmlElement(name="tableType")
    public String tableType;
    @XmlElement(name="columnNumber")
    public Integer columnNumber;
    @XmlElement(name="columnJavaSmallHump")
    public String columnJavaSmallHump;
    @XmlElement(name="columnFile")
    private Boolean columnFile;
    @XmlElement(name="filterType")
    private Integer filterType;
    @XmlElement(name="assocTableCode")
    private String assocTableCode;
    @XmlElement(name="assocColumnCode")
    private String assocColumnCode;
    @XmlElement(name="assocColumnPk")
    private String assocColumnPk;
    @XmlElement(name="assocShowCode")
    private String assocShowCode;
    @XmlElement(name="assocShowName")
    private String assocShowName;
    @XmlElement(name="columnOnly")
    private Boolean columnOnly;
    @XmlElement(name="columnExp")
    private Boolean columnExp;
    @XmlElement(name="columnImp")
    private Boolean columnImp;

    @XmlElement(name="column")
    private List<SysTableColumnXmlDTO> sysTableColumns;
}

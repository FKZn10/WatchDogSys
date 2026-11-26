package com.jpmsoft.fileUpload.pojo.dto.jaxbxml;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/22 13:59
 */
@Data
@XmlRootElement(name = "tables")
@XmlAccessorType(XmlAccessType.FIELD)
public class TableRootXmlDTO {

    @XmlElement(name="table")
    private List<SysTableXmlDTO> tables;
}

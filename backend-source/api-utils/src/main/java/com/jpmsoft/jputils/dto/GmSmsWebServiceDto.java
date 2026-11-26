package com.jpmsoft.jputils.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "message")
@XmlType(propOrder = {"orgaddr", "mobile", "content", "sendtime", "needreport",})
public class GmSmsWebServiceDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String orgaddr;
    private String mobile;
    private String content;
    private String sendtime;
    private String needreport;

}

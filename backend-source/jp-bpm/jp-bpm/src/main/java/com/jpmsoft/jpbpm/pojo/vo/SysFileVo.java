package com.jpmsoft.jpbpm.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysFileVo implements Serializable {

    private static final long serialVersionUID = 5400456316078852066L;
    public String fileid;
    public String tId;
    public String filetitle;
    public String filecode;
    public String filekeyword;
    public String fileemergencylevel;
    public String filedescription;
    public Integer fileflowstatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date filecreatedate;
    public String filecreateuser;
    public String filecreateusername;
    public String filecreatecompanyid;
    public String fileproject;
    public String filesectionid;
    public String filecontract;
    public String flowId;
    public String fileRelationId;
    public String tCode;
}

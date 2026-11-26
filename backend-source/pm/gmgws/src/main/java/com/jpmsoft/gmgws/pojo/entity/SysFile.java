package com.jpmsoft.gmgws.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 文档信息
 */
@Data
@TableName("SYS_File")
public class SysFile {
    @TableId(type = IdType.INPUT)
    public String fileid;
    public String tId;
    public String filetitle;
    public String filecode;
    public String filekeyword;
    public String fileemergencylevel;
    public String filedescription;
    public String fileflowstatus;
    public Date filecreatedate;
    public String filecreateuser;
    public String filecreateusername;
    public String filecreatecompanyid;
    public String fileproject;
    public String filesectionid;
    public String filecontract;
    private String fileRelationId;
    private String tCode;

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public String getFiletitle() {
        return filetitle;
    }

    public void setFiletitle(String filetitle) {
        this.filetitle = filetitle;
    }

    public String getFilecode() {
        return filecode;
    }

    public void setFilecode(String filecode) {
        this.filecode = filecode;
    }

    public String getFilekeyword() {
        return filekeyword;
    }

    public void setFilekeyword(String filekeyword) {
        this.filekeyword = filekeyword;
    }

    public String getFileemergencylevel() {
        return fileemergencylevel;
    }

    public void setFileemergencylevel(String fileemergencylevel) {
        this.fileemergencylevel = fileemergencylevel;
    }

    public String getFiledescription() {
        return filedescription;
    }

    public void setFiledescription(String filedescription) {
        this.filedescription = filedescription;
    }

    public String getFileflowstatus() {
        return fileflowstatus;
    }

    public void setFileflowstatus(String fileflowstatus) {
        this.fileflowstatus = fileflowstatus;
    }

    public Date getFilecreatedate() {
        return filecreatedate;
    }

    public void setFilecreatedate(Date filecreatedate) {
        this.filecreatedate = filecreatedate;
    }

    public String getFilecreateuser() {
        return filecreateuser;
    }

    public void setFilecreateuser(String filecreateuser) {
        this.filecreateuser = filecreateuser;
    }

    public String getFilecreateuserName() {
        return filecreateusername;
    }

    public void setFilecreateuserName(String filecreateusername) {
        this.filecreateusername = filecreateusername;
    }

    public String getFilecreatecompanyid() {
        return filecreatecompanyid;
    }

    public void setFilecreatecompanyid(String filecreatecompanyid) {
        this.filecreatecompanyid = filecreatecompanyid;
    }

    public String getFileproject() {
        return fileproject;
    }

    public void setFileproject(String fileproject) {
        this.fileproject = fileproject;
    }

    public String getFilesectionid() {
        return filesectionid;
    }

    public void setFilesectionid(String filesectionid) {
        this.filesectionid = filesectionid;
    }

    public String getFilecontract() {
        return filecontract;
    }

    public void setFilecontract(String filecontract) {
        this.filecontract = filecontract;
    }

    public String getFilecreateusername() {
        return filecreateusername;
    }

    public void setFilecreateusername(String filecreateusername) {
        this.filecreateusername = filecreateusername;
    }

    public String getFileRelationId() {
        return fileRelationId;
    }

    public void setFileRelationId(String fileRelationId) {
        this.fileRelationId = fileRelationId;
    }

    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }
}

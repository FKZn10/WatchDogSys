package com.jpmsoft.fileUpload.pojo.dto;

import com.jpmsoft.fileUpload.constants.FileMsg;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class UploadDtoWithNoBlank implements Serializable {

    private static final long serialVersionUID = -401551863999629000L;
    @NotBlank(message = FileMsg.upNoParaFileId)
    private String fileId;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

}
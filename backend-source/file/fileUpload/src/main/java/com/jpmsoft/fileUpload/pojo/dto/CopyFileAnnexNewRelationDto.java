package com.jpmsoft.fileUpload.pojo.dto;

import java.io.Serializable;
import java.util.List;

public class CopyFileAnnexNewRelationDto implements Serializable {

    private static final long serialVersionUID = 6761668701750844534L;

    private List<String> oldIds;
    private List<String> newIds;

    private String entityId;
    public List<String> getOldIds() {
        return oldIds;
    }

    public void setOldIds(List<String> oldIds) {
        this.oldIds = oldIds;
    }

    public List<String> getNewIds() {
        return newIds;
    }

    public void setNewIds(List<String> newIds) {
        this.newIds = newIds;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
}

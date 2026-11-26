package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;
import com.jpmsoft.userProducer.pojo.entity.SysToolTableRelationship;

import java.io.Serializable;
import java.util.List;

public class SysToolTableRelationshipDto extends BaseModeRequest implements Serializable {
    private static final long serialVersionUID = -2088479187599790363L;
    private List<SysToolTableRelationship> tList;
    private List<String> deleteIds;

    public List<SysToolTableRelationship> gettList() {
        return tList;
    }

    public void settList(List<SysToolTableRelationship> tList) {
        this.tList = tList;
    }

    public List<String> getDeleteIds() {
        return deleteIds;
    }

    public void setDeleteIds(List<String> deleteIds) {
        this.deleteIds = deleteIds;
    }
}

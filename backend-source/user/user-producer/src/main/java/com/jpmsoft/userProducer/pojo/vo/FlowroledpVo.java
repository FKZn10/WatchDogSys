package com.jpmsoft.userProducer.pojo.vo;

import java.io.Serializable;
import java.util.List;

public class FlowroledpVo implements Serializable {
    private static final long serialVersionUID = -4884631477377212485L;
    public List<DepartmentVo> department;
    public List<PositionVo> position;
    public List<RoleVo> role;

    public List<DepartmentVo> getDepartment() {
        return department;
    }

    public void setDepartment(List<DepartmentVo> department) {
        this.department = department;
    }

    public List<PositionVo> getPosition() {
        return position;
    }

    public void setPosition(List<PositionVo> position) {
        this.position = position;
    }

    public List<RoleVo> getRole() {
        return role;
    }

    public void setRole(List<RoleVo> role) {
        this.role = role;
    }
}

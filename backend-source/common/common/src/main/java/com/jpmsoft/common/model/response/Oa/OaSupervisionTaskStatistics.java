package com.jpmsoft.common.model.response.Oa;

public class OaSupervisionTaskStatistics {

    //年
    private String year;
    //部门名称
    private String stDName;
    //事项类型
    private String stType;
    //办理类型
    private String stHandleType;
    //总计
    private Integer total;
    //办理中
    private Integer inProcess;
    //已办结束
    private Integer completed;
    //正常(绿灯)
    private Integer normal;
    //超时办理(黄灯)
    private Integer handled;
    //超时未办理(红灯)
    private Integer notHandled;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getStDName() {
        return stDName;
    }

    public void setStDName(String stDName) {
        this.stDName = stDName;
    }

    public String getStType() {
        return stType;
    }

    public void setStType(String stType) {
        this.stType = stType;
    }

    public String getStHandleType() {
        return stHandleType;
    }

    public void setStHandleType(String stHandleType) {
        this.stHandleType = stHandleType;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getInProcess() {
        return inProcess;
    }

    public void setInProcess(Integer inProcess) {
        this.inProcess = inProcess;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

    public Integer getNormal() {
        return normal;
    }

    public void setNormal(Integer normal) {
        this.normal = normal;
    }

    public Integer getHandled() {
        return handled;
    }

    public void setHandled(Integer handled) {
        this.handled = handled;
    }

    public Integer getNotHandled() {
        return notHandled;
    }

    public void setNotHandled(Integer notHandled) {
        this.notHandled = notHandled;
    }
}

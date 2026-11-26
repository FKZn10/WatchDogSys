package com.jpmsoft.fileUpload.pojo.vo;

import java.io.Serializable;
import java.util.List;

public class VuePowerTreeVo implements Serializable {

    private static final long serialVersionUID = -3407870452663125171L;
    //节点id
    private String key;
    //节点名称
    private String title;
    //值(vue赋值要有)
    private String value;
    //上级结点id
    private String pid;
    private String powerId;
    //菜单：0 按钮：1 方法：2
    private String powerType;
    private String powerName;
    private String powerCode;
    private String powerParentid;
    private String powerPath;
    private String powerUrl;
    private String powerController;
    private String powerAction;
    private String powerSolution;
    private Integer powerSort;
    private String powerStatus;
    private String powerIcon;
    private String powerHomePage;
    private String powerComponent;
    private String powerView;
    private String powerRedirect;
    private Boolean powerRoute;
    private Boolean powerHidden;
    private Boolean powerKeepAlive;
    private Boolean powerAlwaysShow;
    private Boolean powerInternalOrExternal;
    private String powerAuthorizationCode;
    private String powerAuthorizationPolicy;
    private String powerHighlight;
    private Boolean powerAdmin;
    //子集
    private List<VuePowerTreeVo> children;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPowerId() {
        return powerId;
    }

    public void setPowerId(String powerId) {
        this.powerId = powerId;
    }

    public String getPowerType() {
        return powerType;
    }

    public void setPowerType(String powerType) {
        this.powerType = powerType;
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }

    public String getPowerCode() {
        return powerCode;
    }

    public void setPowerCode(String powerCode) {
        this.powerCode = powerCode;
    }

    public String getPowerParentid() {
        return powerParentid;
    }

    public void setPowerParentid(String powerParentid) {
        this.powerParentid = powerParentid;
    }

    public String getPowerPath() {
        return powerPath;
    }

    public void setPowerPath(String powerPath) {
        this.powerPath = powerPath;
    }

    public String getPowerUrl() {
        return powerUrl;
    }

    public void setPowerUrl(String powerUrl) {
        this.powerUrl = powerUrl;
    }

    public String getPowerController() {
        return powerController;
    }

    public void setPowerController(String powerController) {
        this.powerController = powerController;
    }

    public String getPowerAction() {
        return powerAction;
    }

    public void setPowerAction(String powerAction) {
        this.powerAction = powerAction;
    }

    public String getPowerSolution() {
        return powerSolution;
    }

    public void setPowerSolution(String powerSolution) {
        this.powerSolution = powerSolution;
    }

    public Integer getPowerSort() {
        return powerSort;
    }

    public void setPowerSort(Integer powerSort) {
        this.powerSort = powerSort;
    }

    public String getPowerStatus() {
        return powerStatus;
    }

    public void setPowerStatus(String powerStatus) {
        this.powerStatus = powerStatus;
    }

    public Boolean getPowerAdmin() {
        return powerAdmin;
    }

    public void setPowerAdmin(Boolean powerAdmin) {
        this.powerAdmin = powerAdmin;
    }

    public List<VuePowerTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<VuePowerTreeVo> children) {
        this.children = children;
    }

    public String getPowerIcon() {
        return powerIcon;
    }

    public void setPowerIcon(String powerIcon) {
        this.powerIcon = powerIcon;
    }

    public String getPowerHomePage() {
        return powerHomePage;
    }

    public void setPowerHomePage(String powerHomePage) {
        this.powerHomePage = powerHomePage;
    }

    public String getPowerComponent() {
        return powerComponent;
    }

    public void setPowerComponent(String powerComponent) {
        this.powerComponent = powerComponent;
    }

    public String getPowerView() {
        return powerView;
    }

    public void setPowerView(String powerView) {
        this.powerView = powerView;
    }

    public String getPowerRedirect() {
        return powerRedirect;
    }

    public void setPowerRedirect(String powerRedirect) {
        this.powerRedirect = powerRedirect;
    }

    public Boolean getPowerRoute() {
        return powerRoute;
    }

    public void setPowerRoute(Boolean powerRoute) {
        this.powerRoute = powerRoute;
    }

    public Boolean getPowerHidden() {
        return powerHidden;
    }

    public void setPowerHidden(Boolean powerHidden) {
        this.powerHidden = powerHidden;
    }

    public Boolean getPowerKeepAlive() {
        return powerKeepAlive;
    }

    public void setPowerKeepAlive(Boolean powerKeepAlive) {
        this.powerKeepAlive = powerKeepAlive;
    }

    public Boolean getPowerAlwaysShow() {
        return powerAlwaysShow;
    }

    public void setPowerAlwaysShow(Boolean powerAlwaysShow) {
        this.powerAlwaysShow = powerAlwaysShow;
    }

    public Boolean getPowerInternalOrExternal() {
        return powerInternalOrExternal;
    }

    public void setPowerInternalOrExternal(Boolean powerInternalOrExternal) {
        this.powerInternalOrExternal = powerInternalOrExternal;
    }

    public String getPowerAuthorizationCode() {
        return powerAuthorizationCode;
    }

    public void setPowerAuthorizationCode(String powerAuthorizationCode) {
        this.powerAuthorizationCode = powerAuthorizationCode;
    }

    public String getPowerAuthorizationPolicy() {
        return powerAuthorizationPolicy;
    }

    public void setPowerAuthorizationPolicy(String powerAuthorizationPolicy) {
        this.powerAuthorizationPolicy = powerAuthorizationPolicy;
    }

    public String getPowerHighlight() {
        return powerHighlight;
    }

    public void setPowerHighlight(String powerHighlight) {
        this.powerHighlight = powerHighlight;
    }
}

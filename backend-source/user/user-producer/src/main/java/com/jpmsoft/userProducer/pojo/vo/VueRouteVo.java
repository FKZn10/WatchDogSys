package com.jpmsoft.userProducer.pojo.vo;

import java.io.Serializable;
import java.util.List;

public class VueRouteVo implements Serializable {

    private static final long serialVersionUID = -9192329819895829631L;
    private String router;
    private String name;
    private String type;
    private String url;
    private String icon;
    private String homePage;
    private String component;
    private String view;
    private String redirect;
    private Boolean route;
    private Boolean hidden;
    private Boolean keepAlive;
    private Boolean alwaysShow;
    private Boolean internalOrExternal;
    private String authorizationCode;
    private String authorizationPolicy;
    private String highlight;
    private String powerPattern;
    private String powerParameter;
    private Object authority;
    // 子菜单
    private List<VueRouteVo> children;


    public String getRouter() {
        return router;
    }

    public void setRouter(String router) {
        this.router = router;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public Boolean getRoute() {
        return route;
    }

    public void setRoute(Boolean route) {
        this.route = route;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Boolean getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public Boolean getAlwaysShow() {
        return alwaysShow;
    }

    public void setAlwaysShow(Boolean alwaysShow) {
        this.alwaysShow = alwaysShow;
    }

    public Boolean getInternalOrExternal() {
        return internalOrExternal;
    }

    public void setInternalOrExternal(Boolean internalOrExternal) {
        this.internalOrExternal = internalOrExternal;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public String getAuthorizationPolicy() {
        return authorizationPolicy;
    }

    public void setAuthorizationPolicy(String authorizationPolicy) {
        this.authorizationPolicy = authorizationPolicy;
    }

    public String getHighlight() {
        return highlight;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }

    public List<VueRouteVo> getChildren() {
        return children;
    }

    public void setChildren(List<VueRouteVo> children) {
        this.children = children;
    }

    public Object getAuthority() {
        return authority;
    }

    public void setAuthority(Object authority) {
        this.authority = authority;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public String getPowerPattern() {
        return powerPattern;
    }

    public void setPowerPattern(String powerPattern) {
        this.powerPattern = powerPattern;
    }

    public String getPowerParameter() {
        return powerParameter;
    }

    public void setPowerParameter(String powerParameter) {
        this.powerParameter = powerParameter;
    }
}

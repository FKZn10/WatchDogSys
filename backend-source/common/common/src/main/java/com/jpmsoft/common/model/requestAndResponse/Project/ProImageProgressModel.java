package com.jpmsoft.common.model.requestAndResponse.Project;

import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;

public class ProImageProgressModel extends BaseFileRequest {
    private String id;
    private String pId;
    private String title;
    private String content;
    private String img;
    private Integer state;
    private String createUid;
    private String createUname;
    private Date createTime;

    private String pName;
    private String imgIds;
    private String[] imgList;
    private Integer commentCount;
    private Integer likeCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getpId() {
        return pId;
    }

    @Override
    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateUid() {
        return createUid;
    }

    public void setCreateUid(String createUid) {
        this.createUid = createUid;
    }

    public String getCreateUname() {
        return createUname;
    }

    public void setCreateUname(String createUname) {
        this.createUname = createUname;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getImgIds() {
        return imgIds;
    }

    public void setImgIds(String imgIds) {
        this.imgIds = imgIds;
    }

    public String[] getImgList() {
        return imgList;
    }

    public void setImgList(String[] imgList) {
        this.imgList = imgList;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }
}

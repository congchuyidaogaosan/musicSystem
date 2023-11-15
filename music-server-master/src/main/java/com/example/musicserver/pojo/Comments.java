package com.example.musicserver.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.util.Date;

@ApiModel("Comments评论")
public class Comments {
    @ApiModelProperty(name = "commId",value = "主键Id",hidden = true)
    private Integer commId;
    @ApiModelProperty(name = "commDetails",value = "内容",example="这是评论")
    private String commDetails;
    @ApiModelProperty(name = "commTime",value = "时间")
    private Date commTime;
    @ApiModelProperty(name = "commTime",value = "创建者Id")
    private Integer commUserId;
    @ApiModelProperty(name = "commTargetId",value = "目标ID")
    private Integer commTargetId;
    @ApiModelProperty(name = "commType",value = "目标类型",example = "歌单传1，专辑传2，歌曲传3")
    private Integer commType;
    @ApiModelProperty(hidden = true)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getCommId() {
        return commId;
    }

    public void setCommId(Integer commId) {
        this.commId = commId;
    }

    public String getCommDetails() {
        return commDetails;
    }

    public void setCommDetails(String commDetails) {
        this.commDetails = commDetails;
    }

    public Date getCommTime() {
        return commTime;
    }

    public void setCommTime(Date commTime) {
        this.commTime = commTime;
    }

    public Integer getCommUserId() {
        return commUserId;
    }

    public void setCommUserId(Integer commUserId) {
        this.commUserId = commUserId;
    }

    public Integer getCommTargetId() {
        return commTargetId;
    }

    public void setCommTargetId(Integer commTargetId) {
        this.commTargetId = commTargetId;
    }

    public Integer getCommType() {
        return commType;
    }

    public void setCommType(Integer commType) {
        this.commType = commType;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "commId=" + commId +
                ", commDetails='" + commDetails + '\'' +
                ", commTime=" + commTime +
                ", commUserId=" + commUserId +
                ", commTargetId=" + commTargetId +
                ", commType=" + commType +
                '}';
    }
}

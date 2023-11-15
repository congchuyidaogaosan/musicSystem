package com.example.musicserver.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;

@ApiModel("Lists歌单")
public class Lists {
    @ApiModelProperty(value = "歌单Id",name = "listId",hidden = true)
    private Integer listId;
    @ApiModelProperty(value = "歌单标题",name = "listTitle")
    private String listTitle;
    @ApiModelProperty(value = "歌单封面",name = "listImg")
    private String listImg;
    @ApiModelProperty(value = "歌单创建者Id",name = "listUserId")
    private Integer listUserId;
    @ApiModelProperty(value = "歌单创建时间",name = "listTime")
    private Date listTime;
    @ApiModelProperty(value = "歌单创建时间",name = "listTime")
    private String listDetails;
    @ApiModelProperty(value = "歌单类型",name = "listType")
    private Integer listType;
//    2,经典
//    3流行
//    4民谣
//    5摇滚
//    6安静
//    7
    @ApiModelProperty(value = "歌单点击量",name = "listType")
    private Integer listPlayNum;
    @ApiModelProperty(value = "歌曲集合",name = "song",hidden = true)
    private List<Song> song;
    @ApiModelProperty(value = "用户",name = "user",hidden = true)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Song> getSong() {
        return song;
    }

    public void setSong(List<Song> song) {
        this.song = song;
    }

    public Integer getListPlayNum() {
        return listPlayNum;
    }

    public void setListPlayNum(Integer listPlayNum) {
        this.listPlayNum = listPlayNum;
    }

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public String getListImg() {
        return listImg;
    }

    public void setListImg(String listImg) {
        this.listImg = listImg;
    }

    public Integer getListUserId() {
        return listUserId;
    }

    public void setListUserId(Integer listUserId) {
        this.listUserId = listUserId;
    }

    public Date getListTime() {
        return listTime;
    }

    public void setListTime(Date listTime) {
        this.listTime = listTime;
    }

    public String getListDetails() {
        return listDetails;
    }

    public void setListDetails(String listDetails) {
        this.listDetails = listDetails;
    }

    public Integer getListType() {
        return listType;
    }

    public void setListType(Integer listType) {
        this.listType = listType;
    }

    @Override
    public String toString() {
        return "Lists{" +
                "listId=" + listId +
                ", listTitle='" + listTitle + '\'' +
                ", listImg='" + listImg + '\'' +
                ", listUserId=" + listUserId +
                ", listTime=" + listTime +
                '}';
    }
}

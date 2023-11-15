package com.example.musicserver.pojo;

public class Fans {
    private Integer fansId;
    private Integer fansType;
    private Integer fansFocusId;
    private Integer fansFansId;
    private User user;
    private Singer singer;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Singer getSinger() {
        return singer;
    }

    public void setSinger(Singer singer) {
        this.singer = singer;
    }

    public Integer getFansId() {
        return fansId;
    }

    public void setFansId(Integer fansId) {
        this.fansId = fansId;
    }

    public Integer getFansType() {
        return fansType;
    }

    public void setFansType(Integer fansType) {
        this.fansType = fansType;
    }

    public Integer getFansFocusId() {
        return fansFocusId;
    }

    public void setFansFocusId(Integer fansUserId) {
        this.fansFocusId = fansUserId;
    }

    public Integer getFansFansId() {
        return fansFansId;
    }

    public void setFansFansId(Integer fansFansId) {
        this.fansFansId = fansFansId;
    }
}

package com.example.musicserver.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel("Album专辑")
public class Album {
    @ApiModelProperty(value = "专辑Id",name = "albumId",hidden = true)
    private Integer albumId;
    @ApiModelProperty(value = "专辑名",name = "albumName")
    private String albumName;
    @ApiModelProperty(value = "歌手名",name = "albumSinger")
    private Integer albumSinger;
    @ApiModelProperty(value = "专辑简介",name = "albumDetails")
    private String albumDetails;
    @ApiModelProperty(value = "专辑发行时间",name = "albumTime")
    private Date albumTime;
    @ApiModelProperty(value = "专辑图",name = "albumImg")
    private String albumImg;
    @ApiModelProperty(value = "专辑对应歌曲",name = "song",hidden = true)
    private List<Song> song;
    @ApiModelProperty(value = "歌手",name = "singer",hidden = true)
    private Singer singer;

    public Singer getSinger() {
        return singer;
    }

    public void setSinger(Singer singer) {
        this.singer = singer;
    }

    public String getAlbumImg() {
        return albumImg;
    }

    public void setAlbumImg(String albumImg) {
        this.albumImg = albumImg;
    }

    public List<Song> getSong() {
        return song;
    }

    public void setSong(List<Song> song) {
        this.song = song;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Integer getAlbumSinger() { return albumSinger;    }

    public void setAlbumSinger(Integer albumSinger) { this.albumSinger = albumSinger;    }

    public String getAlbumDetails() {
        return albumDetails;
    }

    public void setAlbumDetails(String albumDetails) {
        this.albumDetails = albumDetails;
    }

    public Date getAlbumTime() {
        return albumTime;
    }

    public void setAlbumTime(Date albumTime) {
        this.albumTime = albumTime;
    }

    @Override
    public String toString() {
        return "Album{" +
                "albumId=" + albumId +
                ", albumName='" + albumName + '\'' +
                ", albumSinger='" + albumSinger + '\'' +
                ", albumDetails='" + albumDetails + '\'' +
                ", albumTime=" + albumTime +
                '}';
    }
}

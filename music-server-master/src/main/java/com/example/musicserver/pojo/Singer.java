package com.example.musicserver.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("Singer歌手")
public class Singer {

    @ApiModelProperty(name = "singerId",value = "主键",hidden = true)
    private Integer singerId;
    @ApiModelProperty(name = "singerName",value = "歌手名")
    private String singerName;
    @ApiModelProperty(name = "singerDetails",value = "歌手简介")
    private String singerDetails;
    @ApiModelProperty(name = "singerPhoto",value = "歌手头像")
    private String singerPhoto;
    @ApiModelProperty(name = "singerType",value = "歌手类型")
    private Integer singerType;
    @ApiModelProperty(name = "song",hidden = true)
    private List<Song> song;
    @ApiModelProperty(name = "album",hidden = true)
    private List<Album> album;

    public List<Song> getSong() {
        return song;
    }

    public void setSong(List<Song> song) {
        this.song = song;
    }

    public List<Album> getAlbum() {
        return album;
    }

    public void setAlbum(List<Album> album) {
        this.album = album;
    }

    public Integer getSingerId() {
        return singerId;
    }

    public void setSingerId(Integer singerId) {
        this.singerId = singerId;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getSingerDetails() {
        return singerDetails;
    }

    public void setSingerDetails(String singerDetails) {
        this.singerDetails = singerDetails;
    }

    public String getSingerPhoto() {
        return singerPhoto;
    }

    public void setSingerPhoto(String singerPhoto) {
        this.singerPhoto = singerPhoto;
    }

    public Integer getSingerType() {
        return singerType;
    }

    public void setSingerType(Integer singerType) {
        this.singerType = singerType;
    }

    @Override
    public String toString() {
        return "Singer{" +
                "singerId=" + singerId +
                ", singerName='" + singerName + '\'' +
                ", singerDetails='" + singerDetails + '\'' +
                ", singerPhoto='" + singerPhoto + '\'' +
                '}';
    }
}

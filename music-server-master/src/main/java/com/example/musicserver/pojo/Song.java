package com.example.musicserver.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Song歌曲")
public class Song {

    @ApiModelProperty(value = "歌曲Id",name = "songId",hidden = true)
    private Integer songId;
    @ApiModelProperty(value = "歌曲名",name = "songName")
    private String songName;
    @ApiModelProperty(value = "歌手Id",name = "songSinger")
    private Integer songSinger;
    @ApiModelProperty(value = "歌曲文件路径",name = "songFilePath")
    private String songFilePath;
    @ApiModelProperty(value = "专辑Id",name = "songAlbum")
    private Integer songAlbum;
    @ApiModelProperty(value = "歌词",name = "songLyc")
    private String songLyc;
    @ApiModelProperty(value = "专辑",name = "album",hidden = true)
    private Album album;
    @ApiModelProperty(value = "歌手",name = "singer",hidden = true)
    private Singer singer;

    public String getSongLyc() {
        return songLyc;
    }

    public void setSongLyc(String songLyc) {
        this.songLyc = songLyc;
    }

    public Album getAlbum() {       return album;    }

    public void setAlbum(Album album) {        this.album = album;    }

    public Singer getSinger() {        return singer;    }

    public void setSinger(Singer singer) {        this.singer = singer;    }

    public Integer getSongId() {
        return songId;
    }

    public void setSongId(Integer songId) {
        this.songId = songId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongFilePath() {
        return songFilePath;
    }

    public void setSongFilePath(String songFilePath) {
        this.songFilePath = songFilePath;
    }

    public Integer getSongSinger() {
        return songSinger;
    }

    public void setSongSinger(Integer songSinger) {
        this.songSinger = songSinger;
    }

    public Integer getSongAlbum() {
        return songAlbum;
    }

    public void setSongAlbum(Integer songAlbum) {
        this.songAlbum = songAlbum;
    }

    @Override
    public String toString() {
        return "Song{" +
                "songId=" + songId +
                ", songName='" + songName + '\'' +
                ", songSinger=" + songSinger +
                ", songFilePath='" + songFilePath + '\'' +
                ", songAlbum=" + songAlbum +
                ", album=" + album +
                ", singer=" + singer +
                '}';
    }
}

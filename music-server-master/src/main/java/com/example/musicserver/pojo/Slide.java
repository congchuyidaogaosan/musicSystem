package com.example.musicserver.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Slide幻灯片")
public class Slide {

    @ApiModelProperty(value = "幻灯片Id",name = "slideId",hidden = true)
    private Integer slideId;
    @ApiModelProperty(value = "幻灯片图片",name = "slideImg")
    private String slideImg;
    @ApiModelProperty(value = "对应专辑Id",name = "slideAlbumId")
    private Integer slideAlbumId;
    @ApiModelProperty(value = "有效性",name = "slideValid")
    private Integer slideValid;

    public Integer getSlideValid() {
        return slideValid;
    }

    public void setSlideValid(Integer slideValid) {
        this.slideValid = slideValid;
    }

    public Integer getSlideId() {
        return slideId;
    }

    public void setSlideId(Integer slideId) {
        this.slideId = slideId;
    }

    public String getSlideImg() {
        return slideImg;
    }

    public void setSlideImg(String slideImg) {
        this.slideImg = slideImg;
    }

    public Integer getSlideAlbumId() {
        return slideAlbumId;
    }

    public void setSlideAlbumId(Integer slideAlbumId) {
        this.slideAlbumId = slideAlbumId;
    }

    @Override
    public String toString() {
        return "Slide{" +
                "slideId=" + slideId +
                ", slideImg='" + slideImg + '\'' +
                ", slideAlbumId=" + slideAlbumId +
                '}';
    }
}

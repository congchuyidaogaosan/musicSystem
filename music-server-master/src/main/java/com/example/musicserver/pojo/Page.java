package com.example.musicserver.pojo;

import io.swagger.annotations.ApiModelProperty;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
public class Page {

    @ApiModelProperty(value = "页面数据大小",name = "pageSize")
    private int pageSize;
    @ApiModelProperty(value = "页码",name = "pageNum")
    private int pageNum;
    @ApiModelProperty(hidden = true)
    private int startIndex;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }
}

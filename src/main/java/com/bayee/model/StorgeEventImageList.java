package com.bayee.model;

public class StorgeEventImageList {
    private Integer imgIdx;

    private Integer parentImgIdx;

    private String cacheUrl;

    private String imgUrl;

    private String imgSize;

    private String imgPixel;

    private Integer imgType;

    private Integer objLeft;

    private Integer objTop;

    private Integer objRight;

    private Integer objBottom;

    private Integer imgWidth;

    private Integer imgHeight;

    private String picRecordId;

    public Integer getImgIdx() {
        return imgIdx;
    }

    public void setImgIdx(Integer imgIdx) {
        this.imgIdx = imgIdx;
    }

    public Integer getParentImgIdx() {
        return parentImgIdx;
    }

    public void setParentImgIdx(Integer parentImgIdx) {
        this.parentImgIdx = parentImgIdx;
    }

    public String getCacheUrl() {
        return cacheUrl;
    }

    public void setCacheUrl(String cacheUrl) {
        this.cacheUrl = cacheUrl == null ? null : cacheUrl.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public String getImgSize() {
        return imgSize;
    }

    public void setImgSize(String imgSize) {
        this.imgSize = imgSize == null ? null : imgSize.trim();
    }

    public String getImgPixel() {
        return imgPixel;
    }

    public void setImgPixel(String imgPixel) {
        this.imgPixel = imgPixel == null ? null : imgPixel.trim();
    }

    public Integer getImgType() {
        return imgType;
    }

    public void setImgType(Integer imgType) {
        this.imgType = imgType;
    }

    public Integer getObjLeft() {
        return objLeft;
    }

    public void setObjLeft(Integer objLeft) {
        this.objLeft = objLeft;
    }

    public Integer getObjTop() {
        return objTop;
    }

    public void setObjTop(Integer objTop) {
        this.objTop = objTop;
    }

    public Integer getObjRight() {
        return objRight;
    }

    public void setObjRight(Integer objRight) {
        this.objRight = objRight;
    }

    public Integer getObjBottom() {
        return objBottom;
    }

    public void setObjBottom(Integer objBottom) {
        this.objBottom = objBottom;
    }

    public Integer getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(Integer imgWidth) {
        this.imgWidth = imgWidth;
    }

    public Integer getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(Integer imgHeight) {
        this.imgHeight = imgHeight;
    }

    public String getPicRecordId() {
        return picRecordId;
    }

    public void setPicRecordId(String picRecordId) {
        this.picRecordId = picRecordId == null ? null : picRecordId.trim();
    }
}
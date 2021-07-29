package com.bayee.model;

public class ProcessingEventImagelist {
    private Integer imgIdx;

    private Integer parentImgIdx;

    private String cacheUrl;

    private String imgUrl;

    private Integer imgSize;

    private String imgPixel;

    private Integer imgType;

    private String plateCharReliability;

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

    public Integer getImgSize() {
        return imgSize;
    }

    public void setImgSize(Integer imgSize) {
        this.imgSize = imgSize;
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

    public String getPlateCharReliability() {
        return plateCharReliability;
    }

    public void setPlateCharReliability(String plateCharReliability) {
        this.plateCharReliability = plateCharReliability == null ? null : plateCharReliability.trim();
    }

    public String getPicRecordId() {
        return picRecordId;
    }

    public void setPicRecordId(String picRecordId) {
        this.picRecordId = picRecordId == null ? null : picRecordId.trim();
    }
}
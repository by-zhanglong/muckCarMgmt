package com.bayee.model;

public class SysPalceInfo {
    private Integer palceId;

    private String palceName;

    private String palceX;

    private String palceY;

    private String palceNo;
    
    private String placeId;
    private int limitCount;

    public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public int getLimitCount() {
		return limitCount;
	}

	public void setLimitCount(int limitCount) {
		this.limitCount = limitCount;
	}

	public Integer getPalceId() {
        return palceId;
    }

    public void setPalceId(Integer palceId) {
        this.palceId = palceId;
    }

    public String getPalceName() {
        return palceName;
    }

    public void setPalceName(String palceName) {
        this.palceName = palceName == null ? null : palceName.trim();
    }

    public String getPalceX() {
        return palceX;
    }

    public void setPalceX(String palceX) {
        this.palceX = palceX == null ? null : palceX.trim();
    }

    public String getPalceY() {
        return palceY;
    }

    public void setPalceY(String palceY) {
        this.palceY = palceY == null ? null : palceY.trim();
    }

    public String getPalceNo() {
        return palceNo;
    }

    public void setPalceNo(String palceNo) {
        this.palceNo = palceNo == null ? null : palceNo.trim();
    }
}
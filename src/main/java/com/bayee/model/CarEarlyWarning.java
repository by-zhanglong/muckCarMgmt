package com.bayee.model;

public class CarEarlyWarning {
    private Long wId;

    private String wCarNum;

    private String wInfo;

    private String wChannelCode;

    private String wChannelName;

    private Double wGpsX;

    private Double wGpsY;

    private String wCapDateStr;

    private String wImgUrl;

    private String wUdf1;

    private String wUdf2;

    private String wUdf3;

    private String wUdf4;

    private String wUdf5;
    private int warnCount;

    public int getWarnCount() {
		return warnCount; 
	}

	public void setWarnCount(int warnCount) {
		this.warnCount = warnCount;
	}

	public Long getwId() {
        return wId;
    }

    public void setwId(Long wId) {
        this.wId = wId;
    }

    public String getwCarNum() {
        return wCarNum;
    }

    public void setwCarNum(String wCarNum) {
        this.wCarNum = wCarNum == null ? null : wCarNum.trim();
    }

    public String getwInfo() {
        return wInfo;
    }

    public void setwInfo(String wInfo) {
        this.wInfo = wInfo == null ? null : wInfo.trim();
    }

    public String getwChannelCode() {
        return wChannelCode;
    }

    public void setwChannelCode(String wChannelCode) {
        this.wChannelCode = wChannelCode == null ? null : wChannelCode.trim();
    }

    public String getwChannelName() {
        return wChannelName;
    }

    public void setwChannelName(String wChannelName) {
        this.wChannelName = wChannelName == null ? null : wChannelName.trim();
    }

    public Double getwGpsX() {
        return wGpsX;
    }

    public void setwGpsX(Double wGpsX) {
        this.wGpsX = wGpsX;
    }

    public Double getwGpsY() {
        return wGpsY;
    }

    public void setwGpsY(Double wGpsY) {
        this.wGpsY = wGpsY;
    }

    public String getwCapDateStr() {
        return wCapDateStr;
    }

    public void setwCapDateStr(String wCapDateStr) {
        this.wCapDateStr = wCapDateStr == null ? null : wCapDateStr.trim();
    }

    public String getwImgUrl() {
        return wImgUrl;
    }

    public void setwImgUrl(String wImgUrl) {
        this.wImgUrl = wImgUrl == null ? null : wImgUrl.trim();
    }

    public String getwUdf1() {
        return wUdf1;
    }

    public void setwUdf1(String wUdf1) {
        this.wUdf1 = wUdf1 == null ? null : wUdf1.trim();
    }

    public String getwUdf2() {
        return wUdf2;
    }

    public void setwUdf2(String wUdf2) {
        this.wUdf2 = wUdf2 == null ? null : wUdf2.trim();
    }

    public String getwUdf3() {
        return wUdf3;
    }

    public void setwUdf3(String wUdf3) {
        this.wUdf3 = wUdf3 == null ? null : wUdf3.trim();
    }

    public String getwUdf4() {
        return wUdf4;
    }

    public void setwUdf4(String wUdf4) {
        this.wUdf4 = wUdf4 == null ? null : wUdf4.trim();
    }

    public String getwUdf5() {
        return wUdf5;
    }

    public void setwUdf5(String wUdf5) {
        this.wUdf5 = wUdf5 == null ? null : wUdf5.trim();
    }
}
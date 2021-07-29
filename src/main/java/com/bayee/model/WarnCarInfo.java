package com.bayee.model;

public class WarnCarInfo {
    private Integer warnCId;

    private String warnCNum;

    private String warnCTime;

    private String warnCPlace;

    private Integer warnCTotal;

    private String warnCColor;

    private String warnItem;

    private String warnCUdf1;

    private String warnCUdf2;

    private String warnCUdf3;

    private String warnCUdf4;

    private String warnCUdf5;
    private String channelName;
    private String channelX;
    private String channelY;
    private Integer sumScore;
    /**
     * 车辆布控表主键id
     */
    private Integer controlId;



	public Integer getControlId() {
		return controlId;
	}

	public void setControlId(Integer controlId) {
		this.controlId = controlId;
	}

	public Integer getSumScore() {
		return sumScore;
	}

	public void setSumScore(Integer sumScore) {
		this.sumScore = sumScore;
	}

	public String getChannelX() {
		return channelX;
	}

	public void setChannelX(String channelX) {
		this.channelX = channelX;
	}

	public String getChannelY() {
		return channelY;
	}

	public void setChannelY(String channelY) {
		this.channelY = channelY;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Integer getWarnCId() {
        return warnCId;
    }

    public void setWarnCId(Integer warnCId) {
        this.warnCId = warnCId;
    }

    public String getWarnCNum() {
        return warnCNum;
    }

    public void setWarnCNum(String warnCNum) {
        this.warnCNum = warnCNum == null ? null : warnCNum.trim();
    }

    public String getWarnCTime() {
        return warnCTime;
    }

    public void setWarnCTime(String warnCTime) {
        this.warnCTime = warnCTime == null ? null : warnCTime.trim();
    }

    public String getWarnCPlace() {
        return warnCPlace;
    }

    public void setWarnCPlace(String warnCPlace) {
        this.warnCPlace = warnCPlace == null ? null : warnCPlace.trim();
    }

    public Integer getWarnCTotal() {
        return warnCTotal;
    }

    public void setWarnCTotal(Integer warnCTotal) {
        this.warnCTotal = warnCTotal;
    }

    public String getWarnCColor() {
        return warnCColor;
    }

    public void setWarnCColor(String warnCColor) {
        this.warnCColor = warnCColor == null ? null : warnCColor.trim();
    }

    public String getWarnItem() {
        return warnItem;
    }

    public void setWarnItem(String warnItem) {
        this.warnItem = warnItem == null ? null : warnItem.trim();
    }

    public String getWarnCUdf1() {
        return warnCUdf1;
    }

    public void setWarnCUdf1(String warnCUdf1) {
        this.warnCUdf1 = warnCUdf1 == null ? null : warnCUdf1.trim();
    }

    public String getWarnCUdf2() {
        return warnCUdf2;
    }

    public void setWarnCUdf2(String warnCUdf2) {
        this.warnCUdf2 = warnCUdf2 == null ? null : warnCUdf2.trim();
    }

    public String getWarnCUdf3() {
        return warnCUdf3;
    }

    public void setWarnCUdf3(String warnCUdf3) {
        this.warnCUdf3 = warnCUdf3 == null ? null : warnCUdf3.trim();
    }

    public String getWarnCUdf4() {
        return warnCUdf4;
    }

    public void setWarnCUdf4(String warnCUdf4) {
        this.warnCUdf4 = warnCUdf4 == null ? null : warnCUdf4.trim();
    }

    public String getWarnCUdf5() {
        return warnCUdf5;
    }

    public void setWarnCUdf5(String warnCUdf5) {
        this.warnCUdf5 = warnCUdf5 == null ? null : warnCUdf5.trim();
    }
}
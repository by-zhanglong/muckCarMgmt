package com.bayee.model;

import java.util.Date;

public class CarControlInfo {
    private Long cId;

    private String cCarNum;

    private String cObjCode;

    private String creatBy;

    private String createByName;

    private Date createTime;

    private Integer cStatus;

    private String cUdf1;

    private String cUdf2;

    private String cUdf3;

    private String cUdf4;

    private String cUdf5;
    
    private String updateBy;

    private String updateByName;

    private Date updateTime;

    public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateByName() {
		return updateByName;
	}

	public void setUpdateByName(String updateByName) {
		this.updateByName = updateByName;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public String getcCarNum() {
        return cCarNum;
    }

    public void setcCarNum(String cCarNum) {
        this.cCarNum = cCarNum == null ? null : cCarNum.trim();
    }

    public String getcObjCode() {
        return cObjCode;
    }

    public void setcObjCode(String cObjCode) {
        this.cObjCode = cObjCode == null ? null : cObjCode.trim();
    }

    public String getCreatBy() {
        return creatBy;
    }

    public void setCreatBy(String creatBy) {
        this.creatBy = creatBy == null ? null : creatBy.trim();
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName == null ? null : createByName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getcStatus() {
        return cStatus;
    }

    public void setcStatus(Integer cStatus) {
        this.cStatus = cStatus;
    }

    public String getcUdf1() {
        return cUdf1;
    }

    public void setcUdf1(String cUdf1) {
        this.cUdf1 = cUdf1 == null ? null : cUdf1.trim();
    }

    public String getcUdf2() {
        return cUdf2;
    }

    public void setcUdf2(String cUdf2) {
        this.cUdf2 = cUdf2 == null ? null : cUdf2.trim();
    }

    public String getcUdf3() {
        return cUdf3;
    }

    public void setcUdf3(String cUdf3) {
        this.cUdf3 = cUdf3 == null ? null : cUdf3.trim();
    }

    public String getcUdf4() {
        return cUdf4;
    }

    public void setcUdf4(String cUdf4) {
        this.cUdf4 = cUdf4 == null ? null : cUdf4.trim();
    }

    public String getcUdf5() {
        return cUdf5;
    }

    public void setcUdf5(String cUdf5) {
        this.cUdf5 = cUdf5 == null ? null : cUdf5.trim();
    }
}
package com.bayee.model;

import java.util.Date;

public class SysCarInfo {
	
    private Integer carId;

    private Integer carType;

    private String carNum;

    private String carUser;

    private String carUserIdCard;

    private String carUserCompany;

    private String carUserMobile;

    private String carUdf1;

    private String carUdf2;

    private String carUdf3;

    private String carUdf4;

    private String carUdf5;

    private String createBy;

    private Date createTime;

    private String cardType;

    private String updateBy;

    private String updateByName;

    private Date updateTime;

    private String createByName;

    private String remark;
    
    private Long conId;

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getCarType() {
        return carType;
    }

    public void setCarType(Integer carType) {
        this.carType = carType;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum == null ? null : carNum.trim();
    }

    public String getCarUser() {
        return carUser;
    }

    public void setCarUser(String carUser) {
        this.carUser = carUser == null ? null : carUser.trim();
    }

    public String getCarUserIdCard() {
        return carUserIdCard;
    }

    public void setCarUserIdCard(String carUserIdCard) {
        this.carUserIdCard = carUserIdCard == null ? null : carUserIdCard.trim();
    }

    public String getCarUserCompany() {
        return carUserCompany;
    }

    public void setCarUserCompany(String carUserCompany) {
        this.carUserCompany = carUserCompany == null ? null : carUserCompany.trim();
    }

    public String getCarUserMobile() {
        return carUserMobile;
    }

    public void setCarUserMobile(String carUserMobile) {
        this.carUserMobile = carUserMobile == null ? null : carUserMobile.trim();
    }

    public String getCarUdf1() {
        return carUdf1;
    }

    public void setCarUdf1(String carUdf1) {
        this.carUdf1 = carUdf1 == null ? null : carUdf1.trim();
    }

    public String getCarUdf2() {
        return carUdf2;
    }

    public void setCarUdf2(String carUdf2) {
        this.carUdf2 = carUdf2 == null ? null : carUdf2.trim();
    }

    public String getCarUdf3() {
        return carUdf3;
    }

    public void setCarUdf3(String carUdf3) {
        this.carUdf3 = carUdf3 == null ? null : carUdf3.trim();
    }

    public String getCarUdf4() {
        return carUdf4;
    }

    public void setCarUdf4(String carUdf4) {
        this.carUdf4 = carUdf4 == null ? null : carUdf4.trim();
    }

    public String getCarUdf5() {
        return carUdf5;
    }

    public void setCarUdf5(String carUdf5) {
        this.carUdf5 = carUdf5 == null ? null : carUdf5.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType == null ? null : cardType.trim();
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public String getUpdateByName() {
        return updateByName;
    }

    public void setUpdateByName(String updateByName) {
        this.updateByName = updateByName == null ? null : updateByName.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName == null ? null : createByName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public Long getConId() {
		return conId;
	}

	public void setConId(Long conId) {
		this.conId = conId;
	}
}
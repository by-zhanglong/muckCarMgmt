package com.bayee.model;

import java.util.Date;

public class SysTagInfo {
    private Integer tagId;

    private String tagName;

    private Integer tagPId;

    private Integer tagType;

    private String tagRemark;

    private Integer tagStatus;

    private String tagUdf1;

    private String tagUdf2;

    private String tagUdf3;

    private String tagUdf4;

    private String tagUdf5;
    
    private String createBy;
    
    private String createByName;
    
    private Date createTime;

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName == null ? null : tagName.trim();
    }

    public Integer getTagPId() {
        return tagPId;
    }

    public void setTagPId(Integer tagPId) {
        this.tagPId = tagPId;
    }

    public Integer getTagType() {
        return tagType;
    }

    public void setTagType(Integer tagType) {
        this.tagType = tagType;
    }

    public String getTagRemark() {
        return tagRemark;
    }

    public void setTagRemark(String tagRemark) {
        this.tagRemark = tagRemark == null ? null : tagRemark.trim();
    }

    public Integer getTagStatus() {
        return tagStatus;
    }

    public void setTagStatus(Integer tagStatus) {
        this.tagStatus = tagStatus;
    }

    public String getTagUdf1() {
        return tagUdf1;
    }

    public void setTagUdf1(String tagUdf1) {
        this.tagUdf1 = tagUdf1 == null ? null : tagUdf1.trim();
    }

    public String getTagUdf2() {
        return tagUdf2;
    }

    public void setTagUdf2(String tagUdf2) {
        this.tagUdf2 = tagUdf2 == null ? null : tagUdf2.trim();
    }

    public String getTagUdf3() {
        return tagUdf3;
    }

    public void setTagUdf3(String tagUdf3) {
        this.tagUdf3 = tagUdf3 == null ? null : tagUdf3.trim();
    }

    public String getTagUdf4() {
        return tagUdf4;
    }

    public void setTagUdf4(String tagUdf4) {
        this.tagUdf4 = tagUdf4 == null ? null : tagUdf4.trim();
    }

    public String getTagUdf5() {
        return tagUdf5;
    }

    public void setTagUdf5(String tagUdf5) {
        this.tagUdf5 = tagUdf5 == null ? null : tagUdf5.trim();
    }

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateByName() {
		return createByName;
	}

	public void setCreateByName(String createByName) {
		this.createByName = createByName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
package com.bayee.model;

import java.util.Date;

public class SysRuleList {
    private Integer listId;

    private String listTitle;

    private Integer listTypeId;

    private String listValueFir;

    private String listValueSec;

    private Integer listValueScore;

    private String listUdf1;

    private String listUdf2;

    private String listUdf3;

    private String listUdf4;

    private String listUdf5;

    private String createBy;

    private Date createTime;

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle == null ? null : listTitle.trim();
    }

    public Integer getListTypeId() {
        return listTypeId;
    }

    public void setListTypeId(Integer listTypeId) {
        this.listTypeId = listTypeId;
    }

    public String getListValueFir() {
        return listValueFir;
    }

    public void setListValueFir(String listValueFir) {
        this.listValueFir = listValueFir == null ? null : listValueFir.trim();
    }

    public String getListValueSec() {
        return listValueSec;
    }

    public void setListValueSec(String listValueSec) {
        this.listValueSec = listValueSec == null ? null : listValueSec.trim();
    }

    public Integer getListValueScore() {
        return listValueScore;
    }

    public void setListValueScore(Integer listValueScore) {
        this.listValueScore = listValueScore;
    }

    public String getListUdf1() {
        return listUdf1;
    }

    public void setListUdf1(String listUdf1) {
        this.listUdf1 = listUdf1 == null ? null : listUdf1.trim();
    }

    public String getListUdf2() {
        return listUdf2;
    }

    public void setListUdf2(String listUdf2) {
        this.listUdf2 = listUdf2 == null ? null : listUdf2.trim();
    }

    public String getListUdf3() {
        return listUdf3;
    }

    public void setListUdf3(String listUdf3) {
        this.listUdf3 = listUdf3 == null ? null : listUdf3.trim();
    }

    public String getListUdf4() {
        return listUdf4;
    }

    public void setListUdf4(String listUdf4) {
        this.listUdf4 = listUdf4 == null ? null : listUdf4.trim();
    }

    public String getListUdf5() {
        return listUdf5;
    }

    public void setListUdf5(String listUdf5) {
        this.listUdf5 = listUdf5 == null ? null : listUdf5.trim();
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
}
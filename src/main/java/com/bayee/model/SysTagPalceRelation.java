package com.bayee.model;

public class SysTagPalceRelation {
    private Integer relId;

    private Integer relPlaceId;

    private Integer relTagId;

    private Integer relStatus;

    private String relUdf1;

    private String relUdf2;

    private String relUdf3;

    private String relUdf4;

    private String relUdf5;

    public Integer getRelId() {
        return relId;
    }

    public void setRelId(Integer relId) {
        this.relId = relId;
    }

    public Integer getRelPlaceId() {
        return relPlaceId;
    }

    public void setRelPlaceId(Integer relPlaceId) {
        this.relPlaceId = relPlaceId;
    }

    public Integer getRelTagId() {
        return relTagId;
    }

    public void setRelTagId(Integer relTagId) {
        this.relTagId = relTagId;
    }

    public Integer getRelStatus() {
        return relStatus;
    }

    public void setRelStatus(Integer relStatus) {
        this.relStatus = relStatus;
    }

    public String getRelUdf1() {
        return relUdf1;
    }

    public void setRelUdf1(String relUdf1) {
        this.relUdf1 = relUdf1 == null ? null : relUdf1.trim();
    }

    public String getRelUdf2() {
        return relUdf2;
    }

    public void setRelUdf2(String relUdf2) {
        this.relUdf2 = relUdf2 == null ? null : relUdf2.trim();
    }

    public String getRelUdf3() {
        return relUdf3;
    }

    public void setRelUdf3(String relUdf3) {
        this.relUdf3 = relUdf3 == null ? null : relUdf3.trim();
    }

    public String getRelUdf4() {
        return relUdf4;
    }

    public void setRelUdf4(String relUdf4) {
        this.relUdf4 = relUdf4 == null ? null : relUdf4.trim();
    }

    public String getRelUdf5() {
        return relUdf5;
    }

    public void setRelUdf5(String relUdf5) {
        this.relUdf5 = relUdf5 == null ? null : relUdf5.trim();
    }
}
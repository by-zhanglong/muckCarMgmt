package com.bayee.model;

public class WarnDetail {
    private Integer wdId;

    private Integer wdListId;

    private Integer wdCarId;

    private String wdChannelId;

    private String wdChannelPlace;

    private Integer wdTypeId;

    private String wdTypeName;

    private String wdUdf2;

    private String wdUdf3;

    private String wdUdf4;

    private String wdUdf5;

    public Integer getWdId() {
        return wdId;
    }

    public void setWdId(Integer wdId) {
        this.wdId = wdId;
    }

    public Integer getWdListId() {
        return wdListId;
    }

    public void setWdListId(Integer wdListId) {
        this.wdListId = wdListId;
    }

    public Integer getWdCarId() {
        return wdCarId;
    }

    public void setWdCarId(Integer wdCarId) {
        this.wdCarId = wdCarId;
    }

    public String getWdChannelId() {
        return wdChannelId;
    }

    public void setWdChannelId(String wdChannelId) {
        this.wdChannelId = wdChannelId == null ? null : wdChannelId.trim();
    }

    public String getWdChannelPlace() {
        return wdChannelPlace;
    }

    public void setWdChannelPlace(String wdChannelPlace) {
        this.wdChannelPlace = wdChannelPlace == null ? null : wdChannelPlace.trim();
    }

    public Integer getWdTypeId() {
        return wdTypeId;
    }

    public void setWdTypeId(Integer wdTypeId) {
        this.wdTypeId = wdTypeId;
    }

    public String getWdTypeName() {
        return wdTypeName;
    }

    public void setWdTypeName(String wdTypeName) {
        this.wdTypeName = wdTypeName == null ? null : wdTypeName.trim();
    }

    public String getWdUdf2() {
        return wdUdf2;
    }

    public void setWdUdf2(String wdUdf2) {
        this.wdUdf2 = wdUdf2 == null ? null : wdUdf2.trim();
    }

    public String getWdUdf3() {
        return wdUdf3;
    }

    public void setWdUdf3(String wdUdf3) {
        this.wdUdf3 = wdUdf3 == null ? null : wdUdf3.trim();
    }

    public String getWdUdf4() {
        return wdUdf4;
    }

    public void setWdUdf4(String wdUdf4) {
        this.wdUdf4 = wdUdf4 == null ? null : wdUdf4.trim();
    }

    public String getWdUdf5() {
        return wdUdf5;
    }

    public void setWdUdf5(String wdUdf5) {
        this.wdUdf5 = wdUdf5 == null ? null : wdUdf5.trim();
    }
}
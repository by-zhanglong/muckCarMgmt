package com.bayee.model;

public class SysTagTypeInfo {
    private Integer typeId;

    private String typeName;

    private Integer typeStatus;

    private String typeUdf1;

    private String typeUdf2;

    private String typeUdf3;

    private String typeUdf4;

    private String typeUdf5;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public Integer getTypeStatus() {
        return typeStatus;
    }

    public void setTypeStatus(Integer typeStatus) {
        this.typeStatus = typeStatus;
    }

    public String getTypeUdf1() {
        return typeUdf1;
    }

    public void setTypeUdf1(String typeUdf1) {
        this.typeUdf1 = typeUdf1 == null ? null : typeUdf1.trim();
    }

    public String getTypeUdf2() {
        return typeUdf2;
    }

    public void setTypeUdf2(String typeUdf2) {
        this.typeUdf2 = typeUdf2 == null ? null : typeUdf2.trim();
    }

    public String getTypeUdf3() {
        return typeUdf3;
    }

    public void setTypeUdf3(String typeUdf3) {
        this.typeUdf3 = typeUdf3 == null ? null : typeUdf3.trim();
    }

    public String getTypeUdf4() {
        return typeUdf4;
    }

    public void setTypeUdf4(String typeUdf4) {
        this.typeUdf4 = typeUdf4 == null ? null : typeUdf4.trim();
    }

    public String getTypeUdf5() {
        return typeUdf5;
    }

    public void setTypeUdf5(String typeUdf5) {
        this.typeUdf5 = typeUdf5 == null ? null : typeUdf5.trim();
    }
}
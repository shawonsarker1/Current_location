package com.techdecode.startechattendance.models;

public class Leave_Type {
    String CODE_DESC;
    String SOFT_CODE;

    public void setCODE_DESC(String CODE_DESC) {
        this.CODE_DESC = CODE_DESC;
    }

    public void setSOFT_CODE(String SOFT_CODE) {
        this.SOFT_CODE = SOFT_CODE;
    }

    public String getCODE_DESC() {
        return CODE_DESC;
    }

    public String getSOFT_CODE() {
        return SOFT_CODE;
    }

    public Leave_Type(String CODE_DESC, String SOFT_CODE) {
        this.CODE_DESC = CODE_DESC;
        this.SOFT_CODE = SOFT_CODE;
    }
    public String toString() {
        return CODE_DESC;
    }
}

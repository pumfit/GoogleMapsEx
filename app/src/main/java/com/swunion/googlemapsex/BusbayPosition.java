package com.swunion.googlemapsex;

public class BusbayPosition {
    private String busbayNo;
    private String name;
    private String x;
    private String y;

    public  BusbayPosition(){}

    public  BusbayPosition(String busbayNo,String name,String x,String y)
    {
        this.busbayNo = busbayNo;
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getBusbayNo()
    {
        return busbayNo;
    }

    public void setBusbayNo(String busbayNo) {
        this.busbayNo = busbayNo;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getX()
    {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }
}

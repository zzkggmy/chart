package com.example.chart.pie_chart;

import android.graphics.Color;

import java.util.List;

public class PieChartBean {
    private float divideNum;
    private TextShowType textShowType;
    private float textSize;
    private int textColor = Color.WHITE;
    private List<DataBean> datas;
    private float circleRadius = 0;

    private float shadowWidth = 25;

    private float textMarginStart = 20;

    public float getTextMarginStart() {
        return textMarginStart;
    }

    public void setTextMarginStart(float textMarginStart) {
        this.textMarginStart = textMarginStart;
    }

    public float getCircleRadius() {
        return circleRadius;
    }

    public void setCircleRadius(float circleRadius) {
        this.circleRadius = circleRadius;
    }

    public float getShadowWidth() {
        return shadowWidth;
    }

    public void setShadowWidth(float shadowWidth) {
        this.shadowWidth = shadowWidth;
    }

    public List<DataBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DataBean> datas) {
        this.datas = datas;
    }

    public float getDivideNum() {
        return divideNum;
    }

    public void setDivideNum(float divideNum) {
        this.divideNum = divideNum;
    }

    public TextShowType getTextShowType() {
        return textShowType;
    }

    public void setTextShowType(TextShowType textShowType) {
        this.textShowType = textShowType;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public static class DataBean {
        private double num;

        public double getNum() {
            return num;
        }

        public void setNum(double num) {
            this.num = num;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        private int color;
    }

    public enum TextShowType {
        Horizontal,
        FollowArc,
        Extension;
    }
}

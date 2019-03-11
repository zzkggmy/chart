package com.example.chart.line_chart;

import java.util.List;

public class LineChartBean {
    private double xAxis;
    private double yAxis;

    private double xAxisMaxNum;
    private double yAxisMaxNum;

    private List<Double> datas;

    private List<OtherDataBean> otherData;

    private int lineColor;

    private SelectType selectType = SelectType.point;

    private boolean clickEnable = true;

    private double selectPaintWidth;

    private int selectPaintColor;

    private float otherLineWidth;

    public float getOtherLineWidth() {
        return otherLineWidth;
    }

    public void setOtherLineWidth(float otherLineWidth) {
        this.otherLineWidth = otherLineWidth;
    }

    public boolean isClickEnable() {
        return clickEnable;
    }

    public void setClickEnable(boolean clickEnable) {
        this.clickEnable = clickEnable;
    }

    public SelectType getSelectType() {
        return selectType;
    }

    public void setSelectType(SelectType selectType) {
        this.selectType = selectType;
    }

    public List<OtherDataBean> getOtherData() {
        return otherData;
    }

    public void setOtherData(List<OtherDataBean> otherData) {
        this.otherData = otherData;
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public double getSelectPaintWidth() {
        return selectPaintWidth;
    }

    public void setSelectPaintWidth(double selectPaintWidth) {
        this.selectPaintWidth = selectPaintWidth;
    }

    public int getSelectPaintColor() {
        return selectPaintColor;
    }

    public void setSelectPaintColor(int selectPaintColor) {
        this.selectPaintColor = selectPaintColor;
    }

    public double getxAxisMaxNum() {
        return xAxisMaxNum;
    }

    public void setxAxisMaxNum(double xAxisMaxNum) {
        this.xAxisMaxNum = xAxisMaxNum;
    }

    public double getyAxisMaxNum() {
        return yAxisMaxNum;
    }

    public void setyAxisMaxNum(double yAxisMaxNum) {
        this.yAxisMaxNum = yAxisMaxNum;
    }

    public double getxAxis() {
        return xAxis;
    }

    public void setxAxis(double xAxis) {
        this.xAxis = xAxis;
    }

    public double getyAxis() {
        return yAxis;
    }

    public void setyAxis(double yAxis) {
        this.yAxis = yAxis;
    }

    public List<Double> getDatas() {
        return datas;
    }

    public void setDatas(List<Double> datas) {
        this.datas = datas;
    }

    public static class OtherDataBean{
        public List<Double> getOthersNumList() {
            return othersNumList;
        }

        public void setOthersNumList(List<Double> othersNumList) {
            this.othersNumList = othersNumList;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        private List<Double> othersNumList;
        private int color;
    }

    public enum SelectType {
        all,
        point;
    }
}

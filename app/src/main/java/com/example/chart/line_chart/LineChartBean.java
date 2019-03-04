package com.example.chart.line_chart;

import java.util.List;

public class LineChartBean {
    private double xAxis;
    private double yAxis;
    private double xAxisMaxNum;
    private double yAxisMaxNum;
    private List<DataBean> datas;
    private int lineColor;
    private SelectType selectType = SelectType.point;
    private boolean clickEnable = true;

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

    private double selectPaintWidth;
    private int selectPaintColor;

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

    public List<DataBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DataBean> datas) {
        this.datas = datas;
    }

    public static class DataBean {
        private double num;
        private int color;

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
    }

    public enum SelectType{
        all,
        point;
    }
}

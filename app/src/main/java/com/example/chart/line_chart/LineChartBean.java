package com.example.chart.line_chart;

import java.util.List;

public class LineChartBean {
    private double xAxis;
    private double yAxis;
    private double xAxisMaxNum;
    private double selectPaintWidth;
    private int selectPaintColor;

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

    private double yAxisMaxNum;
    private List<DataBean> datas;

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
}

package com.example.chart.pie_chart;

import java.util.List;

public class BarChartBean {
    private double xAxisNum;
    private double yAxisNum;
    private List<Integer> barColors;
    private float barWidth;

    private List<DataBean> datas;

    public double getxAxisNum() {
        return xAxisNum;
    }

    public void setxAxisNum(double xAxisNum) {
        this.xAxisNum = xAxisNum;
    }

    public double getyAxisNum() {
        return yAxisNum;
    }

    public void setyAxisNum(double yAxisNum) {
        this.yAxisNum = yAxisNum;
    }

    public List<DataBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DataBean> datas) {
        this.datas = datas;
    }

    public List<Integer> getBarColors() {
        return barColors;
    }

    public void setBarColors(List<Integer> barColors) {
        this.barColors = barColors;
    }

    public float getBarWidth() {
        return barWidth;
    }

    public void setBarWidth(float barWidth) {
        this.barWidth = barWidth;
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

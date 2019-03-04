package com.example.chart;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FormatUtils {

    private XAxisFormat xAxisFormat;
    private YAxisFormat yAxisFormat;

    public void xAxisFormat(XAxisFormat xAxisFormat) {
        if (this.xAxisFormat == null) {
            this.xAxisFormat = this::roundFormat;
        } else {
            this.xAxisFormat = xAxisFormat;
        }
    }

    public void yAxisFormat(YAxisFormat yAxisFormat) {
        if (this.yAxisFormat == null) {
            this.yAxisFormat = this::roundFormat;
        } else {
            this.yAxisFormat = yAxisFormat;
        }
    }

    private String roundFormat(Double value) {
        BigDecimal b = new BigDecimal(value);
        return b.setScale(1, RoundingMode.HALF_UP).toString();
    }
}

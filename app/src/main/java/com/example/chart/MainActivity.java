package com.example.chart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.chart.bar_chart.BarChartBean;
import com.example.chart.bar_chart.BarChartView;
import com.example.chart.line_chart.LineChartBean;
import com.example.chart.line_chart.LineChartView;
import com.example.chart.pie_chart.PieChartBean;
import com.example.chart.pie_chart.PieChartView;
import com.example.chart.pie_chart.ViewData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private int[] colors = {R.color.color_e10000, R.color.color_ff6060, R.color.color_628ef9_20, R.color.color_73dc1d, R.color.color_3ebfff, R.color.color_BD10E0, R.color.color_565759, R.color.blue_1, R.color.yellow_1, R.color.color_56d7ba};
    private Double[] nums = {30.5, 33.6, 19d, 13.6, 17.8, 20d, 60d, 45d, 65d, 78d};
    private List<PieChartBean.DataBean> pieList = new ArrayList<>();
    private PieChartBean pieChartBean = new PieChartBean();

    private List<BarChartBean.DataBean> barList = new ArrayList<>();
    private BarChartBean barChartBean = new BarChartBean();

    private List<LineChartBean.DataBean> lineList = new ArrayList<>();
    private LineChartBean lineChartBean = new LineChartBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PieChartView pieChartView = findViewById(R.id.pie_chart);
        BarChartView barChartView = findViewById(R.id.bar_chart);
        LineChartView lineChartView = findViewById(R.id.line_chart);
        for (int i = 0; i < colors.length; i++) {
            PieChartBean.DataBean pieDataBean = new PieChartBean.DataBean();
            pieDataBean.setColor(colors[i]);
            pieDataBean.setNum(nums[i]);
            pieList.add(pieDataBean);


            BarChartBean.DataBean dataBean = new BarChartBean.DataBean();
            dataBean.setColor(colors[i]);
            dataBean.setNum(nums[i]);
            barList.add(dataBean);

            LineChartBean.DataBean lineBean = new LineChartBean.DataBean();
            lineBean.setColor(colors[i]);
            lineBean.setNum(nums[i]);
            lineList.add(lineBean);
        }
        pieChartBean.setDatas(pieList);
        pieChartBean.setTextShowType(PieChartBean.TextShowType.Extension);


        lineChartBean.setxAxis(5);
        lineChartBean.setyAxis(5);
        lineChartBean.setDatas(lineList);
        lineChartBean.setxAxisMaxNum(100);
        lineChartBean.setSelectPaintColor(this.getResources().getColor(R.color.color_BD10E0));
        lineChartBean.setSelectPaintWidth(2);
        lineChartBean.setLineColor(getResources().getColor(R.color.color_73DC1D));
        lineChartBean.setClickEnable(true);
        lineChartBean.setSelectType(LineChartBean.SelectType.all);


        barChartBean.setAxisTextColor(getResources().getColor(R.color.black_3));
        barChartBean.setAxisTextSize(12);
        barChartBean.setyScale(4);
        barChartBean.setBarWidth(20);
        barChartBean.setSelectType(BarChartBean.SelectType.zoom);
        barChartBean.setDatas(barList);


        lineChartView.xAxisFormat(this::roundFormat);
        pieChartView.setResource(pieChartBean);
        barChartView.setData(barChartBean);
        lineChartView.setData(lineChartBean);
    }

    private String roundFormat(Double value) {
        BigDecimal b = new BigDecimal(value);
        return b.setScale(0, RoundingMode.HALF_UP).toString();
    }
}
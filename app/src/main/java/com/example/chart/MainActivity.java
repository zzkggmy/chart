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

    private List<Double> lineList = new ArrayList<>();
    private LineChartBean lineChartBean = new LineChartBean();
    private List<Double> list1 = new ArrayList<>();
    private List<Double> list2 = new ArrayList<>();
    private List<Double> list3 = new ArrayList<>();
    private LineChartBean.OtherDataBean otherDataBean1 = new LineChartBean.OtherDataBean();
    private LineChartBean.OtherDataBean otherDataBean2 = new LineChartBean.OtherDataBean();
    private LineChartBean.OtherDataBean otherDataBean3 = new LineChartBean.OtherDataBean();
    private List<LineChartBean.OtherDataBean> otherData = new ArrayList<>();

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
            list1.add(nums[i] * 0.5);
            list2.add(nums[i] * 1.5);
            list3.add(nums[i] * 2.0);
            lineList.add(nums[i]);
        }
        pieChartBean.setDatas(pieList);
        pieChartBean.setTextShowType(PieChartBean.TextShowType.FollowArc);


        otherDataBean1.setColor(colors[0]);
        otherDataBean1.setOthersNumList(list1);

        otherDataBean2.setColor(colors[9]);
        otherDataBean2.setOthersNumList(list2);

        otherDataBean3.setColor(colors[8]);
        otherDataBean3.setOthersNumList(list3);

        otherData.add(otherDataBean1);
        otherData.add(otherDataBean2);
        otherData.add(otherDataBean3);
        lineChartBean.setOtherData(otherData);

        lineChartBean.setOtherLineWidth(2);
        lineChartBean.setxAxis(5);
        lineChartBean.setyAxis(5);
        lineChartBean.setDatas(lineList);
        lineChartBean.setxAxisMaxNum(100);
        lineChartBean.setSelectPaintColor(R.color.color_BD10E0);
        lineChartBean.setSelectPaintWidth(2);
        lineChartBean.setLineColor(R.color.color_73DC1D);
        lineChartBean.setClickEnable(true);
        lineChartBean.setSelectType(LineChartBean.SelectType.all);


        barChartBean.setAxisTextColor(R.color.black_3);
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
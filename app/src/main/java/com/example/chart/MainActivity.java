package com.example.chart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private int[] colors = {R.color.color_e10000, R.color.color_ff6060, R.color.color_628ef9_20, R.color.color_73dc1d, R.color.color_3ebfff, R.color.color_BD10E0, R.color.color_565759};
    private Double[] nums = {10.5, 33.6, 14d, 13.6, 17.8, 20d, 60d};
    private List<ViewData> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PieChartView pieChartView = findViewById(R.id.pie_chart);
        for (int i = 0; i < colors.length; i++) {
            ViewData bean = new ViewData();
            bean.setColor(colors[i]);
            bean.setPercent(nums[i]);
            list.add(bean);
        }
        pieChartView.setResource(list);
    }
}

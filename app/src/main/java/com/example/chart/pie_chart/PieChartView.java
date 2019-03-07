package com.example.chart.pie_chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.PI;
import static java.lang.Math.floor;

public class PieChartView extends View {
    private Context mContext;
    private float left = 0f;
    private float right = 0f;
    private float top = 0f;
    private float bottom = 0f;

    private float width = 0f;
    private float height = 0f;
    private Paint paint = new Paint();
    private Paint withinPaint = new Paint();
    private Paint textPaint = new Paint();
    private Paint circlePaint = new Paint();
    private Paint dividePaint = new Paint();

    private float allNum = 0F;
    private List<ViewData> datas = new ArrayList<>();
    private boolean select = false;
    private float radius = 0;
    private List<PieBaseData> pieList = new ArrayList<>();
    private float bigRadius = 0;
    private float circleRadius = 0;
    private float divideNum = 3;
    private int index = -1;

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        withinPaint.setAntiAlias(true);
        withinPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(20f);
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.WHITE);
        circlePaint.setStyle(Paint.Style.FILL);
        dividePaint.setAntiAlias(true);
        dividePaint.setColor(Color.TRANSPARENT);
        dividePaint.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.left = left;
        this.right = right;
        this.bottom = bottom;
        this.top = top;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
        bigRadius = (Math.min(w, h)) / 2;
        radius = bigRadius - 30;
        circleRadius = radius / 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPieChart(canvas);
    }

    private void drawPieChart(Canvas canvas) {
        canvas.translate(width / 2, height / 2);
        RectF oval = new RectF(-radius, -radius, radius, radius);
        RectF oval1 = new RectF(-bigRadius, -bigRadius, bigRadius, bigRadius);
        RectF oval2 = new RectF(-circleRadius - 25, -circleRadius - 25, circleRadius + 25, circleRadius + 25);
        float startAngle = -90;
        float textStartAngle = 0;
        float sweepAngle;
        float textAngle;
        for (int i = 0; i < datas.size(); i++) {
            withinPaint.setColor(mContext.getResources().getColor(datas.get(i).getColor()));
            paint.setColor(mContext.getResources().getColor(datas.get(i).getColor()));
            canvas.drawArc(oval, -startAngle, -divideNum, true, dividePaint);
            startAngle += divideNum;
            textStartAngle += divideNum;
            sweepAngle = (float) (datas.get(i).getPercent() / allNum * (360 - datas.size() * divideNum));
            PieBaseData pieBaseData = new PieBaseData();
            pieBaseData.setStartAngle(startAngle);
            pieBaseData.setSweepAngle(sweepAngle);
            pieList.add(pieBaseData);
            if (select && index == i)
                canvas.drawArc(oval1, -startAngle, -sweepAngle, true, paint);
            else
                canvas.drawArc(oval, -startAngle, -sweepAngle, true, paint);
            canvas.drawArc(oval2, -startAngle, -sweepAngle, true, withinPaint);
            //文字角度
            textAngle = textStartAngle + sweepAngle / 2;
            float y = (float) (radius / 2 * Math.cos(Math.toRadians(textAngle)));    //计算文字位置坐标
            float x = (float) (radius / 2 * Math.sin(Math.toRadians(textAngle)));
            canvas.drawText("" + (-startAngle), x, y, textPaint);
            startAngle += sweepAngle;
            textStartAngle += sweepAngle;
        }
        canvas.drawCircle(0, 0, circleRadius, circlePaint);
    }

    public void setResource(List<ViewData> data) {
        this.datas.clear();
        this.datas.addAll(data);
        for (ViewData bean : data) {
            allNum += bean.getPercent();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_DOWN:
                float startAngle = -90;
                float sweepAngle;
                float angle;
                float x1;
                float y1;
                x1 = (float) (x * radius / Math.abs(Math.sqrt(x * x + y * y)));
                y1 = (float) (y * radius / Math.abs(Math.sqrt(x * x + y * y)));
                angle = -((float) (Math.asin(x1 / Math.abs(Math.sqrt((x1 * x1 + y1 * y1))))) - 1) * 360;
                for (int i = 0; i < datas.size(); i++) {
                    startAngle += divideNum;
                    sweepAngle = (float) (datas.get(i).getPercent() / allNum * (360 - datas.size() * divideNum));
                    if (angle <= -startAngle && angle >= -startAngle - sweepAngle) {
                        select = !select;
                        index = i;
                    }
                    startAngle += sweepAngle;
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);

    }
}
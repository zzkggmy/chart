package com.example.chart.pie_chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.chart.LibUtils;

import java.util.ArrayList;
import java.util.List;

public class PieChartView extends View {
    private Context mContext;

    private float width = 0f;
    private float height = 0f;
    private Paint paint = new Paint();
    private Paint shadowPaint = new Paint();
    private Paint textPaint = new Paint();
    private Paint circlePaint = new Paint();
    private Paint dividePaint = new Paint();
    private Paint pathPaint = new Paint();

    private float allNum = 0F;
    private List<PieChartBean.DataBean> datas = new ArrayList<>();
    private boolean select = false;
    private float radius = 0;
    private List<PieBaseData> pieList = new ArrayList<>();
    private float selectRadius = 0;
    private float circleRadius = 0;
    private float divideNum = 3;
    private int index = -1;
    private float shadowRadius;
    private float textMarginStart;
    private float extensionLength = 0;
    private float cornerLength = 0;

    private int textColor;

    private PieChartBean.TextShowType textShowType;

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initPaint();
        this.width = w;
        this.height = h;
        selectRadius = (Math.min(w, h)) / 2;
        radius = selectRadius - 30;
        circleRadius = circleRadius == 0 ? radius / 3 : LibUtils.dip2px(mContext, circleRadius);
        this.extensionLength = LibUtils.dip2px(mContext, 5);
        this.cornerLength = LibUtils.dip2px(mContext, 10);
    }

    private void initPaint() {
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        shadowPaint.setAntiAlias(true);
        shadowPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(20f);
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.WHITE);
        circlePaint.setStyle(Paint.Style.FILL);
        dividePaint.setAntiAlias(true);
        dividePaint.setColor(Color.TRANSPARENT);
        dividePaint.setStyle(Paint.Style.FILL);
        pathPaint.setAntiAlias(true);
        pathPaint.setColor(Color.BLACK);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(LibUtils.dip2px(mContext, 1));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPieChart(canvas);
    }

    private void drawPieChart(Canvas canvas) {
        canvas.translate(width / 2, height / 2);
        RectF oval = new RectF(-radius, -radius, radius, radius);
        RectF oval1 = new RectF(-selectRadius, -selectRadius, selectRadius, selectRadius);
        RectF oval2 = new RectF(-shadowRadius, -shadowRadius, shadowRadius, shadowRadius);
        float startAngle = -90;
        float textStartAngle = 0;
        float sweepAngle;
        float textAngle;
        switch (textShowType) {
            case FollowArc:
                for (int i = 0; i < datas.size(); i++) {
                    shadowPaint.setColor(mContext.getResources().getColor(datas.get(i).getColor()));
                    shadowPaint.setAlpha(25);
                    paint.setColor(mContext.getResources().getColor(datas.get(i).getColor()));
                    canvas.drawArc(oval, -startAngle, -divideNum, true, dividePaint);
                    startAngle += divideNum;
                    textStartAngle += divideNum;
                    sweepAngle = (float) (datas.get(i).getNum() / allNum * (360 - datas.size() * divideNum));
                    PieBaseData pieBaseData = new PieBaseData();
                    pieBaseData.setStartAngle(startAngle);
                    pieBaseData.setSweepAngle(sweepAngle);
                    pieList.add(pieBaseData);
                    canvas.drawArc(oval2, -startAngle, -sweepAngle, true, shadowPaint);
                    if (select && index == i)
                        canvas.drawArc(oval1, -startAngle, -sweepAngle, true, paint);
                    else
                        canvas.drawArc(oval, -startAngle, -sweepAngle, true, paint);
                    //文字角度
                    textAngle = textStartAngle + sweepAngle / 2;
                    Path path = new Path();
                    float endTextY = (float) ((radius - LibUtils.dip2px(mContext, 5)) * Math.cos(Math.toRadians(textAngle)));    //计算文字位置坐标
                    float endTextX = (float) ((radius - LibUtils.dip2px(mContext, 5)) * Math.sin(Math.toRadians(textAngle)));
                    float startTextY = (float) ((circleRadius + textMarginStart) * Math.cos(Math.toRadians(textAngle)));    //计算文字位置坐标
                    float startTextX = (float) ((circleRadius + textMarginStart) * Math.sin(Math.toRadians(textAngle)));
                    path.rMoveTo(startTextX, startTextY);
                    path.lineTo(endTextX, endTextY);
                    canvas.drawTextOnPath("" + (-startAngle), path, 10, 10, textPaint);
                    startAngle += sweepAngle;
                    textStartAngle += sweepAngle;
                }
                break;
            case Extension:
                for (int i = 0; i < datas.size(); i++) {
                    shadowPaint.setColor(mContext.getResources().getColor(datas.get(i).getColor()));
                    paint.setColor(mContext.getResources().getColor(datas.get(i).getColor()));
                    canvas.drawArc(oval, -startAngle, -divideNum, true, dividePaint);
                    startAngle += divideNum;
                    textStartAngle += divideNum;
                    sweepAngle = (float) (datas.get(i).getNum() / allNum * (360 - datas.size() * divideNum));
                    PieBaseData pieBaseData = new PieBaseData();
                    pieBaseData.setStartAngle(startAngle);
                    pieBaseData.setSweepAngle(sweepAngle);
                    pieList.add(pieBaseData);
                    canvas.drawArc(oval2, -startAngle, -sweepAngle, true, shadowPaint);
                    if (select && index == i)
                        canvas.drawArc(oval1, -startAngle, -sweepAngle, true, paint);
                    else
                        canvas.drawArc(oval, -startAngle, -sweepAngle, true, paint);
                    //文字角度
                    textAngle = textStartAngle + sweepAngle / 2;
                    Path path = new Path();
                    float lastY = (float) ((radius / 12 * 13) * Math.cos(Math.toRadians(textAngle)));
                    float endY = lastY >= 0 ? lastY + cornerLength : lastY - cornerLength;
                    path.rMoveTo((float) ((radius / 12 * 11) * Math.sin(Math.toRadians(textAngle))), (float) ((radius / 12 * 11) * Math.cos(Math.toRadians(textAngle))));
                    path.lineTo((float) ((radius / 12 * 13) * Math.sin(Math.toRadians(textAngle))), (float) ((radius / 12 * 13) * Math.cos(Math.toRadians(textAngle))));
                    path.lineTo((float) ((radius / 12 * 13) * Math.sin(Math.toRadians(textAngle))), lastY + cornerLength);
                    canvas.drawPath(path, pathPaint);
                    float y = (float) (radius / 2 * Math.cos(Math.toRadians(textAngle)));    //计算文字位置坐标
                    float x = (float) (radius / 2 * Math.sin(Math.toRadians(textAngle)));
                    canvas.drawText("" + (-startAngle), (float) ((radius / 12 * 13) * Math.sin(Math.toRadians(textAngle))), lastY + cornerLength, textPaint);
                    startAngle += sweepAngle;
                    textStartAngle += sweepAngle;
                }
                break;
            case Horizontal:
                for (int i = 0; i < datas.size(); i++) {
                    shadowPaint.setColor(mContext.getResources().getColor(datas.get(i).getColor()));
                    paint.setColor(mContext.getResources().getColor(datas.get(i).getColor()));
                    canvas.drawArc(oval, -startAngle, -divideNum, true, dividePaint);
                    startAngle += divideNum;
                    textStartAngle += divideNum;
                    sweepAngle = (float) (datas.get(i).getNum() / allNum * (360 - datas.size() * divideNum));
                    PieBaseData pieBaseData = new PieBaseData();
                    pieBaseData.setStartAngle(startAngle);
                    pieBaseData.setSweepAngle(sweepAngle);
                    pieList.add(pieBaseData);
                    canvas.drawArc(oval2, -startAngle, -sweepAngle, true, shadowPaint);
                    if (select && index == i)
                        canvas.drawArc(oval1, -startAngle, -sweepAngle, true, paint);
                    else
                        canvas.drawArc(oval, -startAngle, -sweepAngle, true, paint);
                    //文字角度
                    textAngle = textStartAngle + sweepAngle / 2;
                    float y = (float) (radius / 2 * Math.cos(Math.toRadians(textAngle)));    //计算文字位置坐标
                    float x = (float) (radius / 2 * Math.sin(Math.toRadians(textAngle)));
                    canvas.drawText("" + (-startAngle), x, y, textPaint);
                    startAngle += sweepAngle;
                    textStartAngle += sweepAngle;
                }
                break;
        }
        canvas.drawCircle(0, 0, circleRadius, circlePaint);
    }

    public void setResource(PieChartBean pieChartBean) {
        this.datas.clear();
        this.datas.addAll(pieChartBean.getDatas());
        for (PieChartBean.DataBean bean : pieChartBean.getDatas()) {
            allNum += bean.getNum();
        }
        this.circleRadius = pieChartBean.getCircleRadius();
        this.textShowType = pieChartBean.getTextShowType();
        this.shadowRadius = this.circleRadius + LibUtils.dip2px(mContext, pieChartBean.getShadowWidth());
        this.textMarginStart = LibUtils.dip2px(mContext, pieChartBean.getTextMarginStart());
        this.textColor = pieChartBean.getTextColor();
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
                    sweepAngle = (float) (datas.get(i).getNum() / allNum * (360 - datas.size() * divideNum));
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
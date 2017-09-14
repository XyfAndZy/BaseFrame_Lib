package com.xyf.baseframe_lib.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.xyf.baseframe_lib.R;

/**
 * 仿IOS的菊花过渡动画
 */

public class ChrysantheView extends View {

    private int mDefault = 0xff999999;
    private int mLength = 10;
    private int mWidth = 2;
    private int color;
    private float length;
    private int width;
    private Paint mPaint;
    private int mHeight;
    private int mCenterX;
    private int mCenterY;
    private int control = 1;

    public ChrysantheView(Context context) {
        this(context, null);
    }

    public ChrysantheView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChrysantheView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(attrs);
        initPaint();
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ChrysantheView);
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.ChrysantheView_ChrysantheView_color:
                    color = typedArray.getColor(attr, mDefault);
                    break;
                case R.styleable.ChrysantheView_ChrysantheView_length:
                    length = typedArray.getDimensionPixelSize(attr, mLength);
                    break;
                case R.styleable.ChrysantheView_ChrysantheView_width:
                    width = typedArray.getDimensionPixelSize(attr, mWidth);
                    break;
            }
        }
        typedArray.recycle();
    }

    private void initPaint() {
        mPaint = new Paint();
        //抗锯齿
        //http://blog.csdn.net/lovexieyuan520/article/details/50732023
        mPaint.setAntiAlias(true);
        //抗抖动
        mPaint.setDither(true);
        //设置笔触
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //设置填充方式
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(color);
        mPaint.setStrokeWidth(width);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        mCenterX = mWidth / 2;
        mCenterY = mHeight / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //12等分,画12个直线
        for (int i = 0; i < 12; i++) {
            mPaint.setAlpha(((i + 1 + control) % 12 * 255) / 12);
            canvas.drawLine(mCenterX, mCenterY - length, mCenterX, mCenterY - length * 2, mPaint);
            canvas.rotate(30, mCenterX, mCenterY);
        }
    }

    //绘制结束时候的渲染
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(12, 1);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                control = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }
}

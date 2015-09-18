package org.ilulu.countdowncircle;

import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.text.DecimalFormat;

/**
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * ┃　　　┃   神兽保佑
 * ┃　　　┃   代码无BUG！
 * ┃　　　┗━━━┓
 * ┃　　　　　　　┣┓
 * ┃　　　　　　　┏┛
 * ┗┓┓┏━┳┓┏┛
 * ┃┫┫　┃┫┫
 * ┗┻┛　┗┻┛
 * Created by leelucifer on 15/9/16.
 */
public class Chart extends Drawable {
    Paint paint;
    float mMargin =50f;


    float mLeft;
    float mRight;
    float mTop;
    float mBottom;
    float mWidth;
    float mStrokeWidth;

    float mOnlineNum =500;
    float mTotalNum =1000;
    String online_label="在线人数";

    int mProgressColor=0;

    public float getOnlineNum() {
        return mOnlineNum;
    }

    public void setOnlineNum(float mOnlineNum) {
        this.mOnlineNum = mOnlineNum;
        invalidateSelf();
    }

    public float getTotalNum() {
        return mTotalNum;
    }

    public void setTotalNum(float mTotalNum) {
        this.mTotalNum = mTotalNum;
        invalidateSelf();
    }

    @Override
    public void draw(Canvas canvas) {
        paint=new Paint();
        setArcBounds(canvas);
        drawBack(canvas, paint);
        drawProgress(canvas, paint);
        drawCenterPoint(canvas,paint);
        drawText(canvas,paint);
    }

    /**
     * 计算圆弧的大小并确定4个边界
     * @param canvas
     */
    private void setArcBounds(Canvas canvas){
        //画布的大小
        Rect canvasBounds=canvas.getClipBounds();
        float width=canvasBounds.width();
        float height=canvasBounds.height();

        if(width>height){
            mWidth=height;
            float whDiff=width-height;
            mLeft =canvasBounds.left+(whDiff/2)+ mMargin;
            mRight =canvasBounds.right-(whDiff/2)- mMargin;
            mTop =canvasBounds.top+ mMargin;
            mBottom =canvasBounds.bottom- mMargin;
        }else{
            mWidth=width;
            float whDiff=height-width;
            mLeft =canvasBounds.left+ mMargin;
            mRight =canvasBounds.right- mMargin;
            mTop =canvasBounds.top+(whDiff/2)+ mMargin;
            mBottom =canvasBounds.bottom-(whDiff/2)- mMargin;
        }
        mStrokeWidth =mWidth/20;
    }

    /**
     * 绘制地板颜色
     * @param canvas
     * @param paint
     */
    private void drawBack(Canvas canvas,Paint paint){
        //先绘制底色
        paint.setAntiAlias(true);                       //设置画笔为无锯齿
        paint.setColor(Color.rgb(230,230,230));                    //设置画笔颜色
        canvas.drawColor(Color.WHITE);                  //白色背景
        paint.setStrokeWidth(mStrokeWidth);              //线宽
        paint.setStyle(Paint.Style.STROKE);
        RectF oval=new RectF();
        oval.left= mLeft;
        oval.right= mRight;
        oval.top= mTop;
        oval.bottom= mBottom;
        float startAngle=135f;
        float sweepAngle=360f-((startAngle-90f)*2f);
        canvas.drawArc(oval, startAngle, sweepAngle, false, paint);
    }

    /**
     * 绘制当前进度
     * @param canvas
     * @param paint
     */
    private void drawProgress(Canvas canvas,Paint paint){

        paint.setColor(Color.rgb(30, 216, 55));
        RectF oval=new RectF();
        oval.left= mLeft;
        oval.right= mRight;
        oval.top= mTop;
        oval.bottom= mBottom;
        float startAngle=135f;
        float sweepAngle=(360f-((startAngle-90f)*2f))*(mOnlineNum / mTotalNum);
        canvas.drawArc(oval, startAngle, sweepAngle, false, paint);
        drawCenterLine(startAngle, sweepAngle, canvas, paint);
    }

    /**
     * 绘制圆心和弧线最后的连线
     * @param startAngle
     * @param sweepAngle
     * @param canvas
     * @param paint
     */
    private void drawCenterLine(float startAngle,float sweepAngle,Canvas canvas,Paint paint){
        float centerX=canvas.getClipBounds().centerX();
        float centerY=canvas.getClipBounds().centerY();

        float angle=sweepAngle-(180f-(startAngle-90f));
        double realAngle=Math.toRadians(angle);
        float radius=(mRight - mLeft)/2;
        double x=centerX+(radius*Math.sin(realAngle));
        double y=centerY-(radius*Math.cos(realAngle));

        paint.setStrokeWidth(mWidth/150);
        canvas.drawLine(centerX,centerY,(float)x,(float)y,paint);
    }
    /**
     * 绘制底部的数字和文字
     * @param canvas
     * @param paint
     */
    private void drawText(Canvas canvas,Paint paint){
        //文字大致是居中对齐的样子 先找到居中的位置
        Rect bounds=canvas.getClipBounds();
        int x=bounds.centerX();

        Paint paintText=new Paint();
        paintText.setAntiAlias(true);
        paintText.setColor(Color.rgb(175, 175, 175));
        float lableFontSize=mWidth/3;
        paintText.setTextSize(lableFontSize);
        float labelFontWidth=paintText.measureText(online_label);
        while(labelFontWidth>mWidth/3){
            paintText.setTextSize(--lableFontSize);
            labelFontWidth=paintText.measureText(online_label);
        }

        float labelX=x-labelFontWidth/2;
        float labelY= mBottom - mStrokeWidth;
        //此处计算出文本的高度
        Paint.FontMetrics fontMetrics=paintText.getFontMetrics();
        float fontHeight=fontMetrics.descent - fontMetrics.ascent;
        canvas.drawText(online_label, labelX, labelY, paintText);

        Paint paint1=new Paint();
        paint1.setAntiAlias(true);
        paint1.setColor(Color.rgb(59, 85, 110));
        float fontSize=mWidth/3;
        paint1.setTextSize(fontSize);

        DecimalFormat decimalFormat=new DecimalFormat("#");
        String numString=decimalFormat.format(mOnlineNum);
        float fontWidth=paint1.measureText(numString);
        while(fontWidth>mWidth/2){
            paint1.setTextSize(--fontSize);
            fontWidth=paint1.measureText(numString);
        }
        float numX=x-fontWidth/2;
        float numY= mBottom -fontHeight;
        canvas.drawText(numString,numX,numY-50,paint1);
    }

    /**
     * 绘制中心
     */
    private void drawCenterPoint(Canvas canvas,Paint paint){
        Rect bounds=canvas.getClipBounds();
        int x=bounds.centerX();
        int y=bounds.centerY();
        paint.setStrokeWidth(mWidth/200);
        canvas.drawCircle(x,y,mWidth/50,paint);
    }


    @Override
    public void setAlpha(int i) {
        paint.setAlpha(i);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return paint.getAlpha();
    }
}

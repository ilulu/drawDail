package org.ilulu.countdowncircle.numchart;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import org.ilulu.countdowncircle.Chart;

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
 * Created by leelucifer on 15/9/18.
 */
public class NumberChart extends LinearLayout {
    Chart chart;
    ImageView imageView;
    float oldNum=0f;
    float mTotalNum;
    float mOnlineNum;
    int mProgressColor=0;

    public void setProgressColor(int mProgressColor) {
        this.mProgressColor = mProgressColor;
        chart.setProgressColor(this.mProgressColor);
    }

    public NumberChart(Context context) {
        super(context);
        init();
    }

    public NumberChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        imageView=new ImageView(getContext());
        LayoutParams layoutParams=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(imageView,layoutParams);

        chart=new Chart();
        imageView.setImageDrawable(chart);

        setChartValue();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setChartValue(){
        ObjectAnimator progressAnimator=new ObjectAnimator().ofFloat(chart,"onlineNum",oldNum,mOnlineNum);
        progressAnimator.setDuration(1000);
        progressAnimator.setInterpolator(new LinearInterpolator());

        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.playTogether(progressAnimator);

        animatorSet.start();
    }

    public void setmTotalNum(float mTotalNum) {
        this.mTotalNum = mTotalNum;
        chart.setTotalNum(this.mTotalNum);
        setChartValue();
    }

    public void setmOnlineNum(float mOnlineNum) {
        oldNum=this.mOnlineNum;
        this.mOnlineNum = mOnlineNum;
        chart.setOnlineNum(this.mOnlineNum);
        setChartValue();
    }
}

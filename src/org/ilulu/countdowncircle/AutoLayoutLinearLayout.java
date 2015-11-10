package org.ilulu.countdowncircle;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

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
 * Created by leelucifer on 15/9/21.
 */
public abstract class AutoLayoutLinearLayout extends LinearLayout {

    List<View> mAutoLayoutViews;

    public AutoLayoutLinearLayout(Context context) {
        super(context);
        init();
    }

    public AutoLayoutLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public abstract List<View> getAutoLayoutViews();

    private void init(){
        mAutoLayoutViews=getAutoLayoutViews();
        int layoutSize=mAutoLayoutViews.size();
        LayoutParams layoutParams=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1/(float)layoutSize);
        for(View childView:mAutoLayoutViews){
            addView(childView,layoutParams);
        }
    }


}

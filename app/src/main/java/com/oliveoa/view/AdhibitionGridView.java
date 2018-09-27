package com.oliveoa.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @Author：Guo 时间:2018/9/26 0026
 * 项目名:OliveOA_User
 * 包名:com.oliveoa.view
 * 类名:
 * 简述:<功能简述>
 */
public class AdhibitionGridView extends GridView {
    public AdhibitionGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdhibitionGridView(Context context) {
        super(context);
    }

    public AdhibitionGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}

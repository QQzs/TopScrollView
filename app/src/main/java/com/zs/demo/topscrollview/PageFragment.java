package com.zs.demo.topscrollview;

import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zs.demo.topscrollview.fragment.LazyFragment2;

/**
 * Created by zs
 * Date：2017年 11月 08日
 * Time：13:56
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class PageFragment extends LazyFragment2 {

    private RelativeLayout view_bg;
    private TextView mTvCenter;

    private int mIndex;
    private int[] mColors = {R.color.color1 , R.color.color2 , R.color.color3 , R.color.color4 ,
            R.color.color5 , R.color.color6 , R.color.color7 , R.color.color8 , R.color.color9
            , R.color.color10};

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_layout);

        mIndex = getArguments().getInt("index");

        view_bg = (RelativeLayout) findViewById(R.id.view_bg);
        mTvCenter = (TextView) findViewById(R.id.tv_frg);

        view_bg.setBackgroundResource(mColors[mIndex]);
        mTvCenter.setText("~~ " + mIndex + " ~~");

        Log.d("My_Log","onCreateViewLazy  " + mIndex);
    }

    @Override
    protected void onFragmentStartLazy() {
        super.onFragmentStartLazy();
        // 可见时调用
        Log.d("My_Log","onFragmentStartLazy  " + mIndex);
    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
        // 创建时调用
        Log.d("My_Log","onResumeLazy  " + mIndex);
    }
}

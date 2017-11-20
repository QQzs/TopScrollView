package com.zs.demo.topscrollview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zs.demo.topscrollview.utils.DensityUtil;
import com.zs.demo.topscrollview.utils.ScreenUtil;
import com.zs.demo.topscrollview.view.topscorllview.indicator.IndicatorViewPager;
import com.zs.demo.topscrollview.view.topscorllview.indicator.ScrollIndicatorView;
import com.zs.demo.topscrollview.view.topscorllview.indicator.slidebar.ColorBar;
import com.zs.demo.topscrollview.view.topscorllview.indicator.slidebar.ScrollBar;
import com.zs.demo.topscrollview.view.topscorllview.indicator.transition.OnTransitionTextListener;

/**
 * @author zs
 */
public class MainActivity extends FragmentActivity {

    private int unSelectTextColor;
    private ScrollIndicatorView indicator_layout;
    private IndicatorViewPager indicatorViewPager;
    private ViewPager viewPager;

    private Context mContext;
    private MyAdapter adapter;
//    private String[] mTitles = {"勇士", "马刺"};
    private String[] mTitles = {"勇士", "马刺", "凯尔特人", "骑士", "湖人", "爵士", "热火", "小牛", "雄鹿", "猛龙"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int screenWidth = ScreenUtil.getScreenWidth(this);
        mContext = this;
        indicator_layout = findViewById(R.id.indicator_layout);
        viewPager = findViewById(R.id.moretab_viewPager);

        // 当标签不多时，是否要占满屏幕
        indicator_layout.setSplitAuto(true);
        // 设置快捷返回tab
        indicator_layout.setPinnedTabView(true);
        indicator_layout.setPinnedTabBgColor(R.color.color3);
        indicator_layout.setPinnedShadow(R.drawable.tab_shadow_bg,DensityUtil.dip2px(this,2));
        // 设置滑块颜色
        ColorBar colorBar = new ColorBar(this, R.color.textColor,
                DensityUtil.dip2px(this, 2), ScrollBar.Gravity.BOTTOM);
//        indicator_layout.setScrollBarPadding(screenWidth/6);
        indicator_layout.setScrollBar(colorBar);
        // 设置选中标题颜色
        unSelectTextColor = getResources().getColor(R.color.defaultText);
        indicator_layout.setOnTransitionListener(new OnTransitionTextListener().setColor(getResources().getColor(R.color.textColor), unSelectTextColor));
        viewPager.setOffscreenPageLimit(15);
        indicatorViewPager = new IndicatorViewPager(indicator_layout, viewPager);
        adapter = new MyAdapter(getSupportFragmentManager());
        indicatorViewPager.setAdapter(adapter);
        adapter.setStrTab(mTitles);

    }

    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
        String[] titles = new String[0];

        int index;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.tab_top, container, false);
            }
            index = position;
            TextView textView = (TextView) convertView;
            textView.setText(titles[getIndex() % titles.length]);
            int padding = DensityUtil.dip2px(mContext, 11);
            textView.setPadding(padding, 0, padding, 0);
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            index = position;
            PageFragment fragment = new PageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("index", index);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getItemPosition(Object object) {
            //这是ViewPager适配器的特点,有两个值 POSITION_NONE，POSITION_UNCHANGED，默认就是POSITION_UNCHANGED,
            // POSITION_NONE 表示该 Item 会被 destroyItem方法remove 掉，然后重新加载，notifyDataChange的时候重新调用getViewForPage
            // POSITION_UNCHANGED表示不会重新加载
            return PagerAdapter.POSITION_NONE;
        }

        void setStrTab(String[] titles) {
            this.titles = titles;
            notifyDataSetChanged();
        }
    }
}

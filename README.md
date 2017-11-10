# TopScrollView
## 顶部滑动菜单
顶部滑动菜单 + ViewPager 的布局方式是新闻app的最普遍的方式，viewpager中是去除懒加载的fragment，每个fragment只有在首次可见时才加载数据，来回滑动不去重新加载，方式重新加载数据。<br>
### 效果图
![效果图](https://github.com/QQzs/Image/blob/master/TopScrollView/show_img.gif)

## 代码部分
初始化的一些设置
```Java
        indicator_layout.setSplitAuto(true);
        // 设置快捷返回tab
        indicator_layout.setPinnedTabView(true);
        indicator_layout.setPinnedTabBgColor(R.color.color3);
        indicator_layout.setPinnedShadow(R.drawable.tab_shadow_bg,DensityUtil.dip2px(this,2));
        // 设置滑块颜色
        ColorBar colorBar = new ColorBar(this, R.color.textColor,
                DensityUtil.dip2px(this, 2), ScrollBar.Gravity.BOTTOM);
        indicator_layout.setScrollBar(colorBar);
        // 设置选中标题颜色
        unSelectTextColor = getResources().getColor(R.color.defaultText);
        indicator_layout.setOnTransitionListener(new OnTransitionTextListener().setColor(getResources().getColor(R.color.textColor), unSelectTextColor));
        viewPager.setOffscreenPageLimit(15);
        indicatorViewPager = new IndicatorViewPager(indicator_layout, viewPager);
        adapter = new MyAdapter(getSupportFragmentManager());
        indicatorViewPager.setAdapter(adapter);
        adapter.setStrTab(mTitles);
```

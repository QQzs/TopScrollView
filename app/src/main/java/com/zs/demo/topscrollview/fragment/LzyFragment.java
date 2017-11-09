package com.zs.demo.topscrollview.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by cuijl on 2016/8/26.
 */
public abstract class LzyFragment extends Fragment {
    protected Handler mHandler = new Handler();

    private View convertView;
    private SparseArray<View> mViews;
    private boolean isVisible = false;//当前Fragment是否可见
    private boolean isInitView = false;//是否与View建立起映射关系
    public boolean isFirstLoad = true;//是否是第一次加载数据
    boolean isFirstResume = false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        convertView = inflater.inflate(getLayoutId(), container, false);
        mViews = new SparseArray<>();
        initView();
        isInitView = true;
        lazyLoadData();
        return convertView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.e("isVisibleToUser ", isVisibleToUser + "   " + this.getClass().getSimpleName());
        if (isVisibleToUser) {
            isVisible = true;
            lazyLoadData();
        } else {
            isVisible = false;
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void lazyLoadData() {
        if (isFirstLoad) {
//            Log.e("第一次加载 ", " isInitView  " + isInitView + "  isVisible  " + isVisible);
        } else {
            LoadCacheData();
//            Log.e("不是第一次加载", " isInitView  " + isInitView + "  isVisible  " + isVisible + "   " + this.getClass().getSimpleName());
        }
        if (!isFirstLoad || !isVisible || !isInitView) {
//            Log.e("不加载", "   " + this.getClass().getSimpleName());
            return;
        }

//        Log.e("完成数据第一次加载", "   " + this.getClass().getSimpleName());
        initData();
        isFirstLoad = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstResume) {
            initData();
            isFirstResume = false;
        }
    }

    public void setIsFirstResume(boolean isFirstResume){
        this.isFirstResume = isFirstResume;
    }
    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
//            onUserInvisible();
        }
    }

    /**
     * 让布局中的view与fragment中的变量建立起映射
     */
    protected abstract void initView();

    /**
     * 加载页面布局文件
     *
     * @return
     */
    protected abstract int getLayoutId();


    /**
     * 加载要显示的数据
     */
    protected abstract void initData();

    /**
     *
     */
    protected abstract void LoadCacheData();

    /**
     * fragment中可以通过这个方法直接找到需要的view，而不需要进行类型强转
     *
     * @param viewId
     * @param <E>
     * @return
     */
    protected <E extends View> E findView(int viewId) {
        if (convertView != null) {
            E view = (E) mViews.get(viewId);
            if (view == null) {
                view = (E) convertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return view;
        }
        return null;
    }

}

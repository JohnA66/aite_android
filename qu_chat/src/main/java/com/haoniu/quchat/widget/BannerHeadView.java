package com.haoniu.quchat.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.aite.chat.R;
import com.haoniu.quchat.entity.BannerInfo;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.zds.base.util.DensityUtils;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * 日期 2018/3/5
 * 描述 main banner
 */

public class BannerHeadView extends LinearLayout {
    Banner banner;

    public BannerHeadView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.main_head_banner, this);
        initView();
    }

    public BannerHeadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.main_head_banner, this);
        initView();
    }

    private void initView() {
        banner = findViewById(R.id.banner);
        initBanner();
    }

    public <T> void upData(List<T> list) {
        banner.update(list);
    }

    public Banner getBanner() {
        return banner;
    }

    public void initBanner() {
        List<BannerInfo> mBannerInfos = new ArrayList<>();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) banner.getLayoutParams();
        layoutParams.height = (int) (DensityUtils.getWidthInPx(getContext()) * 0.92 / 69 * 26);
        //设置banner样式
//        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());

        //设置图片集合
        banner.setImages(mBannerInfos);
        //设置banner动画效果
//        new ScaleInTransformer()
        banner.setBannerAnimation(ScaleInTransformer.class);
        //设置标题集合（当banner样式有显示title时）
        // banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
            }
        });
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        setBannerEffect();

    }

    /**
     * 设置banner效果
     */
    private void setBannerEffect() {
        float density = getResources().getDisplayMetrics().density;
        //设置banner里viewpager的效果
        ViewPager pager = banner.getViewPager();
        pager.setPadding((int) (density * 15), 0, (int) (density * 15), 0);
        pager.setPageMargin((int) (density * 5));
        pager.setClipToPadding(false);
        pager.setOffscreenPageLimit(10);
    }
}

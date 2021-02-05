package com.haoniu.quchat.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.activity.fragment.MyRedRecordReceiveFragment;
import com.haoniu.quchat.activity.fragment.MyRedRecordSendFragment;
import com.haoniu.quchat.aop.SingleClick;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.widget.CommonDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 零钱-我的红包记录
 */
public class MyRedRecordActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.bar)
    View mBar;
    @BindView(R.id.llayout_title_1)
    RelativeLayout mLlayoutTitle1;
    @BindView(R.id.toolbar_subtitle)
    TextView mToolbarSubtitle;
    @BindView(R.id.img_right)
    ImageView mImgRight;
    @BindView(R.id.img_left_back)
    ImageView mImgLeftBack;

    private Fragment[] mFragments;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_my_red_record);

    }

    @Override
    protected void initLogic() {
        mToolbarTitle.setText("我的红包");
        mLlayoutTitle1.setBackgroundResource(R.color.text_color_red);
        mBar.setBackgroundResource(R.color.text_color_red);
        isTransparency(true);
        mToolbarTitle.setTextColor(ContextCompat.getColor(this, R.color.text_color_red1));
        mToolbarSubtitle.setTextColor(ContextCompat.getColor(this, R.color.text_color_red1));
        mToolbarSubtitle.setVisibility(View.GONE);
        mImgRight.setVisibility(View.VISIBLE);
        mImgRight.setImageResource(R.drawable.zhankai_y);
        mImgLeftBack.setImageResource(R.drawable.fanh_y);
        mImgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            @SingleClick(1500)
            public void onClick(View v) {
                toChangeRed();
            }
        });


        MyRedRecordReceiveFragment receiveFragment = new MyRedRecordReceiveFragment();
        MyRedRecordSendFragment sendFragment = new MyRedRecordSendFragment();
        mFragments = new Fragment[]{receiveFragment, sendFragment};

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, receiveFragment)
                .add(R.id.fragment_container, sendFragment)
                .hide(sendFragment)
                .show(receiveFragment)
                .commit();

    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    private CommonDialog.Builder builder;

    /**
     * 红包切换
     */
    private void toChangeRed() {
        if (builder != null) {
            builder.dismiss();
        }
        builder = new CommonDialog.Builder(this).fullWidth().fromBottom()
                .setView(R.layout.dialog_red_change);
        builder.setOnClickListener(R.id.tv_cell, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消
                builder.dismiss();
            }
        });
        builder.setOnClickListener(R.id.tv_xiangji, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //我收到的

                getSupportFragmentManager().beginTransaction().hide(mFragments[1]).show(mFragments[0]).commit();
                builder.dismiss();
            }
        });
        builder.setOnClickListener(R.id.tv_xiangce, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //我发出的
                getSupportFragmentManager().beginTransaction().hide(mFragments[0]).show(mFragments[1]).commit();
                builder.dismiss();

            }
        });
        builder.create().show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

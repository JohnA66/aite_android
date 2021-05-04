package com.haoniu.quchat.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.entity.EventCenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author lhb
 * 聊天设置
 */
public class ChatSetActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_chat_set);
    }

    @Override
    protected void initLogic() {
        mToolbarTitle.setText("聊天设置");
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @OnClick({R.id.tv_chat_bg, R.id.tv_chat_text_size})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_chat_bg:
                //聊天背景
                Bundle bundle = new Bundle();
                bundle.putString("from", "2");
                startActivity(ChatBgActivity.class, bundle);
                break;
            case R.id.tv_chat_text_size:
                //文字大小
                break;
            default:
                break;
        }
    }

}

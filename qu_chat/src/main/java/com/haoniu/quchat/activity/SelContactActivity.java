package com.haoniu.quchat.activity;

import android.os.Bundle;
import android.view.WindowManager;

import com.aite.chat.R;
import com.haoniu.quchat.activity.fragment.SelContactFragment;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.entity.EventCenter;

/**
 * 作   者：lhb
 * 描   述：发送名片-联系人列表
 * 日   期: 2017/11/17 18:07
 * 更新日期: 2017/11/17
 *
 * @author Administrator
 */
public class SelContactActivity extends BaseActivity {
    private SelContactFragment mSelContactFragment;

    /**
     * 替代onCreate的使用
     *
     * @param bundle
     */
    @Override
    protected void initContentView(Bundle bundle) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_chat);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
    }

    /**
     * 初始化逻辑
     */
    @Override
    protected void initLogic() {
        //use SelContactFragment
        mSelContactFragment = new SelContactFragment();
        //pass parameters to chat fragment
        mSelContactFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, mSelContactFragment).commit();
    }

    /**
     * EventBus接收消息
     *
     * @param center 获取事件总线信息
     */
    @Override
    protected void onEventComing(EventCenter center) {

    }

    /**
     * Bundle  传递数据
     *
     * @param extras
     */
    @Override
    protected void getBundleExtras(Bundle extras) {

    }


}

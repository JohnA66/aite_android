package com.haoniu.quchat.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.adapter.ChatBgAdapter;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.MyHelper;
import com.haoniu.quchat.base.Storage;
import com.haoniu.quchat.entity.ChatBgInfo;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.help.RclViewHelp;
import com.haoniu.quchat.utils.EventUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author lhb
 * 聊天背景列表
 */
public class ChatBgActivity extends BaseActivity implements ChatBgAdapter.SelChatBgOnListener {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.rv_chat_bg)
    RecyclerView mRvChatBg;
    private String from = "";
    private String toChatUsername;
    private List<ChatBgInfo> mInfoList = new ArrayList<>();
    private ChatBgAdapter mChatBgAdapter;

    private int mBitmap[] = {R.mipmap.bg_2, R.mipmap.bg_1, R.mipmap.bg_3, R.mipmap.bg_4, R.mipmap.bg_5, R.mipmap.bg_6};

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_chat_bg);
    }

    @Override
    protected void initLogic() {
        mToolbarTitle.setText("聊天背景");

        for (int i = 0; i < mBitmap.length; i++) {
            ChatBgInfo info = new ChatBgInfo(i, mBitmap[i], false);
            mInfoList.add(info);
        }

        mChatBgAdapter = new ChatBgAdapter(mInfoList);
        mChatBgAdapter.setSelChatBgOnListener(this);
        RclViewHelp.initRcStaggeredGrid(this, mRvChatBg, 3, mChatBgAdapter);
    }

    @Override
    protected void onEventComing(EventCenter center) {
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        toChatUsername = extras.getString("username");
        from = extras.getString("from");
    }


    @OnClick({R.id.tv_sel_pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sel_pic:
                toSelectPic();
                break;
            default:
                break;
        }
    }


    /**
     * 选择图片上传
     */
    private void toSelectPic() {
        PictureSelector.create(ChatBgActivity.this)
                .openGallery(PictureMimeType.ofImage())
                .selectionMode(PictureConfig.SINGLE)
                .withAspectRatio(1, 1)
                .showCropFrame(false)
                .showCropGrid(false)
                .compress(true)
                .freeStyleCropEnabled(true)
                .circleDimmedLayer(true)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList.size() > 0) {
                    if (selectList.get(0).isCut()) {
                        if (from.equals("1")) {
                            MyHelper.getInstance().getModel().saveChatBg(toChatUsername, selectList.get(0).getCutPath(), null, null);
                        } else {
                            Storage.saveGlobalChatBg(selectList.get(0).getCutPath());
                        }
                    } else {
                        if (from.equals("1")) {
                            MyHelper.getInstance().getModel().saveChatBg(toChatUsername, selectList.get(0).getPath(), null, null);
                        } else {
                            Storage.saveGlobalChatBg(selectList.get(0).getPath());
                        }
                    }
                    EventBus.getDefault().post(new EventCenter<>(EventUtil.SET_CHAT_BG));
                    toast("设置成功");
                    finish();
                }

            }
        }
    }

    @Override
    public void selBg(int id) {
        int bg = 0;
        for (ChatBgInfo info : mInfoList) {
            if (info.getId() == id) {
                info.setSel(true);
                bg = info.getBg();
            } else {
                info.setSel(false);
            }
        }

        mChatBgAdapter.notifyDataSetChanged();

        //聊天过来设置
        if (from.equals("1")) {
            MyHelper.getInstance().getModel().saveChatBg(toChatUsername, "none", null, null);
            Storage.saveChatBgLocal(toChatUsername, bg);
            EventBus.getDefault().post(new EventCenter<>(EventUtil.SET_CHAT_BG));
        } else {
            //全局设置
            Storage.saveGlobalChatBg("none");
            Storage.saveGlobalChatBgLocal(bg);

        }
        toast("选取成功");

    }
}

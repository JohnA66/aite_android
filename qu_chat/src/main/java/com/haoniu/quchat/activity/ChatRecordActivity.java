package com.haoniu.quchat.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.adapter.ChatRecordAdapter;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.executor.ThreadPoolManager;
import com.haoniu.quchat.help.RclViewHelp;
import com.haoniu.quchat.utils.EaseCommonUtils;
import com.haoniu.quchat.utils.StringUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author lhb
 * 聊天记录
 */
public class ChatRecordActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.rv_chat_record)
    RecyclerView mRvChatRecord;
    @BindView(R.id.et_query)
    EditText mEtQuery;
    @BindView(R.id.tv_no_data)
    TextView mTvNoData;
    @BindView(R.id.img_clear)
    ImageView mImgClear;

    private List<EMMessage> messageList;
    private ChatRecordAdapter mChatRecordAdapter;
    private String chatId;
    List<EMMessage> resultList;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_chat_record);
    }

    @Override
    protected void initLogic() {
        mToolbarTitle.setText("聊天记录");
        messageList = new ArrayList<>();
        resultList=new ArrayList<>();

        mChatRecordAdapter = new ChatRecordAdapter(messageList);
        RclViewHelp.initRcLmVertical(this, mRvChatRecord, mChatRecordAdapter);
        mEtQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mImgClear.setVisibility(View.VISIBLE);
                } else {
                    mImgClear.setVisibility(View.GONE);
                }

            }
        });

        mEtQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchMessages();
                    hideSoftKeyboard();
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        chatId = extras.getString("chatId");
    }

    private void searchMessages() {
        showLoading("正在搜索...");
        ThreadPoolManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                EMConversation conversation = EMClient.getInstance().chatManager().getConversation(chatId, EMConversation.EMConversationType.Chat);
//                List<EMMessage> resultList = conversation.searchMsgFromDB(mEtQuery.getText().toString().trim(),
//                        System.currentTimeMillis(), 50, chatId, EMConversation.EMSearchDirection.UP);
                 if (conversation == null){
                     mRvChatRecord.setVisibility(View.GONE);
                     mTvNoData.setVisibility(View.VISIBLE);
                     return;
                 }
                resultList.clear();
                resultList.addAll(conversation.getAllMessages());
                String search = mEtQuery.getText().toString().trim();
                Iterator it = resultList.iterator();
                while (it.hasNext()) {
                    EMMessage em = (EMMessage) it.next();
                    String text = EaseCommonUtils.getMessageDigest(em,
                            ChatRecordActivity.this);
                    if (StringUtil.isEmpty(text)){
                        continue;
                    }
                    if (!text.contains(search) && !text.contains(search)) {
                        it.remove();
                    }
                }

                if (messageList == null) {
                    messageList = resultList;
                } else {
                    messageList.clear();

                    messageList.addAll(resultList);
                }
                onSearchResulted();
            }

        });
    }

    private void onSearchResulted() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dismissLoading();
                if (messageList.size() <= 0) {
                    mRvChatRecord.setVisibility(View.GONE);
                    mTvNoData.setVisibility(View.VISIBLE);
                    return;
                } else {
                    mRvChatRecord.setVisibility(View.VISIBLE);
                    mTvNoData.setVisibility(View.GONE);
                }
                if (mChatRecordAdapter == null) {
                    mChatRecordAdapter = new ChatRecordAdapter(messageList);
                    mChatRecordAdapter.notifyDataSetChanged();
                } else {
                    mChatRecordAdapter.notifyDataSetChanged();
                }
            }
        });
    }


    @OnClick({R.id.img_clear, R.id.tv_no_data})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_clear:
                //删除
                mEtQuery.setText("");
                break;
            case R.id.tv_no_data:
                break;
            default:
                break;
        }
    }
}

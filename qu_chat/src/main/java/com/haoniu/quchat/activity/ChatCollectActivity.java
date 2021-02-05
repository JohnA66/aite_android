/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.haoniu.quchat.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.chat.R;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.Storage;
import com.haoniu.quchat.entity.EventCenter;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import static com.haoniu.quchat.activity.fragment.ChatFragment.ITEM_MENU_COLLECT;
import static com.haoniu.quchat.activity.fragment.ChatFragment.ITEM_MENU_DEL;
import static com.haoniu.quchat.activity.fragment.ChatFragment.ITEM_MENU_RECALL;
import static com.haoniu.quchat.activity.fragment.ChatFragment.ITEM_MENU_SAVE_IMAGE;

/**
 * @author lhb
 * 聊天收藏
 */
public class ChatCollectActivity extends BaseActivity {
    EMMessage message;
    TextView tvRecall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        message = getIntent().getParcelableExtra("message");
        boolean isChatroom = getIntent().getBooleanExtra("ischatroom", false);

        int type = message.getType().ordinal();
        if (type == EMMessage.Type.TXT.ordinal()) {
            setContentView(R.layout.em_context_menu_for_collect);

//            if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)
//                    || message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false) || message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.REDPACKET)) {
//                setContentView(R.layout.em_context_menu_for_location);
//            } else if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_BIG_EXPRESSION, false)) {
//                setContentView(R.layout.em_context_menu_for_image);
//            } else {
//                setContentView(R.layout.em_context_menu_for_text);
//            }
        } else if (type == EMMessage.Type.IMAGE.ordinal()) {
            setContentView(R.layout.em_context_menu_for_collect);
        }

        if (type == EMMessage.Type.IMAGE.ordinal()) {
            TextView tv_save=findViewById(R.id.tv_save);
            tv_save.setVisibility(View.VISIBLE);
        }

        if (type == EMMessage.Type.TXT.ordinal()) {
            TextView tv_copy=findViewById(R.id.tv_copy);
            tv_copy.setVisibility(View.VISIBLE);
        }

        tvRecall = findViewById(R.id.tv_recall);

        if (message.direct() == EMMessage.Direct.RECEIVE){
            tvRecall.setVisibility(View.GONE);
            tvRecall.setTextColor(getResources().getColor(R.color.gray));
        }else if  (System.currentTimeMillis() - message.getMsgTime() >  120000){
            tvRecall.setTextColor(getResources().getColor(R.color.gray));
        }

//        else if (type == EMMessage.Type.VOICE.ordinal()) {
//            setContentView(R.layout.em_context_menu_for_voice);
//        } else if (type == EMMessage.Type.VIDEO.ordinal()) {
//            setContentView(R.layout.em_context_menu_for_video);
//        } else if (type == EMMessage.Type.FILE.ordinal()) {
//            setContentView(R.layout.em_context_menu_for_location);
//        } else if (type == EMMessage.Type.LOCATION.ordinal()) {
//            setContentView(R.layout.em_context_menu_for_location);
//        }

//        if (isChatroom) {
//            //red packet code : 屏蔽红包消息的撤回功能
////                || message.getBooleanAttribute(RPConstant.MESSAGE_ATTR_IS_RED_PACKET_MESSAGE, false)) {
//            //end of red packet code
//            View v = (View) findViewById(R.id.forward);
//            if (v != null) {
//                v.setVisibility(View.GONE);
//            }
//        }
//        if (message.direct() == EMMessage.Direct.RECEIVE) {
//            View recall = (View) findViewById(R.id.recall);
//            recall.setVisibility(View.GONE);
//        }
    }

    /**
     * 替代onCreate的使用
     *
     * @param bundle
     */
    @Override
    protected void initContentView(Bundle bundle) {

    }

    /**
     * 初始化逻辑
     */
    @Override
    protected void initLogic() {

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    public void collect(View view) {
        Intent intent = new Intent();
        intent.putExtra("MenuFlag", ITEM_MENU_COLLECT);
        setResult(Activity.RESULT_OK,intent);
        Storage.saveImage("0");
        finish();
    }

    public void saveImage(View view) {
        Intent intent = new Intent();
        intent.putExtra("MenuFlag", ITEM_MENU_SAVE_IMAGE);
        setResult(Activity.RESULT_OK,intent);
        Storage.saveImage("1");
        finish();
    }

    public void delete(View view) {
        Intent intent = new Intent();
        intent.putExtra("MenuFlag", ITEM_MENU_DEL);
        intent.putExtra("msgId",message.getMsgId());
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    public void recall(View view) {
        if (System.currentTimeMillis() - message.getMsgTime() >  120000){
            Toast.makeText(this, "超出两分钟无法撤回", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("MenuFlag", ITEM_MENU_RECALL);
        intent.putExtra("msgId",message.getMsgId());
        setResult(Activity.RESULT_OK,intent);
        finish();
    }




    public void copyText(View view )
    {
        EMMessage message = getIntent().getParcelableExtra("message");

        ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        //第一个参数只是一个标记，随便传入。
        //第二个参数是要复制到剪贴版的内容
        EMTextMessageBody emTextMessageBody = (EMTextMessageBody) message.getBody();
        ClipData clip = ClipData.newPlainText("IntersetTag", emTextMessageBody.getMessage());
        clipboard.setPrimaryClip(clip);
        finish();
    }
}

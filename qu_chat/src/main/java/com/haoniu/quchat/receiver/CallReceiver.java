package com.haoniu.quchat.receiver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import com.haoniu.quchat.activity.VideoCallActivity;
import com.haoniu.quchat.activity.VoiceCallActivity;
import com.haoniu.quchat.base.MyHelper;
import com.hyphenate.util.EMLog;

/**
 * @author lhb
 */
public class CallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!MyHelper.getInstance().isLoggedIn()) {
            return;
        }
        //username
        String from = intent.getStringExtra("from");
        //call typePACKAGE_REMOVEDPACKAGE_REMOVED
        String type = intent.getStringExtra("type");
        if ("video".equals(type)) {
            //video call
            context.startActivity(new Intent(context, VideoCallActivity.class).
                    putExtra("username", from).putExtra("isComingCall", true).
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else {
            //voice call
            context.startActivity(new Intent(context, VoiceCallActivity.class).
                    putExtra("username", from).putExtra("isComingCall", true).
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }

        EMLog.d("CallReceiver11", "app received a incoming call");

    }

}

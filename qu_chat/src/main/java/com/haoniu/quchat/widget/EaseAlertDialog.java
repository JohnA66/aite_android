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
package com.haoniu.quchat.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.aite.chat.R;


public class EaseAlertDialog extends Dialog {

    public interface AlertDialogUser {
        void onResult(boolean confirmed, Bundle bundle);
    }

    private String title;
    private String msg;
    private String btn_cancel;
    private String btn_ok;

    private AlertDialogUser user;
    private Bundle bundle;
    private boolean showCancel = false;
    private boolean showTitle = false;


    public EaseAlertDialog(Context context, int msgId) {
        super(context);
        this.title = context.getResources().getString(R.string.prompt);
        this.msg = context.getResources().getString(msgId);
        this.setCanceledOnTouchOutside(true);
    }

    public EaseAlertDialog(Context context, String msg, String cancelText, String okText, AlertDialogUser user) {
        super(context);
        this.msg = msg;
        this.btn_cancel = cancelText;
        this.btn_ok = okText;
        this.user = user;
        this.setCanceledOnTouchOutside(true);
    }

    public EaseAlertDialog(Context context, int titleId, int msgId) {
        super(context);
        this.title = context.getResources().getString(titleId);
        this.msg = context.getResources().getString(msgId);
        this.setCanceledOnTouchOutside(true);
    }

    public EaseAlertDialog(Context context, String title, String msg) {
        super(context);
        this.title = title;
        this.msg = msg;
        this.setCanceledOnTouchOutside(true);
    }

    public EaseAlertDialog(Context context, int titleId, int msgId, Bundle bundle, AlertDialogUser user, boolean showCancel) {
        super(context);
        this.title = context.getResources().getString(titleId);
        this.msg = context.getResources().getString(msgId);
        this.user = user;
        this.bundle = bundle;
        this.showCancel = showCancel;
        this.setCanceledOnTouchOutside(true);
    }

    public EaseAlertDialog(Context context, String title, String msg, Bundle bundle, AlertDialogUser user, boolean showCancel) {
        super(context);
        this.title = title;
        this.msg = msg;
        this.user = user;
        this.bundle = bundle;
        this.showCancel = showCancel;
        this.setCanceledOnTouchOutside(true);
    }

    public EaseAlertDialog(Context context, String title, String msg, boolean showTitle, AlertDialogUser user, boolean showCancel) {
        super(context);
        this.title = title;
        this.msg = msg;
        this.user = user;
        this.showTitle = showTitle;
        this.showCancel = showCancel;
        this.setCanceledOnTouchOutside(true);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alert_dialog);

        TextView cancel = (TextView) findViewById(R.id.btn_cancel);
        TextView ok = (TextView) findViewById(R.id.btn_ok);
        TextView titleView = (TextView) findViewById(R.id.title);
        setTitle(title);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btn_ok) {
                    onOk(view);
                } else if (view.getId() == R.id.btn_cancel) {
                    onCancel(view);
                }
            }
        };
        cancel.setOnClickListener(listener);
        ok.setOnClickListener(listener);

        if (showTitle) {
            titleView.setVisibility(View.VISIBLE);
        }

        if (title != null) {
            titleView.setText(title);
        }

        if (showCancel) {
            cancel.setVisibility(View.VISIBLE);
        }

        if (msg != null) {
            ((TextView) findViewById(R.id.alert_message)).setText(msg);
        }

        if (btn_cancel != null) {
            cancel.setText(btn_cancel);
        }

        if (btn_ok != null) {
            ok.setText(btn_ok);
        }

    }

    public void onOk(View view) {
        this.dismiss();
        if (this.user != null) {
            this.user.onResult(true, this.bundle);
        }
    }

    public void onCancel(View view) {
        this.dismiss();
        if (this.user != null) {
            this.user.onResult(false, this.bundle);
        }
    }
}

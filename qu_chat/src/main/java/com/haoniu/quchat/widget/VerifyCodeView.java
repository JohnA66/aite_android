package com.haoniu.quchat.widget;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.chat.R;

public class VerifyCodeView extends RelativeLayout {
    private EditText editText;
    private TextView[] textViews;
    private TextView tvGetCode;
    private TextView tvPhone;
    private static int MAX = 6;
    private String inputContent;
    private CountDownTimer getCodeTimer;
    private OnVerifyCodeListener verifyCodeListener;

    public VerifyCodeView(Context context) {
        this(context, null);
    }

    public VerifyCodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerifyCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.layout_sms_verify, this);

        textViews = new TextView[MAX];
        textViews[0] = (TextView) findViewById(R.id.tv_0);
        textViews[1] = (TextView) findViewById(R.id.tv_1);
        textViews[2] = (TextView) findViewById(R.id.tv_2);
        textViews[3] = (TextView) findViewById(R.id.tv_3);
        textViews[4] = (TextView) findViewById(R.id.tv_4);
        textViews[5] = (TextView) findViewById(R.id.tv_5);
        editText = (EditText) findViewById(R.id.edit_text_view);
        tvGetCode = (TextView) findViewById(R.id.get_code);
        tvPhone = (TextView) findViewById(R.id.phone);

        editText.setCursorVisible(false);//隐藏光标
        setEditTextListener();

        findViewById(R.id.close).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
                if (verifyCodeListener != null) {
                    verifyCodeListener.close();
                }
            }
        });
        tvGetCode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verifyCodeListener != null) {
                    verifyCodeListener.getCode();
                }
                getCodeTimer.start();
            }
        });
        initCountTimer();
    }

    private void initCountTimer() {
        if (getCodeTimer == null)
            getCodeTimer = new CountDownTimer(60 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tvGetCode.setText(millisUntilFinished / 1000 + "s后重新获取");
                    tvGetCode.setEnabled(false);
                    //   tvCode.setBackgroundResource(R.drawable.shap_gray_5);
                }

                @Override
                public void onFinish() {
                    tvGetCode.setText("获取验证码");
                    //   tvCode.setBackgroundResource(R.drawable.border_redgray5);
                    tvGetCode.setEnabled(true);
                }
            };
    }

    private void setEditTextListener() {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                inputContent = editText.getText().toString();

                if (verifyCodeListener != null) {
                    if (inputContent.length() >= MAX) {
                        verifyCodeListener.inputComplete(inputContent);
                    } else {
                        verifyCodeListener.invalidContent();
                    }
                }

                for (int i = 0; i < MAX; i++) {
                    if (i < inputContent.length()) {
                        textViews[i].setText(String.valueOf(inputContent.charAt(i)));
                    } else {
                        textViews[i].setText("");
                    }
                }
            }
        });
    }




    public void setVerifyCodeListener(OnVerifyCodeListener listener) {
        verifyCodeListener = listener;
    }

    public void reset(){
        editText.setText("");
    }
    public interface OnVerifyCodeListener {

        void inputComplete(String code);

        void invalidContent();

        void getCode();

        void close();
    }

    public String getEditContent() {
        return inputContent;
    }

    public void stopTimer() {

        if (getCodeTimer != null) {
            getCodeTimer.cancel();
        }

        if (tvGetCode != null){
            tvGetCode.setText("获取验证码");
            tvGetCode.setEnabled(true);
        }
    }

    public void setPhone(String phone) {
        this.tvPhone.setText(phone);
    }
}

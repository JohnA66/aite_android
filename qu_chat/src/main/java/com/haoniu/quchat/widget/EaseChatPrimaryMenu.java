package com.haoniu.quchat.widget;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.chat.R;
import com.hyphenate.util.EMLog;
import com.zds.base.Toast.ToastUtil;

/**
 * primary menu
 */
public class EaseChatPrimaryMenu extends EaseChatPrimaryMenuBase implements OnClickListener {
    private EditText editText;
    private View buttonSetModeKeyboard;
    private RelativeLayout edittext_layout;
    private View buttonSetModeVoice;
    private View buttonSend;
    private View buttonPressToSpeak;
    private ImageView faceNormal;
    private ImageView faceChecked;
    private Button buttonMore;
    private boolean ctrlPress = false;
    private RelativeLayout faceLayout;


    public EaseChatPrimaryMenu(Context context, AttributeSet attrs,
                               int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public EaseChatPrimaryMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EaseChatPrimaryMenu(Context context) {
        super(context);
        init(context, null);
    }

    private void init(final Context context, AttributeSet attrs) {

        Context context1 = context;
        LayoutInflater.from(context).inflate(R.layout.ease_widget_chat_primary_menu, this);
        editText = (EditText) findViewById(R.id.et_sendmessage);
        buttonSetModeKeyboard = findViewById(R.id.btn_set_mode_keyboard);
        edittext_layout = (RelativeLayout) findViewById(R.id.edittext_layout);
        buttonSetModeVoice = findViewById(R.id.btn_set_mode_voice);
        buttonSend = findViewById(R.id.btn_send);
        buttonPressToSpeak = findViewById(R.id.btn_press_to_speak);
        faceNormal = (ImageView) findViewById(R.id.iv_face_normal);
        faceChecked = (ImageView) findViewById(R.id.iv_face_checked);
        faceLayout = (RelativeLayout) findViewById(R.id.rl_face);
        buttonMore = (Button) findViewById(R.id.btn_more);
//        edittext_layout.setBackgroundResource(R.drawable
//        .ease_input_bar_bg_normal);
        editText.setVisibility(VISIBLE);
        buttonSend.setOnClickListener(this);
        buttonSetModeKeyboard.setOnClickListener(this);
        buttonSetModeVoice.setOnClickListener(this);
        buttonMore.setOnClickListener(this);
        faceLayout.setOnClickListener(this);
        editText.setOnClickListener(this);
        editText.requestFocus();
        editText.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    edittext_layout.setBackgroundResource(R.drawable
//                    .ease_input_bar_bg_active);
//                } else {
//                    edittext_layout.setBackgroundResource(R.drawable
//                    .ease_input_bar_bg_normal);
//                }

            }
        });
        // listen the text change
        editText.addTextChangedListener(new TextWatcher() {

            int addlength = 0;
            int startposition = 0;
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (!TextUtils.isEmpty(s)) {
                    buttonMore.setVisibility(View.GONE);
                    buttonSend.setVisibility(View.VISIBLE);
                } else {
                    buttonMore.setVisibility(View.VISIBLE);
                    buttonSend.setVisibility(View.GONE);
                }
                if ( (addlength == 1)
                        && (s.toString().substring(startposition, startposition + 1).equals("@"))) {
                    listener.onAtSomeOne();
                }


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                                addlength = after;
                                startposition = start;

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editText.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                EMLog.d("key",
                        "keyCode:" + keyCode + " action:" + event.getAction());

                // test on Mac virtual machine: ctrl map to KEYCODE_UNKNOWN
                if (keyCode == KeyEvent.KEYCODE_UNKNOWN) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        ctrlPress = true;
                    } else if (event.getAction() == KeyEvent.ACTION_UP) {
                        ctrlPress = false;
                    }
                }
                return false;
            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                try {
                    if (actionId == EditorInfo.IME_ACTION_SEND ||
                            (event.getKeyCode() == KeyEvent.KEYCODE_ENTER &&
                                    event.getAction() == KeyEvent.ACTION_DOWN &&
                                    ctrlPress == true)) {
                        String s = editText.getText().toString();
                        editText.setText("");
                        listener.onSendBtnClicked(s);
                        return true;
                    } else {
                        return false;
                    }
                } catch (Exception e) {
                    return false;
                }
            }
        });

        buttonPressToSpeak.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (listener != null) {
                    return listener.onPressToSpeakBtnTouch(v, event);
                }
                return false;
            }
        });
    }

    /**
     * set recorder view when speak icon is touched
     *
     * @param voiceRecorderView
     */
    public void setPressToSpeakRecorderView(EaseVoiceRecorderView voiceRecorderView) {
        EaseVoiceRecorderView voiceRecorderView1 = voiceRecorderView;
    }

    /**
     * append emoji icon to editText
     *
     * @param emojiContent
     */
    @Override
    public void onEmojiconInputEvent(CharSequence emojiContent) {
        editText.append(emojiContent);
    }


    /**
     * delete emojicon
     */
    @Override
    public void onEmojiconDeleteEvent() {
        if (!TextUtils.isEmpty(editText.getText())) {
            KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0
                    , 0, 0, KeyEvent.KEYCODE_ENDCALL);
            editText.dispatchKeyEvent(event);
        }
    }

    /**
     * on clicked event
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_send) {

            if (listener != null) {
                String s = editText.getText().toString();
                if (s.length() > 600 && !s.contains("水")) {
                    ToastUtil.toast("发送文本不能超出600个字符");
                    return;
                }
                editText.setText("");
                listener.onSendBtnClicked(s);
            }
        } else if (id == R.id.btn_set_mode_voice) {
            if (!iscansend) {
                return;
            }
            setModeVoice();
            showNormalFaceImage();
            if (listener != null) {
                listener.onToggleVoiceBtnClicked();
            }
        } else if (id == R.id.btn_set_mode_keyboard) {
            setModeKeyboard();
            showNormalFaceImage();
            if (listener != null) {
                listener.onToggleVoiceBtnClicked();
            }
        } else if (id == R.id.btn_more) {
            buttonSetModeVoice.setVisibility(View.VISIBLE);
            buttonSetModeKeyboard.setVisibility(View.GONE);
            edittext_layout.setVisibility(View.VISIBLE);
            buttonPressToSpeak.setVisibility(View.GONE);
            showNormalFaceImage();
            if (listener != null) {
                listener.onToggleExtendClicked();
            }
        } else if (id == R.id.et_sendmessage) {
            if (!iscansend) {
                ToastUtil.toast("已禁言");
                return;
            }
//            edittext_layout.setBackgroundResource(R.drawable
//            .ease_input_bar_bg_active);
            faceNormal.setVisibility(View.VISIBLE);
            faceChecked.setVisibility(View.INVISIBLE);
            if (listener != null) {
                listener.onEditTextClicked();
            }
        } else if (id == R.id.rl_face) {
            if (!iscansend) {
                return;
            }
            toggleFaceImage();
            if (listener != null) {
                listener.onToggleEmojiconClicked();
            }
        } else {
        }
    }


    /**
     * show voice icon when speak bar is touched
     */
    protected void setModeVoice() {
        hideKeyboard();
        edittext_layout.setVisibility(View.GONE);
        buttonSetModeVoice.setVisibility(View.GONE);
        buttonSetModeKeyboard.setVisibility(View.VISIBLE);
        buttonSend.setVisibility(View.GONE);
        buttonMore.setVisibility(View.VISIBLE);
        buttonPressToSpeak.setVisibility(View.VISIBLE);
        faceNormal.setVisibility(View.VISIBLE);
        faceChecked.setVisibility(View.INVISIBLE);

    }
    @Override
    public void hideVoice() {
        buttonSetModeVoice.setVisibility(View.GONE);
    }
    /**
     * show keyboard
     */
    protected void setModeKeyboard() {
        edittext_layout.setVisibility(View.VISIBLE);
        buttonSetModeKeyboard.setVisibility(View.GONE);
        buttonSetModeVoice.setVisibility(View.VISIBLE);
        // mEditTextContent.setVisibility(View.VISIBLE);
        editText.requestFocus();
        // buttonSend.setVisibility(View.VISIBLE);
        buttonPressToSpeak.setVisibility(View.GONE);
        if (TextUtils.isEmpty(editText.getText())) {
            buttonMore.setVisibility(View.VISIBLE);
            buttonSend.setVisibility(View.GONE);
        } else {
            buttonMore.setVisibility(View.GONE);
            buttonSend.setVisibility(View.VISIBLE);
        }

    }

    protected void toggleFaceImage() {
        if (faceNormal.getVisibility() == View.VISIBLE) {
            showSelectedFaceImage();
        } else {
            showNormalFaceImage();
        }
    }

    private void showNormalFaceImage() {
        faceNormal.setVisibility(View.VISIBLE);
        faceChecked.setVisibility(View.INVISIBLE);
    }

    private void showSelectedFaceImage() {
        faceNormal.setVisibility(View.INVISIBLE);
        faceChecked.setVisibility(View.VISIBLE);
    }

    @Override
    public void onExtendMenuContainerHide() {
        showNormalFaceImage();
    }

    @Override
    public void onTextInsert(CharSequence text) {
        if (editText != null) {
            int start = editText.getSelectionStart();
            Editable editable = editText.getEditableText();
            editable.insert(start, text);
            setModeKeyboard();
        }
    }

    @Override
    public EditText getEditText() {
        return editText;
    }

    @Override
    public void executeMute() {
        editText.post(new Runnable() {
            @Override
            public void run() {
                editText.setVisibility(VISIBLE);
                editText.setLongClickable(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // call that method
                    editText.setCustomInsertionActionModeCallback(new ActionModeCallbackInterceptor());
                } else {
                    editText.setCustomSelectionActionModeCallback(new ActionModeCallbackInterceptor());
                }
                editText.setHint("当前禁言中");
                faceLayout.setEnabled(false);
                buttonMore.setEnabled(false);
                buttonSend.setEnabled(false);
                buttonSetModeVoice.setEnabled(false);
                iscansend = false;
                editText.setFocusable(false);
                editText.setFocusableInTouchMode(false);

            }
        });

    }

    @Override
    public void relieveMute() {
        editText.requestFocus();
        editText.setFocusable(true);
        editText.setHint("");
        iscansend = false;
        editText.setFocusableInTouchMode(true);
        editText.setLongClickable(true);
        editText.setVisibility(VISIBLE);
        faceLayout.setEnabled(true);
        buttonMore.setEnabled(true);
        buttonSend.setEnabled(true);
        buttonSetModeVoice.setEnabled(true);

    }

    private class ActionModeCallbackInterceptor implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return false;
        }


        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }


        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }


        @Override
        public void onDestroyActionMode(ActionMode mode) {}
    }

}

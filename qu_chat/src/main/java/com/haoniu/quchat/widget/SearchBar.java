package com.haoniu.quchat.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.chat.R;

public class SearchBar extends RelativeLayout {
    EditText    query;
    TextView    hint;
    ImageView   clear;

    OnSearchBarListener onSearchBarListener;
    public SearchBar(Context context) {
        this(context,null);
    }

    public SearchBar(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public SearchBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
        parseStyle(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.layout_search_bar, this);
        query   = findViewById(R.id.query);
        clear   = findViewById(R.id.clear);
        hint    = findViewById(R.id.hint);
        clear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                query.getText().clear();
                try {
                    if (((Activity)getContext()).getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
                        if (((Activity)getContext()).getCurrentFocus() != null) {
                            ((InputMethodManager) ((Activity)getContext()).getSystemService(Context.INPUT_METHOD_SERVICE))
                                    .hideSoftInputFromWindow(((Activity)getContext()).getCurrentFocus().getWindowToken(),
                                     InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                    }
                } catch (Exception e) {}
            }
        });

        query.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (onSearchBarListener != null) {
                    onSearchBarListener.onTextChanged(s, start, before, count);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    hint.setVisibility(View.GONE);
                    clear.setVisibility(View.VISIBLE);
//                    queryGroup();
                } else {
                    clear.setVisibility(View.GONE);
                    hint.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    private void parseStyle(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SearchBar);
            String hintStr = typedArray.getString(R.styleable.SearchBar_hint);

            if (!TextUtils.isEmpty(hintStr)) {
                hint.setText(hintStr);
            }
            Drawable leftDrawable = typedArray.getDrawable(R.styleable.SearchBar_hintImg);
            if (null != leftDrawable) {
                hint.setCompoundDrawablesWithIntrinsicBounds(leftDrawable,null,null,null);
            }
            typedArray.recycle();
        }
    }

    public void setOnSearchBarListener(OnSearchBarListener onSearchBarListener) {
        this.onSearchBarListener = onSearchBarListener;
    }

    public interface OnSearchBarListener {

        public void onTextChanged(CharSequence s, int start, int before, int count);

    }

}

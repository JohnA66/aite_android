package com.haoniu.quchat.widget.chatrow;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.haoniu.quchat.base.EaseConstant;
import com.aite.chat.R;
import com.haoniu.quchat.base.EaseUI;
import com.haoniu.quchat.domain.EaseEmojicon;
import com.zds.base.ImageLoad.GlideUtils;

/**
 * big emoji icons
 */
public class EaseChatRowBigExpression extends EaseChatRowText {

    private ImageView imageView;


    public EaseChatRowBigExpression(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                R.layout.ease_row_received_bigexpression : R.layout.ease_row_sent_bigexpression, this);
    }

    @Override
    protected void onFindViewById() {
        percentageView = (TextView) findViewById(R.id.percentage);
        imageView = (ImageView) findViewById(R.id.image);
    }


    @Override
    public void onSetUpView() {
        String emojiconId = message.getStringAttribute(EaseConstant.MESSAGE_ATTR_EXPRESSION_ID, null);
        EaseEmojicon emojicon = null;
        if (EaseUI.getInstance().getEmojiconInfoProvider() != null) {
            emojicon = EaseUI.getInstance().getEmojiconInfoProvider().getEmojiconInfo(emojiconId);
        }
        if (emojicon != null) {
            if (emojicon.getBigIcon() != 0) {
                GlideUtils.loadImageView(emojicon.getBigIcon(), imageView);
            } else if (emojicon.getBigIconPath() != null) {
                GlideUtils.loadImageViewLoding(emojicon.getBigIconPath(), imageView, R.drawable.ease_default_expression);
            } else {
                imageView.setImageResource(R.drawable.ease_default_expression);
            }
        }
    }
}

package com.haoniu.quchat.widget.chatrow;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.widget.EaseImageView;
import com.hyphenate.chat.EMMessage;
import com.zds.base.ImageLoad.GlideUtils;

/**
 * @author lhb
 * 自定义地图会话布局
 */
public class EaseChatRowLocationPacket extends EaseChatRow {

    private TextView locationView;
    private TextView locationViewDetail;
    private EaseImageView imgLocation;


    public EaseChatRowLocationPacket(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        if (message.getBooleanAttribute(Constant.SEND_LOCATION, false)) {
            inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                    R.layout.ease_row_received_location : R.layout.ease_row_sent_location, this);
        }
    }

    @Override
    protected void onFindViewById() {
        imgLocation = (EaseImageView) findViewById(R.id.img_location);
        locationView = (TextView) findViewById(R.id.tv_location);
        locationViewDetail = (TextView) findViewById(R.id.tv_location_detail);
    }


    @Override
    protected void onSetUpView() {
        String latitude = message.getStringAttribute("latitude", "");
        String longitude = message.getStringAttribute("longitude", "");
        String locationAddress = message.getStringAttribute("locationAddress", "");
        String localDetail = message.getStringAttribute("localDetail", "");
        String path = message.getStringAttribute("path", "");
        locationView.setText(locationAddress + "");
        locationViewDetail.setText(localDetail + "");
        if (imgLocation != null) {
            GlideUtils.loadImageViewLoding(AppConfig.checkimg(path), imgLocation);
        }
    }

    @Override
    protected void onViewUpdate(EMMessage msg) {
        switch (msg.status()) {
            case CREATE:
                onMessageCreate();
                break;
            case SUCCESS:
                onMessageSuccess();
                break;
            case FAIL:
                onMessageError();
                break;
            case INPROGRESS:
                onMessageInProgress();
                break;
            default:
                break;
        }
    }

    private void onMessageCreate() {
        progressBar.setVisibility(View.VISIBLE);
        statusView.setVisibility(View.GONE);
    }

    private void onMessageSuccess() {
        progressBar.setVisibility(View.GONE);
        statusView.setVisibility(View.GONE);
    }

    private void onMessageError() {
        progressBar.setVisibility(View.GONE);
        statusView.setVisibility(View.VISIBLE);
    }

    private void onMessageInProgress() {
        progressBar.setVisibility(View.VISIBLE);
        statusView.setVisibility(View.GONE);
    }
}

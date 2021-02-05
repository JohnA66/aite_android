package com.haoniu.quchat.widget.chatrow;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoniu.quchat.http.AppConfig;
import com.hyphenate.chat.EMLocationMessageBody;
import com.hyphenate.chat.EMMessage;
import com.aite.chat.R;
import com.hyphenate.util.LatLng;
import com.zds.base.ImageLoad.GlideUtils;

public class EaseChatRowLocation extends EaseChatRow {

    private TextView locationView;
    private TextView locationViewDetail;
    private ImageView imgLocation;

    private EMLocationMessageBody locBody;

    public EaseChatRowLocation(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                R.layout.ease_row_received_location : R.layout.ease_row_sent_location, this);
    }

    @Override
    protected void onFindViewById() {
        imgLocation = (ImageView) findViewById(R.id.img_location);
        locationView = (TextView) findViewById(R.id.tv_location);
        locationViewDetail = (TextView) findViewById(R.id.tv_location_detail);
    }


    @Override
    protected void onSetUpView() {
        locBody = (EMLocationMessageBody) message.getBody();
        String locInfo = locBody.getAddress();
        if (null != locInfo && locInfo.split("-").length == 3) {
            locationView.setText(locInfo.split("-")[0]);
            locationViewDetail.setText(locInfo.split("-")[1]);
            if (locInfo.split("-")[2].length() > 0) {
                String locPath = locInfo.split("-")[2];
                if (imgLocation != null) {
                    GlideUtils.loadImageViewLoding(AppConfig.checkimg(locPath),imgLocation);
                }
            }

        } else {
            locationView.setText(locBody.getAddress());
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

    /*
     * listener for map clicked
     */
    protected class MapClickListener implements OnClickListener {

        LatLng location;
        String address;

        public MapClickListener(LatLng loc, String address) {
            location = loc;
            this.address = address;

        }

        @Override
        public void onClick(View v) {

        }
    }

}

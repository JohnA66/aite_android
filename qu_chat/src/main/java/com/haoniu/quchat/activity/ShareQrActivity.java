package com.haoniu.quchat.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.aite.chat.R;
import com.google.zxing.WriterException;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.help.QRHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author lhb
 * 分享我的二维码
 */
public class ShareQrActivity extends BaseActivity {
//    @BindView(R.id.img_back)
//    ImageView mImgBack;
    @BindView(R.id.img_qr)
    ImageView mImgQr;
//    @BindView(R.id.bar)
//    View mBar;
    private Bitmap qrCodeBitmap;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_share_qr);
    }

    @Override
    protected void initLogic() {
        isTransparency(true);
        setTitle("分享");
        setBar();
//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mBar.getLayoutParams();
//        params.height = BarUtils.getStatusBarHeight(this);
//        mBar.setLayoutParams(params);
        createImgQr();
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    private void createImgQr() {
        try {
            qrCodeBitmap = QRHelper.addLogo(QRHelper.createQRCode("https://a.app.qq.com/o/simple.jsp?pkgname=com.aite.chat", 800), BitmapFactory.decodeResource(getResources(),R.mipmap.aite_launcher));
            mImgQr.setImageBitmap(qrCodeBitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.img_qr})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_qr:
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

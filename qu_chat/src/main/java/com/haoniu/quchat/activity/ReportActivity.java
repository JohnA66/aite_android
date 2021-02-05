package com.haoniu.quchat.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author lhb
 * 举报
 */
public class ReportActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.et_report)
    EditText mEtReport;
    @BindView(R.id.tv_report)
    TextView mTvReport;

    /**
     * 1 单聊 ， 2：群聊
     */
    private String from;
    private String userGroupId;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_report);
    }

    @Override
    protected void initLogic() {
        mToolbarTitle.setText("举报");
        mEtReport.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mTvReport.setBackgroundResource(R.drawable.wallet_tx_bg_sel);
                } else {
                    mTvReport.setBackgroundResource(R.drawable.report_bg_nor_sel);
                }

            }
        });
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        from = extras.getString("from");
        userGroupId = extras.getString("userGroupId");
    }


    /**
     * 举报
     */
    private void report() {
        if (mEtReport.getText().toString().trim().length() <= 0) {
            toast("请输入举报理由");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userGroupId", userGroupId);
        //举报类型（1.个人 ， 2.群组）
        map.put("reportType", from);
        //举报详情
        map.put("reportDetails", mEtReport.getText().toString().trim());

        ApiClient.requestNetHandle(this, AppConfig.SAVE_REPORT, "正在举报...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                toast("举报成功");
                finish();
            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
            }
        });


    }


    @OnClick({R.id.et_report, R.id.tv_report})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_report:
                break;
            case R.id.tv_report:
                report();
                break;
            default:
                break;
        }
    }
}

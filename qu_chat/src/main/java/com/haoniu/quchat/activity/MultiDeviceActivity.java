package com.haoniu.quchat.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Switch;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.adapter.MultiDeviceAdapter;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.MultiDevice;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.help.RclViewHelp;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.widget.CommonDialog;
import com.haoniu.quchat.widget.VerifyCodeView;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.StringUtil;
import com.zds.base.util.SystemUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class MultiDeviceActivity extends BaseActivity {
    @BindView(R.id.switch_multi)
    Switch switchMulti;
    @BindView(R.id.mobile)
    TextView mobile;
    @BindView(R.id.ip)
    TextView ip;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.kick_out)
    TextView kickOut;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    MultiDeviceAdapter adapter;
    private VerifyCodeView verifyCodeView;
    private CommonDialog verifyDialog;

    private MultiDevice multiDevice;
    public boolean openMultiDeviceStatus;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_multi_device);
    }

    @Override
    protected void initLogic() {
        setTitle("多设备登录");

        mobile.setText(SystemUtil.getDeviceManufacturer() + " " + SystemUtil.getSystemModel());

        verifyCodeView = new VerifyCodeView(this);
        verifyDialog = new CommonDialog.Builder(this).fullWidth().center()
                .setView(verifyCodeView)
                .loadAniamtion()
                .create();
        verifyCodeView.setPhone(UserComm.getUserInfo().getPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));


        verifyCodeView.setVerifyCodeListener(new VerifyCodeView.OnVerifyCodeListener() {
            @Override
            public void inputComplete(String code) {
                verifyDialog.cancel();
                verifyCodeView.reset();

                if (!openMultiDeviceStatus) {
                    openMultiDevice(code);
                } else {
                    closeMultiDevice(code);
                }
            }

            @Override
            public void invalidContent() {

            }

            @Override
            public void getCode() {
                getSMSCode();
            }

            @Override
            public void close() {
                verifyDialog.dismiss();
                setMultiStatus(openMultiDeviceStatus);
            }
        });
        checkMultiStatus();
        getMultiDevices();
    }

    public void setData() {
        mobile.setText(multiDevice.getDname());
        time.setText("登录时间: "+ StringUtil.formatTime(multiDevice.getUt(),"yyyy-MM-dd HH:mm:ss"));
        address.setText("登录地点:  "+multiDevice.getAddress());
        ip.setText("IP地址:  "+ multiDevice.getIp());
        adapter = new MultiDeviceAdapter(multiDevice.getDevList());
        RclViewHelp.initRcLmVertical(this, recyclerView, adapter);
    }


    public void checkMultiStatus() {
        Map<String,Object> map =new HashMap<>();
        ApiClient.requestNetHandle(this, AppConfig.isOpenMultiDevice, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                openMultiDeviceStatus = "1".equals(json);
                setMultiStatus(openMultiDeviceStatus);
            }
            @Override
            public void onFailure(String msg) {
                setMultiStatus(false);
            }
        });
    }

    public void getMultiDevices() {
        Map<String,Object> map =new HashMap<>();
        ApiClient.requestNetHandle(this, AppConfig.getDeviceList, "请稍候", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                multiDevice =  FastJsonUtil.getObject(json, MultiDevice.class);
                setData();
            }
            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

    public void openMultiDevice(String code){
        Map<String,Object> map =new HashMap<>();
        map.put("authCode", code);
        ApiClient.requestNetHandle(this, AppConfig.openMultiDevice, "多设备登录开启中", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtil.toast("多设备登录开启成功");
                openMultiDeviceStatus = true;
                setMultiStatus(openMultiDeviceStatus);
                verifyCodeView.stopTimer();
            }
            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
                openMultiDeviceStatus = false;
                setMultiStatus(openMultiDeviceStatus);
                verifyCodeView.stopTimer();
            }
        });
    }

    public void closeMultiDevice(String code){
        Map<String,Object> map =new HashMap<>();
        map.put("authCode", code);
        ApiClient.requestNetHandle(this, AppConfig.stopMultiDevice, "多设备登录关闭中", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtil.toast("多设备登录关闭成功");
                openMultiDeviceStatus = false;
                setMultiStatus(openMultiDeviceStatus);
                verifyCodeView.stopTimer();
            }
            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
                openMultiDeviceStatus = true;
                setMultiStatus(openMultiDeviceStatus);
                verifyCodeView.stopTimer();

            }
        });
    }
    public void setMultiStatus(boolean isOpen) {
        switchMulti.setOnCheckedChangeListener(null);
        switchMulti.setChecked(isOpen);

        switchMulti.setOnCheckedChangeListener((buttonView, isChecked) -> {
            verifyDialog.show();
        });
    }

    public void getSMSCode(){
        Map<String,Object> map =new HashMap<>();
        ApiClient.requestNetHandle(this, AppConfig.sendMultiDeviceCode, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtil.toast(msg);
            }
            @Override
            public void onFailure(String msg) {

            }
        });
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }
}

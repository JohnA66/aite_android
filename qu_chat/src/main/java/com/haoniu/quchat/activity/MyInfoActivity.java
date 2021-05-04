package com.haoniu.quchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.chat.R;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.MyApplication;
import com.haoniu.quchat.base.MyHelper;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.LoginInfo;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.utils.DeviceIdUtil;
import com.haoniu.quchat.utils.EventUtil;
import com.haoniu.quchat.widget.CommonDialog;
import com.haoniu.quchat.widget.EaseAlertDialog;
import com.haoniu.quchat.widget.dialog.LogoffDialog;
import com.hyphenate.EMCallBack;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zds.base.ImageLoad.GlideUtils;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.widget.SwitchButton;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * @author lhb
 * 个人资料
 */
public class MyInfoActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.img_head)
    ImageView mImgHead;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_account)
    TextView mTvAccount;
    @BindView(R.id.tv_sign)
    TextView mTvSign;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    private LoginInfo info;

    @BindView(R.id.sb_verify)
    SwitchButton sb_verify;
    @BindView(R.id.rg_sex)
    RadioGroup rg_sex;
    @BindView(R.id.rb_male)
    RadioButton rb_male;
    @BindView(R.id.rb_female)
    RadioButton rb_female;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_my_info);
    }

    @Override
    protected void initLogic() {
        mToolbarTitle.setText("我的资料");
        info = UserComm.getUserInfo();
        //手机号
        mTvPhone.setText(info.getPhone());
        //昵称
        mTvName.setText(info.getNickName());
        //头像
        GlideUtils.GlideLoadCircleErrorImageUtils(this, AppConfig.checkimg(info.getUserHead()), mImgHead, R.mipmap.img_default_avatar);

        if (!TextUtils.isEmpty(info.getUserCode())) {
            //艾特号
            mTvAccount.setText(info.getUserCode());
        }

        if (!TextUtils.isEmpty(info.getSign())) {
            //艾特号
            mTvSign.setText(info.getSign());
        }


        sb_verify.setChecked(info.addWay == 0);
        rg_sex.check(info.sex == 0 ? R.id.rb_male : R.id.rb_female);

        sb_verify.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {

                Map<String,Object> map =new HashMap<>();
                map.put("addWay",isChecked ? 0 : 1);
                ApiClient.requestNetHandle(MyInfoActivity.this, AppConfig.MODIFY_FRIEND_CONSENT + "/" + (isChecked ? 0 : 1), "请稍候...", map, new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        LoginInfo loginInfo = UserComm.getUserInfo();
                        loginInfo.addWay = isChecked ? 0 : 1;
                        UserComm.saveUsersInfo(loginInfo);
                        ToastUtil.toast("修改成功");
                    }
                    @Override
                    public void onFailure(String msg) {
                        ToastUtil.toast(msg);

                    }
                });
            }
        });

        rg_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                Map<String,Object> map =new HashMap<>();
                map.put("sex",id == R.id.rb_male ? 0 : 1);
                ApiClient.requestNetHandle(MyInfoActivity.this, AppConfig.MODIFY_SEX + "/" + (id == R.id.rb_male ? 0 : 1), "请稍候...", map, new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        LoginInfo loginInfo = UserComm.getUserInfo();
                        loginInfo.sex = id == R.id.rb_male ? 0 : 1;
                        UserComm.saveUsersInfo(loginInfo);
                        ToastUtil.toast("修改成功");
                    }
                    @Override
                    public void onFailure(String msg) {
                        ToastUtil.toast(msg);
                    }
                });
            }
        });
    }


    @Override
    protected void onEventComing(EventCenter center) {
        if (center.getEventCode() == EventUtil.FLUSHUSERINFO) {
            mTvName.setText(UserComm.getUserInfo().getNickName());
            mTvSign.setText(UserComm.getUserInfo().getSign());
            mTvAccount.setText(UserComm.getUserInfo().getUserCode());
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }


    /**
     * 上传头像地址到服务器
     *
     */
    private void saveHead(File file) {
        ApiClient.requestNetHandleFile(MyInfoActivity.this, AppConfig.groupUpHead, "正在上传...", file, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                modifyHead(json);
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });

    }

    /**
     * 更新头像
     *
     * @param filePath
     */
    private void modifyHead(String filePath) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("userHead", filePath);

        ApiClient.requestNetHandle(MyInfoActivity.this, AppConfig.MODIFY_USER_HEAD, "", map, new ResultListener() {

            @Override
            public void onSuccess(String json, String msg) {
                GlideUtils.GlideLoadCircleErrorImageUtils(MyInfoActivity.this, AppConfig.checkimg(filePath), mImgHead, R.mipmap.img_default_avatar);
                MyApplication.getInstance().UpUserInfo();
                toast("上传成功");
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });

    }


    /**
     * 选择图片上传
     */
    private void toSelectPic() {
        final CommonDialog.Builder builder = new CommonDialog.Builder(this).fullWidth().fromBottom()
                .setView(R.layout.dialog_select_head);
        builder.setOnClickListener(R.id.tv_cell, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        builder.setOnClickListener(R.id.tv_xiangji, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                PictureSelector.create(MyInfoActivity.this)
                        .openCamera(PictureMimeType.ofImage())
                        .selectionMode(PictureConfig.SINGLE)
                        .withAspectRatio(1, 1)
                        .enableCrop(true)
                        .showCropFrame(false)
                        .showCropGrid(false)
                        .freeStyleCropEnabled(true)
                        .circleDimmedLayer(false)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            }
        });
        builder.setOnClickListener(R.id.tv_xiangce, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                PictureSelector.create(MyInfoActivity.this)
                        .openGallery(PictureMimeType.ofImage())
                        .selectionMode(PictureConfig.SINGLE)
                        .withAspectRatio(1, 1)
                        .enableCrop(true)
                        .showCropFrame(false)
                        .showCropGrid(false)
                        .freeStyleCropEnabled(true)
                        .circleDimmedLayer(false)
                        .forResult(PictureConfig.CHOOSE_REQUEST);

            }
        });
        builder.create().show();
    }


    @OnClick({R.id.rl_my_share, R.id.img_head, R.id.tv_name, R.id.rl_my_sign, R.id.tv_account, R.id.tv_exit, R.id.rl_my_qr,R.id.tv_logoff})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_head:
                toSelectPic();
                break;
            case R.id.rl_my_qr:
                Bundle bundle = new Bundle();
                bundle.putString("from", "1");
                startActivity(MyQrActivity.class, bundle);
                break;
            case R.id.tv_name:
                Bundle bundl1 = new Bundle();
                bundl1.putString("from", "1");
                startActivity(EditInfoActivity.class, bundl1);
                break;
            case R.id.rl_my_sign:
                Bundle bundle1 = new Bundle();
                bundle1.putString("from", "3");
                startActivity(EditInfoActivity.class, bundle1);
                break;
            /*case R.id.tv_account:
                Bundle bundle3 = new Bundle();
                bundle3.putString("from", "2");
                startActivity(EditInfoActivity.class, bundle3);
                break;*/
            case R.id.tv_exit:
                new EaseAlertDialog(this, "确定退出帐号？", null, null, new EaseAlertDialog.AlertDialogUser() {
                    @Override
                    public void onResult(boolean confirmed, Bundle bundle) {
                        if (confirmed) {
                            logout();
                        }
                    }
                }).show();
                break;
            case R.id.rl_my_share:
                Bundle bundle2 = new Bundle();
                bundle2.putString("nickName", info.getNickName());
                bundle2.putString("userCode", info.getUserCode());
                bundle2.putString("avatar", info.getUserHead());
                bundle2.putString("friendUserId", info.getUserId());

                startActivity(ShareCardIdActivity.class, bundle2);
                break;
            case R.id.tv_logoff:
                //注销账号
                /*new EaseAlertDialog(this, "确定注销帐号？", null, null, new EaseAlertDialog.AlertDialogUser() {
                    @Override
                    public void onResult(boolean confirmed, Bundle bundle) {
                        if (confirmed) {
                            logoff();
                        }
                    }
                }).show();*/
                showLogoffDialog();
                break;
            default:
                break;
        }
    }

    private LogoffDialog mLogoffDialog;
    private void showLogoffDialog(){
        if(mLogoffDialog == null){
            mLogoffDialog = new LogoffDialog(this);
            mLogoffDialog.setOnConfirmClickListener(new LogoffDialog.OnConfirmClickListener() {
                @Override
                public void onConfirmClick(View view) {
                    logoff();
                }
            });
        }
        mLogoffDialog.show();
    }

    private void logoff() {
        Map<String,Object> map =new HashMap<>();
        map.put("userId",UserComm.getUserInfo().getUserId());
        ApiClient.requestNetHandle(MyInfoActivity.this, AppConfig.toLogoff, "请稍候...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                //关闭当前所有页面并跳转到登录页面
                ToastUtil.toast("注销成功");
                EventBus.getDefault().post(new EventCenter(EventUtil.LOSETOKEN, "关闭"));

                MyHelper.getInstance().logout(false, new EMCallBack() {

                    @Override
                    public void onSuccess() {
                        /*runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SetActivity.this.finish();
                            }
                        });*/
                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }

                    @Override
                    public void onError(int code, String message) {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                //dismissLoading();
                                Toast.makeText(MyInfoActivity.this, "退出环信失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList.size() > 0) {
                    if (selectList.get(0).isCut()) {
                        compressImg(selectList.get(0).getCutPath());

                    } else {
                        compressImg(selectList.get(0).getPath());
                    }
                }
            }
        }
    }

    public void compressImg(String path) {
        Luban.with(this)
                .load(path)                                     // 传人要压缩的图片列表
                .ignoreBy(80)                                   // 忽略不压缩图片的大小
                .setTargetDir(this.getExternalCacheDir().getAbsolutePath())                             // 设置压缩后文件存储位置
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        saveHead(file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                    }
                }).launch();
    }


    private void logout() {
        String st = getResources().getString(R.string.Are_logged_out);
        showLoading(st);

        Map<String,Object> map =new HashMap<>();
        map.put("deviceId", DeviceIdUtil.getDeviceId(this));
        ApiClient.requestNetHandle(this, AppConfig.multiDeviceLogout, "请稍候...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtil.toast(json);
            }
            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);

            }
        });

        MyHelper.getInstance().logout(false, new EMCallBack() {

            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissLoading();
                        UserComm.clearUserInfo();
                        MyInfoActivity.this.finish();
                        EventBus.getDefault().post(new EventCenter(EventUtil.LOSETOKEN, "关闭"));
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        dismissLoading();
                        Toast.makeText(MyInfoActivity.this, "退出失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


}

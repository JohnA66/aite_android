package com.haoniu.quchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.adapter.ContactAdapter;
import com.haoniu.quchat.aop.SingleClick;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.help.RclViewHelp;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.model.ContactListInfo;
import com.haoniu.quchat.utils.EventUtil;
import com.haoniu.quchat.widget.CommonDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zds.base.ImageLoad.GlideUtils;
import com.zds.base.Toast.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * @author lhb
 * 新的群组
 */
public class NewGroupActivity extends BaseActivity {
    @BindView(R.id.toolbar_subtitle)
    TextView mToolbarSubtitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.img_group)
    ImageView mImgGroup;
    @BindView(R.id.rv_contact)
    RecyclerView mRvContact;
    @BindView(R.id.tv_group_num)
    TextView mTvGroupNum;
    @BindView(R.id.et_group)
    EditText mEtGroup;
    @BindView(R.id.tv_group_hint)
    TextView mTvGroupHint;
    private List<ContactListInfo.DataBean> mContactList = new ArrayList<>();
    private ContactAdapter mContactAdapter;


    /**
     * mIdList 群成员ID集合
     * headUrl 头像地址
     */
    private List<String> mIdList;
    private String headUrl;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_new_group);
    }

    @Override
    protected void initLogic() {
        mToolbarTitle.setText("新的群组");
        mToolbarSubtitle.setText("完成");
        mToolbarSubtitle.setVisibility(View.VISIBLE);
        mToolbarSubtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            @SingleClick(1500)
            public void onClick(View v) {
                //确定建群
                saveGroup();
            }
        });

        mEtGroup.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mTvGroupHint.setText(s.toString().substring(0, 1));
                } else {
                    mTvGroupHint.setText("群");
                }

            }
        });


        mContactAdapter = new ContactAdapter(mContactList);
        mContactAdapter.setFrom("2");
        RclViewHelp.initRcLmVertical(this, mRvContact, mContactAdapter);
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        mContactList.addAll((List<ContactListInfo.DataBean>) extras.getSerializable("contact"));
        mTvGroupNum.setText(mContactList.size() + "个群成员");
        mIdList = new ArrayList<>();
        for (ContactListInfo.DataBean bean : mContactList) {
            mIdList.add(bean.getFriendUserId());
        }

    }


    /**
     * 新建群
     *
     * @param
     */
    private void saveGroup() {
        if (mEtGroup.getText().toString().trim().length() <= 0) {
            toast("请先填写群名称");
            return;
        } else if (headUrl == null || headUrl.length() <= 0) {
            toast("请先上传群头像");
            return;
        }

        Map<String, Object> map = new HashMap<>(2);

        map.put("groupName", mEtGroup.getText().toString().trim());
        map.put("groupHead", headUrl);
        String userId = "";
        for (int i = 0; i < mIdList.size(); i++) {
            if (i != mIdList.size() - 1) {
                userId += mIdList.get(i) + ",";
            } else {
                userId += mIdList.get(i);
            }
        }
        map.put("userId", userId);

        ApiClient.requestNetHandle(this, AppConfig.SAVE_GROUP, "正在创建群...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                EventBus.getDefault().post(new EventCenter<>(EventUtil.CREATE_GROUP_SUCCESS));
                toast("群组创建成功");
                finish();
            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
            }
        });
    }


    @OnClick(R.id.fl_img)
    public void onViewClicked() {
        toSelectPic();
    }

    /**
     * 更新头像
     */
    private void saveHead(File file) {

        ApiClient.requestNetHandleFile(NewGroupActivity.this, AppConfig.groupUpHead, "正在上传...", file, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                mTvGroupHint.setVisibility(View.GONE);
                toast("上传成功");
                headUrl = json;
                GlideUtils.GlideLoadCircleErrorImageUtils(NewGroupActivity.this, AppConfig.checkimg(headUrl)  , mImgGroup, R.mipmap.img_default_avatar);
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
                PictureSelector.create(NewGroupActivity.this)
                        .openCamera(PictureMimeType.ofImage())
                        .selectionMode(PictureConfig.SINGLE)
                        .withAspectRatio(1, 1)
                        .enableCrop(true)
                        .showCropFrame(false)
                        .showCropGrid(false)
                        .freeStyleCropEnabled(true)
                        .circleDimmedLayer(true)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            }
        });
        builder.setOnClickListener(R.id.tv_xiangce, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                PictureSelector.create(NewGroupActivity.this)
                        .openGallery(PictureMimeType.ofImage())
                        .selectionMode(PictureConfig.SINGLE)
                        .withAspectRatio(1, 1)
                        .enableCrop(true)
                        .showCropFrame(false)
                        .showCropGrid(false)
                        .freeStyleCropEnabled(true)
                        .circleDimmedLayer(true)
                        .forResult(PictureConfig.CHOOSE_REQUEST);

            }
        });
        builder.create().show();
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
                .load(path)                                   // 传人要压缩的图片列表
                .ignoreBy(80)                                  // 忽略不压缩图片的大小
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
}

package com.haoniu.quchat.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.MyApplication;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.utils.EventUtil;
import com.zds.base.Toast.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author lhb
 * 编辑用户信息，eg:昵称
 */
public class EditInfoActivity extends BaseActivity {

    public static final String FUNC_TYPE_MODIFY_GROUP_REMARK = "6";

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_subtitle)
    TextView mToolSubTitle;
    @BindView(R.id.et_nick_name)
    EditText mEtNickName;
    @BindView(R.id.img_del)
    ImageView mImgDel;
    @BindView(R.id.tv_hint_account)
    TextView mTvHintAccount;


    /**
     * 1:修改昵称
     * 2：设置艾特号
     * 3：设置个性签名
     * 4.修改群昵称
     * 5.修改我的群昵称
     */
    private String from;

    /**
     * 群ID
     */
    private String groupId;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_edit_my_info);
    }

    @Override
    protected void initLogic() {
        mToolSubTitle.setVisibility(View.VISIBLE);
        mToolSubTitle.setText("保存");
        if (mEtNickName.getText().length() > 0) {
            mImgDel.setVisibility(View.VISIBLE);
        }


        //昵称保存
        mToolSubTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置昵称
                if (from.equals("1")) {
                    editNickName();
                }//设置艾特号
                else if (from.equals("2")) {
                    setYxAccount();
                }//设置个性签名
                else if (from.equals("3")) {
                    setSelfLable();
                } else if (from.equals("4")) {
                    setGroupNickName();
                } else if (from.equals("5")) {
                    setMyGroupNickName();
                }else if (from.equals(FUNC_TYPE_MODIFY_GROUP_REMARK)) {
                    setGroupRemark();
                }
            }
        });
        mEtNickName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    mImgDel.setVisibility(View.VISIBLE);
                } else {
                    mImgDel.setVisibility(View.GONE);
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
        if (from.equals("1")) {
            mToolbarTitle.setText("修改昵称");
            mEtNickName.setHint("输入昵称");
            mEtNickName.setText(UserComm.getUserInfo().getNickName());
            mEtNickName.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20)});

        } else if (from.equals("2")) {
            mToolbarTitle.setText("艾特号");
            mEtNickName.setHint("请输入您的艾特号");
            mEtNickName.setText(UserComm.getUserInfo().getUserCode());
            mTvHintAccount.setVisibility(View.VISIBLE);
            String dig = "0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM_";
            mEtNickName.setKeyListener(DigitsKeyListener.getInstance(dig));

        } else if (from.equals("3")) {
            mToolbarTitle.setText("个性签名");
            mEtNickName.setHint("输入个性签名");
            mEtNickName.setText(UserComm.getUserInfo().getSign());
        } else if (from.equals("4")) {
            mToolbarTitle.setText("修改群名称");
            mEtNickName.setHint("输入群名称");
            mEtNickName.setText(extras.getString("groupName"));
            groupId = extras.getString("groupId");
            mEtNickName.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20)});
        } else if (from.equals("5")) {
            mToolbarTitle.setText("修改我的群昵称");
            mEtNickName.setHint("输入我的群昵称");
            mEtNickName.setText(extras.getString("myGroupName"));
            groupId = extras.getString("groupId");
        }else if (from.equals(FUNC_TYPE_MODIFY_GROUP_REMARK)) {
            mToolbarTitle.setText("群备注");
            mEtNickName.setHint("输入群备注");
            mEtNickName.setText(extras.getString("key_intent_group_remark"));
            groupId = extras.getString("groupId");
        }
    }


    /**
     * 保存昵称
     */
    private void editNickName() {
        if (mEtNickName.getText().toString().trim().length() <= 0) {
            toast("请先填写昵称");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("nickName", mEtNickName.getText().toString().trim());
        ApiClient.requestNetHandle(this, AppConfig.MODIFY_USER_NICK_NAME, "正在保存...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                toast("修改成功");
                MyApplication.getInstance().UpUserInfo();
                finish();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }


    /**
     * 设置艾特号
     */
    private void setYxAccount() {
        if (mEtNickName.getText().toString().trim().length() <= 0) {
            toast("请先填写艾特号");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userCode", mEtNickName.getText().toString().trim());
        ApiClient.requestNetHandle(this, AppConfig.MODIFY_USER_CODE, "正在保存...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                toast("修改成功");
                MyApplication.getInstance().UpUserInfo();
                finish();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }


    /**
     * 修改个性签名
     */
    private void setSelfLable() {
        if (mEtNickName.getText().toString().trim().length() <= 0) {
            toast("请先填写个性签名");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("sign", mEtNickName.getText().toString().trim());
        ApiClient.requestNetHandle(this, AppConfig.MODIFY_SELF_LABLE, "正在保存...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                toast("修改成功");
                MyApplication.getInstance().UpUserInfo();
                finish();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }


    /**
     * 修改群名称
     */
    private void setGroupNickName() {
        if (mEtNickName.getText().toString().trim().length() <= 0) {
            toast("请先填写群昵称");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("groupName", mEtNickName.getText().toString().trim());
        map.put("groupHead", "");
        map.put("groupId", groupId);

        //1-群名称 2-群头像
        map.put("updateType", "1");

        ApiClient.requestNetHandle(this, AppConfig.MODIFY_GROUP_NAME_OF_HEAD, "正在保存...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                EventBus.getDefault().post(new EventCenter<>(EventUtil.REFRESH_GROUP_NAME, mEtNickName.getText().toString().trim()));
                EventBus.getDefault().post(new EventCenter(EventUtil.FLUSHGROUP));
                toast("修改成功");
                finish();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }


    /**
     * 修改我的群昵称
     */
    private void setMyGroupNickName() {
        if (mEtNickName.getText().toString().trim().length() <= 0) {
            toast("请先填写群昵称");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userNickName", mEtNickName.getText().toString().trim());
        map.put("groupId", groupId);

        //1-群名称 2-群头像
        map.put("updateType", "1");

        ApiClient.requestNetHandle(this, AppConfig.MODIFY_USER_GROUP_NICKNAME, "正在保存...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                EventBus.getDefault().post(new EventCenter<>(EventUtil.REFRESH_MY_GROUP_NAME, mEtNickName.getText().toString().trim()));
                toast("修改成功");
                finish();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

    /**
     * 修改群备注
     */
    private void setGroupRemark() {

        Map<String, Object> map = new HashMap<>();
        map.put("groupNickName", mEtNickName.getText().toString().trim());
        map.put("groupId", groupId);


        ApiClient.requestNetHandle(this, AppConfig.MODIFY_GROUP_REMARK, "正在保存...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                //修改群备注之后，原先所有显示群昵称的地方都要被群备注替代
                EventBus.getDefault().post(new EventCenter<>(EventUtil.REFRESH_GROUP_NAME, mEtNickName.getText().toString().trim()));
                EventBus.getDefault().post(new EventCenter(EventUtil.FLUSHGROUP));
                toast("保存成功");
                finish();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }


    @OnClick(R.id.img_del)
    public void onViewClicked() {
        mEtNickName.getText().clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


}

package com.haoniu.quchat.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.activity.CustomListActivity;
import com.haoniu.quchat.activity.MultiDeviceActivity;
import com.haoniu.quchat.activity.MyCollectActivity;
import com.haoniu.quchat.activity.MyInfoActivity;
import com.haoniu.quchat.activity.SetActivity;
import com.haoniu.quchat.activity.ShareQrActivity;
import com.haoniu.quchat.activity.UserInfoDetailActivity;
import com.haoniu.quchat.activity.WalletActivity;
import com.haoniu.quchat.activity.WebViewActivity;
import com.haoniu.quchat.aop.SingleClick;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.LoginInfo;
import com.haoniu.quchat.global.Global;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.operate.UserOperateManager;
import com.haoniu.quchat.pay.StoreShowSwitchBean;
import com.haoniu.quchat.paysdk.TradeSession;
import com.haoniu.quchat.utils.EventUtil;
import com.haoniu.quchat.utils.StringUtil;
import com.haoniu.quchat.widget.EaseImageView;
import com.zds.base.ImageLoad.GlideUtils;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.code.activity.CaptureActivity;
import com.zds.base.json.FastJsonUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.darsh.multipleimageselect.helpers.Constants.REQUEST_CODE;
import static com.zds.base.code.activity.CaptureActivity.INTENT_EXTRA_KEY_QR_SCAN;

/**
 * 描   述: 我的
 * 日   期: 2019/06/5 17:09
 * 更新日期: 2017/12/5
 *
 * @author lhb
 */
public class PersonalFragment extends EaseBaseFragment {

    @BindView(R.id.tv_nick_name)
    TextView mTvNickName;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.my_head)
    EaseImageView mMyHead;
    @BindView(R.id.tv_amount)
    TextView mTvAmount;
    Unbinder unbinder;
//    private KFTPaySDKManager router;

    @BindView(R.id.tv_shop)
    TextView tv_shop;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_center, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 初始化用户信息
     */
    private void initUserInfo() {
        LoginInfo loginInfo = UserComm.getUserInfo();
        if (loginInfo != null) {
            GlideUtils.GlideLoadCircleErrorImageUtils(getContext(), AppConfig.checkimg(loginInfo.getUserHead()), mMyHead, R.mipmap.img_default_avatar);
            mTvNickName.setText(loginInfo.getNickName());
            if (TextUtils.isEmpty(loginInfo.getUserCode())) {
                mTvPhone.setText("艾特号： 无");
            } else {
                mTvPhone.setText("艾特号： " + loginInfo.getUserCode());
            }
            mTvAmount.setText("余额： " + StringUtil.getFormatValue2(loginInfo.getMoney()));

        }
    }

    /**
     * 初始化逻辑
     */
    @Override
    protected void initLogic() {
        initUserInfo();
//        router = KFTPaySDK.getKftPayManager();
//        getAccessToken();

        getStoreShowSwitch();
    }

    private void getStoreShowSwitch() {
        tv_shop.setVisibility(View.GONE);
        Map<String, Object> map = new HashMap<>();
        ApiClient.requestNetHandleNoParam(getActivity(), AppConfig.showStore,
                "", new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        if (json != null && json.length() > 0) {
                            StoreShowSwitchBean storeShowSwitchBean = FastJsonUtil.getObject(json, StoreShowSwitchBean.class);
                            if(storeShowSwitchBean.status == 1){
                                tv_shop.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });
    }

    /**
     * EventBus接收消息
     *
     * @param center 获取事件总线信息
     */
    @Override
    protected void onEventComing(EventCenter center) {
        if (center.getEventCode() == EventUtil.FLUSHUSERINFO) {
            initUserInfo();
        }
    }

    /**
     * Bundle  传递数据
     *
     * @param extras
     */
    @Override
    protected void getBundleExtras(Bundle extras) {
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                String result = bundle.getString(INTENT_EXTRA_KEY_QR_SCAN);
                if (result.contains("person") || result.contains("group")) {
                    if ("person".equals(result.split("_")[1])) {
                        startActivity(new Intent(getContext(), UserInfoDetailActivity.class).putExtra("friendUserId", result.split("_")[0]));
                    } else {
                        UserOperateManager.getInstance().scanInviteContact(getContext(),result);
                    }
                }
            }
        }
    }

    @SingleClick(1500)
    @OnClick({R.id.tv_device,R.id.rl_wallet,R.id.tv_shop, R.id.rel_my_info, R.id.tv_collect, R.id.tv_set, R.id.tv_scan, R.id.tv_custom, R.id.tv_share_qr})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_scan:
                Global.addUserOriginType = Constant.ADD_USER_ORIGIN_TYPE_QRCODE;
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.rl_wallet:
                //我的钱包
                //判断是否开通钱包账户
                /*if(TextUtils.isEmpty(UserComm.getUserInfo().ncountUserId)){
                    OpenWalletActivity.start(getActivity());
                }else {
                    startActivity(WalletActivity.class);
                }*/
                startActivity(WalletActivity.class);
                break;
            case R.id.rel_my_info:
                //个人信息
                startActivity(MyInfoActivity.class);
                break;
            case R.id.tv_collect:
                //我的收藏
                startActivity(MyCollectActivity.class);
                break;
            case R.id.tv_share_qr:
                //分享
                startActivity(ShareQrActivity.class);
                break;
            case R.id.tv_set:
                //设置
                startActivity(SetActivity.class);
                break;
            case R.id.tv_device:
                startActivity(new Intent(getContext(), MultiDeviceActivity.class));
                break;
            case R.id.tv_custom:
                //客服
                startActivity(CustomListActivity.class);
                break;
            case R.id.tv_shop:
                startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra("title","艾特商城").putExtra("url",AppConfig.shopUrl));
                break;
            default:
                break;
        }
    }




    /**
     * 获取快付通accessToken
     */
    private void getAccessToken() {
        Map<String, Object> map = new HashMap<>();
        ApiClient.requestNetHandle(getContext(), AppConfig.accessTokenKft, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {

                ToastUtil.toast(msg);
                TradeSession.setAccessToken(json);
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

//    /**
//     * 获取快付通预下单
//     */
//    private void getCommonTrade(KFTPaySDKManager router, String custName, String certNo, String phone) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("merchantId", Common.MERCHANT_ID);
//        params.put("orderType", "6");
//        params.put("body", "测试获取预支付订单");
//        params.put("tradeName", "充值测试");
//        params.put("orderNo", String.valueOf(System.currentTimeMillis()));
//        params.put("certificateType", "0");
//        if (!TextUtils.isEmpty(custName)) {
//            params.put("custName", custName);
//        }
//        if (!TextUtils.isEmpty(certNo)) {
//            params.put("certificateNo", certNo);
//        }
//        if (!TextUtils.isEmpty(phone)) {
//            params.put("mobile", phone);
//        }
//        KFTPaySDK.getKftPayManager();
//        ApiClient.requestNetHandle(getContext(), AppConfig.accessCommonTradeKft, "", params, new ResultListener() {
//            @Override
//            public void onSuccess(String json, String msg) {
//                doNameAuth(router, json);
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                ToastUtil.toast(msg);
//            }
//        });
//    }
//
//
//    private void doNameAuth(KFTPaySDKManager router, String preOrderNo) {
//        PayParams params = new PayParams();
//        params.prepayOrderNo = preOrderNo;
//
//        BaseReq req = new BaseReq(TradeSession.getAccessToken());
//        req.data = params;
//        router.createAccount(getContext(), req, new IKftApiCallback<AccountInfo>() {
//            @Override
//            public void onResponse(BaseResp<AccountInfo> resp) {
//                AccountInfo info = resp.data;
//                if (resp.status == Common.Status.SUCCESS) {
//                    PreferencesUtils.putString(MyApplication.getInstance().getApplicationContext(), Common.PrefKey.LAST_CUST_ID, info.custId);
//                    Common.CUST_ID = info.custId;
//                    TradeSession.setCustId(info.custId);
//                    KFTPaySDK.setCustId(info.custId);
//                    startActivity(WalletActivity.class);
//                }
//            }
//        });
//    }


}

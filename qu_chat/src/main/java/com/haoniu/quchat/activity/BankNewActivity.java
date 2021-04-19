package com.haoniu.quchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.entity.BankDetailInfo;
import com.haoniu.quchat.entity.BankDto;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.utils.PhoneFormatUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 银行卡
 */
public class BankNewActivity extends BaseActivity {

    @BindView(R.id.et_bank_card_number)
    EditText mBankCardNum;
    @BindView(R.id.et_bank_location)
    EditText mBankLocation;
    @BindView(R.id.et_bank_phone)
    EditText mBankPhone;
    @BindView(R.id.et_identity_card_number)
    EditText mIdentityCardNum;
    @BindView(R.id.et_bank_card_name)
    EditText mBankCardName;
    @BindView(R.id.tv_new_bank_card_submit)
    TextView mNewBankCardSubmit;


    BankDetailInfo bankDetailInfo;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_bank_new);
    }


    private boolean checkForm() {
        if (mBankCardNum.getText().toString().trim().length() <= 0) {
            toast("请输入卡号");
            return false;
        } else if (mBankLocation.getText().toString().trim().length() <= 0) {
            toast("请输入开户行");
            return false;
        } else if (mBankPhone.getText().toString().trim().length() <= 0) {
            toast("请输入银行预留电话");
            return false;
        } else if (!PhoneFormatUtil.isPhoneNumberValid(mBankPhone.getText().toString().trim())) {
            toast("请输入正确的银行预留电话");
            return false;
        } else if (mIdentityCardNum.getText().toString().trim().length() <= 0) {
            toast("请输入您的身份证号");
            return false;
        }
//        else if (!IDCardValidateUtils.validate_effective(mIdentityCardNum.getText().toString().trim())) {
//            toast("请输入正确的身份证号");
//            return false;
//        }
        else if (mBankCardName.getText().toString().trim().length() <= 0) {
            toast("请输入您的姓名");
            return false;
        }
        return true;
    }

    /**
     * 绑定银行卡
     */
    private void addBankCard() {
        Map<String, Object> map = new HashMap<>();
        map.put("realName", mBankCardName.getText().toString().trim());
        map.put("idCard", mIdentityCardNum.getText().toString().trim());
        map.put("bankCard", mBankCardNum.getText().toString().trim());
        map.put("bankName", mBankLocation.getText().toString().trim());
        map.put("bankPhone", mBankPhone.getText().toString().trim());
        ApiClient.requestNetHandle(this, AppConfig.addBankCardList, "正在提交...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                BankDto dto = new BankDto();
                dto.setBankCard(map.get("bankCard").toString());
                dto.setBankName(map.get("bankName").toString());
                dto.setBankPhone(map.get("bankPhone").toString());
                dto.setIdCard(map.get("idCard").toString());
                dto.setRealName(map.get("realName").toString());
                dto.setTokenId(json);
//                Bundle b = new Bundle();
//                b.putSerializable("bean",dto);
//                startActivity(CheakBankActivity.class,b);
                sureBankCard(dto);
            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
            }
        });
    }

    /**
     * 确认绑定银行卡
     * @param dto
     */
    private void sureBankCard(BankDto dto) {
        Map<String, Object> map = new HashMap<>();
        map.put("realName", dto.getRealName());
        map.put("idCard", dto.getIdCard());
        map.put("bankCard", dto.getBankCard());
        map.put("bankName", dto.getBankName());
        map.put("bankPhone", dto.getBankPhone());
        ApiClient.requestNetHandle(this, AppConfig.check_bank, "请稍后...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                finish();
            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
            }
        });

    }
    @Override
    protected void initLogic() {
        setTitle("添加储蓄卡");
        mBankCardName.setFilters(new InputFilter[]{filter});
        mBankLocation.setFilters(new InputFilter[]{filter});
    }


    /**
     * 绑定银行卡
     */
    private void apply() {
//
//        Map<String, Object> map = new HashMap<>();
//        //银行帐号
//        map.put("bankNumber", erKahao.getText().toString());
//        //银行
//        map.put("bankName", erBank.getText().toString());
//        //类型
//        map.put("type","1");
//        ApiClient.requestNetHandle(this, AppConfig.addBank, "正在提交...", map, new ResultListener() {
//            @Override
//            public void onSuccess(String json, String msg) {
//                toast(msg);
//                finish();
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                toast(msg);
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 13) {
            if (resultCode == 102) {
                String realName = data.getStringExtra("realName");
                //upRealName(realName);
            }
        }
    }

//     /**
//     * 更新真实姓名
//     */
//    private void upRealName(final String realName) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("realName", realName);
//        ApiClient.requestNetHandle(this, AppConfig.upDataRealName, "正在更新...", map, new ResultListener() {
//            @Override
//            public void onSuccess(String json, String msg) {
//                UserInfo userInfo = MyApplication.getInstance().getUserInfo();
//                userInfo.setRealName(realName);
//                MyApplication.getInstance().saveUserInfo(userInfo);
//                erName.setText(realName);
//                ToastUtil.toast(msg);
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                ToastUtil.toast(msg);
//            }
//        });
//    }

    @Override
    protected void onEventComing(EventCenter center) {
        if (center.getEventCode() == 1011) {
            finish();
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    /**
     * 判定输入汉字
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                if (!isChinese(source.charAt(i))) {
                    return "";
                }
            }
            return null;
        }
    };


    @OnClick(R.id.tv_new_bank_card_submit)
    public void onViewClicked() {
        if (checkForm()) {
            addBankCard();
        }
    }
}

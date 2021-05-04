package com.haoniu.quchat.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;

import com.aite.chat.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.model.LocalPhoneMatchFreindInfo;
import com.zds.base.ImageLoad.GlideUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zds.base.Toast.ToastUtil.toast;

/**
 * @author lhb
 * 本地通讯录
 */
public class LocalPhoneAdapter extends BaseQuickAdapter<LocalPhoneMatchFreindInfo, BaseViewHolder> implements Filterable {
    private MyFilter myFilter;
    private List<LocalPhoneMatchFreindInfo> copyUserList;

    public LocalPhoneAdapter(@Nullable List<LocalPhoneMatchFreindInfo> data) {
        super(R.layout.adapter_local_phone, data);
        copyUserList = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalPhoneMatchFreindInfo item) {
        helper.setText(R.id.tv_name, item.getNickName());
        helper.setText(R.id.tv_account, "艾特号 : " + item.getUserCode());
        GlideUtils.GlideLoadCircleErrorImageUtils(mContext, AppConfig.checkimg(item.getUserHead()), helper.getView(R.id.img_head), R.mipmap.img_default_avatar);
        //好友标识 0 不是好友 1 是好友
        if (item.getFriendFlag().equals("1")) {
            helper.setBackgroundColor(R.id.tv_add, ContextCompat.getColor(mContext, R.color.white));
            helper.setTextColor(R.id.tv_add, ContextCompat.getColor(mContext, R.color.gray_text_color));
            helper.setText(R.id.tv_add, "已添加");
        } else {
            helper.setBackgroundRes(R.id.tv_add, R.drawable.agree_friend_bg_selector);
            helper.setTextColor(R.id.tv_add, ContextCompat.getColor(mContext, R.color.white));
            helper.setText(R.id.tv_add, "添加");
        }


        if (item.getFriendFlag().equals("0")) {
            helper.setOnClickListener(R.id.tv_add, new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("toUserId", item.getUserId());
                    map.put("originType", Constant.ADD_USER_ORIGIN_TYPE_ADDRESS_BOOK);
                    ApiClient.requestNetHandle(mContext, AppConfig.APPLY_ADD_USER, "", map, new ResultListener() {
                        @Override
                        public void onSuccess(String json, String msg) {
                            toast(msg);
                            if (Activity.class.isInstance(mContext)) {
                                // 转化为activity，然后finish就行了
                                Activity activity = (Activity) mContext;
                                activity.finish();
                            }
                        }

                        @Override
                        public void onFailure(String msg) {
                            toast(msg);
                        }
                    });
                }
            });
        }
    }


    @Override
    public Filter getFilter() {
        if (myFilter == null) {
            myFilter = new MyFilter(mData);
        }
        return myFilter;
    }

    protected class MyFilter extends Filter {
        List<LocalPhoneMatchFreindInfo> mOriginalList = null;

        public MyFilter(List<LocalPhoneMatchFreindInfo> myList) {
            this.mOriginalList = myList;
        }

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (mOriginalList == null) {
                mOriginalList = new ArrayList<LocalPhoneMatchFreindInfo>();
            }

            String charString = prefix.toString();
            if (TextUtils.isEmpty(charString)) {
                //没有过滤的内容，则使用源数据
                results.values = copyUserList;
                results.count = copyUserList.size();
            } else {
                if (copyUserList.size() > mOriginalList.size()) {
                    mOriginalList = copyUserList;
                }

                String prefixString = prefix.toString();
                final int count = mOriginalList.size();
                final ArrayList<LocalPhoneMatchFreindInfo> newValues = new ArrayList<LocalPhoneMatchFreindInfo>();
                for (int i = 0; i < count; i++) {
                    final LocalPhoneMatchFreindInfo bean = mOriginalList.get(i);
                    String username = bean.getNickName();

                    if (username.contains(prefixString)) {
                        newValues.add(bean);
                    } else {
                        final String[] words = username.split(" ");
                        final int wordCount = words.length;

                        // Start at index 0, in case valueText starts with space(s)
                        for (String word : words) {
                            if (word.contains(prefixString)) {
                                newValues.add(bean);
                                break;
                            }
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //把过滤后的值返回出来
            mData = ((List<LocalPhoneMatchFreindInfo>) results.values);
            notifyDataSetChanged();
        }
    }


}

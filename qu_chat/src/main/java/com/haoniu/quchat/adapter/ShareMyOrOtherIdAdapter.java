package com.haoniu.quchat.adapter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;

import com.aite.chat.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.quchat.activity.ChatActivity;
import com.haoniu.quchat.activity.ShareMyOrOtherActivity;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.entity.AddContactInfo;
import com.haoniu.quchat.http.AppConfig;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.zds.base.ImageLoad.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 分享自己或者自己其他好友的名片给他人adapter
 *
 * @author lhb
 */
public class ShareMyOrOtherIdAdapter extends BaseQuickAdapter<AddContactInfo, BaseViewHolder> implements Filterable {
    private MyFilter myFilter;
    private List<AddContactInfo> copyUserList;

    public ShareMyOrOtherIdAdapter(List<AddContactInfo> list) {
        super(R.layout.adapter_share_my_other_id_card, list);
        copyUserList = list;

    }

    @Override
    protected void convert(BaseViewHolder helper, AddContactInfo item) {
        if (item.getType().equals("0")) {
            EMGroup group = EMClient.getInstance().groupManager().getGroup(item.getUserId());
            if (group != null) {
                helper.setText(R.id.tv_group_name_amout, "  (" + group.getMemberCount() + "人)");
            }
        }
        helper.setText(R.id.tv_group_name, item.getNickName());
        GlideUtils.GlideLoadCircleErrorImageUtils(mContext, AppConfig.checkimg(item.getUserImg()), helper.getView(R.id.img_group), R.mipmap.img_default_avatar);

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareMyOrOtherActivity activity = (ShareMyOrOtherActivity) mContext;
                Intent intent = new Intent(mContext, ChatActivity.class);
                if (item.getType().equals("0")) {
                    //群组
                    intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);
                }
                intent.putExtra(Constant.EXTRA_USER_ID, item.getUserId());
                //发送自己的名片和好友的名片给他人判断
                intent.putExtra("from", "1");
                intent.putExtras(activity.getBundle());
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public Filter getFilter() {
        if (myFilter == null) {
            myFilter = new MyFilter(mData);
        }
        return myFilter;
    }

    protected class MyFilter extends Filter {
        List<AddContactInfo> mOriginalList = null;

        public MyFilter(List<AddContactInfo> myList) {
            this.mOriginalList = myList;
        }

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (mOriginalList == null) {
                mOriginalList = new ArrayList<AddContactInfo>();
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
                final ArrayList<AddContactInfo> newValues = new ArrayList<AddContactInfo>();
                for (int i = 0; i < count; i++) {
                    final AddContactInfo bean = mOriginalList.get(i);
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
            mData = ((List<AddContactInfo>) results.values);
            notifyDataSetChanged();
        }
    }
}
package com.haoniu.quchat.adapter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.aite.chat.R;
import com.haoniu.quchat.activity.ChatActivity;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.base.EaseConstant;
import com.haoniu.quchat.entity.GroupInfo;
import com.haoniu.quchat.entity.MyGroupInfoList;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.model.ContactListInfo;
import com.zds.base.ImageLoad.GlideUtils;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的群组
 *
 * @author lhb
 */
public class MyGroupAdapter extends BaseQuickAdapter<GroupInfo, BaseViewHolder> implements Filterable {

    private List<GroupInfo> copyGroupList;
    private MyFilter myFilter;

    public MyGroupAdapter(List<GroupInfo> list) {
        super(R.layout.adapter_my_group, list);
        copyGroupList = list;
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupInfo item) {
        helper.setText(R.id.tv_group_name, item.getGroupName());
        GlideUtils.GlideLoadCircleErrorImageUtils(mContext, AppConfig.checkimg(item.getGroupHead()), helper.getView(R.id.img_group), R.mipmap.ic_group_default);

        helper.setOnClickListener(R.id.ll_item, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ChatActivity.class).putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP)
                        .putExtra(EaseConstant.EXTRA_USER_ID, item.getHuanxinGroupId())
                        .putExtra(Constant.ROOMTYPE, ""));
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
        List<GroupInfo> mOriginalList = null;

        public MyFilter(List<GroupInfo> myList) {
            this.mOriginalList = myList;
        }

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (mOriginalList == null) {
                mOriginalList = new ArrayList<GroupInfo>();
            }

            String charString = prefix.toString();
            if (TextUtils.isEmpty(charString)) {
                //没有过滤的内容，则使用源数据
                results.values = copyGroupList;
                results.count = copyGroupList.size();
            } else {
                if (copyGroupList.size() > mOriginalList.size()) {
                    mOriginalList = copyGroupList;
                }

                String prefixString = prefix.toString();
                final int count = mOriginalList.size();
                final ArrayList<GroupInfo> newValues = new ArrayList<GroupInfo>();
                for (int i = 0; i < count; i++) {
                    final GroupInfo bean = mOriginalList.get(i);
                    String groupName = bean.getGroupName();

                    if (groupName.contains(prefixString)) {
                        newValues.add(bean);
                    } else {
                        final String[] words = groupName.split(" ");
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
            mData = ((List<GroupInfo>) results.values);
            notifyDataSetChanged();
        }
    }
}
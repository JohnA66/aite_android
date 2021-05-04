package com.haoniu.quchat.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;

import com.aite.chat.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.quchat.entity.GroupDetailInfo;
import com.zds.base.ImageLoad.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 群用户列表adapter
 *
 */
public class GroupUserMultiSelectListAdapter extends BaseQuickAdapter<GroupDetailInfo.GroupUserDetailVoListBean, BaseViewHolder> implements Filterable {

    private List<GroupDetailInfo.GroupUserDetailVoListBean> copyGroupList;
    private MyFilter myFilter;

    private List<GroupDetailInfo.GroupUserDetailVoListBean> mIdList = new ArrayList<>();

    public List<GroupDetailInfo.GroupUserDetailVoListBean> getIdList() {
        return mIdList;
    }

    private OnSelGroupManageListener mOnSelGroupManageListener;

    public void setIdList(List<GroupDetailInfo.GroupUserDetailVoListBean> idList) {
        mIdList = idList;
    }

    public GroupUserMultiSelectListAdapter(List<GroupDetailInfo.GroupUserDetailVoListBean> list) {
        super(R.layout.adapter_contact, list);
        copyGroupList = list;
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupDetailInfo.GroupUserDetailVoListBean item) {
        helper.setText(R.id.tv_name, item.getUserNickName());
        GlideUtils.GlideLoadCircleErrorImageUtils(mContext, item.getUserHead(), helper.getView(R.id.img_group), R.mipmap.img_default_avatar);
        CheckBox checkBox = helper.getView(R.id.ck_contact);
        checkBox.setOnCheckedChangeListener(null);
        checkBox.setChecked(false);

        if (mIdList.contains(item)){
            checkBox.setChecked(true);
        }

        if (item.getUserRank().equals("1")) {
            helper.setText(R.id.tv_name, item.getUserNickName()+"(管理员)");
        }
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBox.setChecked(!checkBox.isChecked());
            }
        });
        helper.setOnCheckedChangeListener(R.id.ck_contact, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!mIdList.contains(item)) {
                        mIdList.add(item);
                    }
                } else {
                    if (mIdList.contains(item)) {
                        mIdList.remove(item);
                    }
                }
                notifyDataSetChanged();
            }
        });
    }

    public interface OnSelGroupManageListener {
        void selGroupUser(int pos);
    }

    @Override
    public Filter getFilter() {
        if (myFilter == null) {
            myFilter = new MyFilter(mData);
        }
        return myFilter;
    }

    protected class MyFilter extends Filter {
        List<GroupDetailInfo.GroupUserDetailVoListBean> mOriginalList = null;

        public MyFilter(List<GroupDetailInfo.GroupUserDetailVoListBean> myList) {
            this.mOriginalList = myList;
        }

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (mOriginalList == null) {
                mOriginalList = new ArrayList<GroupDetailInfo.GroupUserDetailVoListBean>();
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
                final ArrayList<GroupDetailInfo.GroupUserDetailVoListBean> newValues = new ArrayList<GroupDetailInfo.GroupUserDetailVoListBean>();
                for (int i = 0; i < count; i++) {
                    final GroupDetailInfo.GroupUserDetailVoListBean bean = mOriginalList.get(i);
                    String userName = bean.getUserNickName();

                    if (userName.contains(prefixString)) {
                        newValues.add(bean);
                    } else {
                        final String[] words = userName.split(" ");
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
            mData = ((List<GroupDetailInfo.GroupUserDetailVoListBean>) results.values);
            notifyDataSetChanged();
        }
    }
}
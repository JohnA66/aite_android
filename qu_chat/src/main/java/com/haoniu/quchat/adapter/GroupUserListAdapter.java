package com.haoniu.quchat.adapter;

import android.widget.CheckBox;

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
 * @author lhb
 */
public class GroupUserListAdapter extends BaseQuickAdapter<GroupDetailInfo.GroupUserDetailVoListBean, BaseViewHolder> {

    private int selected = -1;

    private List<GroupDetailInfo.GroupUserDetailVoListBean> mIdList = new ArrayList<>();

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public List<GroupDetailInfo.GroupUserDetailVoListBean> getIdList() {
        return mIdList;
    }

    private OnSelGroupManageListener mOnSelGroupManageListener;

    public void setIdList(List<GroupDetailInfo.GroupUserDetailVoListBean> idList) {
        mIdList = idList;
    }

    public GroupUserListAdapter(List<GroupDetailInfo.GroupUserDetailVoListBean> list) {
        super(R.layout.adapter_contact, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupDetailInfo.GroupUserDetailVoListBean item) {
        helper.setText(R.id.tv_name, item.getUserNickName());
        GlideUtils.GlideLoadCircleErrorImageUtils(mContext, item.getUserHead(), helper.getView(R.id.img_group), R.mipmap.img_default_avatar);
        CheckBox checkBox = helper.getView(R.id.ck_contact);
        checkBox.setClickable(false);
        if (selected == helper.getAdapterPosition()) {
            helper.setChecked(R.id.ck_contact, true);
            helper.itemView.setSelected(true);
        } else {
            helper.setChecked(R.id.ck_contact, false);
            helper.itemView.setSelected(false);
        }


//        helper.setOnCheckedChangeListener(R.id.ck_contact, new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    item.setSel(true);
//                    mIdList.add(item);
//                } else {
//                    item.setSel(false);
//                    if (mIdList.contains(item)) {
//                        mIdList.remove(mIdList.indexOf(item));
//                    }
//                }
//                notifyDataSetChanged();
//            }
//        });
    }

    public interface OnSelGroupManageListener {
        void selGroupUser(int pos);
    }
}
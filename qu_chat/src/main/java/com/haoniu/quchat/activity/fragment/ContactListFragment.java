/**
 * 作   者：赵大帅
 */
package com.haoniu.quchat.activity.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.aite.chat.R;
import com.haoniu.quchat.activity.AddContactActivity;
import com.haoniu.quchat.activity.ApplyJoinGroupActivity;
import com.haoniu.quchat.activity.AuditMsgActivity;
import com.haoniu.quchat.activity.ChatActivity;
import com.haoniu.quchat.activity.ContactActivity;
import com.haoniu.quchat.activity.MyGroupActivity;
import com.haoniu.quchat.activity.MyQrActivity;
import com.haoniu.quchat.activity.UserInfoDetailActivity;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.constant.SP;
import com.haoniu.quchat.domain.EaseUser;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.global.Global;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.operate.UserOperateManager;
import com.haoniu.quchat.utils.EventUtil;
import com.haoniu.quchat.utils.PreferenceManager;
import com.haoniu.quchat.widget.ContactItemView;
import com.haoniu.quchat.widget.PopWinShare;
import com.haoniu.quchat.widget.SearchBar;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.zds.base.code.activity.CaptureActivity;
import com.zds.base.util.DensityUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.darsh.multipleimageselect.helpers.Constants.REQUEST_CODE;
import static com.zds.base.code.activity.CaptureActivity.INTENT_EXTRA_KEY_QR_SCAN;

/**
 * contact list
 */
public class ContactListFragment extends EaseContactListFragment {

    private static final String TAG = ContactListFragment.class.getSimpleName();
    private View loadingView;
    private ContactItemView friendNoticeItem;
    private ContactItemView groupdNoticeItem;
    @BindView(R.id.search_bar)
    SearchBar searchBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ease_fragment_contact_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @SuppressLint("InflateParams")
    @Override
    protected void initLogic() {
        super.initLogic();

        mImgShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    showPopudown();
                } catch (Exception e) {}
            }
        });

        @SuppressLint("InflateParams")
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.em_contacts_header, null);
        HeaderItemClickListener clickListener = new HeaderItemClickListener();
        friendNoticeItem = headerView.findViewById(R.id.friend_notice);
        groupdNoticeItem = headerView.findViewById(R.id.group_notice);

        headerView.findViewById(R.id.friend_notice).setOnClickListener(clickListener);
        headerView.findViewById(R.id.group_notice).setOnClickListener(clickListener);
        headerView.findViewById(R.id.group_item).setOnClickListener(clickListener);

        listView.addHeaderView(headerView);


        //add loading view
        loadingView = LayoutInflater.from(getActivity()).inflate(R.layout.em_layout_loading_data, null);
        contentContainer.addView(loadingView);
        registerForContextMenu(listView);
        //设置联系人数据
//        Map<String, EaseUser> m = MyHelper.getInstance().getContactList();
////        if (m instanceof Hashtable<?, ?>) {
////            m = (Map<String, EaseUser>) ((Hashtable<String, EaseUser>) m).clone();
////        }
        searchBar.setOnSearchBarListener(new SearchBar.OnSearchBarListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                contactListLayout.filter(s);
            }
        });



        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EaseUser user = (EaseUser) listView.getItemAtPosition(position);
                if (user != null) {
                    // start chat acitivity
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    String username = user.getUsername();
                    // it's single chat
                    intent.putExtra(Constant.EXTRA_USER_ID, username);
                    startActivity(intent);
                    // startActivity(new Intent(getActivity(), UserInfoActivity.class).putExtra("username", username));
                }
            }
        });
    }

    protected class HeaderItemClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.friend_notice:
                    // 好友申请列表
                    friendNoticeItem.setUnreadCount(0);
                    startActivity(AuditMsgActivity.class);
                    break;
                case R.id.group_notice:
                    groupdNoticeItem.setUnreadCount(0);
                    startActivity(ApplyJoinGroupActivity.class);
                    break;
                case R.id.group_item:
                    // 我的群组
                    startActivity(MyGroupActivity.class);
                    break;
                default:
                    break;
            }
        }

    }


    @Override
    public void refresh() {
        super.refresh();
        refreshApplyLayout();
    }

    public void refreshApplyLayout() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int applyJoinGroupcount = (int) PreferenceManager.getInstance().getParam(SP.APPLY_JOIN_GROUP_NUM, 0);
                groupdNoticeItem.setUnreadCount(applyJoinGroupcount);
                int addUserCount        = (int) PreferenceManager.getInstance().getParam(SP.APPLY_ADD_USER_NUM, 0);
                friendNoticeItem.setUnreadCount(addUserCount);
            }
        });
    }
    @Override
    protected void onEventComing(EventCenter center) {
        super.onEventComing(center);
        if (center.getEventCode() == EventUtil.NOTICNUM) {
            refreshApplyLayout();
        } else if (center.getEventCode() == EventUtil.REFRESH_REMARK) {
            refresh();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        toBeProcessUser = (EaseUser) listView.getItemAtPosition(((AdapterView.AdapterContextMenuInfo) menuInfo).position);
        if (toBeProcessUser == null) {
            return;
        }
        toBeProcessUsername = toBeProcessUser.getUsername();
        getActivity().getMenuInflater().inflate(R.menu.em_context_contact_list, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_contact) {
            try {
                // delete contact
                deleteContact(toBeProcessUser);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return super.onContextItemSelected(item);
    }


    /**
     * delete contact
     *
     * @param tobeDeleteUser
     */
    public void deleteContact(final EaseUser tobeDeleteUser) {
        Map<String, Object> map = new HashMap<>();
        map.put("friendUserId", tobeDeleteUser.getUsername().split("-")[0]);
        ApiClient.requestNetHandle(getActivity(), AppConfig.DEL_USER_FRIEND, "正在删除...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {

                try {
                    EMClient.getInstance().contactManager().deleteContact(tobeDeleteUser.getUsername());
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                contactList.remove(tobeDeleteUser);
                contactListLayout.refresh();
            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
            }
        });
    }


    private PopWinShare popWinShare;

    /**
     * 显示浮窗菜单
     */
    private void showPopudown() throws Exception {
        if (popWinShare == null) {
            //自定义的单击事件
            View.OnClickListener paramOnClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == R.id.layout_saoyisao) {
                        Global.addUserOriginType = Constant.ADD_USER_ORIGIN_TYPE_QRCODE;
                        Intent intent = new Intent(getActivity(), CaptureActivity.class);
                        startActivityForResult(intent, REQUEST_CODE);
                    } else if (v.getId() == R.id.layout_add_firend) {
                        startActivity(new Intent(getActivity(), AddContactActivity.class));
                    } else if (v.getId() == R.id.layout_group) {
                        startActivity(new Intent(getActivity(), ContactActivity.class).putExtra("from", "1"));
                    } else if (v.getId() == R.id.layout_my_qr) {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "1");
                        startActivity(MyQrActivity.class, bundle);
                    }

                    popWinShare.dismiss();
                }
            };
            popWinShare = new PopWinShare(getActivity(), paramOnClickListener, (int) DensityUtils.getWidthInPx(getContext()), (int) DensityUtils.getHeightInPx(getContext()) - DensityUtils.dip2px(getContext(), 45) - DensityUtils.statusBarHeight2(getContext()));
            //监听窗口的焦点事件，点击窗口外面则取消显示
            popWinShare.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        popWinShare.dismiss();
                    }
                }
            });
        }
        //设置默认获取焦点
        popWinShare.setFocusable(true);
        //以某个控件的x和y的偏移量位置开始显示窗口
        if (!popWinShare.isShowing()) {
            popWinShare.showAsDropDown(mImgShow, 0, DensityUtils.dip2px(getActivity(), 8));
        }
        //如果窗口存在，则更新
        popWinShare.update();
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
}

/**
 * 作   者：赵大帅
 * 描   述: 好友
 * 日   期: 2017/11/27 9:39
 * 更新日期: 2017/11/27
 */
package com.haoniu.quchat.activity.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.chat.R;
import com.haoniu.quchat.activity.AddContactActivity;
import com.haoniu.quchat.activity.ChatActivity;
import com.haoniu.quchat.activity.ContactActivity;
import com.haoniu.quchat.activity.MyGroupActivity;
import com.haoniu.quchat.activity.NewFriendActivity;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.domain.EaseUser;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.global.Global;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.widget.EaseAlertDialog;
import com.haoniu.quchat.widget.PopWinShare;
import com.haoniu.quchat.widget.SearchBar;
import com.zds.base.Toast.ToastUtil;
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
public class ShareCardIdContactListFragment extends EaseContactListFragment {

    private static final String TAG = ShareCardIdContactListFragment.class.getSimpleName();
    private View loadingView;
    private Bundle mBundle;

    @BindView(R.id.hint)
    TextView mHint;
    @BindView(R.id.search_bar)
    SearchBar searchBar;

    public static final ShareCardIdContactListFragment newInstance(Bundle bundles) {
        ShareCardIdContactListFragment fragment = new ShareCardIdContactListFragment();
        Bundle bundle = new Bundle();
        bundle = bundles;
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ease_fragment_share_cardid_contact_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @SuppressLint("InflateParams")
    @Override
    protected void initLogic() {
        super.initLogic();

        mImgShow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    showPopudown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //add loading view
        loadingView = LayoutInflater.from(getActivity()).inflate(R.layout.em_layout_loading_data, null);
        contentContainer.addView(loadingView);
        registerForContextMenu(listView);

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
                    showShareCardIdDailog(user);
                }
            }
        });

    }


    private void showShareCardIdDailog(EaseUser user) {

        new EaseAlertDialog(getContext(), "确定发给：", user.getNickname(), true, new EaseAlertDialog.AlertDialogUser() {
            @Override
            public void onResult(boolean confirmed, Bundle bundle) {
                if (confirmed) {
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    //发送自己的名片和好友的名片给他人判断
                    intent.putExtra("from", "1");
                    intent.putExtras(mBundle);
                    String username = user.getUsername();
                    // it's single chat
                    intent.putExtra(Constant.EXTRA_USER_ID, username);
                    startActivity(intent);
                    // it's single chat
                }
            }
        }, true).show();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        mBundle = extras;
    }


    @Override
    public void refresh() {
        super.refresh();
    }

    @Override
    protected void onEventComing(EventCenter center) {
        super.onEventComing(center);

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


    private PopWinShare popWinShare;

    /**
     * 显示浮窗菜单
     */
    private void showPopudown() {
        if (popWinShare == null) {
            //自定义的单击事件
            OnClickListener paramOnClickListener = new OnClickListener() {
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
        popWinShare.showAsDropDown(mImgShow, 0, DensityUtils.dip2px(getActivity(), 8));
        //如果窗口存在，则更新
        popWinShare.update();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}

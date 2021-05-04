package com.haoniu.quchat.utils;

import com.haoniu.quchat.entity.GroupDetailInfo;
import com.haoniu.quchat.operate.UserOperateManager;
import com.hyphenate.util.HanziToPinyin;

import java.text.CollationKey;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class SortUtil {
    private static SortUtil instance;

    //方法同步，调用效率低
    public static synchronized SortUtil getInstance() {
        if (instance == null) {
            instance = new SortUtil();
        }
        return instance;
    }

    public List groupUserAlphabetical(List<GroupDetailInfo.GroupUserDetailVoListBean> list) {
        List operatorList = new ArrayList();
        List letterList = new ArrayList();
        List specialList = new ArrayList();
        List combinationList = new ArrayList();
        for (GroupDetailInfo.GroupUserDetailVoListBean bean : list) {

            if (UserOperateManager.getInstance().hasUserName(bean.getUserId())) {
                bean.setUserNickName(UserOperateManager.getInstance().getUserName(bean.getUserId()));
            }

            if (bean.getUserRank().equals("1") || bean.getUserRank().equals("2")) {
                operatorList.add(bean);
                bean.setTop("群主/管理员");
                continue;
            }
            try {
                String spell = HanziToPinyin.getInstance().get(bean.getUserNickName().trim().substring(0, 1)).get(0).target.toUpperCase();
                char c = spell.charAt(0);
                if(c >= 65 && c <= 90 ){//如果是A-Z
                    bean.setTop(c+"");
                    letterList.add(bean);
                }else{
                    bean.setTop("#");//否则设置
                }
            } catch (Exception e) {
                bean.setTop("#");
            }finally {
                if (bean.getTop().equals("#")) {
                    specialList.add(bean);
                }
            }
        }
        Collections.sort(letterList, new Comparator<GroupDetailInfo.GroupUserDetailVoListBean>() {
            Collator collator = Collator.getInstance(Locale.CHINA);

            @Override
            public int compare(GroupDetailInfo.GroupUserDetailVoListBean o1, GroupDetailInfo.GroupUserDetailVoListBean o2) {
                CollationKey key1 = collator.getCollationKey(o1.getTop());
                CollationKey key2 = collator.getCollationKey(o2.getTop());
                if (o1.getTop().equals("#") || o2.getTop().equals("#")) {
                    if (o1.getTop().equals("#") && o2.getTop().equals("#")) {
                        return 0;
                    }
                    if (!o1.getTop().equals("#") && o2.getTop().equals("#")) {
                        return -1;
                    }
                    if (o1.getTop().equals("#") && !o2.getTop().equals("#")) {
                        return 0;
                    }
                }
                return key1.compareTo(key2);
            }
        });

        combinationList.addAll(operatorList);
        combinationList.addAll(letterList);
        combinationList.addAll(specialList);

        operatorList.clear();
        letterList.clear();
        specialList.clear();

        return combinationList;
    }
}

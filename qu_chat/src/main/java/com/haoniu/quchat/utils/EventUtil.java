package com.haoniu.quchat.utils;

/**
 * 作   者：赵大帅
 * 描   述:
 * 邮   箱: 2510722254@qqq.com
 * 日   期: 2017/11/15 15:52
 * 更新日期: 2017/11/15
 */
public class EventUtil {

    //token 失效退出登录
    public static final int LOSETOKEN = 1;

    //支付结果
    public static final int PAYSUCCESS = 2;

    //转账
    public static final int TRANSFERMESSAGE = 3;

    //验证密码修改
    public static final int CLOSE1 = 4;
    //验证密码忘记
    public static final int CLOSE2 = 5;
    //刷新群组
    public static final int FLUSHGROUP = 6;

    //刷新群组
    public static final int FLUSHBANNER = 7;

    //注册 chat
    public static final int REGISTERBUTTON = 8;
    //刷新用户信息
    public static final int FLUSHUSERINFO = 9;
    //刷新公告
    public static final int FLUSHNOTICE = 10;

    /**
     * 授权登成功
     */
    public static final int WXLOGOINSUCCESS = 11;
    /**
     * 授权登录失败
     */
    public static final int WXLOGOINERROR = 12;

    //to rigister
    public static final int TOREGISTER = 13;


    /**
     * 刷新审核列表信息
     */
    public static final int FLUSHAUDIT = 14;

    /**
     * 刷新消息数量
     */
    public static final int UNREADCOUNT = 15;

    /**
     * 刷新系统通知
     */
    public static final int FLUSHXTTZ = 16;
    /**
     * 修改房间名称
     */
    public static final int FLUSHRENAME = 17;

    /**
     * 刷新平台消息数量
     */
    public static final int NOTICNUM = 18;
    /**
     * 刷新审核未读数量
     */
    public static final int APPLYNUMER = 19;

    /**
     * 客服
     */
    public static final int KEFU = 20;

    /**
     * 转账
     */
    public static final int TONGXUNLU = 21;


    /**
     * 刷新通讯录和本地数据库
     */
    public static final int REFRESH_CONTACT = 22;


    /**
     * 修改群昵称后通知群房间刷新
     */
    public static final int REFRESH_GROUP_NAME = 23;


    /**
     * 退出群组或者删除群组通知
     */
    public static final int DEL_EXIT_GROUP = 24;

    /**
     * 修改我的群昵称后通知群房间刷新和群详情
     */
    public static final int REFRESH_MY_GROUP_NAME = 25;


    /**
     * 修改房间信息
     */
    public static final int ROOM_INFO = 101;

    /**
     * 退出房间
     */
    public static final int EXIT_ROOM = 102;

    /**
     * 群主同意
     */
    public static final int AGREE_SUCCESS = 103;

    /**
     * 修改包数
     */
    public static final int UPDATE_PACK = 104;

    /**
     * 设置群管理员
     */
    public static final int SET_GROUP_MANAGE = 105;

    /**
     * 清空聊天本地数据
     */
    public static final int CLEAR_HUISTROY = 106;

    /**
     * 删除联系人退出聊天界面
     */
    public static final int DELETE_CONTACT = 107;

    /**
     * 设置聊天背景
     */
    public static final int SET_CHAT_BG = 108;

    /**
     * 创建群聊成功
     */
    public static final int CREATE_GROUP_SUCCESS = 109;

    /**
     * 修改群头像通知会话刷新
     */
    public static final int REFRESH_CONVERSION = 110;

    /**
     * 邀请好友进群成功
     */
    public static final int INVITE_USER_ADD_GROUP = 111;


    /**
     * 刷新黑名单列表
     */
    public static final int REFRESH_BLACK = 112;
    /**
     * 刷新黑名单列表
     */
    public static final int OPERATE_BLACK = 113;

    /**
     * 刷新备注
     */
    public static final int REFRESH_REMARK = 114;
    /**
     * 删除群成员
     */
    public static final int DEL_GROUP_MEMBER = 115;

    /**
     * 刷新备注
     */
    public static final int REFRESH_MESSAGE_LIST = 116;

    /**
     * 检查多设备登录状态
     */
    public static final int CHECK_MULTI_STATUS = 117;

}

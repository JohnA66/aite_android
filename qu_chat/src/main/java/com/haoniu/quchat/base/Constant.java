package com.haoniu.quchat.base;

/**
 * 作   者：赵大帅
 * 描   述: 全局变量
 * 日   期: 2018/1/2 9:36
 * 更新日期: 2018/1/2
 *
 * @author 赵大帅
 */
public class Constant extends EaseConstant {
    public static final String SEPARATOR_UNDERLINE  = "_";
    public static final String FLAG_QR_GROUP = "group";
    public static final String PREFIX_QR_USER  = "person";

    public static final String FLAG_ADD_GROUP  = "1";

    public static final String PARAM_FOR_AT_MERBER              = "from_at_merber";
    public static final String PARAM_MERBER                     = "member";
    public static final String PARAM_AT_NAME                    = "at_name";
    public static final String PARAM_AT_USERID                  = "at_user_id";
    public static final String PARAM_GROUP_ID                   = "group_id";
    public static final String PARAM_EM_GROUP_ID                = "em_group_id";
    public static final String PARAM_EM_CHAT_ID                 = "em_chat_id";
    public static final String PARAM_STATUS                     = "status";

    public static final int REQUEST_AT_MERBER_CODE = 3000;    //at成员

    /**
     * 环信
     */
    public static final String NEW_FRIENDS_USERNAME = "item_new_friends";
    public static final String GROUP_USERNAME = "item_groups";
    public static final String CHAT_ROOM = "item_chatroom";
    public static final String ACCOUNT_REMOVED = "account_removed";
    public static final String ACCOUNT_CONFLICT = "conflict";
    public static final String ACCOUNT_FORBIDDEN = "user_forbidden";
    public static final String ACCOUNT_KICKED_BY_CHANGE_PASSWORD = "kicked_by_change_password";
    public static final String ACCOUNT_KICKED_BY_OTHER_DEVICE = "kicked_by_another_device";
    public static final String CHAT_ROBOT = "item_robots";
    public static final String ACTION_GROUP_CHANAGED = "action_group_changed";
    public static final String ACTION_CONTACT_CHANAGED = "action_contact_changed";


    /**
     * 申请添加好友动作（环信穿透消息）
     */
    public static final String ACTION_APPLY_ADD_FRIEND = "action_apply_add_friend";

    /**
     * 申请加入群组
     */
    public static final String ACTION_APPLY_JOIN_GROUP = "action_apply_join_group";
    /**
     * 发送个人红包
     */
    public static final String ACTION_ALONE_REDPACKET = "action_send_alone_redPacket";

    /**
     * 抢红包
     */

    public static final String ACTION_RAB = "rab";

    public static final String ACTION_RAB_SELF = "selfRab";

    /**
     * 申请添加好友的id（环信穿透消息）
     */
    public static final String APPLY_ADD_FRIEND_ID = "friend_user_id";


    /**
     * 加密key
     */
    public static final String SMS_ENCRYPT_KEY = "QTTjJhntSqETavDu";
    /**
     * 标识消息已读
     */
    public static final String MSG_READ = "1";
    /**
     * 禁言
     */
    public static final String GROUP_MSG_SET = "1";
    /**
     * 取消禁言
     */
    public static final String GROUP_MSG_SET_NO = "0";

    /**
     * 服务大厅 类型 1、客服2、通知3、公告4、接龙玩法说明 5、踩雷玩法说明
     */
    public static final String CUSTOM_KF = "1";
    public static final String CUSTOM_TZ = "2";
    public static final String CUSTOM_GG = "3";
    public static final String CUSTOM_JL = "4";
    public static final String CUSTOM_CL = "5";

    /**
     * 平台公告未读消息数量
     */
    public static final String UNREADCOUNT = "unReadCount";

    /**
     * 环信使用别名
     */
    public static final String ID_REDPROJECT = "-aite";

    public static String getTitleInv(int Invite) {
        String title = "";
        switch (Invite) {
            case 0:
                title = "一级推荐";
                break;
            case 1:
                title = "二级推荐";
                break;
            case 2:
                title = "三级推荐";
                break;
            case 3:
                title = "四级推荐";
                break;
            case 4:
                title = "五级推荐";
                break;
            case 5:
                title = "六级推荐";
                break;
            case 6:
                title = "七级推荐";
                break;
            case 7:
                title = "八级推荐";
                break;
            case 8:
                title = "九级推荐";
                break;
            case 9:
                title = "十级推荐";
                break;
            case 10:
                title = "十一级推荐";
                break;
            case 11:
                title = "十二级推荐";
                break;
            case 12:
                title = "十三级推荐";
                break;
            case 13:
                title = "十四级推荐";
                break;
            case 14:
                title = "十五级推荐";
                break;
            case 15:
                title = "十六级推荐";
                break;
            case 16:
                title = "十七级推荐";
                break;
            case 17:
                title = "十八级推荐";
                break;
            case 18:
                title = "十九级推荐";
                break;
            case 19:
                title = "二十级推荐";
                break;
            case 20:
                title = "二十一级推荐";
                break;
            default:
        }
        return title;
    }
    // http code
    /**
     * 成功
     */
    public static final int CODESUCCESS = 200;
    /**
     * 请求失败 错误
     */
    public static final int CODEERROR = -1;
    /**
     * token 异常
     */
    public static final int CODETOKENERROR = 401;
    /**
     * 微信授权失败
     */
    public static final int CODEWXERROR = 300;
    /**
     * 请填写注册信息（未注册）
     */
    public static final int CODENORIGISTER = 301;
    //修改房间名称
    public static final String RENAME = "rename";

    /**
     * 自定义消息类型互相欣赏包容理解
     */
    public static final String MSGTYPE = "msgType";

    public static final String INVITER_ID = "inviteId";
    public static final String USER_ID = "userId";
    public static final String DEL_USER_ID = "delUserId";
    public static final String USER_ADDNIKE = "addNickname";
    /**
     * 消息类型 抢包
     */
    public static final String RAB = "rab";
    public static final String RAB_SELF = "selfRab";
    public static final String SURE_TURN = "sureTurn";

    /**
     * 消息类型 发红包
     */
    public static final String REDPACKET = "redpacket";
    /**
     * 红包抢完
     */
    public static final String RABEND = "rabend";
    /**
     * 红包退回
     */
    public static final String RETURNGOLD = "returngold";
    /**
     * 提现通知
     */
    public static final String WITHDRAW = "withdrawal";

    /**
     * 系统公告
     */
    public static final String SYSTEM_NOTICE        = "systematic";
    public static final String SYSTEM_WALLET        = "walletMsg";
    public static final String SYSTEM_GROUP_ADD     = "showAddGroup";
    /**
     * 通知
     */
    public static final String NOTICE = "registerMoney";
    /**
     * 转账
     */
    public static final String TURN = "turn";
    /**
     * 加入房间
     */
    public static final String ADDUSER = "add_user";
    /**
     * 惩罚
     */
    public static final String PUNISHMENT = "punishment";
    /**
     * 福利包
     */
    public static final String WELFARE = "welfare";
    /**
     * 红包退回
     */
    public static final String RETURNGO = "returngo";
    /**
     * 退出房间
     */
    public static final String DELUSER = "deluser";
    /**
     * 踢人
     */
    public static final String SHOTUSER = "shotuser";
    /**
     * 多人加入房间
     */
    public static final String ADDUSERS = "addusers";
    /**
     * 创建房间
     */
    public static final String ADDROOM = "addroom";
    /**
     * 房间头像
     */
    public static final String ROOMURL = "roomUrl";
    /**
     * 充值
     */
    public static final String RECHARGE = "recharge";
    /**
     * 晋级代理
     */
    public static final String PROMOTION = "promotion";


    /**
     * 拓展红包消息字段 红包Id
     */
    public static final String REDPACKETID = "redpacketid";

    /**
     * 拓展字段 好友申请通过或拒绝后的发送的消息字段（用于刷新好友列表）
     */
    public static final String ADD_USER = "adduser";


    /**
     * 拓展字段 删除好友发送的消息字段（用于刷新好友列表）
     */
    public static final String DELETE_USER = "deleteuser";


    /**
     * 头像
     */
    public static final String AVATARURL = "headImg";
//    public static final String AVATARURL_S = "headImg";

    /**
     * 昵称
     */
    public static final String NICKNAME = "nickname";

    /**
     * 好友昵称
     */
    public static final String FRIEND_NICKNAME = "friendName";

    /**
     * 好友头像
     */
    public static final String FRIEND_AVATARURL = "friendImg";

    /**
     * 好友ID
     */
    public static final String FRIEND_USERID = "friendUserId";

    /**
     * 用户在群中的昵称
     */
    public static final String GROUP_NICKNAME = "group_nickname";
    /**
     * 福利包类型
     */
    public static final String WELFARETYPE = "welfareType";

    /**
     * 申请加群通知
     */
    public static final String ADD_GROUP = "addgroup";


    /**
     * 房间类型
     */
    public static final String ROOMTYPE = "roomType";
    /**
     * 踩雷
     */
    public static final int CAILEI = 2;

    /**
     * 系统用户
     */
    public static final String ADMIN = "admin";

    public static final String WALLET = "wallet";
    /**
     * 用户类型
     */
    public static final String SERVICE = "service";
    /**
     * 昵称
     */
    public static final String NAME = "name";
    /**
     * 昵称
     */
    public static final String NICK = "nick";

    /**
     * 个人红包标志
     */
    public static final String PERSON_RED_BAG = "perredbag";


    /**
     * 名片
     */
    public static final String SEND_CARD = "records";


    /**
     * 地图
     */
    public static final String SEND_LOCATION = "send_location";

    /**
     * 微信appId
     */
    public static final String WXAPPID = "xxx";
    /**
     * 微信AppSecret
     */
    public static final String WXAPPSECRET = "64020361b8ec4c99936c0e3999a9f249";

    /**
     * 审核状态
     * 审核中
     */
    public static int AUDITSTATEUNDERREVIEW = 3;
    /**
     * 审核状态
     * 被拒绝
     */
    public static int AUDITSTATEBEREJECTED = 2;
    /**
     * 审核状态
     * 审核通过
     */
    public static int AUDITSTATEPASSED = 1;

    /**
     * 一级代理查询 一级代理下普通会员 手续费
     */
    public static final String REFEREETYPE1 = "1";

    /**
     * 一级代理查询 二级代理及二级代理下普通会员 手续费
     */
    public static final String REFEREETYPE2 = "2";
    /**
     * 二级代理查询 二级代理下普通会员 手续费
     */
    public static final String REFEREETYPE3 = "3";

    /**
     * 崩溃
     */
    public static final String BENGKUI = "bengkui";


    /**
     * 保存通讯录
     */
    public static final String IS_SYNCEDCONTACT = "is_synced_contact";


    /**
     * 添加好友方式
     */
    public static final String ADD_USER_ORIGIN_TYPE_QRCODE	            = "1"; 	//扫描二维码
    public static final String ADD_USER_ORIGIN_TYPE_SEARCH	            = "2"; 	//搜索手机号/艾特号
    public static final String ADD_USER_ORIGIN_TYPE_GROUPCHAT	            = "3"; 	//群聊{originName}
    public static final String ADD_USER_ORIGIN_TYPE_ADDRESS_BOOK	        = "4"; 	//通讯录
    public static final String ADD_USER_ORIGIN_TYPE_RECOMMEND	            = "5"; 	//{originName}推荐名片

    /**
     *  账号操作
     */
    public static final String ACCOUNT_FREEZE = "0";
    public static final String ACCOUNT_THAW = "1";

    /**
     * 授权认证状态
     */

    public static final String AUTHENTICATION_STATUS_LOGIN = "1";
    public static final String AUTHENTICATION_STATUS_REGISTER = "2";

    public static final String AUTHENTICATION_LOGIN_PASSWORD = "1";
    public static final String AUTHENTICATION_LOGIN_SMSCODE = "2";

    /**
     * 商户编号
     */
    public static final String MERCHANT_ID ="897301977";
}

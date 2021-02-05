package com.haoniu.quchat.http;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.aite.chat.BuildConfig;
import com.haoniu.quchat.entity.VersionInfo;
import com.haoniu.quchat.utils.CretinAutoUpdateUtils;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.upDated.interfaces.ForceExitCallBack;
import com.zds.base.upDated.model.UpdateEntity;
import com.zds.base.util.SystemUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局配置
 *
 * @author Administrator
 */
public class AppConfig {
    public static String checkimg(String path) {
        if (path == null) {
            return "";
        }
        if (path.contains("http://") || path.contains("https://")) {
            return path;
        } else {
            return ImageMainUrl + path;
        }
    }

    /**
     * 服务器
     */
    public static final String baseService = BuildConfig.BASEURL;
    /**
     * 主地址
     */
    public static final String mainUrl = baseService + "aiteApi/";

    /**
     * 图片地址
     */
    public static String ImageMainUrl = BuildConfig.BASEURL + "profile/";

    /**
     * 商城地址
     */
    public static final String shopUrl = "http://shopping.tonghua-hn.com/app/index.php?i=1&c=entry&m=ewei_shopv2&do=mobile";

    /**
     * 图片地址
     */
    public static final String ImageMainUrl1 = baseService;

    /**
     * 修改用户昵称
     */
    public static String MODIFY_USER_NICK_NAME = mainUrl + "app_user/modifyUserNickname";

    /**
     * 修改用户昵称
     */
    public static String GET_SYS_CONFIG = mainUrl + "common/getSysConfig";

    /**
     * 修改艾特号
     */
    public static String MODIFY_USER_CODE = mainUrl + "app_user/modifyUserCode";

    /**
     * 修改个性签名
     */
    public static String MODIFY_SELF_LABLE = mainUrl + "app_user/modifyUserSign";


    /**
     * 认证
     */
    public static final String openAccount = mainUrl + "app_user/openAccount/";


    /**
     * 提现
     */
    public static final String withdrawUrl = mainUrl + "app_user_wallet/withdraw";

    /**
     * 提现到银行卡
     */
    public static final String afterWithdraw = mainUrl + "app_user_wallet/afterWithdraw";


    public static final String check_bank = mainUrl + "app_user_wallet/sureBankCard";

    /**
     * 支付密码
     */
    public static final String updateYeepayPayPwd = mainUrl + "app_user_wallet/updateYeepayPayPwd";

    /**
     * 充值
     */
    public static final String rechargeUrl = mainUrl + "app_user_wallet/recharge";
    /**
     * 银行卡
     */
    public static final String queryBankCards = mainUrl + "app_user/queryBankCards";

    /**
     * 用户信息
     */
    public static String USER_INFO = mainUrl + "app_user/findUserDetail";
    /**
     * 提现说明
     */
    public static String getWithdrawExplain = mainUrl + "common/getWithdrawExplain";

    /**
     * 用户头像修改
     */
    public static String MODIFY_USER_HEAD = mainUrl + "app_user/modifyUserHead";


    /**
     * 搜索用户
     */
    public static String FIND_USER = mainUrl + "app_user_friend/findUserByPhoneAndUserCode";


    /**
     * 获取好友详情
     */
    public static String FRIEND_INFO = mainUrl + "app_user_friend/getFriendUser";


    /**
     * 申请加好友
     */
    public static String APPLY_ADD_USER = mainUrl + "app_user_friend/saveUserFriendApply";

    /**
     * 好友申请列表
     */
    public static String APPLY_ADD_USER_LIST = mainUrl + "app_user_friend/findUserFriendApplyVosByPage";


    /**
     * 好友申请同意或者拒绝
     */
    public static String APPLY_ADD_USER_STATUS = mainUrl + "app_user_friend/modifyApplyStatus";

    /**
     * 好友列表
     */
    public static String USER_FRIEND_LIST           = mainUrl + "app_user_friend/findUserFriendVosByPageCache";

    public static String GET_INVITE_USER_LIST       = mainUrl + "app_user_friend/findUserFriendVosByPage";

    public static String CHECK_FRIEND_DATA_VERSION  = mainUrl + "app_user_friend/getUserFriendCacheVersion";

    /**
     * 拉黑好友
     */
    public static String BLACK_USER_FRIEND = mainUrl + "app_user_friend/saveUserToBlacklist";

    /**
     * 黑名单列表
     */
    public static String BLACK_USER_LIST = mainUrl + "app_user_friend/findUserBlacklistVosByPage";


    /**
     * 删除好友
     */
    public static String DEL_USER_FRIEND = mainUrl + "app_user_friend/deleteFriendByFriendUserId";

    /**
     * 通过手机号查询通讯录好友
     */
    public static String LIST_USER_PHONE = mainUrl + "app_user_friend/listUserByPhone";

    /**
     * 新建群
     */
    public static String SAVE_GROUP = mainUrl + "app_group/saveGroup";

    /**
     * 搜索群
     */
    public static String FIND_GROUP = mainUrl + "app_group/findGroupByName";

    /**
     * 删除群
     */
    public static String DEL_GROUP = mainUrl + "app_group/delGroup";

    /**
     * 修改群头像或昵称
     */
    public static String MODIFY_GROUP_NAME_OF_HEAD = mainUrl + "app_group/modifyGroupNameOrHead";


    /**
     * 修改群公告
     */
    public static String MODIFY_GROUP_NOTICE = mainUrl + "app_group/modifyGroupNotice";

    /**
     * 设置群管理员/撤销管理员
     */
    public static String MODIFY_GROUP_MANEGER = mainUrl + "app_group/modifyGroupUserRankToMange";

    /**
     * 禁言/取消禁言
     */
    public static String MODIFY_GROUP_USER_SAY_STATUS = mainUrl + "app_group/modifyGroupUserSayStatus";

    /**
     * 禁言/取消禁言(全言禁言)
     */
    public static String MODIFY_GROUP_ALL_USER_SAY_STATUS = mainUrl + "app_group/modifyGroupSayFlag";

    /**
     * 邀请新成员入群
     */
    public static String SAVE_GROUP_USER = mainUrl + "app_group/saveGroupUser";

    /**
     * 查询群里有哪些好友
     */
    public static String GET_USER_IN_GROUP = mainUrl + "app_user_friend/getUserFriendInGroup";

    /**
     * 我的群组列表
     */
    public static String MY_GROUP_LIST = mainUrl + "app_group/listGroupByUserId";

    /**
     * 我的群组好友
     */
    public static String MY_GROUP_LIST_GOODS_FRIEND = mainUrl + "app_group/listGroupFriendUserByGroupId";

    /**
     * 修改我的群昵称
     */
    public static String MODIFY_USER_GROUP_NICKNAME = mainUrl + "app_group/modifyGroupUserNickName";

    /**
     * 移出群员
     */
    public static String DEL_GROUP_USER = mainUrl + "app_group/delGroupUser";


    /**
     * 群详情
     */
    public static String GROUP_DETAIL = mainUrl + "app_group/groupDetail";

    public static String CHECK_GROUP_DATA_VERSION = mainUrl + "app_group/getGroupDetailCacheVersion";

    public static String GET_GROUP_DETAIL = mainUrl + "app_group/groupDetailCache";

    public static String GET_GROUP_INFO = mainUrl + "app_group/groupInfo";

    /**
     * 群详情用户能否查看之间的详情
     */
    public static String GROUP_DETAIL_READ_USER_DETAIL = mainUrl + "app_group/modifySeeFriendFlag";

    /**
     * 通过环信id获取群组id
     */
    public static String GET_GROUP_ID = mainUrl + "app_group/getGroupId";


    /**
     * 发现列表
     */
    public static String GET_FIND_LIST = mainUrl + "app_news/findAllNews";


    /**
     * 发现列表
     */
    public static String GET_FIND_LIST_NEW = mainUrl + "find/all";

    /**
     * 举报
     */
    public static String SAVE_REPORT = mainUrl + "app_report/saveReport";

    /**
     * 转账记录
     */
    public static String TRANSFER_RECORD = mainUrl + "app_user_transfer/findAllTransfer";


    /**
     * 新闻资讯详情
     */
    public static String FIND_NEW_BY_ID = mainUrl + "app_news/findOneNewsById";

    /**
     * 推荐阅读
     */
    public static String FIND_PUSH_NEWS = mainUrl + "app_news/findPushNews";


    /**
     * 充值记录
     */
    public static String GET_RECHARGE_RECORD = mainUrl + "app_user_wallet/listUserWalletRecharge";

    /**
     * 提现记录
     */
    public static String GET_WITHDRAAW_RECORD = mainUrl + "app_user_wallet/listUserWalletWithdraw";

    /**
     * 新建红包
     */
    public static String CREATE_RED_PACKE = mainUrl + "app_red_packet/createRedPacket";

    /**
     * 个人红包
     */
    public static String CREATE_PERSON_RED_PACKE = mainUrl + "app_red_packet/createAloneRedPacket";

    /**
     * 红包记录
     */
    public static String RED_PACK_RECORD = mainUrl + "app_red_packet/listRedPacketLog";

    /**
     * 获取未领取红包
     */
    public static String NO_RED_PACK_RECORD = mainUrl + "app_red_packet/listNoRobRedPackets";


    /**
     * 红包统计
     */
    public static String RED_PACK_TOTAL = mainUrl + "app_red_packet/totalRedPacket";

    /**
     * 客服列表
     */
    public static String CUSTOM_LIST = mainUrl + "app_user/listCustomerUser";

    /**
     * 客服问题展示
     */
    public static String CUSTOM_QUESTIONS = mainUrl + "app_customer/all";

    /**
     * 配置
     */
    public static final String peizhi = mainUrl + "common/getConfig";
    /**
     * 图形验证码
     */
    public static final String tuxingCode = mainUrl + "common/getCaptcha";

    /**
     * 获取验证码
     */
    public static String getPhoneCodeUrl = mainUrl + "app_user/sendRegisterCode";


    /**
     * 发送忘记支付密码短信验证码
     */
    public static String sendForgetPayPwdCode = mainUrl + "app_user_wallet/sendForgetPayPwdCode";

    /**
     * 获取验证码
     */
    public static String getForgetPhoneCodeUrl = mainUrl + "app_user/sendForgetPwdCode";
    /**
     * 获取登录验证码
     */
    public static String getSMSCodeForLogin = mainUrl + "app_user/sendLoginCode";

    /**
     * 获取冻结接口验证码
     */
    public static String getAccountFrozenSMSCode = mainUrl + "app_user/sendFrozenAccountCode";

    /**
     * 冻结账号
     */
    public static String frozenAccount = mainUrl + "app_user/frozenAccount";

    /**
     * 获取解冻接口验证码
     */
    public static String getAccountThawSMSCode = mainUrl + "app_user/sendThawAccountCode";

    /**
     * 解冻账号
     */
    public static String thawAccount = mainUrl + "app_user/thawAccountCode";

    /**
     * 获取房间列表
     */
    public static String getRoomList = mainUrl + "room/list";


    /**
     * 待审核列表
     */
    public static String applyRoomList = mainUrl + "room/applyRoomList";

    /**
     * 获取搜索群信息
     */
    public static String searchGroup = mainUrl + "room/selectRoom";
    /**
     * 获取搜索群信息
     */
    public static String updateRoomMsg = mainUrl + "room/updateRoomMsg";

    /**
     * 发起申请加群
     */
    public static String applyToRoom = mainUrl + "room/applyToRoom";

    /**
     * 群主审核
     */
    public static String groupApply = mainUrl + "room/groupApply";

    /**
     * 登录
     */
    public static String toLoginUrl             = mainUrl + "app_user/login";
    public static String multiLogin             = mainUrl + "app_device/multiDeviceLogin";
    public static String getDeviceList          = mainUrl + "app_device/getDevList";
    public static String isSingleDevice          = mainUrl + "app_device/isSingleDevice";
    public static String sendMultiDeviceCode    = mainUrl + "app_device/sendMultiDeviceCode";
    public static String openMultiDevice        = mainUrl + "app_device/openMultiDevice";
    public static String stopMultiDevice        = mainUrl + "app_device/stopMultiDevice";
    public static String isOpenMultiDevice      = mainUrl + "app_device/isOpenMultiDevice";
    public static String multiDeviceLogout      = mainUrl + "app_device/multiDeviceLogout";
    /**
     * 验证码登录
     */

    public static String toSMSMultiLoginUrl = mainUrl + "app_device/smsMultiDeviceLogin";
    /**
     * 注册
     */
    public static String toRegister = mainUrl + "app_user/register";
    /**
     * 加入房间
     */
    public static String AddRoom = mainUrl + "room/addUserToRoom";
    /**
     * 平台公告
     */
    public static String PlatformAnnouncement = mainUrl + "sysNotice/list";
    /**
     * 房间详情
     */
    public static String getRoomDetail = mainUrl + "room/get";

    /**
     * 获取当前群主所有的群
     */
    public static String listGroup = mainUrl + "room/listGroup";

    /**
     * 群主收益
     */
    public static String groupOwnerIncome = mainUrl + "room/groupOwnerIncome";

    /**
     * 群公告
     */
    public static String groupNotice = mainUrl + "room/getRoomNotice";

    /**
     * banner
     */
    public static final String getBannerUrl = mainUrl + "common/listBanner";


    /**
     * 发踩雷红包
     */
    public static String sendCLRedEnvelope = mainUrl + "redPacket/createMineRedPacket";

    /**
     * 编辑群发包数和赔率
     */
    public static String groupPackageOdds = mainUrl + "room/updateRoomAmout";
    /**
     * 添加支付密码
     */
    public static String addPayPassword = mainUrl + "app_user_wallet/saveNewPayPassword";
    /**
     * 修改支付密码
     */
    public static String upPassword = mainUrl + "app_user_wallet/modifyPayPwd";

    /**
     * 检测红包
     */
    public static String getRedEnvelopeState = mainUrl + "app_red_packet/isRobRedPacket";
    /**
     * 抢红包
     */
    public static String grabRedEnvelope = mainUrl + "app_red_packet/robRedPacket";


    /**
     * 从本地缓存获取红包详情
     */
    public static String getRedPacket = mainUrl + "app_red_packet/getRedPacket";
    /**
     * 从数据库获取红包详情
     */
    public static String getRedPacketFromDB = mainUrl + "app_red_packet/getRedPacketFromDB";

    /**
     * 代理流水
     */
    public static String dlls = mainUrl + "award/listAward";
    /**
     * 首页 banner
     */
    public static String bannerUrl = mainUrl + "api/carouselFigure/list";

    /**
     * 月总收益
     */
    public static final String monthMoney = mainUrl + "award/getTodayAndMonthAward";
    /**
     * 刷新token
     */
    public static String refreshToken = mainUrl + "api/frontBase/userThirdLogin/refreshLogin";

    /**
     * 玩法说明
     */
    public static String howToPlay = mainUrl + "api/frontBase/user/howToPlay?type=";

    /**
     * 新_玩法说明
     */
    public static String howToPlayNew = mainUrl + "/h5/user/howToPlay?type=1";

    /**
     * 获取用户信息
     */
    public static String getUserByPhone = mainUrl + "api/frontBase/user/getUserByPhone";

    /**
     * 获取用户信息(新)
     */
    public static String getUserByInvite = mainUrl + "userFriend/searchUser";

    /**
     * 修改密码
     */
    public static String up_Password = mainUrl + "api/frontBase/user/updatePassword";


    /**
     * 忘记密码
     */
    public static String forgetpasswordUrl = mainUrl + "app_user/forgetPwd";


    /**
     * 创建房间
     */
    public static String creatRoom = mainUrl + "api/redPacket/room/create";

    /**
     * 修改房间信息
     */
    public static String upRoom = mainUrl + "api/redPacket/room/updateRoom";

    /**
     * 用户信息
     */
    public static String UserInfo = mainUrl + "user/getUserInfo";

    /**
     * 获取房间账户余额
     */
    public static String ROOM_MONEY = mainUrl + "room/getRoomMoney";

    /**
     * 退出房间
     */
    public static final String layoutRoom = mainUrl + "room/deleteUserFromGroup";

    /**
     * 获取群所有成员信息
     */
    public static final String roomAllUser = mainUrl + "room/getRoomAllUser";

    /**
     * 获取群所有成员信息
     */
    public static final String updateAutoRob = mainUrl + "room/updateAutoRob";
    /**
     * 充值记录
     */
    public static String BalancePayments = mainUrl + "walletRecord/listUserWalletIn";

    /**
     * 忘记支付密码
     */
    public static String forgetPassword = mainUrl + "app_user_wallet/forgetPayPwd";

    /**
     * 收藏列表
     */
    public static String CollectList = mainUrl + "app_user/listCollect";

    /**
     * 收藏
     */
    public static String addCollect = mainUrl + "app_user/saveCollect";

    /**
     * 取消收藏
     */
    public static String CancelCollect = mainUrl + "app_user/delCollect";


    /**
     * 银行卡列表
     */
    public static String bankCardList = mainUrl + "app_user_wallet/findBankCardVosByPage";

    /**
     * 保存银行卡
     */
    public static String addBankCardList = mainUrl + "app_user_wallet/saveBankCard";

    /**
     * 提现银行卡
     */
    public static String withdrawBankCard = mainUrl + "app_user_wallet/withdraw";

    /**
     * 移除银行卡
     */
    public static String removeBankCardList = mainUrl + "app_user_wallet/cancelBindBankCard";

    /**
     * 提现记录
     */
    public static final String tixianjil = mainUrl + "walletRecord/listUserWalletOut";
    /**
     * 修改昵称
     */
    public static String upDataNickName = mainUrl + "user/updateNickName";

    /**
     * 联系客服
     */
    public static String getCustemUrl = mainUrl + "common/getCustemUrl";

    /**
     * 更新用户真实姓名
     */
    public static final String upDataRealName = mainUrl + "user/updateRealName";

    /**
     * 更新头像
     */
    public static String upHead = mainUrl + "user/uploadAvatar";


    /**
     * 更新群头像
     */
    public static String groupUpHead = mainUrl + "common/uploadImg";

    /**
     * 退出群组
     */
    public static String exitGroup = mainUrl + "app_group/exitGroup";


    /**
     * 群管理员列表
     */
    public static String LIST_GROUP_MANAGE = mainUrl + "app_group/listMangerByGroupId";


    /**
     * 更换手机号
     */
    public static String upPhone = mainUrl + "api/frontBase/user/updatePhone";

    /**
     * 加好友
     */
    public static String addFriend = mainUrl + "api/frontBase/userFriend/saveUserFriend";

    /**
     * 加好友(新)
     */
    public static String addFriend_NEW = mainUrl + "userFriend/addFriend";

    /**
     * 删除好友
     */
    public static String delFriend = mainUrl + "api/frontBase/userFriend/deleteUserFriend";

    /**
     * 更改密码
     */
    public static String upDataPassword = mainUrl + "user/updatePassword";


    /**
     * 群组踢人
     */
    public static String RemoveRoom = mainUrl + "api/redPacket/room/shotUserFromGroup";

    /**
     * 新_群组踢人
     */
    public static String NeWRemoveRoom = mainUrl + "room/kickRoomUser";
    /**
     * 群主解散房间
     */
    public static String deleteGroup = mainUrl + "api/redPacket/room/deleteGroup";

    /**
     * 发个人红包
     */
    public static String sendPerRedEnvelope = mainUrl + "api/redPacket/redPacket/createGroupChatRedPacket";

    /**
     * 发接龙红包
     */
    public static String sendDZRedEnvelope = mainUrl + "api/redPacket/redPacket/createFightRedPacket";


    /**
     * 我的下级
     */
    public static final String xiaji = mainUrl + "user/listUserInvite";

    /**
     * 我的好友
     */
    public static final String FRIEND_LIST = mainUrl + "userFriend/listFriend";

    /**


    /**
     * 签到
     */
    public static String signIn = mainUrl + "api/frontBase/userSign/create";
    /**
     * 历史签到
     */
    public static String getSign = mainUrl + "api/frontBase/userSign/list";

    /**
     * 查询代理下会员
     */
    public static String getDlList = mainUrl + "user/listUserInvite";

    /**
     * 字典
     */
    public static String getZD = mainUrl + "api/dictionary/listByParentCode";
    /**
     * 单个配置
     */
    public static String getOneZD = mainUrl + "api/dictionary/getByParentAndItem";
    /**
     * 获取敏感词汇
     */
    public static String getMGCH = mainUrl + "api/frontBase/wordInfo/list";
    /**
     * 添加绑定支付宝
     */
    public static String addAliPay = mainUrl + "api/frontBase/user/updateAlipay";
    /**
     * 添加绑定银行卡
     */
    public static String addBank = mainUrl + "wallet/updateAccount";

    /**
     * 查询银行卡信息或者支付宝信息
     */
    public static final String bankInfo = mainUrl + "wallet/getAccount";

    /**
     * 充值
     */
    public static String getRecharge = mainUrl + "app_user_wallet/recharge";

    /**
     * 趣币流水
     */
    public static String getQBLS = mainUrl + "api/frontBase/userMoney/page";
    /**
     * 收入
     */
    public static String getMOney = mainUrl + "api/frontBase/userMoney/get";

    /**
     * 排行榜
     */
    public static String getBank = mainUrl + "api/frontBase/userCenter/pageFunMoneyRank";
    /**
     * 排行榜（自己）
     */
    public static String getBankMine = mainUrl + "api/frontBase/userCenter/getUserCenter";

    /**
     * 转账
     */
    public static String transfer = mainUrl + "app_user_transfer/expend";

    /**
     * 转账记录
     */
    public static String transferRecord = mainUrl + "walletRecord/listTransferRecord";

    /**
     * 积分列表
     */
    public static final String scoreList = mainUrl + "api/frontBase/exchange/page";


    /**
     * 房间加人
     */
    public static String addGroupPeople = mainUrl + "api/redPacket/room/addFriendToGroup";

    /**
     * 积分兑换
     */
    public static String creditChangeMoney = mainUrl + "api/frontBase/exchange/exchange";
    /**
     * 积分兑换记录
     */
    public static String exchangeCrediRecords = mainUrl + "api/frontBase/exchange/pageDetail";

    /**
     * 红包流水
     */
    public static String qhbls = mainUrl + "redPacket/listRedPacketLog";

    /**
     * 二维码
     */
    public static String QR = "?inviteCode=";

    /**
     * 晋升代理
     */
    public static String promote = mainUrl + "api/frontBase/exchange/promote";
    /**
     * 关于我们
     */
    public static String about = mainUrl + "api/frontBase/user/aboutOur";
    /**
     * 我的会员总数
     */
    public static String memberCount = mainUrl + "api/frontBase/user/getCountByRecommend";

    /**
     * 盈亏
     */
    public static String ykUrl = mainUrl + "api/frontBase/userMoney/profitLoss";

    /**
     * 客服
     */
    public static String serviceUrl = mainUrl + "customerServe/list";

    /**
     * 审核
     */
    public static String review = mainUrl + "api/frontBase/userProxyDetail/audit";

    /**
     * 申请代理
     */
    public static String apply = mainUrl + "api/frontBase/userProxyDetail/apply";

    /**
     * 申请记录(审核记录)
     */
    public static String applyRecord = mainUrl + "api/frontBase/userProxyDetail/page";
    /**
     * 投诉意见
     */
    public static String complaints = mainUrl + "api/frontBase/complaintProposal/save";
    /**
     * 搜索
     */
    public static String search = mainUrl + "api/redPacket/room/getByRoomId";

    /**
     * 平台公告未读消息count
     */
    public static String unReadCount = mainUrl + "api/frontBase/noticeInfo/nonRead";
    /**
     * 平台公告设置为已读
     */
    public static String updateStatus = mainUrl + "api/frontBase/noticeInfo/updateNumStatus";
    /**
     * 未审核数量
     */
    public static String ApplyNumber = mainUrl + "api/frontBase/userProxyDetail/getCount";


    /**
     * 进群用户申请列表
     */
    public static String FIND_APPLY_GROUP_USER = mainUrl + "app_group/findApplyGroupUser";
    /**
     * 删除用户申请数据
     */
    public static String DEL_APPLY_GROUP_USER = mainUrl + "app_group/delApplyGroupUser";

    public static String DEL_APPLY_ADD_USER = mainUrl + "app_user_friend/delUserFriendApply";

    /**
     * 同意入群
     */
    public static String AGREE_GROUP_USER = mainUrl + "app_group/agreeGroupUser";


    /**
     * 快付通获取accesstoken
     */
    public static String accessTokenKft = mainUrl + "kftPay/accessToken";

    /**
     * 快付通获取预订单号
     */
    public static String accessCommonTradeKft = mainUrl + "kftPay/commonTrade";


    /**
     * 快付通获取充值，转账等预订单号
     */
    public static String accessPreTradeKft = mainUrl + "kftPay/transTrade";

    /**
     * 检查版本号
     */
    public static String checkVersion = mainUrl + "common/versionUpdate";


    /**
     * 消息免打扰设置
     */
    public static String getMessageSet = mainUrl + "api/redPacket/roomMessage/getAll";
    /**
     * 提现申请
     */
    public static String withdraw = mainUrl + "wallet/withdraw";
    /**
     * 更新群消息设置
     */
    public static String upMessageSet = mainUrl + "api/redPacket/roomMessage/update";
    /**
     * http://www.weaigu.com
     */
    public static String downloadUrl = "https://fir.im/flyingfish";


    public static String register_agree = mainUrl + "xieyi/web/register_agree.html";

    /**
     * 设置好友昵称
     */
    public static String ADD_GOODS_FRIEND_REMARK = mainUrl + "app_user_friend/modifyFriendNickName";

    /**
     * 转账状态
     */
    public static String TRANSFER_STATUS = mainUrl + "app_user_transfer/getExpendStatus";

    /**
     * 获取禁言状态
     */
    public static String WHETHER_THE_SILENCE = mainUrl + "app_group/getGroupUserSayStatus";

    /**
     * 确定转账
     */
    public static String CONFIRM = mainUrl + "app_user_transfer/sureExpend";

    /**
     * 温馨提示
     */
    public static String TIP = mainUrl + "common/getReminder";


    /**
     * 零钱锁状态
     */
    public static String walletLockStatus = mainUrl + "app_user_wallet/isPayLock";
    /**
     * 获取零钱锁验证码
     */
    public static String walletLockGetCode = mainUrl + "app_user_wallet/sendPayLockCode";
    /**
     * 零钱锁开启
     */
    public static String walletLockOpen = mainUrl + "app_user_wallet/setPayLock";
    /**
     * 零钱锁关闭
     */
    public static String walletLockClose = mainUrl + "app_user_wallet/delPayLock";

    /**
     * 检查版本
     */
    public static void checkVersion(final Context context, boolean isinge) {
        if (isinge) {
            CretinAutoUpdateUtils.getInstance(context).check(new ForceExitCallBack() {
                @Override
                public void exit() {
                    ((Activity) context).finish();
                }

                @Override
                public void isHaveVersion(boolean isHave) {

                }

                @Override
                public void cancel() {

                }
            });
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("type", "Android");
            ApiClient.requestNetHandle(context, checkVersion, "正在检测...", map, new ResultListener() {
                @Override
                public void onSuccess(String json, String msg) {
                    final VersionInfo versionInfo = FastJsonUtil.getObject(json, VersionInfo.class);
                    if (versionInfo != null) {
                        if (versionInfo.getVersionCodes() > SystemUtil.getAppVersionNumber()) {
                            new AlertDialog.Builder(context).setTitle("新版本").setMessage(versionInfo.getUpdateLogs()).setPositiveButton("更新", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface anInterface, int i) {
                                    UpdateEntity updateEntity = new UpdateEntity();
                                    updateEntity.setVersionCode(versionInfo.getVersionCodes());
                                    updateEntity.setIsForceUpdate(versionInfo.getIsForceUpdates());
                                    updateEntity.setPreBaselineCode(versionInfo.getPreBaselineCodes());
                                    updateEntity.setVersionName(versionInfo.getVersionNames());
                                    updateEntity.setDownurl(versionInfo.getDownurls());
                                    updateEntity.setUpdateLog(versionInfo.getUpdateLogs());
                                    updateEntity.setSize(versionInfo.getApkSizes());
                                    updateEntity.setHasAffectCodes(versionInfo.getHasAffectCodess());
                                    UpdateEntity var8 = updateEntity;
                                    CretinAutoUpdateUtils.getInstance(context).startUpdate(var8);
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface anInterface, int i) {
                                    anInterface.dismiss();
                                }
                            }).show();

                        } else {
                            ToastUtil.toast("当前已是最新版本");
                        }

                    } else {
                        ToastUtil.toast("请求数据失败");
                    }
                }

                @Override
                public void onFailure(String msg) {
                    ToastUtil.toast(msg);
                }
            });
        }
    }

}

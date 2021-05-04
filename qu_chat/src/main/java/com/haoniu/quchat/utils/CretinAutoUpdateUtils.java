package com.haoniu.quchat.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.chat.R;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.ResultListener;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.upDated.interfaces.ForceExitCallBack;
import com.zds.base.upDated.model.LibraryUpdateEntity;
import com.zds.base.upDated.model.UpdateEntity;
import com.zds.base.upDated.utils.DownloadReceiver;
import com.zds.base.upDated.utils.DownloadService;
import com.zds.base.upDated.view.ProgressView;
import com.zds.base.util.SystemUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by cretin on 2017/3/13.
 */

public class CretinAutoUpdateUtils {
    //广播接受者
    private static MyReceiver receiver;
    private static CretinAutoUpdateUtils cretinAutoUpdateUtils;

    //定义一个展示下载进度的进度条
    private static ProgressDialog progressDialog;

    private static Context mContext;

    private static ForceExitCallBack forceCallBack;
    //检查更新的url
    private static String checkUrl;

    //展示下载进度的方式 对话框模式 通知栏进度条模式
    private static int showType = Builder.TYPE_DIALOG;
    //是否展示忽略此版本的选项 默认开启
    private static boolean canIgnoreThisVersion = true;
    //app图标
    private static int iconRes;
    //appName
    private static String appName;
    //是否开启日志输出
    private static boolean showLog = true;
    //自定义Bean类
    private static Object cls;
    //设置请求方式
    private static int requestMethod = Builder.METHOD_POST;

    //自定义对话框的所有控件的引用
    private static AlertDialog showAndDownDialog;
    private static AlertDialog showAndBackDownDialog;

    //绿色可爱型
    private static TextView showAndDownTvMsg;
    private static TextView showAndDownTvBtn1;
    private static TextView showAndDownTvBtn2;
    private static TextView showAndDownTvTitle;
    private static LinearLayout showAndDownLlProgress;
    private static ImageView showAndDownIvClose;
    private static ProgressView showAndDownUpdateProView;

    //前台展示后台下载
    private static TextView showAndBackDownMsg;
    private static ImageView showAndBackDownClose;
    private static TextView showAndBackDownUpdate;

    //私有化构造方法
    private CretinAutoUpdateUtils() {

    }

    /**
     * 检查更新
     */
    public void check() {
        if (TextUtils.isEmpty(checkUrl)) {
            throw new RuntimeException("checkUrl is null. You must call init before using the cretin checking library.");
        } else {
            initUp();
        }
    }

    /**
     * 检查更新
     */
    public void check(ForceExitCallBack forceCallBack) {
        CretinAutoUpdateUtils.forceCallBack = forceCallBack;
        if (TextUtils.isEmpty(checkUrl)) {
            throw new RuntimeException("checkUrl is null. You must call init before using the cretin checking library.");
        } else {
            initUp();
        }
    }

    /**
     * 初始化url
     *
     * @param url
     */
    public static void init(String url) {
        checkUrl = url;
    }

    /**
     * 初始化url
     *
     * @param builder
     */
    public static void init(Builder builder) {
        checkUrl = builder.baseUrl;
        showType = builder.showType;
        canIgnoreThisVersion = builder.canIgnoreThisVersion;
        iconRes = builder.iconRes;
        showLog = builder.showLog;
        requestMethod = builder.requestMethod;
        cls = builder.cls;
    }

    /**
     * getInstance()
     *
     * @param context
     * @return
     */
    public static CretinAutoUpdateUtils getInstance(Context context) {
        mContext = context;
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.MY_RECEIVER");
        //注册
        context.registerReceiver(receiver, filter);
        requestPermission(null);
        if (cretinAutoUpdateUtils == null) {
            cretinAutoUpdateUtils = new CretinAutoUpdateUtils();
        }
        return cretinAutoUpdateUtils;
    }


    /**
     * 取消广播的注册
     */
    public void destroy() {
        //不要忘了这一步
        if (mContext != null && intent != null)
            mContext.stopService(intent);
        if (mContext != null && receiver != null)
            mContext.unregisterReceiver(receiver);
    }

    private void initUp() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", "Android");
        map.put("version", SystemUtil.getAppVersionName());
        ApiClient.requestNetHandle(mContext, checkUrl, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (cls != null) {
                    if (cls instanceof LibraryUpdateEntity) {
                        LibraryUpdateEntity o = (LibraryUpdateEntity)
                                FastJsonUtil.getObject(json, cls.getClass());//反序列化
                        UpdateEntity updateEntity = new UpdateEntity();
                        updateEntity.setVersionCode(o.getVersionCodes());
                        updateEntity.setIsForceUpdate(o.getIsForceUpdates());
                        updateEntity.setPreBaselineCode(o.getPreBaselineCodes());
                        updateEntity.setVersionName(o.getVersionNames());
                        updateEntity.setDownurl(o.getDownurls());
                        updateEntity.setUpdateLog(o.getUpdateLogs());
                        updateEntity.setSize(o.getApkSizes());
                        updateEntity.setHasAffectCodes(o.getHasAffectCodess());
                        updateEntity.setIsGrade(o.isGrade());
                        up(updateEntity);
                    }
                }

            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

    private void up(UpdateEntity data) {
        try {
            if (data != null) {
                if (0 == data.getIsGrade()) {//不升级
                    if (forceCallBack != null) {
                        forceCallBack.isHaveVersion(false);
                    }
                    return;
                }
                if (forceCallBack != null) {
                    forceCallBack.isHaveVersion(true);
                }
                if (data.isForceUpdate == 2) {
                    //所有旧版本强制更新
                    showUpdateDialog(data, true, false);
                } else {
                    showUpdateDialog(data, false, true);
                }
            } else {
                //Toast.makeText(mContext, "网络错误", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

        }
    }

    /**
     * 显示更新对话框
     *
     * @param data
     */
    private void showUpdateDialog(final UpdateEntity data, final boolean isForceUpdate, boolean showIgnore) {
        if (showType == Builder.TYPE_DIALOG || showType == Builder.TYPE_NITIFICATION) {
            //简约式对话框展示对话信息的方式
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            if (isForceUpdate) {
                builder.setCancelable(false);
            }
            AlertDialog alertDialog = builder.create();
            String updateLog = data.updateLog;
            if (TextUtils.isEmpty(updateLog)) {
                updateLog = "新版本，欢迎更新";
            }
            String versionName = data.versionName;
            if (TextUtils.isEmpty(versionName)) {
                versionName = "1.0";
            }
            alertDialog.setTitle("新版本v" + versionName);
            alertDialog.setMessage(updateLog);
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (isForceUpdate) {
                        if (forceCallBack != null) {
                            forceCallBack.exit();
                        }
                    } else {
                        if (forceCallBack != null) {
                            forceCallBack.cancel();
                        }
                    }
                }
            });
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    startUpdate(data);
                    openBrowser(mContext, data.downurl);
//                    mContext.startActivity(new Intent(mContext, WebViewActivity.class).putExtra("title", "lan").putExtra("url",  data.downurl));
                }
            });
            if (canIgnoreThisVersion && showIgnore) {
                final String finalVersionName = versionName;
                alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "忽略此版本", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //忽略此版本
                        List listCodes = loadArray();
                        if (listCodes != null) {
                            listCodes.add(finalVersionName);
                        } else {
                            listCodes = new ArrayList();
                            listCodes.add(finalVersionName);
                        }
                        saveArray(listCodes);
                        Toast.makeText(mContext, "此版本已忽略", Toast.LENGTH_SHORT).show();
                        if (forceCallBack != null) {
                            forceCallBack.cancel();
                        }
                    }
                });
            }
            if (isForceUpdate) {
                alertDialog.setCancelable(false);
            }
            alertDialog.show();
            ((TextView) alertDialog.findViewById(android.R.id.message)).setLineSpacing(5, 1);
            Button btnPositive =
                    alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            Button btnNegative =
                    alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            btnNegative.setTextColor(Color.parseColor("#16b2f5"));
            if (canIgnoreThisVersion && showIgnore) {
                Button btnNeutral =
                        alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL);
                btnNeutral.setTextColor(Color.parseColor("#16b2f5"));
            }
            btnPositive.setTextColor(Color.parseColor("#16b2f5"));
        } else if (showType == Builder.TYPE_DIALOG_WITH_PROGRESS) {
            //在一个对话框中展示信息和下载进度
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.dialog);
            if (isForceUpdate) {
                builder.setCancelable(false);
            }
            View view = View.inflate(mContext, R.layout.download_dialog, null);
            builder.setView(view);
            showAndDownTvBtn1 = (TextView) view.findViewById(R.id.tv_btn1);
            showAndDownTvBtn2 = (TextView) view.findViewById(R.id.tv_btn2);
            showAndDownTvTitle = (TextView) view.findViewById(R.id.tv_title);
            showAndDownTvMsg = (TextView) view.findViewById(R.id.tv_msg);
            showAndDownIvClose = (ImageView) view.findViewById(R.id.iv_close);
            showAndDownLlProgress = (LinearLayout) view.findViewById(R.id.ll_progress);
            showAndDownUpdateProView = (ProgressView) showAndDownLlProgress.findViewById(R.id.progressView);
            String updateLog = data.updateLog;
            if (TextUtils.isEmpty(updateLog))
                updateLog = "新版本，欢迎更新";
            showAndDownTvMsg.setText(updateLog);
            showAndDownDialog = builder.show();

            showAndDownTvBtn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String btnStr = showAndDownTvBtn2.getText().toString();
                    if (btnStr.equals("立即更新")) {
                        //点更新
//                        showAndDownTvMsg.setVisibility(View.GONE);
//                        showAndDownLlProgress.setVisibility(View.VISIBLE);
//                        showAndDownTvTitle.setText("正在更新...");
//                        showAndDownTvBtn2.setText("取消更新");
//                        showAndDownTvBtn1.setText("隐藏窗口");
//                        showAndDownIvClose.setVisibility(View.GONE);
//                        startUpdate(data);

                        openBrowser(mContext, data.downurl);
//                        mContext.startActivity(new Intent(mContext, WebViewActivity.class).putExtra("title", "lan").putExtra("url", data.downurl));
                        showAndDownDialog.dismiss();
                    } else {
                        if (forceCallBack != null) {
                            forceCallBack.cancel();
                        }
                        //点取消更新
                        showAndDownDialog.dismiss();
                        //取消更新 ？
                        destroy();
                    }
                }
            });

            showAndDownTvBtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String btnStr = showAndDownTvBtn1.getText().toString();
                    if (btnStr.equals("下次再说") || btnStr.equals("退出")) {
                        //点下次再说
                        if (isForceUpdate) {
                            if (forceCallBack != null)
                                forceCallBack.exit();
                        } else {
                            if (forceCallBack != null) {
                                forceCallBack.cancel();
                            }
                            showAndDownDialog.dismiss();
                        }
                    } else if (btnStr.equals("隐藏窗口")) {
                        //点隐藏窗口
                        showAndDownDialog.dismiss();
                        if (forceCallBack != null) {
                            forceCallBack.cancel();
                        }
                    }
                }
            });

            showAndDownIvClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //关闭按钮
                    showAndDownDialog.dismiss();
                    if (isForceUpdate) {
                        if (forceCallBack != null) {
                            forceCallBack.exit();
                        }
                    } else {
                        if (forceCallBack != null) {
                            forceCallBack.cancel();
                        }
                    }
                }
            });

            if (isForceUpdate) {
                //强制更新
                showAndDownTvBtn1.setText("退出");
            }
        } else if (showType == Builder.TYPE_DIALOG_WITH_BACK_DOWN) {
            //前台展示 后台下载
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.dialog);
            if (isForceUpdate) {
                builder.setCancelable(false);
            }
            View view = View.inflate(mContext, R.layout.download_dialog_super, null);
            builder.setView(view);
            showAndBackDownMsg = (TextView) view.findViewById(R.id.tv_content);
            showAndBackDownClose = (ImageView) view.findViewById(R.id.iv_close);
            showAndBackDownUpdate = (TextView) view.findViewById(R.id.tv_update);
            String updateLog = data.updateLog;
            if (TextUtils.isEmpty(updateLog))
                updateLog = "新版本，欢迎更新";
            showAndBackDownMsg.setText(updateLog);
            showAndBackDownDialog = builder.show();

            showAndBackDownUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点更新
//                    startUpdate(data);
                    openBrowser(mContext, data.downurl);

//                    mContext.startActivity(new Intent(mContext, WebViewActivity.class).putExtra("title", "lan").putExtra("url",  data.downurl));
                    showAndBackDownDialog.dismiss();
                }
            });

            showAndBackDownClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAndBackDownDialog.dismiss();
                    if (isForceUpdate) {
                        if (forceCallBack != null)
                            forceCallBack.exit();
                    } else {
                        if (forceCallBack != null) {
                            forceCallBack.cancel();
                        }
                    }
                }
            });
        }
    }

    private static int PERMISSON_REQUEST_CODE = 2;

    @TargetApi(M)
    private static void requestPermission(final UpdateEntity data) {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // 第一次请求权限时，用户如果拒绝，下一次请求shouldShowRequestPermissionRationale()返回true
            // 向用户解释为什么需要这个权限
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(mContext)
                        .setMessage("申请存储权限")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //申请相机权限
                                ActivityCompat.requestPermissions((Activity) mContext,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSON_REQUEST_CODE);
                            }
                        })
                        .show();
            } else {
                //申请相机权限
                ActivityCompat.requestPermissions((Activity) mContext,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSON_REQUEST_CODE);
            }
        } else {
            if (data != null) {
                if (!TextUtils.isEmpty(data.downurl)) {
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        try {
                            String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                            final String fileName = filePath + "/" + getPackgeName(mContext) + "-v" + getVersionName(mContext) + ".apk";
                            final File file = new File(fileName);
                            //如果不存在
                            if (!file.exists()) {
                                createFileAndDownload(file, data.downurl);
                            } else {
                                if (file.length() == Long.parseLong(data.size)) {
                                    installApkFile(mContext, file);
                                    showAndDownDialog.dismiss();
                                    return;
                                } else {
                                    file.delete();
                                    createFileAndDownload(file, data.downurl);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(mContext, "没有挂载的SD卡", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, "下载路径为空", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private static Intent intent;

    //创建文件并下载文件
    private static void createFileAndDownload(File file, String downurl) {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            if (!file.createNewFile()) {
                Toast.makeText(mContext, "文件创建失败", Toast.LENGTH_SHORT).show();
            } else {
                //文件创建成功
                intent = new Intent(mContext, DownloadService.class);
                intent.putExtra("downUrl", downurl);
                intent.putExtra("appName", mContext.getString(R.string.app_name));
                intent.putExtra("type", showType);
                if (iconRes != 0)
                    intent.putExtra("icRes", iconRes);
                mContext.startService(intent);

                //显示dialog
                if (showType == Builder.TYPE_DIALOG) {
                    progressDialog = new ProgressDialog(mContext);
                    if (iconRes != 0) {
                        progressDialog.setIcon(iconRes);
                    } else {
                        progressDialog.setIcon(R.mipmap.ic_launcher1);
                    }
                    progressDialog.setTitle("正在更新...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//设置进度条对话框//样式（水平，旋转）
                    //进度最大值
                    progressDialog.setMax(100);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 调用第三方浏览器打开
     *
     * @param context
     * @param url     要浏览的资源地址
     */
    public static void openBrowser(Context context, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        // 注意此处的判断intent.resolveActivity()可以返回显示该Intent的Activity对应的组件名
        // 官方解释 : Name of the component implementing an activity that can display the intent
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            final ComponentName componentName = intent.resolveActivity(context.getPackageManager());
            // 打印Log   ComponentName到底是什么
            context.startActivity(Intent.createChooser(intent, "请选择浏览器"));
        } else {
            Toast.makeText(context.getApplicationContext(), "请下载浏览器", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 开始更新操作
     */
    public void startUpdate(UpdateEntity data) {
        openBrowser(mContext, data.downurl);
//        mContext.startActivity(new Intent(mContext, WebViewActivity.class).putExtra("title", "lan").putExtra("url",  data.downurl));

//        requestPermission(data);
    }

    /**
     * 广播接收器
     *
     * @author user
     */
    private static class MyReceiver extends DownloadReceiver {
        @Override
        protected void downloadComplete() {
            if (progressDialog != null)
                progressDialog.dismiss();
            if (showAndDownDialog != null)
                showAndDownDialog.dismiss();
            try {
                if (mContext != null && intent != null)
                    mContext.stopService(intent);
                if (mContext != null && receiver != null)
                    mContext.unregisterReceiver(receiver);
            } catch (Exception e) {
            }
        }

        @Override
        protected void downloading(int progress) {
            if (showType == Builder.TYPE_DIALOG) {
                if (progressDialog != null)
                    progressDialog.setProgress(progress);
            } else if (showType == Builder.TYPE_DIALOG_WITH_PROGRESS) {
                if (showAndDownUpdateProView != null)
                    showAndDownUpdateProView.setProgress(progress);
            }
        }

        @Override
        protected void downloadFail(String e) {
            if (progressDialog != null)
                progressDialog.dismiss();
            Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 安装app
     *
     * @param context
     * @param file
     */
    public static void installApkFile(Context context, File file) {
        Intent intent1 = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent1.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
            intent1.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent1.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (context.getPackageManager().queryIntentActivities(intent1, 0).size() > 0) {
            context.startActivity(intent1);
        }
    }

    /**
     * 获得apkPackgeName
     *
     * @param context
     * @return
     */
    public static String getPackgeName(Context context) {
        String packName = "";
        PackageInfo packInfo = getPackInfo(context);
        if (packInfo != null) {
            packName = packInfo.packageName;
        }
        return packName;
    }

    private static String getVersionName(Context context) {
        String versionName = "";
        PackageInfo packInfo = getPackInfo(context);
        if (packInfo != null) {
            versionName = packInfo.versionName;
        }
        return versionName;
    }

    /**
     * 获得apk版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        PackageInfo packInfo = getPackInfo(context);
        if (packInfo != null) {
            versionCode = packInfo.versionCode;
        }
        return versionCode;
    }


    /**
     * 获得apkinfo
     *
     * @param context
     * @return
     */
    public static PackageInfo getPackInfo(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo;
    }

    //建造者模式
    public static final class Builder {
        private String baseUrl;
        private int showType = TYPE_DIALOG;
        //是否显示忽略此版本 true 是 false 否
        private boolean canIgnoreThisVersion = true;
        //在通知栏显示进度
        public static final int TYPE_NITIFICATION = 1;
        //对话框显示进度
        public static final int TYPE_DIALOG = 2;
        //对话框展示提示和下载进度
        public static final int TYPE_DIALOG_WITH_PROGRESS = 3;
        //对话框展示提示后台下载
        public static final int TYPE_DIALOG_WITH_BACK_DOWN = 4;
        //POST方法
        public static final int METHOD_POST = 3;
        //GET方法
        public static final int METHOD_GET = 4;
        //显示的app资源图
        private int iconRes;
        //显示的app名
        private String appName;
        //显示log日志
        private boolean showLog;
        //设置请求方式
        private int requestMethod = METHOD_POST;
        //自定义Bean类
        private Object cls;

        public final Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public final Builder setTransition(Object cls) {
            this.cls = cls;
            return this;
        }

        public final Builder showLog(boolean showLog) {
            this.showLog = showLog;
            return this;
        }

        public final Builder setRequestMethod(int requestMethod) {
            this.requestMethod = requestMethod;
            return this;
        }

        public final Builder setShowType(int showType) {
            this.showType = showType;
            return this;
        }

        public final Builder setIconRes(int iconRes) {
            this.iconRes = iconRes;
            return this;
        }

        public final Builder setAppName(String appName) {
            this.appName = appName;
            return this;
        }

        public final Builder setIgnoreThisVersion(boolean canIgnoreThisVersion) {
            this.canIgnoreThisVersion = canIgnoreThisVersion;
            return this;
        }

        public final Builder build() {
            return this;
        }
    }

    public boolean saveArray(List<String> list) {
        SharedPreferences sp = mContext.getSharedPreferences("ingoreList", mContext.MODE_PRIVATE);
        SharedPreferences.Editor mEdit1 = sp.edit();
        mEdit1.putInt("Status_size", list.size());

        for (int i = 0; i < list.size(); i++) {
            mEdit1.remove("Status_" + i);
            mEdit1.putString("Status_" + i, list.get(i));
        }
        return mEdit1.commit();
    }

    public List loadArray() {
        List<String> list = new ArrayList<>();
        SharedPreferences mSharedPreference1 = mContext.getSharedPreferences("ingoreList", mContext.MODE_PRIVATE);
        list.clear();
        int size = mSharedPreference1.getInt("Status_size", 0);
        for (int i = 0; i < size; i++) {
            list.add(mSharedPreference1.getString("Status_" + i, null));
        }
        return list;
    }
}

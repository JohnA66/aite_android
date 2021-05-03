package com.haoniu.quchat.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.aite.chat.R;
import com.haoniu.quchat.EaseShowBigImageNewItem;
import com.haoniu.quchat.activity.fragment.EaseBaseFragment;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.model.EaseImageCache;
import com.haoniu.quchat.utils.EaseLoadLocalBigImgTask;
import com.haoniu.quchat.widget.photoview.EasePhotoView;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.ImageUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

public class EaseShowBigImageNewFragment extends EaseBaseFragment {
    private static final String TAG = "EaseShowBigImageNewFragment";
    public static final EaseShowBigImageNewFragment newInstance(EaseShowBigImageNewItem showBigImageNewItem) {
        EaseShowBigImageNewFragment myRedFragment = new EaseShowBigImageNewFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("showBigImageNewItem", showBigImageNewItem);
        myRedFragment.setArguments(bundle);
        return myRedFragment;
    }

    Unbinder unbinder;
    private EasePhotoView image;
    private ProgressBar pb_load_local;
    private int default_res = R.drawable.ease_default_image;
    private Bitmap bitmap;
    private ProgressDialog pd;
    private String localFilePath;
    private boolean isDownloaded;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ease_activity_show_big_image_fragment, container, false);
        image = view.findViewById(R.id.image);
        pb_load_local = view.findViewById(R.id.pb_load_local);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EaseShowBigImageNewItem imageNewItem = getArguments().getParcelable("showBigImageNewItem");
        if (imageNewItem != null) {
            Uri uri = imageNewItem.getUri();
            String messageId = imageNewItem.getMessageId();
            localFilePath = imageNewItem.getLocalUrl();
            if (uri != null && new File(uri.getPath()).exists()) {
                EMLog.d(TAG, "showbigimage file exists. directly show it");
                DisplayMetrics metrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
                // int screenWidth = metrics.widthPixels;
                // int screenHeight =metrics.heightPixels;
                bitmap = EaseImageCache.getInstance().get(uri.getPath());
                if (bitmap == null) {
                    EaseLoadLocalBigImgTask task = new EaseLoadLocalBigImgTask(getContext(), uri.getPath(), image, pb_load_local,3000,
                           4000);
                    if (android.os.Build.VERSION.SDK_INT > 10) {
                        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    } else {
                        task.execute();
                    }
                } else {
                    image.setImageBitmap(bitmap);
                }
            } else if(messageId != null) {
                downloadImage(messageId);
            }else {
                image.setImageResource(default_res);
            }
        }
    }

    /**
     * download image
     * @param msgId
     */
    @SuppressLint("NewApi")
    private void downloadImage(final String msgId) {
        EMLog.e(TAG, "download with messageId: " + msgId);
        String str1 = getResources().getString(R.string.Download_the_pictures);
        pd = new ProgressDialog(getActivity());
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage(str1);
        pd.show();
        File temp = new File(localFilePath);
        final String tempPath = temp.getParent() + "/temp_" + temp.getName();
        final EMCallBack callback = new EMCallBack() {
            @Override
            public void onSuccess() {
                EMLog.e(TAG, "onSuccess" );
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new File(tempPath).renameTo(new File(localFilePath));

                        DisplayMetrics metrics = new DisplayMetrics();
                        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
                        int screenWidth = metrics.widthPixels;
                        int screenHeight = metrics.heightPixels;

                        bitmap = ImageUtils.decodeScaleImage(localFilePath, screenWidth, screenHeight);
                        if (bitmap == null) {
                            image.setImageResource(default_res);
                        } else {
                            image.setImageBitmap(bitmap);
                            EaseImageCache.getInstance().put(localFilePath, bitmap);
                            isDownloaded = true;
                        }
                        if (getActivity().isFinishing() || getActivity().isDestroyed()) {
                            return;
                        }
                        if (pd != null) {
                            pd.dismiss();
                        }
                    }
                });
            }

            @Override
            public void onError(int error, String msg) {
                EMLog.e(TAG, "offline file transfer error:" + msg);
                File file = new File(tempPath);
                if (file.exists()&&file.isFile()) {
                    file.delete();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (getActivity() != null && (getActivity().isFinishing() || getActivity().isDestroyed())) {
                            return;
                        }
                        image.setImageResource(default_res);
                        pd.dismiss();
                    }
                });
            }

            @Override
            public void onProgress(final int progress, String status) {
                EMLog.d(TAG, "Progress: " + progress);
                final String str2 = getResources().getString(R.string.Download_the_pictures_new);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (getActivity() != null && (getActivity().isFinishing() || getActivity().isDestroyed())) {
                            return;
                        }
                        pd.setMessage(str2 + progress + "%");
                    }
                });
            }
        };

        EMMessage msg = EMClient.getInstance().chatManager().getMessage(msgId);
        msg.setMessageStatusCallback(callback);

        EMLog.e(TAG, "downloadAttachement");
        EMClient.getInstance().chatManager().downloadAttachment(msg);
    }

    @Override
    protected void initLogic() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }
}

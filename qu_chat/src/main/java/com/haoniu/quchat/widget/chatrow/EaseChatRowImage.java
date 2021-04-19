package com.haoniu.quchat.widget.chatrow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.autonavi.base.amap.mapcore.tools.GLConvertUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.haoniu.quchat.utils.ResourUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMFileMessageBody;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.aite.chat.R;
import com.haoniu.quchat.model.EaseImageCache;
import com.haoniu.quchat.utils.AsyncTaskCompat;
import com.haoniu.quchat.utils.EaseImageUtils;
import com.luck.picture.lib.tools.ScreenUtils;
import com.zds.base.ImageLoad.GlideUtils;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class EaseChatRowImage extends EaseChatRowFile {

    protected ImageView imageView;
    private EMImageMessageBody imgBody;

    public EaseChatRowImage(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ? R.layout.ease_row_received_picture : R.layout.ease_row_sent_picture, this);
    }

    @Override
    protected void onFindViewById() {
        percentageView = (TextView) findViewById(R.id.percentage);
        imageView = (ImageView) findViewById(R.id.image);
    }


    @Override
    protected void onSetUpView() {
        imgBody = (EMImageMessageBody) message.getBody();

        // received messages
        if (message.direct() == EMMessage.Direct.RECEIVE) {
            return;
        }

        String filePath = imgBody.getLocalUrl();
        String thumbPath = EaseImageUtils.getThumbnailImagePath(imgBody.getLocalUrl());
        showImageView(thumbPath, filePath, message);
    }

    @Override
    protected void onViewUpdate(EMMessage msg) {
        if (msg.direct() == EMMessage.Direct.SEND) {
            if (EMClient.getInstance().getOptions().getAutodownloadThumbnail()) {
                super.onViewUpdate(msg);
            } else {
                if (imgBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.DOWNLOADING ||
                        imgBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.PENDING ||
                        imgBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.FAILED) {
                    progressBar.setVisibility(View.INVISIBLE);
                    percentageView.setVisibility(View.INVISIBLE);
                    imageView.setImageResource(R.drawable.ease_default_image);
                } else {
                    progressBar.setVisibility(View.GONE);
                    percentageView.setVisibility(View.GONE);
                    imageView.setImageResource(R.drawable.ease_default_image);
                    String thumbPath = imgBody.thumbnailLocalPath();
                    if (!new File(thumbPath).exists()) {
                        // to make it compatible with thumbnail received in previous version
                        thumbPath = EaseImageUtils.getThumbnailImagePath(imgBody.getLocalUrl());
                    }
                    showImageView(thumbPath, imgBody.getLocalUrl(), message);
                }
            }
            return;
        }

        // received messages
        if (imgBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.DOWNLOADING ||
                imgBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.PENDING) {
            if (EMClient.getInstance().getOptions().getAutodownloadThumbnail()) {
                imageView.setImageResource(R.drawable.ease_default_image);
            } else {
                progressBar.setVisibility(View.INVISIBLE);
                percentageView.setVisibility(View.INVISIBLE);
                imageView.setImageResource(R.drawable.ease_default_image);
            }
        } else if (imgBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.FAILED) {
            if (EMClient.getInstance().getOptions().getAutodownloadThumbnail()) {
                progressBar.setVisibility(View.VISIBLE);
                percentageView.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.INVISIBLE);
                percentageView.setVisibility(View.INVISIBLE);
            }
        } else {
            progressBar.setVisibility(View.GONE);
            percentageView.setVisibility(View.GONE);
            imageView.setImageResource(R.drawable.ease_default_image);
            String thumbPath = imgBody.thumbnailLocalPath();
            if (!new File(thumbPath).exists()) {
                // to make it compatible with thumbnail received in previous version
                thumbPath = EaseImageUtils.getThumbnailImagePath(imgBody.getLocalUrl());
            }
            showImageView(thumbPath, /*imgBody.getLocalUrl()*/"", message);//xgp 解决接收的图片不显示的问题
        }
    }

    /**
     * load image into image view
     */
    private void showImageView(final String thumbernailPath, final String localFullSizePath, final EMMessage message) {
//        Observable.fromCallable(new Callable<Bitmap>() {
//            @Override
//            public Bitmap call() throws Exception {
//                return GlideUtils.load(imageView.getContext(), imgBody.getRemoteUrl());
//            }
//        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Bitmap>() {
//            @Override
//            public void accept(Bitmap bitmap1) throws Exception {
//                // thumbnail image is already loaded, reuse the drawable
//                setImageViewLayoutParamsWithBitmap(imageView, bitmap1);
//                imageView.setImageBitmap(bitmap1);
//            }
//        });
        String url = TextUtils.isEmpty(localFullSizePath) ? imgBody.getRemoteUrl() : localFullSizePath;
        Glide.with(imageView.getContext()).load(url).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable @org.jetbrains.annotations.Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                setImageViewLayoutParamsWithDrawable(imageView,drawable);
                return false;
            }
        }).apply(new RequestOptions().skipMemoryCache(true)).into(imageView);
//        GlideUtils.loadImageViewCache(imageView.getContext(),imgBody.getRemoteUrl(),imageView);
        if (true) return;

        GlideUtils.loadImageViewLoding(imgBody.getRemoteUrl(), imageView);
        // first check if the thumbnail image already loaded into cache s
        Bitmap bitmap = EaseImageCache.getInstance().get(thumbernailPath);

        if (bitmap != null) {
            // thumbnail image is already loaded, reuse the drawable
            setImageViewLayoutParamsWithBitmap(imageView, bitmap);
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(R.drawable.ease_default_image);


            AsyncTaskCompat.executeParallel(new AsyncTask<Object, Void, Bitmap>() {

                @Override
                protected Bitmap doInBackground(Object... args) {
                    File file = new File(thumbernailPath);
                    if (file.exists()) {
                        return EaseImageUtils.decodeScaleImage(thumbernailPath, 200, 200);
                    } else if (new File(imgBody.thumbnailLocalPath()).exists()) {
                        return EaseImageUtils.decodeScaleImage(imgBody.thumbnailLocalPath(), 200, 200);
                    } else {
                        if (message.direct() == EMMessage.Direct.SEND) {
                            if (localFullSizePath != null && new File(localFullSizePath).exists()) {
                                return EaseImageUtils.decodeScaleImage(localFullSizePath, 200, 200);
                            } else {
                                return null;
                            }
                        } else {
                            return null;
                        }
                    }
                }

                @Override
                protected void onPostExecute(Bitmap image) {
                    if (image != null) {
                        setImageViewLayoutParamsWithBitmap(imageView, image);
                        imageView.setImageBitmap(image);
                        EaseImageCache.getInstance().put(thumbernailPath, image);
                    }
                }
            });
        }
    }

    /**
     * 设置宽高
     *
     * @param imageView
     * @param bitmap
     */
    private void setImageViewLayoutParamsWithBitmap(ImageView imageView, Bitmap bitmap) {
        if (imageView == null || bitmap == null) return;
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        int vw = ScreenUtils.dip2px(imageView.getContext(), 100);
        //float scale = (float) vw / (float) resource.getIntrinsicWidth();
        float scale = bitmap.getHeight() * 1.0f / bitmap.getWidth();
        int vh = (int) ((float) vw / (float) 1.78);
        params.height = (int) (vw * scale);
        params.width = vw;
//        imageView.setAdjustViewBounds(true);
        imageView.setLayoutParams(params);
    }
    /**
     * 设置宽高
     *
     * @param imageView
     * @param bitmap
     */
    private void setImageViewLayoutParamsWithDrawable(ImageView imageView, Drawable bitmap) {
        if (imageView == null || bitmap == null) return;
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        int vw = ScreenUtils.dip2px(imageView.getContext(), 100);
        //float scale = (float) vw / (float) resource.getIntrinsicWidth();
        float scale = bitmap.getIntrinsicHeight() * 1.0f / bitmap.getIntrinsicWidth();
        int vh = (int) ((float) vw / (float) 1.78);
        params.height = (int) (vw * scale);
        params.width = vw;
//        imageView.setAdjustViewBounds(true);
        imageView.setLayoutParams(params);
    }
}

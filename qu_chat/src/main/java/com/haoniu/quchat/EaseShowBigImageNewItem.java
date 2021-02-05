package com.haoniu.quchat;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class EaseShowBigImageNewItem implements Parcelable {
    private int default_image;
    private Uri uri;
    private String localUrl;
    private String messageId;

    public EaseShowBigImageNewItem() {
    }

    public EaseShowBigImageNewItem(int default_image, Uri uri, String localUrl, String messageId) {
        this.default_image = default_image;
        this.uri = uri;
        this.localUrl = localUrl;
        this.messageId = messageId;
    }

    protected EaseShowBigImageNewItem(Parcel in) {
        default_image = in.readInt();
        uri = in.readParcelable(Uri.class.getClassLoader());
        localUrl = in.readString();
        messageId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(default_image);
        dest.writeParcelable(uri, flags);
        dest.writeString(localUrl);
        dest.writeString(messageId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EaseShowBigImageNewItem> CREATOR = new Creator<EaseShowBigImageNewItem>() {
        @Override
        public EaseShowBigImageNewItem createFromParcel(Parcel in) {
            return new EaseShowBigImageNewItem(in);
        }

        @Override
        public EaseShowBigImageNewItem[] newArray(int size) {
            return new EaseShowBigImageNewItem[size];
        }
    };

    @Override
    public String toString() {
        return "EaseShowBigImageNewItem{" +
                "default_image=" + default_image +
                ", uri=" + uri +
                ", localUrl='" + localUrl + '\'' +
                ", messageId='" + messageId + '\'' +
                '}';
    }

    public int getDefault_image() {
        return default_image;
    }

    public void setDefault_image(int default_image) {
        this.default_image = default_image;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}

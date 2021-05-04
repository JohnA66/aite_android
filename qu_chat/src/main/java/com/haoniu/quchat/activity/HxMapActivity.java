package com.haoniu.quchat.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.TextView;

import com.aite.chat.R;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.entity.EventCenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 环艾特天地图
 *
 * @author lhb
 */
public class HxMapActivity extends BaseActivity {
    @BindView(R.id.map)
    MapView mMapView;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    private AMap aMap;
    private LatLng mLatLng;
    private String addressDetail;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_hx_map);
        mMapView.onCreate(bundle);
        init();
    }

    @Override
    protected void initLogic() {
        mToolbarTitle.setText("位置");
    }

    @Override
    protected void onEventComing(EventCenter center) {
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        mLatLng = new LatLng(extras.getDouble("latitude"), extras.getDouble("longitude"));
        addressDetail = extras.getString("addressDetail");
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(mLatLng);
        markerOptions.title("当前位置 :" + "\n" + addressDetail);
        markerOptions.visible(true);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.dinwei_center));
        markerOptions.icon(bitmapDescriptor);
        aMap.addMarker(markerOptions);

        location();

    }


    private void init() {
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
    }


    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        if (mMapView != null) {
            mMapView.onResume();
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        if (mMapView != null) {
            mMapView.onPause();
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMapView != null) {
            mMapView.onDestroy();
        }
    }

    private void location() {
        if (mLatLng != null) {
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 16));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.img_start)
    public void onViewClicked() {
        location();
    }
}

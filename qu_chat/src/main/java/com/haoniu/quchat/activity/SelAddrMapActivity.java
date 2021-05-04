package com.haoniu.quchat.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aite.chat.R;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.haoniu.quchat.adapter.AddressAdapter;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.executor.ThreadPoolManager;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.utils.BitmapUtil;
import com.zds.base.Toast.ToastUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author lhb
 * @date 2018/12/29
 * h5选择地址地图
 */

public class SelAddrMapActivity extends BaseActivity implements LocationSource, AMapLocationListener,
        AMap.OnCameraChangeListener, GeocodeSearch.OnGeocodeSearchListener, PoiSearch.OnPoiSearchListener {

    @BindView(R.id.map)
    MapView mapView;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_subtitle)
    TextView mToolbarSubtitle;
    @BindView(R.id.rv_recycler)
    RecyclerView mRecyclerCard;
    private AMap aMap;

    /**
     * 是否第一次定位
     */
    private boolean isFirst = false;
    private double mLat;
    private double mLon;
    private String addr;
    private ArrayList<String> mAddressDetailList;


    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private GeocodeSearch geocodeSearch;

    private String filePath;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_sel_addr_map);
        mapView.onCreate(bundle);
    }

    @Override
    protected void initLogic() {
        mToolbarTitle.setText("位置选择");
        mToolbarSubtitle.setText("确定");

        // 此方法必须重写
        init();
    }


    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            // 添加移动地图事件监听器
            aMap = mapView.getMap();
            aMap.setOnCameraChangeListener(this);
            setUpMap();

            geocodeSearch = new GeocodeSearch(this);
            geocodeSearch.setOnGeocodeSearchListener(this);

        }

    }


    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 设置圆形的边框颜色
        myLocationStyle.strokeColor(Color.BLACK);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
        //设置小蓝点的锚点
//        myLocationStyle.anchor(10, 10);
        // 设置圆形的边框粗细
        myLocationStyle.strokeWidth(0);
        aMap.setMyLocationStyle(myLocationStyle);
        // 设置定位监听
        aMap.setLocationSource(this);
        // 设置默认定位按钮是否显示
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
        aMap.setMyLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(20));
        mlocationClient.startLocation();

    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }

        isFirst = false;
    }


    @OnClick({R.id.toolbar_subtitle, R.id.map})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                showLoading("正在发送");
                ThreadPoolManager.getInstance().execute(new Runnable() {
                    @Override
                    public void run() {
                        aMapScreenShot();
                    }
                });

                break;
            case R.id.map:
                break;
            default:
                break;
        }
    }


    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {

                if (isFirst) {
                    return;
                }
                // 显示系统小蓝点
                mListener.onLocationChanged(aMapLocation);
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()), 20));
                //获取定位信息
                mLat = aMapLocation.getLatitude();
                mLon = aMapLocation.getLongitude();
                addr = aMapLocation.getAddress();
//                Log.d("cdy", "onLocationChanged: " + addr + "纬度" + mLat + "经度" + mLon);
//                //doSearchQuery();
                isFirst = true;
            } else {
                toast("定位失败");
            }
        }
    }

    @Override
    public void onCameraChangeFinish(final CameraPosition cameraPosition) {
        mLat = cameraPosition.target.latitude;
        mLon = cameraPosition.target.longitude;
//        CoordinateConverter converter = new CoordinateConverter(this);
//        //返回true代表当前位置在大陆、港澳地区，反之不在。
//        boolean isAMapDataAvailable = converter.isAMapDataAvailable(mLat, mLon);
        doSearchQuery();
    }


    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    protected void showAddress(ArrayList<String> address, ArrayList<String> addDetail, int addIndex) {
        //设置显示布局
        mRecyclerCard.setLayoutManager(new LinearLayoutManager(this));
        //设置删除与加入的动画
        mRecyclerCard.setItemAnimator(new DefaultItemAnimator());
        final AddressAdapter mAddressAdapter = new AddressAdapter
                (this, address, addDetail, addIndex);
        mRecyclerCard.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerCard.setAdapter(mAddressAdapter);
        mAddressAdapter.setOnItemClickListener(new AddressAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mAddIndex = position;
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
        String formatAddress = regeocodeAddress.getFormatAddress();
        Log.d("cdycdy", "onRegeocodeSearched: " + formatAddress.substring(9));
        mAddressDetailList.add(formatAddress.substring(9));
        if (mAddressDetailList.size() == address.size()) {
            showAddress(address, mAddressDetailList, mAddIndex);
        }

    }


    protected void doSearchQuery() {

        int currentPage = 0;
        PoiSearch.Query query = new PoiSearch.Query("", "", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        LatLonPoint latLonPoint = new LatLonPoint(mLat, mLon);
        if (latLonPoint != null) {
            PoiSearch poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(this);  // 实现  onPoiSearched  和  onPoiItemSearched
            poiSearch.setBound(new PoiSearch.SearchBound(latLonPoint, 2000));//
            // 设置搜索区域为以lp点为圆心，其周围5000米范围
            poiSearch.searchPOIAsyn();// 异步搜索
        }

    }


    private void getAddressByLatlng(double mLat1, double mLon1) {
        //逆地理编码查询条件：逆地理编码查询的地理坐标点、查询范围、坐标类型。
        LatLonPoint latLonPoint = new LatLonPoint(mLat1, mLon1);
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
        //异步查询
        geocodeSearch.getFromLocationAsyn(query);
    }


    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    private int mAddIndex;
    private ArrayList<String> address;
    private ArrayList<Double> mLatList;
    private ArrayList<Double> mLonList;

    @Override
    public void onPoiSearched(PoiResult poiResult, int j) {
        ArrayList<PoiItem> pois = poiResult.getPois();
        address = new ArrayList<>();
        mLatList = new ArrayList<>();
        mLonList = new ArrayList<>();
        mAddressDetailList = new ArrayList<>();
        mAddIndex = 0;
        for (int i = 0; i < pois.size(); i++) {
            address.add(pois.get(i).toString());
            LatLonPoint point = pois.get(i).getLatLonPoint();
            getAddressByLatlng(point.getLatitude(), point.getLongitude());
            mLatList.add(point.getLatitude());
            mLonList.add(point.getLongitude());
        }

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }


    /**
     * 对地图进行截屏
     */
    private void aMapScreenShot() {

        aMap.getMapScreenShot(new AMap.OnMapScreenShotListener() {
            @Override
            public void onMapScreenShot(Bitmap bitmap) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String bitmapString = BitmapUtil.bitmapToString(bitmap);
                try {
                    filePath = Environment.getExternalStorageDirectory() + "/DCIM/Camera/youxin_" + sdf.format(new Date()) + ".png";
                    // 保存在SD卡根目录下，图片为png格式。
                    FileOutputStream fos = new FileOutputStream(filePath);
                    boolean ifSuccess = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    try {
                        fos.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (ifSuccess) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                saveHead(filePath);

                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dismissLoading();
                                toast("位置发送失败");
                            }
                        });
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMapScreenShot(Bitmap bitmap, int i) {

            }
        });

    }


    /**
     * 上传头像地址到服务器
     *
     * @param filePath
     */
    private void saveHead(String filePath) {
        ApiClient.requestNetHandleFile(SelAddrMapActivity.this, AppConfig.groupUpHead, "", new File(filePath), new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                dismissLoading();
                dismissLoading();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("latitude", mLatList.get(mAddIndex));
                resultIntent.putExtra("longitude", mLonList.get(mAddIndex));
                resultIntent.putExtra("address", address.get(mAddIndex));
                resultIntent.putExtra("addressDetail", mAddressDetailList.get(mAddIndex));
                resultIntent.putExtra("path", json);

                SelAddrMapActivity.this.setResult(-1, resultIntent);
                finish();
            }

            @Override
            public void onFailure(String msg) {
                dismissLoading();
                ToastUtil.toast(msg);
            }
        });

    }


}

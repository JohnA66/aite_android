package com.haoniu.quchat.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * @author Administrator
 */
@SuppressLint("Registered")
public abstract class BaseActivity extends EaseBaseActivity {

    @Override
    protected void onCreate(Bundle arg0) {

        super.onCreate(arg0);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void back(View view) {
        finish();
    }

    public void startActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }

    public void startActivity(Class<?> cls,Bundle bundle) {
        startActivity(new Intent(this, cls).putExtras(bundle));
    }


}

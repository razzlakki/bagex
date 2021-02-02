package com.tezsol.bagex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DeliveryActivity extends BaseActivity {


    @Override
    protected int getRootResource() {
        return R.layout.activity_delivery;
    }


    @Override
    public void onPostOnCreate() {
        ((TextView) findViewById(R.id.headerText)).setText("DELIVERY");
        initViews();
    }

    private void initViews() {

    }


}
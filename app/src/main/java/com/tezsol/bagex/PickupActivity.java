package com.tezsol.bagex;

import android.widget.TextView;

public class PickupActivity extends BaseActivity {


    @Override
    protected int getRootResource() {
        return R.layout.activity_pickup;
    }

    @Override
    public void onPostOnCreate() {
        ((TextView) findViewById(R.id.headerText)).setText("PICK UP");
        initViews();
    }

    private void initViews() {

    }

}
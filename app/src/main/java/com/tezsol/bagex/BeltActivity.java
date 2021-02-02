package com.tezsol.bagex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class BeltActivity extends BaseActivity {


    @Override
    protected int getRootResource() {
        return R.layout.activity_belt;
    }

    @Override
    public void onPostOnCreate() {
        ((TextView) findViewById(R.id.headerText)).setText("BELT");
        initViews();
    }

    private void initViews() {

    }


}
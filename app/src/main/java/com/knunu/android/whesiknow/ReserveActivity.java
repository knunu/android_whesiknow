package com.knunu.android.whesiknow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.GridLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReserveActivity extends AppCompatActivity {

    @BindView(R.id.find_toolbar) Toolbar toolbar;
    @OnClick()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
        ButterKnife.bind(this);

        toolbar.setContentInsetStartWithNavigation(0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}

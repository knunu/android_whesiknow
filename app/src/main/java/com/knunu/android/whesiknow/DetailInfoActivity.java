package com.knunu.android.whesiknow;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailInfoActivity extends AppCompatActivity {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @OnClick(R.id.reserve) void onClickReserve() {
        Intent intent;
        String activityName = getIntent().getStringExtra("activityName");
        String shopName = getIntent().getStringExtra("shopName");

        if (activityName.equals("Map")) {
            intent = new Intent(this, ReserveActivity.class);
        } else {
            intent = new Intent(this, ConditionalReserveActivity.class);
        }

        intent.putExtra("shopName", shopName);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);
        ButterKnife.bind(this);

        toolbar.setContentInsetStartWithNavigation(0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String text = null;

        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return false;
        }

        return true;
    }
}

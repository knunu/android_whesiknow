package com.knunu.android.whesiknow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailInfo2Activity extends AppCompatActivity {
    @BindView(R.id.toolbar2) Toolbar toolbar;
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
        setContentView(R.layout.activity_detail_info2);
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

package com.knunu.android.whesiknow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SuccessActivity extends AppCompatActivity {

    @OnClick(R.id.home) void onClickHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    @OnClick(R.id.reserve_history) void onClickReserveHistory() {
        startActivity(new Intent(this, HistoryActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        ButterKnife.bind(this);
    }
}

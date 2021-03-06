package com.knunu.android.whesiknow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DepositActivity extends AppCompatActivity {
    private int seatCount;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.seat) TextView seatView;
    @BindView(R.id.total) TextView totalView;
    @OnClick(R.id.pay) void onClickPay() {
        startActivity(new Intent(this, SuccessActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        ButterKnife.bind(this);

        toolbar.setContentInsetStartWithNavigation(0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        seatCount = getIntent().getIntExtra("seatCount", 0);
        seatView.setText(seatCount + "석");
        totalView.setText(seatCount * 1000 + "원");
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

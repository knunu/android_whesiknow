package com.knunu.android.whesiknow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HistoryActivity extends AppCompatActivity {
    private int currentReservationCount = 2;

    @BindView(R.id.current_reservation_count) TextView textView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.history_layout) LinearLayout historyLayout;
    @BindView(R.id.history_layout2) LinearLayout historyLayout2;
    @OnClick(R.id.home) void onClickHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    @OnClick({R.id.cancel, R.id.cancel2}) void onClickCancel(Button button) {
        switch (button.getId()) {
            case R.id.cancel:
                currentReservationCount -= 1;
                textView.setText("현재 예약 건수: " + currentReservationCount + "건");
                historyLayout.setVisibility(View.GONE);
                break;
            case R.id.cancel2:
                currentReservationCount -= 1;
                textView.setText("현재 예약 건수: " + currentReservationCount + "건");
                historyLayout2.setVisibility(View.GONE);
                break;
        }
        button.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
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

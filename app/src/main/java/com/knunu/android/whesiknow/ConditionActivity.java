package com.knunu.android.whesiknow;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConditionActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.start_time_button) Button startTimeButton;
    @BindView(R.id.end_time_button) Button endTimeButton;
    @OnClick(R.id.start_time_button) void onClickStartTimeButton() {
        TimePickerDialog startTimePickerDialog = new TimePickerDialog(this, timeSetListener, startHour, startMinute, false);
        isStartTime = true;
        startTimePickerDialog.show();
    }
    @OnClick(R.id.end_time_button) void onClickEndTimeButton() {
        TimePickerDialog endTimePickerDialog = new TimePickerDialog(this, timeSetListener, endHour, endMinute, false);
        isStartTime = false;
        endTimePickerDialog.show();
    }
    private int startHour, startMinute;
    private int endHour, endMinute;
    private boolean isStartTime;

    @OnClick(R.id.find_button) void onFind() {

        final ProgressDialog progressDialog = new ProgressDialog(this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("검색 중");
        progressDialog.show();
        startActivity(new Intent(this, MapActivity.class));
        progressDialog.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition);
        ButterKnife.bind(this);

        toolbar.setContentInsetStartWithNavigation(0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GregorianCalendar calendar = new GregorianCalendar();
        startHour = calendar.get(Calendar.HOUR_OF_DAY);
        startMinute = calendar.get(Calendar.MINUTE);
        endHour = calendar.get(Calendar.HOUR_OF_DAY);
        endMinute = calendar.get(Calendar.MINUTE);
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

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TODO Auto-generated method stub
            if (isStartTime) {
                startHour = hourOfDay;
                startMinute = minute;
//                startTimeButton.setText();
            } else {
                endHour = hourOfDay;
                endMinute = minute;
            }

        }
    };

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
}

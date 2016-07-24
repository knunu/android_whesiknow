package com.knunu.android.whesiknow;

/**
 * Created by USER on 2016-07-23.
 */
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class Pop extends Activity {
    @Override
    protected  void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

       // setContentView(R.layout.popupwindow);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


    }}

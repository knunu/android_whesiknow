package com.knunu.android.whesiknow;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReserveActivity extends AppCompatActivity {
    private int seatCount = 0;

    @BindView(R.id.reserve_toolbar) Toolbar toolbar;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.image1, R.id.image2, R.id.image3, R.id.image4, R.id.image5, R.id.image6, R.id.image7, R.id.image8, R.id.image9}) void onClickImage(ImageView image) {
        Bitmap imageBitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();

        Bitmap availableBitmap = ((BitmapDrawable)getDrawable(R.drawable.available)).getBitmap();
        Bitmap longAvailableBitmap = ((BitmapDrawable)getDrawable(R.drawable.long_available)).getBitmap();
        Bitmap ocupiedBitmap = ((BitmapDrawable)getDrawable(R.drawable.ocupied)).getBitmap();
        Bitmap longOcupiedBitmap = ((BitmapDrawable)getDrawable(R.drawable.long_ocupied)).getBitmap();
        Bitmap selectedBitmap = ((BitmapDrawable)getDrawable(R.drawable.selected)).getBitmap();
        Bitmap longSelectedBitmap = ((BitmapDrawable)getDrawable(R.drawable.long_selected)).getBitmap();

        if (imageBitmap.equals(availableBitmap)) {
            image.setImageResource(R.drawable.selected);
            seatCount += 4;
        } else if (imageBitmap.equals(longAvailableBitmap)) {
            image.setImageResource(R.drawable.long_selected);
            seatCount += 8;
        } else if (imageBitmap.equals(ocupiedBitmap)) {
            Toast.makeText(ReserveActivity.this, "이미 예약된 자리입니다.", Toast.LENGTH_LONG).show();
        } else if (imageBitmap.equals(longOcupiedBitmap)) {
            Toast.makeText(ReserveActivity.this, "이미 예약된 자리입니다.", Toast.LENGTH_LONG).show();
        } else if (imageBitmap.equals(selectedBitmap)) {
            image.setImageResource(R.drawable.available);
            if (seatCount >= 4) seatCount -= 4;
        } else if (imageBitmap.equals(longSelectedBitmap)) {
            image.setImageResource(R.drawable.long_available);
            if (seatCount >= 8) seatCount -= 8;
        }
    }

    @OnClick(R.id.reserve_button) void onClickReserve() {
        Intent intent = new Intent(this, DepositActivity.class);
        intent.putExtra("seatCount", seatCount);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
        ButterKnife.bind(this);

        toolbar.setContentInsetStartWithNavigation(0);
        toolbar.setTitle(getIntent().getStringExtra("shopName"));
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

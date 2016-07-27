package com.knunu.android.whesiknow;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    @BindView(R.id.map_find_toolbar) Toolbar toolbar;
    @BindView(R.id.map_search_view) SearchView searchView;

    static final LatLng SEOUL = new LatLng(37.523824, 126.963499);
    private GoogleMap googleMap;
    private GoogleMap.OnInfoWindowClickListener windowClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);

        toolbar.setContentInsetStartWithNavigation(0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setSearchView(searchView);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

    @Override
    public void onMapReady(final GoogleMap map) {
        googleMap = map;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                marker.showInfoWindow();
                return true;
            }
        });

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

            }
        });

        List<Map> markerMapList = new ArrayList<>();
        markerMapList.add(new HashMap<String, String>());
        Map<String, String> valueMap = new HashMap<>();
        Map<Marker, Map<String, String>> markerMap = new HashMap<>();

        Marker marker = googleMap.addMarker(new MarkerOptions().position(SEOUL)
                .icon(BitmapDescriptorFactory.defaultMarker(344)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom( SEOUL, 15));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        valueMap.put("iw_image", String.valueOf(R.drawable.kalbi));
        valueMap.put("iw_label", "윤재네 닭갈비");
        valueMap.put("iw_label2", "02-2012-0700");
        valueMap.put("iw_label3", "★★★★ / 20");
        markerMap.put(marker, valueMap);

        Marker marker2 = googleMap.addMarker(new MarkerOptions().position(new LatLng(37.524984, 126.965882))
                .icon(BitmapDescriptorFactory.defaultMarker(344)));
        valueMap = new HashMap<>();
        valueMap.put("iw_image", String.valueOf(R.drawable.zok));
        valueMap.put("iw_label", "선우네 족발");
        valueMap.put("iw_label2", "02-8080-1123");
        valueMap.put("iw_label3", "★★★★★ / 100");
        markerMap.put(marker2, valueMap);

        Marker marker3 = googleMap.addMarker(new MarkerOptions().position(new LatLng(37.520803, 126.9614283))
                .title("Seoul").icon(BitmapDescriptorFactory.defaultMarker(344)));
        valueMap = new HashMap<>();
        valueMap.put("iw_image", String.valueOf(R.drawable.chimac));
        valueMap.put("iw_label", "원경쓰 치맥");
        valueMap.put("iw_label2", "02-8080-1991");
        valueMap.put("iw_label3", "★★★★★ / 75");
        markerMap.put(marker3, valueMap);

        Marker marker4 = googleMap.addMarker(new MarkerOptions().position(new LatLng(37.527588, 126.969020))
                .title("Seoul").icon(BitmapDescriptorFactory.defaultMarker(344)));
        valueMap = new HashMap<>();
        valueMap.put("iw_image", String.valueOf(R.drawable.kong));
        valueMap.put("iw_label", "수민의 콩불");
        valueMap.put("iw_label2", "02-8080-0070");
        valueMap.put("iw_label3", "★★★★★ / 57");
        markerMap.put(marker4, valueMap);

        Marker marker5 = googleMap.addMarker(new MarkerOptions().position(new LatLng(37.526280, 126.957579))
                .title("Seoul").icon(BitmapDescriptorFactory.defaultMarker(344)));
        valueMap = new HashMap<>();
        valueMap.put("iw_image", String.valueOf(R.drawable.bossam));
        valueMap.put("iw_label", "용산 보쌈");
        valueMap.put("iw_label2", "02-8080-1123");
        valueMap.put("iw_label3", "★★★ / 32");
        markerMap.put(marker5, valueMap);

        googleMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter(markerMap));
    }

    private void setSearchView(android.widget.SearchView searchView) {
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchEditText = (EditText) searchView.findViewById(id);
        searchEditText.setTextColor(Color.WHITE);
        searchEditText.setHintTextColor(Color.WHITE);
    }

    public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter
    {
        private Map<Marker, Map<String, String>> valueMap;

        public MarkerInfoWindowAdapter(Map<Marker, Map<String, String>> valueMap)
        {
            this.valueMap = valueMap;
        }

        public MarkerInfoWindowAdapter()
        {

        }

        @Override
        public View getInfoWindow(Marker marker)
        {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker)
        {
            View v  = getLayoutInflater().inflate(R.layout.info_window, null);

            ImageView iw_image = (ImageView) v.findViewById(R.id.iw_imageView);
            TextView iw_label = (TextView) v.findViewById(R.id.iw_textView);
            TextView iw_label2 = (TextView)v.findViewById(R.id.iw_textView2);
            TextView iw_label3 = (TextView)v.findViewById(R.id.iw_textView3);
            Button iw_button = (Button)v.findViewById(R.id.iw_button);
            Button iw_button2 = (Button)v.findViewById(R.id.iw_button2);

            final String activityName = "map";
            final String shopName = valueMap.get(marker).get("iw_label");

            iw_label.setText(shopName);
            iw_image.setImageDrawable(getDrawable(Integer.parseInt(valueMap.get(marker).get("iw_image"))));
            iw_label2.setText(valueMap.get(marker).get("iw_label2"));
            iw_label3.setText(valueMap.get(marker).get("iw_label3"));

            windowClickListener = new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Intent intent = null;

                    switch (shopName) {
                        case "윤재네 닭갈비":
                            intent = new Intent(getApplicationContext(), DetailInfoActivity.class);
                            break;
                        case "선우네 족발":
                            intent = new Intent(getApplicationContext(), DetailInfo2Activity.class);
                            break;
                        case "원경쓰 치맥":
                            intent = new Intent(getApplicationContext(), DetailInfo3Activity.class);
                            break;
                        case "수민의 콩불":
                            intent = new Intent(getApplicationContext(), DetailInfo4Activity.class);
                            break;
                        case "용산 보쌈":
                            intent = new Intent(getApplicationContext(), DetailInfo5Activity.class);
                            break;
                        default:
                            break;
                    }
                    intent.putExtra("activityName", activityName);
                    intent.putExtra("shopName", shopName);
                    startActivity(intent);
                }
            };
            googleMap.setOnInfoWindowClickListener(windowClickListener);

            iw_button.setOnClickListener(new View.OnClickListener() {
                @Override // 상세 정보
                public void onClick(View v) {
                    Intent intent = null;

                    switch (shopName) {
                        case "윤재네 닭갈비":
                            intent = new Intent(getApplicationContext(), DetailInfoActivity.class);
                            break;
                        case "선우네 족발":
                            intent = new Intent(getApplicationContext(), DetailInfo2Activity.class);
                            break;
                        case "원경쓰 치맥":
                            intent = new Intent(getApplicationContext(), DetailInfo3Activity.class);
                            break;
                        case "수민의 콩불":
                            intent = new Intent(getApplicationContext(), DetailInfo4Activity.class);
                            break;
                        case "용산 보쌈":
                            intent = new Intent(getApplicationContext(), DetailInfo5Activity.class);
                            break;
                        default:
                            break;
                    }
                    intent.putExtra("activityName", activityName);
                    intent.putExtra("shopName", shopName);
                    startActivity(intent);
                }
            });

            iw_button2.setOnClickListener(new View.OnClickListener() {
                @Override // 예약
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ReserveActivity.class);
                    intent.putExtra("activityName", activityName);
                    intent.putExtra("shopName", shopName);
                    startActivity(intent);
                }
            });

            return v;
        }
    }
}

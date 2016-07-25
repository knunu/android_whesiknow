package com.knunu.android.whesiknow;

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
import android.widget.Toast;

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

/**
 * Need to be fixed
 */

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    @BindView(R.id.map_find_toolbar) Toolbar toolbar;
    @BindView(R.id.map_search_view) SearchView searchView;

    static final LatLng SEOUL = new LatLng(37.523824, 126.963499);
    private GoogleMap googleMap;

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

        Marker marker = googleMap.addMarker(new MarkerOptions().position(SEOUL)
                .title("Seoul").icon(BitmapDescriptorFactory.defaultMarker(344)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom( SEOUL, 15));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

//        Marker marker2 = googleMap.addMarker(new MarkerOptions().position(new LatLng(37.524984, 126.965882))
//                .title("Seoul").icon(BitmapDescriptorFactory.defaultMarker(344)));
//
//        Marker marker3 = googleMap.addMarker(new MarkerOptions().position(new LatLng(37.520803, 126.9614283))
//                .title("Seoul").icon(BitmapDescriptorFactory.defaultMarker(344)));
//
//        Marker marker4 = googleMap.addMarker(new MarkerOptions().position(new LatLng(37.527588, 126.969020))
//                .title("Seoul").icon(BitmapDescriptorFactory.defaultMarker(344)));
//
//        Marker marker5 = googleMap.addMarker(new MarkerOptions().position(new LatLng(37.526280, 126.957579))
//                .title("Seoul").icon(BitmapDescriptorFactory.defaultMarker(344)));

        Map<String, String> valueMap = new HashMap<>();
        valueMap.put("iw_label", "윤재네 닭갈비");
        valueMap.put("iw_label2", "02-2220-4886");
        valueMap.put("iw_label3", "★★★★★ / 5");

        Map<Marker, Map<String, String>> markerMap = new HashMap<>();
        markerMap.put(marker, valueMap);

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

            TextView iw_label = (TextView) v.findViewById(R.id.iw_textView);
            TextView iw_label2 = (TextView)v.findViewById(R.id.iw_textView2);
            TextView iw_label3 = (TextView)v.findViewById(R.id.iw_textView3);
            Button iw_button = (Button)v.findViewById(R.id.iw_button);
            Button iw_button2 = (Button)v.findViewById(R.id.iw_button2);

            iw_label.setText(valueMap.get(marker).get("iw_label"));
            iw_label2.setText(valueMap.get(marker).get("iw_label2"));
            iw_label3.setText(valueMap.get(marker).get("iw_label3"));
            iw_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            iw_button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            return v;
        }
    }
}

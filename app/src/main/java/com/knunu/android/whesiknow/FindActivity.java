package com.knunu.android.whesiknow;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindActivity extends AppCompatActivity {
    @BindView(R.id.find_toolbar) Toolbar toolbar;
    @BindView(R.id.search_view) SearchView searchView;
    @BindView(R.id.recyclerview) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        ButterKnife.bind(this);

        toolbar.setContentInsetStartWithNavigation(0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setSearchView(searchView);
        setRecyclerView(recyclerView);
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

    private void setSearchView(SearchView searchView) {
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchEditText = (EditText) searchView.findViewById(id);
        searchEditText.setTextColor(Color.WHITE);
        searchEditText.setHintTextColor(Color.WHITE);
    }

    private void setRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<ExpandableListAdapter.Item> data = new ArrayList<>();

//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "지역"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "서울"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "경기"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "대전"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "대구"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "부산"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "울산"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "음식 종류"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "한식"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "중식"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "양식"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "일식"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "기타"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "기타"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "기타"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "기타"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "기타"));

        recyclerView.setAdapter(new ExpandableListAdapter(data));
    }
}
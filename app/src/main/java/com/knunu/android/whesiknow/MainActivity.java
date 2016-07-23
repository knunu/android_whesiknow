package com.knunu.android.whesiknow;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private BackPressCloseHandler backPressCloseHandler;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tabs) TabLayout tabLayout;
    @BindView(R.id.viewpager) ViewPager viewPager;
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view) NavigationView navigationView;
    @BindString(R.string.recommend_theme) String _recommend_theme;
    @BindString(R.string.event) String _event;
    @BindString(R.string.rank) String _rank;
    @BindString(R.string.real_time_info) String _real_time_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        backPressCloseHandler = new BackPressCloseHandler(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupNavigationView(drawerLayout, navigationView, toolbar);
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String text = null;

        switch(item.getItemId()){
            case R.id.check_reservation_button:
                text = "check";
                break;
            default:
                return false;
        }
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        return true;
    }

    private void setupNavigationView(final DrawerLayout drawerLayout, NavigationView navigationView, Toolbar toolbar) {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ThemeFragment(), _recommend_theme);
        adapter.addFragment(new PackageFragment(), _event);
        adapter.addFragment(new RankFragment(), _rank);
        adapter.addFragment(new GetTogetherFragment(), _real_time_info);
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }
}

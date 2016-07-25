package com.knunu.android.whesiknow;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private BackPressCloseHandler backPressCloseHandler;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tabs) TabLayout tabLayout;
    @BindView(R.id.viewpager) ViewPager viewPager;
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view) NavigationView navigationView;
    @OnClick(R.id.floating_action_button) void onFabClicked() { redirectMap(); }
    @BindString(R.string.recommend_theme) String _recommend_theme;
    @BindString(R.string.event) String _event;
    @BindString(R.string.rank) String _rank;
    @BindString(R.string.real_time_info) String _real_time_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(Constant.USER, MODE_PRIVATE);
        editor = sharedPreferences.edit();

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
                text = "개인 예약 내역, 준비 중";
                break;
            default:
                return false;
        }
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        return true;
    }

    private void setupNavigationView(final DrawerLayout drawerLayout, NavigationView navigationView, Toolbar toolbar) {
        String name = sharedPreferences.getString("name", Constant.DEFAULT_NAME);
        final String login_group = sharedPreferences.getString("login_group", Constant.DEFAULT_LOGIN_GROUP);

        TextView drawerUserName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.drawer_user_name);
        TextView drawerUserLoginGroup = (TextView) navigationView.getHeaderView(0).findViewById(R.id.drawer_user_login_group);

        drawerUserName.setText(name);
        switch (login_group) {
            case Constant.APP:
                drawerUserLoginGroup.setText(Constant.APP_KOR);
                break;
            case Constant.FACEBOOK:
                drawerUserLoginGroup.setText(Constant.FACEBOOK_KOR);
                break;
            case Constant.KAKAO:
                drawerUserLoginGroup.setText(Constant.KAKAO_KOR);
                break;
        }

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.drawer_home:
                        break;
                    case R.id.drawer_reservation:
                        Toast.makeText(MainActivity.this, "예약 관리 페이지, 준비 중", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.drawer_personal_data:
                        Toast.makeText(MainActivity.this, "개인 정보 페이지, 준비 중", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.drawer_settings:
                        Toast.makeText(MainActivity.this, "설정 페이지, 준비 중", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.drawer_logout:
                        editor.clear();
                        editor.commit();
                        logout(login_group);
                        break;
                }
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

    private void logout(String login_group) {
        switch (login_group) {
            case Constant.FACEBOOK:
                FacebookSdk.sdkInitialize(getApplicationContext());
                LoginManager.getInstance().logOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                break;
            case Constant.KAKAO:
                UserManagement.requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                });
                break;
            default:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                break;
        }
    }

    private void redirectMap() {
        Intent intent = new Intent(MainActivity.this, MapActivity.class);
        startActivity(intent);
    }

}

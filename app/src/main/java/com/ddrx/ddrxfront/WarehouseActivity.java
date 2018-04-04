package com.ddrx.ddrxfront;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.*;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class WarehouseActivity extends AppCompatActivity {

    public final static int UPDATE_FRAGMENT = 1;

    private ViewPager viewPager;
    private Toolbar toolbar;
    private MyHandler handler;
    private BottomNavigationView bottomNavigationView;

    private static class MyHandler extends Handler {

        private final WeakReference<WarehouseActivity> mActivity;

        public MyHandler(WarehouseActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_FRAGMENT:

                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new OnNavigationItemClicked());

        toolbar = findViewById(R.id.warehouse_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.btn_add_warehouse:
                        startActivity(new Intent(WarehouseActivity.this, AddNewWarehouseActivity.class));
                        break;
                    case R.id.btn_add_template:
                        startActivity(new Intent(WarehouseActivity.this, AddNewModelActivity.class));
                        break;
                }
                return true;
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.warehouse_viewpager);

        setupViewPager(viewPager);
        viewPager.setCurrentItem(0);

        handler = new MyHandler(this);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.warehouse_toolbar, menu);
        return true;
    }

    public Handler getHandler() {
        return handler;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NeedTrainingFragment(), "训练场");
        adapter.addFragment(new CardFragment(), "卡片仓库");
        adapter.addFragment(new ModelFragment(), "模型仓库");
        viewPager.setAdapter(adapter);
    }

    class OnNavigationItemClicked implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id=0;
            switch (item.getItemId()) {
                case R.id.training_module:
                    Log.d("ddrx", "training_module");
                    id = 0;
                    break;
                case R.id.warehouse_module:
                    Log.d("ddrx", "warehouse_module");
                    id = 1;
                    break;
                case R.id.template_module:
                    Log.d("ddrx", "template_module");
                    id = 2;
                    break;
            }
            viewPager.setCurrentItem(id);
            return true;
        }
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            Log.d("ddrx", "setPrimaryItem" + position);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}

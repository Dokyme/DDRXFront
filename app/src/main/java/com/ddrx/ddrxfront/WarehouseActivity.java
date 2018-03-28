package com.ddrx.ddrxfront;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class WarehouseActivity extends AppCompatActivity {

    public final static int UPDATE_FRAGMENT = 1;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyHandler handler;

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

        toolbar = (Toolbar) findViewById(R.id.warehouse_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.warehouse_viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.warehouse_tabs);
        tabLayout.setupWithViewPager(viewPager);

        handler = new MyHandler(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.warehouse_actionbar, menu);
        return true;
    }

    public Handler getHandler(){
        return handler;
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CardFragment(), "卡片仓库");
        adapter.addFragment(new ModelFragment(), "模型仓库");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter{
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager){
            super(manager);
        }

        @Override
        public Fragment getItem(int position){
            return mFragmentList.get(position);
        }

        @Override
        public int getCount(){
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position){
            return mFragmentTitleList.get(position);
        }
    }
}

package com.ddrx.ddrxfront;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class WarehouseActivity extends AppCompatActivity {

    public final static int UPDATE_FRAGMENT = 1;

    private ViewPager viewPager;
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

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.warehouse_viewpager);
        setupViewPager(viewPager);

        handler = new MyHandler(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.warehouse_actionbar, menu);
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
//            int id = 0;
//            switch (item.getItemId()) {
//                case R.id.training_module:
//                    id = 0;
//                    break;
//                case R.id.warehouse_module:
//                    id = 1;
//                    break;
//                case R.id.template_module:
//                    id = 2;
//                    break;
//            }
            viewPager.setCurrentItem(item.getOrder());
            Log.d("dokyme", item.getTitle().toString());
            return true;
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
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

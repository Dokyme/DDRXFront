package com.ddrx.ddrxfront;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ShowCardActivity extends AppCompatActivity {

    public static final int GET_MEMORY_CARD_FRAGMENT = 1;

    private ShowCardActivity.MyHandler handler;
    private ViewPager viewPager;

    private static class MyHandler extends Handler {

        private final WeakReference<ShowCardActivity> mActivity;

        public MyHandler(ShowCardActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_MEMORY_CARD_FRAGMENT:
                    List<MemoryCard_Fragment> cards = (List<MemoryCard_Fragment>) msg.obj;
                    if(cards != null && !cards.isEmpty()){
                        mActivity.get().setupViewPager(cards);
                    }
                    break;
            }
        }
    }

    public void setupViewPager(List<MemoryCard_Fragment> cards){
        ShowCardActivity.ViewPagerAdapter adapter = new ShowCardActivity.ViewPagerAdapter(getSupportFragmentManager());
        for(MemoryCard_Fragment card: cards){
            adapter.addFragment(card, "card");
        }
        viewPager.setAdapter(adapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_card);
        viewPager = findViewById(R.id.SC_viewpager);
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
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

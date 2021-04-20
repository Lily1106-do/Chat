package com.ucas.chat.ui.home;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ucas.chat.R;
import com.ucas.chat.base.BaseActivity;
import com.ucas.chat.base.BaseFragment;
import com.ucas.chat.ui.home.adapter.HomeFragmentPagerAdapter;
import com.ucas.chat.ui.view.ViewPagerFixed;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener{

    private BottomNavigationView mBottomNavigationView;
    private ViewPagerFixed mContentViewPager;

    private List<BaseFragment> mFragmentList = new ArrayList<>(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }

    private void initView() {

        mBottomNavigationView= findViewById(R.id.bottomNavigationView);
        mContentViewPager = findViewById(R.id.contentViewPager);
        //设置ViewPager的最大缓存页面
        mContentViewPager.setOffscreenPageLimit(2);

        NewsFragment newsFragment = new NewsFragment();
        ContactListFragment contactListFragment = new ContactListFragment();
        MeFragment meFragment = new MeFragment();
        mFragmentList.add(newsFragment);
        mFragmentList.add(contactListFragment);
        mFragmentList.add(meFragment);
        mContentViewPager.setAdapter(new HomeFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList));
        mContentViewPager.setOnPageChangeListener(this);

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.contact:
                        mContentViewPager.setCurrentItem(0);
                        setTitle(getString(R.string.news));
                    break;

                    case R.id.discovery:
                        mContentViewPager.setCurrentItem(1);
                        setTitle(getString(R.string.mail_list));
                    break;
                    case R.id.me:
                        mContentViewPager.setCurrentItem(2);
                        setTitle(getString(R.string.me));
                    break;
                default:
                    break;
            }
                return true;
            }
        });

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        switch (position) {
            case 0:
                mBottomNavigationView.setSelectedItemId(R.id.contact);
                break;
            case 1:
                mBottomNavigationView.setSelectedItemId(R.id.discovery);
                break;
            case 2:
                mBottomNavigationView.setSelectedItemId(R.id.me);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

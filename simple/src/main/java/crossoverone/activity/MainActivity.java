package crossoverone.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import crossoverone.fragment.MainFragment;
import crossoverone.fragment.SecondFragment;
import crossoverone.statuslib.StatusUtil;

public class MainActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private String[] titles = new String[]{"1","2","3"};

    /**
     * It means that the status bar color of the current interface is black.
     * If has view pager,you can set a flag.
     */
    private boolean isBlack = false;

    @Override
    protected void setStatus() {
        StatusUtil.setUseStatusBarColor(this, Color.parseColor("#00000000"), StatusUtil.USE_DEFAULT_COLOR);
        StatusUtil.setSystemStatus(this, true, isBlack);
    }

    @Override
    protected void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mViewPager = (ViewPager) findViewById(R.id.vp);
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);

        mTabLayout.setupWithViewPager(mViewPager);

        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (isBlack) {
                    StatusUtil.setSystemStatus(MainActivity.this, true, slideOffset < 0.5f);
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
        initVp();
    }

    private void initVp() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 2) {
                    return SecondFragment.newInstance(position);
                }
                return MainFragment.newInstance(position);
            }

            @Override
            public int getCount() {
                return titles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        isBlack = false;
                        StatusUtil.setSystemStatus(MainActivity.this, true, isBlack);
                        break;
                    case 1:
                        isBlack = true;
                        StatusUtil.setSystemStatus(MainActivity.this, true, isBlack);
                        break;
                    case 2:
                        isBlack = false;
                        StatusUtil.setSystemStatus(MainActivity.this, true, isBlack);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }
    public void imgClick(View view) {
        startActivity(new Intent(this, FirstActivity.class));
    }
}

package com.dy.manager.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.dy.manager.R;
import com.dy.manager.Services.MessageService;
import com.dy.manager.fragment.DataBaseRecyclerViewFragment;
import com.dy.manager.fragment.MessageRecyclerViewFragment;
import com.dy.manager.fragment.RecyclerViewFragment;
import com.dy.manager.fragment.ScrollFragment;
import com.dy.manager.fragment.TaskRecyclerViewFragment;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import me.drakeet.materialdialog.MaterialDialog;


public class MainActivity extends AppCompatActivity {

    private MaterialViewPager mViewPager;

//    private DrawerLayout mDrawer;
//    private ActionBarDrawerToggle mDrawerToggle;
//    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, MessageService.class));


//        setTitle("");

        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);

//        toolbar = mViewPager.getToolbar();
//
//        if (toolbar != null) {
//            setSupportActionBar(toolbar);
//
//            final ActionBar actionBar = getSupportActionBar();
//            if (actionBar != null) {
//                actionBar.setDisplayHomeAsUpEnabled(true);
//                actionBar.setDisplayShowHomeEnabled(true);
//                actionBar.setDisplayShowTitleEnabled(true);
//                actionBar.setDisplayUseLogoEnabled(false);
//                actionBar.setHomeButtonEnabled(true);
//            }
//        }



        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position % 4) {
                    case 0:
                        return MessageRecyclerViewFragment.newInstance();
                    case 1:
                        return DataBaseRecyclerViewFragment.newInstance();
                    case 2:
                        return TaskRecyclerViewFragment.newInstance();
                    case 3:
                        return ScrollFragment.newInstance();
                    default:
                        return RecyclerViewFragment.newInstance();
                }
            }

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 4) {
                    case 0:
                        return "消息";
                    case 1:
                        return "数据库";
                    case 2:
                        return "任务";
                    case 3:
                        return "用户";
                }
                return "";
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.green,
                                "http://120.27.41.245:3000/android-l-wallpapers-630ea6-h900.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue,
                                "http://120.27.41.245:3000/wallpaper_51.jpg");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                "http://120.27.41.245:3000/lollipop-wallpapers10.jpg");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.red,
                                "http://120.27.41.245:3000/original.jpg");
                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        View logo = findViewById(R.id.logo_white);
        if (logo != null)
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();
                    final MaterialDialog mMaterialDialog = new MaterialDialog(MainActivity.this);
                    mMaterialDialog.setTitle("关于三人行后台管理")
                            .setMessage("用于三人行（我就是热点）服务器端管理，" +
                                    "通过该app可以了解数据库的各表的数据信息量，" +
                                    "可以给服务器设置定时任务，" +
                                    "并收服务器完成定时任务的反馈，" +
                                    "还可以查看客户端app的用户数据，" +
                                    "以及将有想推送的内容推送给指定的用户群体。")
                            .setBackgroundResource(R.mipmap.back)
                            .setPositiveButton("我知道啦！", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                   mMaterialDialog.dismiss();
                                }
                            });


                    mMaterialDialog.show();
                }
            });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        mDrawerToggle.syncState();
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        return mDrawerToggle.onOptionsItemSelected(item) ||
//                super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}

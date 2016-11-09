package com.ly.todayNews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


public class splashActivity extends FragmentActivity {

    ViewPager welcome= null;	//欢迎界面
    LinearLayout directorLayout = null;	//图片位置指示器
    List<Integer> imgs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化欢迎界面图片数组
        imgs = new ArrayList<Integer>();
        imgs.add(R.drawable.help1);
        imgs.add(R.drawable.help2);
        imgs.add(R.drawable.help3);
        imgs.add(R.drawable.help4);

        setContentView(R.layout.splash);

        welcome = (ViewPager) findViewById(R.id.welcome_pager);
        //数据适配器
        welcome.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        //事件监听器
        welcome.setOnPageChangeListener(new pageChangeListener());

        directorLayout = (LinearLayout) findViewById(R.id.director);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


     //viewPager内容适配器
    class pagerAdapter extends FragmentPagerAdapter {

        public pagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            //得到要显示的对象并初始化图片
            WelcomeFragment fm = new WelcomeFragment();
            fm.setImg(imgs.get(arg0));

            return fm;
        }

        @Override
        public int getCount() {
            return imgs.size();
        }

    }

     //页面切换的事件监听器
    class pageChangeListener implements ViewPager.OnPageChangeListener {


         //当某一个页面被选中的时候触发
        @Override
        public void onPageSelected(int arg0) {
            int count = directorLayout.getChildCount();

             //指示器自对象顺序和页面显示顺序一样的设置为on，其余的设置为off
            for(int i=0;i<count;i++){
                ImageView iv = (ImageView) directorLayout.getChildAt(i);

                if(i == arg0){
                    iv.setBackgroundResource(R.drawable.pageindicator_on);
                }else{
                    iv.setBackgroundResource(R.drawable.pageindicator_off);
                }

            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            int count = directorLayout.getChildCount();
            if(arg0==(count-1)) {
                Intent intent=new Intent();
                intent.setClass(splashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    }


}

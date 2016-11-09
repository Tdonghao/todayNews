package com.ly.todayNews;

import android.app.Application;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.RequestQueue;

/**
 * Created by Administrator on 2016/9/5.
 */
public class MyApplication extends Application {
    public static RequestQueue queue = null;
    public static ImageLoader imageLoader = null;


    @Override
    public void onCreate() {

        super.onCreate();
        //初始化NoHttp
        NoHttp.init(this);
        //初始化请求队列
        queue = NoHttp.newRequestQueue();

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }

}

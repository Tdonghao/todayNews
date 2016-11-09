package com.ly.todayNews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/5.
 */
public class WebViewActivity extends AppCompatActivity {

    private Map<String, Object> itm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        Utils.showLoadingDialog(this,"正在加载....",false);

        WebView wView = (WebView)findViewById(R.id.webview);

        Intent intent = getIntent();
        String ext_utl = "http://www.toutiao.com"+intent.getStringExtra(ViewActivity.EXTRA_URL);

        wView.getSettings().setJavaScriptEnabled(true);
        wView.getSettings().setDomStorageEnabled(true);

        wView.getSettings().setBlockNetworkImage(false);
        wView.loadUrl(ext_utl);

        wView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                Utils.closeLoadingDialog();
                return false;
            }
        });


    }



}

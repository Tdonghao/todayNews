package com.ly.todayNews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Administrator on 2016/9/5.
 */
public class PlayViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        Utils.showLoadingDialog(this,"正在加载....",false);

        WebView wView = (WebView)findViewById(R.id.webview);

        Intent intent = getIntent();
        String ext_utl = intent.getStringExtra(YsActivity.EXTRA_URL);

        wView.getSettings().setJavaScriptEnabled(true);
        wView.getSettings().setDomStorageEnabled(true);

        wView.getSettings().setBlockNetworkImage(true);
        wView.loadUrl(ext_utl);

        wView.setWebChromeClient(new WebChromeClient(){

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                Utils.closeLoadingDialog();
                return true;
            }
        });



    }


}

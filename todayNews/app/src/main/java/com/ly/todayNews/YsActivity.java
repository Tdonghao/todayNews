package com.ly.todayNews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/5.
 */
public class YsActivity extends AppCompatActivity {
    private static final int NOHTTP_WHAT_TEST = 0x001;
    public final static String EXTRA_URL = "EXTRA_URL";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ys_main);

        findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.search);
                String name = editText.getText().toString();

                if(name.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"请输入影视名称",Toast.LENGTH_SHORT).show();
                }else {

                    getData(name);
                }

            }
        });

        findViewById(R.id.ys_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView editText = (TextView) findViewById(R.id.ys_play);
                String ext_url = editText.getText().toString();
                if (ext_url.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"暂无播放资源",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent=new Intent(YsActivity.this,PlayViewActivity.class);
                intent.putExtra(EXTRA_URL,ext_url);

                startActivity(intent);

            }
        });
    }

    public void getData(String name) {
        String url = "http://op.juhe.cn/onebox/movie/video?dtype=&key=354b3173787937bfb9057f34c9975c7c&q="+URLtoUTF8.toUtf8String(name);

        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);

        //OnResponseListener监听
        MyApplication.queue.add(NOHTTP_WHAT_TEST, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
                Utils.i("开始:" + String.valueOf(what));
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                //what的作用,多个请求公用一个监听时,就像是id
                if (what == NOHTTP_WHAT_TEST) {
                    Utils.i("成功:" + String.valueOf(what));

                    JSONObject json = JSONObject.parseObject(response.get());

                    if (!json.get("error_code").equals(0)) {
                        Toast.makeText(getApplicationContext(),json.get("reason").toString(),Toast.LENGTH_SHORT).show();
                        return;
                    }

                    JSONObject jsonData = JSONObject.parseObject(json.get("result").toString());

                    JSONObject jsonPlay = JSONObject.parseObject(jsonData.get("playlinks").toString());

                    JSONArray jsonArray = JSON.parseArray(jsonData.get("video_rec").toString());

                    TextView YS_TITLE = (TextView)findViewById(R.id.ys_title);
                    YS_TITLE.setText("名称:"+jsonData.get("title"));

                    TextView YS_ACT = (TextView)findViewById(R.id.ys_act);
                    YS_ACT.setText("演员:"+jsonData.get("act"));

                    TextView YS_AREA = (TextView)findViewById(R.id.ys_area);
                    YS_AREA.setText("导演:"+jsonData.get("area"));

                    TextView YS_DES = (TextView)findViewById(R.id.ys_des);
                    YS_DES.setText("描述:"+jsonData.get("desc"));

                    TextView YS_DIR = (TextView)findViewById(R.id.ys_dir);
                    YS_DIR.setText("导演:"+jsonData.get("dir"));

                    ImageView YS_IMG = (ImageView) findViewById(R.id.ys_img);
                       MyApplication.imageLoader.displayImage(jsonData.get("cover").toString(),
                            YS_IMG);

                    TextView YS_PLAY = (TextView)findViewById(R.id.ys_play);
                    YS_PLAY.setText(jsonPlay.get("youku").toString());


                    TextView YS_TYPE = (TextView)findViewById(R.id.ys_type);
                    YS_TYPE.setText("类型:"+jsonData.get("tag"));

                    TextView YS_YEAR = (TextView)findViewById(R.id.ys_year);
                    YS_YEAR.setText("年代:"+jsonData.get("year"));

                    TextView YS_REC = (TextView)findViewById(R.id.ys_rec);
                    YS_REC.setText("相似影片:"+JSONObject.parseObject(jsonArray.get(0).toString()).get("title"));


                }

            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

                //传下来的每个值都很容易从其名称看出来作用
                //networkMillis--请求消耗的时间
                Utils.i("失败:" + String.valueOf(what));
                Utils.i("失败:" + exception.toString());
                Utils.i("失败:" + responseCode);
            }

            @Override
            public void onFinish(int what) {
                Utils.i("结束:" + String.valueOf(what));
            }
        });
    }


}

package com.ly.todayNews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/5.
 */
public class ViewActivity extends AppCompatActivity {
    private static final int NOHTTP_WHAT_TEST = 0x001;
    public final static String EXTRA_URL = "EXTRA_URL";
    public static String name = "";
    private ListView mListView;
    private Map<String, Object> itm;
    private String url = "";
    Map<String, Object> profileMap;
    List<Map<String, Object>> profilesList = new ArrayList<>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        name = intent.getStringExtra(MainActivity.EXTRA_NAME);
        String reqUrl = intent.getStringExtra(MainActivity.EXTRA_ID);

        url = reqUrl;

        TextView textView = new TextView(this);
        textView.setText(name);
        setContentView(textView);

        setContentView(R.layout.view_main);
        Utils.showLoadingDialog(this,"正在加载....",false);

        mListView = (ListView) findViewById(R.id.list);

        initData();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itm = (Map<String, Object>) parent.getItemAtPosition(position);

                Intent intent=new Intent(ViewActivity.this,WebViewActivity.class);
                intent.putExtra(EXTRA_URL,itm.get("key_url").toString());
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

    }

    public void initData() {
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

                    JSONArray jsonArray = JSON.parseArray(json.get("data").toString());

                    for (int i = 0; i < jsonArray.size(); i++) {
                        profileMap = new HashMap<>();
                        profileMap.put(ViewListAdapter.KEY_CONTENT, JSONObject.parseObject(jsonArray.get(i).toString()).get("title"));
                        profileMap.put(ViewListAdapter.KEY_URL, JSONObject.parseObject(jsonArray.get(i).toString()).get("source_url"));
                        profileMap.put(ViewListAdapter.KEY_TAG, JSONObject.parseObject(jsonArray.get(i).toString()).get("source"));
                        profileMap.put(ViewListAdapter.KEY_IMG, JSONObject.parseObject(jsonArray.get(i).toString()).get("media_avatar_url"));
                        profilesList.add(profileMap);
                    }

                    BaseAdapter ad = getAdapter();
                    mListView.setAdapter(ad);

                }

                Utils.closeLoadingDialog();
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

                Utils.closeLoadingDialog();

                //传下来的每个值都很容易从其名称看出来作用
                //networkMillis--请求消耗的时间
                Utils.i("失败:" + String.valueOf(what));
                Utils.i("失败:" + exception.toString());
                Utils.i("失败:" + responseCode);

            }

            @Override
            public void onFinish(int what) {
                Utils.closeLoadingDialog();
                Utils.i("结束:" + String.valueOf(what));
            }
        });
    }

    protected BaseAdapter getAdapter() {

        return new ViewListAdapter(this, R.layout.view_list, profilesList);
    }


}

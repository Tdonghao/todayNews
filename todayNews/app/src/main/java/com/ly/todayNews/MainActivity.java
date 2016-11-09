package com.ly.todayNews;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_NAME = "EXTRA_NAME";
    public final static String EXTRA_ID = "EXTRA_ID";
    private ListView mListView;
    private Map<String, Object> itm;
    private int time = 10;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.list_view);

        BaseAdapter ad = getAdapter();
        mListView.setAdapter(ad);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itm = (Map<String, Object>) parent.getItemAtPosition(position);

                Intent intent=new Intent();
                if (itm.get("val").equals("100")) {
                    intent.setClass(MainActivity.this,splashActivity.class);
                }else {
                    intent.setClass(MainActivity.this,ViewActivity.class);
                }
                intent.putExtra(EXTRA_ID,itm.get("val").toString());
                intent.putExtra(EXTRA_NAME,itm.get("name").toString());
                startActivity(intent);

            }
        });

    }


    @Override
    public void onBackPressed() {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("温馨提示");
        dialog.setMessage("确认退出？");
        dialog.setCancelable(false);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "是", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int buttonId) {
                onDialogCompleted(true);
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "否", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int buttonId) {
                onDialogCompleted(false);
            }
        });
        dialog.setIcon(android.R.drawable.ic_dialog_alert);
        dialog.show();

    }

    public void onDialogCompleted(boolean bol) {
        if(bol) {
            super.onBackPressed();
            finish();
        }

    }

    protected BaseAdapter getAdapter() {
        Map<String, Object> profileMap;
        List<Map<String, Object>> profilesList = new ArrayList<>();

        String[] names = getResources().getStringArray(R.array.array_names);

        String[] vals = {"http://www.toutiao.com/api/article/feed/?category=news_hot&utm_source=toutiao&widen=0&max_behot_time=0&max_behot_time_tmp=0&as=A115C83139C83ED&cp=5819C8D31E6DFE1",
                        "http://www.toutiao.com/api/article/feed/?category=video&utm_source=toutiao&widen=0&max_behot_time=0&max_behot_time_tmp=0&as=A1C55891397846D&cp=5819B884668DAE1",
                        "http://www.toutiao.com/api/article/feed/?category=essay_joke&utm_source=toutiao&widen=0&max_behot_time=0&max_behot_time_tmp=0&as=A11528E16928566&cp=5819182526B69E1",
                        "http://www.toutiao.com/api/article/feed/?category=news_society&utm_source=toutiao&widen=0&max_behot_time=0&max_behot_time_tmp=0&as=A10528E149B85A8&cp=5819C8A5EA587E1",
                        "http://www.toutiao.com/api/article/feed/?category=news_entertainment&utm_source=toutiao&widen=0&max_behot_time=0&max_behot_time_tmp=0&as=A1D5485129685CE&cp=581998053C3EDE1",
                        "http://www.toutiao.com/api/article/feed/?category=news_tech&utm_source=toutiao&widen=0&max_behot_time=0&max_behot_time_tmp=0&as=A165481129485F0&cp=581988257F702E1",
                        "http://www.toutiao.com/api/article/feed/?category=news_sports&utm_source=toutiao&widen=0&max_behot_time=0&max_behot_time_tmp=0&as=A1B548419968614&cp=581998763174AE1",
                        "http://www.toutiao.com/api/article/recent/?source=2&category=news_car&as=A1F5D861A908636&cp=5819D876F3761E1&_=1478067766318",
                        "http://www.toutiao.com/api/article/feed/?category=news_finance&utm_source=toutiao&widen=0&max_behot_time=0&max_behot_time_tmp=0&as=A165186159486B4&cp=5819C866FBD4FE1",
                        "http://www.toutiao.com/api/article/feed/?category=funny&utm_source=toutiao&widen=0&max_behot_time=0&max_behot_time_tmp=0&as=A1B5581199786C7&cp=581958F66C572E1",
                        "http://www.toutiao.com/api/article/feed/?category=news_essay&utm_source=toutiao&widen=0&max_behot_time=0&max_behot_time_tmp=0&as=A1E5681179686F1&cp=581928B67FF12E1",
                        "http://www.toutiao.com/api/article/feed/?category=news_baby&utm_source=toutiao&widen=0&max_behot_time=0&max_behot_time_tmp=0&as=A145E841F978717&cp=5819B807C1276E1",
                        "http://www.toutiao.com/api/article/feed/?category=news_story&utm_source=toutiao&widen=0&max_behot_time=0&max_behot_time_tmp=0&as=A1F518F1293873B&cp=58191877B3FB3E1",
                        "http://www.toutiao.com/api/article/feed/?category=news_food&utm_source=toutiao&widen=0&max_behot_time=0&max_behot_time_tmp=0&as=A19538C189D875E&cp=5819A8D7352E3E1",
                        "http://www.toutiao.com/api/article/feed/?category=news_discovery&utm_source=toutiao&widen=0&max_behot_time=0&max_behot_time_tmp=0&as=A17508C169987C6&cp=58194847AC461E1",
                        "100"};
        for (int i = 0; i < names.length; i++) {
            profileMap = new HashMap<>();
            profileMap.put(ListAdapter.KEY_NAME, names[i]);
            profileMap.put(ListAdapter.KEY_VAL, vals[i]);
            profilesList.add(profileMap);
        }

        return new ListAdapter(this, R.layout.list_item, profilesList);
    }
}

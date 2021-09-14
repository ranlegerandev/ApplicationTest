package com.ranlegeran.applicationtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ranlegeran.applicationtest.model.TopicModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private static final String BASE_URL = "https://api.sunofbeach.net/ct/moyu/topic/index";
    private Button mBtnRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnRequest = (Button) findViewById(R.id.btn_requrst);
        mBtnRequest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())  {
            case R.id.btn_requrst:
                requestClass();
                break;
        }
    }

    private void requestClass() {
        OkGo.<String>get(BASE_URL)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        try {
                            JSONObject obj = new JSONObject(s);
                            JSONArray jsonArray = obj.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject json = jsonArray.getJSONObject(i);
                                String topicName = json.getString("topicName");
                                Log.e(TAG, "topicName: " + topicName);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }
}
package com.dy.manager.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.dy.manager.R;
import com.dy.manager.Utils.JsonUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by deng on 16-3-15.
 */
public class NewTask extends Activity {

    private TextView mTextViewTextView;
    private NumberPicker mHourPvNumberPicker;
    private TextView mHourTvTextView;
    private NumberPicker mMinutePvNumberPicker;
    private TextView mMinuteTvTextView;
    private EditText mIntefaceurlEditText;
    private EditText mIntefacetagEditText;
    private EditText metTypeEditText;
    private Button mSubmitButton;
    private StringEntity entity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newtask);
        mTextViewTextView = (TextView) findViewById(R.id.textView);
        mHourPvNumberPicker = (NumberPicker) findViewById(R.id.hour_pv);
        mHourTvTextView = (TextView) findViewById(R.id.hour_tv);
        mMinutePvNumberPicker = (NumberPicker) findViewById(R.id.minute_pv);
        mMinuteTvTextView = (TextView) findViewById(R.id.minute_tv);
        mIntefaceurlEditText = (EditText) findViewById(R.id.intefaceurl);
        mIntefacetagEditText = (EditText) findViewById(R.id.intefacetag);
        metTypeEditText = (EditText) findViewById(R.id.et_type);
        mSubmitButton = (Button) findViewById(R.id.bt_submit);

        init();
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mIntefaceurlEditText.getText())){
                    Toast.makeText(NewTask.this,"信息不全",Toast.LENGTH_SHORT).show();
                }else {
                    submitData();
                }
            }
        });

    }



    private void submitData() {
        int hour = mHourPvNumberPicker.getValue();
        int minute = mMinutePvNumberPicker.getValue();
        String interfacetag = mIntefacetagEditText.getText().toString();
        String interfaceurl = mIntefaceurlEditText.getText().toString();
        String type = metTypeEditText.getText().toString();
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("interfacename",interfaceurl);
            jsonObject.put("description",interfacetag);
            jsonObject.put("cycle",hour*60+minute);
            jsonObject.put("type",type);
            entity = new StringEntity(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("notes", "Test api support");
        asyncHttpClient.post(getApplicationContext(), "http://192.168.199.127:3000/tasksetting", entity,
                "application/json", new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        System.out.println(statusCode+""+response);
                        try {
                            if (statusCode==200){

                                Toast.makeText(NewTask.this,response.getString("content"),Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(NewTask.this,"服务器错误",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        System.out.println(statusCode+""+errorResponse);
                    }
                });


    }

    private void init() {

        mHourPvNumberPicker.setMaxValue(24);
        mHourPvNumberPicker.setMinValue(0);
        mHourPvNumberPicker.setValue(9);


        mMinutePvNumberPicker.setMaxValue(60);
        mMinutePvNumberPicker.setMinValue(0);
        mMinutePvNumberPicker.setValue(49);


    }


}

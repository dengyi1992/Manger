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

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.http.request.HttpRequest;
import org.xutils.x;

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
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestParams params = new RequestParams("http://192.168.199.127:3000/tasksetting");
        params.setAsJsonContent(true);
        params.setMethod(HttpMethod.POST);
        params.setBodyContent(String.valueOf(jsonObject));
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
                Toast.makeText(NewTask.this,"任务建立成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

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

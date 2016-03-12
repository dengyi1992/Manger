package com.dy.manger.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dy.manger.R;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class DatabaseFragment extends Fragment {


    private ObservableScrollView mScrollView;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    System.out.println(jb);
                    break;

                default:
                    break;
            }
        }
    };
    private JSONObject jb;

    public static DatabaseFragment newInstance() {
        return new DatabaseFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        new Thread() {//创建子线程进行网络访问的操作
            public void run() {
                try {
                    jb = getJSONObject("http://10.170.46.70:3000/database_info");
                    handler.sendEmptyMessage(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        return inflater.inflate(R.layout.fragment_scroll, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mScrollView = (ObservableScrollView) view.findViewById(R.id.scrollView);

        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView, null);
    }
    public JSONObject getJSONObject(String path) {
        JSONObject jsonObject = null;
        URL url = null;
        try {
            url = new URL(path);
            // 利用HttpURLConnection对象，我们可以从网页中获取网页数据
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 单位为毫秒，设置超时时间为5秒
            conn.setConnectTimeout(5 * 1000);
            // HttpURLConnection对象是通过HTTP协议请求path路径的，所以需要设置请求方式，可以不设置，因为默认为get
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {// 判断请求码是否200，否则为失败
                InputStream inputStream = conn.getInputStream(); // 获取输入流
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                int len=0;
                byte buffer[]=new byte[1024];
                while ((len = inputStream.read(buffer)) != -1){
                    byteArrayOutputStream.write(buffer,0,len);
                }
                inputStream.close();
                byteArrayOutputStream.close();
                String result = new String(byteArrayOutputStream.toByteArray());

                // 数据形式：{"total":2,"success":true,"arrayData":[{"id":1,"name":"张三"},{"id":2,"name":"李斯"}]}
                // 返回的数据形式是一个Object类型，所以可以直接转换成一个Object
                jsonObject = new JSONObject(result);


            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return jsonObject;
    }
}

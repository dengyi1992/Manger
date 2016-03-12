package com.dy.manger.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dy.manger.Adpter.DataBaseRecyclerViewAdapter;
import com.dy.manger.Bean.DataBaseInfo;
import com.dy.manger.R;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class DataBaseRecyclerViewFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private static final int ITEM_COUNT = 1;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:

                    //修改适配器
                    System.out.println(jb);
                    Gson gson = new Gson();
                    DataBaseInfo dataBaseInfo = gson.fromJson(jb, DataBaseInfo.class);
                    mAdapter = new RecyclerViewMaterialAdapter(new DataBaseRecyclerViewAdapter(dataBaseInfo.getContent()));
                    mRecyclerView.setAdapter(mAdapter);
                    break;

                default:
                    break;
            }
        }
    };
    private String jb;

    private List<DataBaseInfo.ContentEntity> mContentItems = new ArrayList<>();
    private String result;

    public static DataBaseRecyclerViewFragment newInstance() {
        return new DataBaseRecyclerViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        new Thread() {//创建子线程进行网络访问的操作
            public void run() {
                try {
//                    jb = getJSONObject("http://10.170.46.70:3000/database_info");
                    jb = getJSONObject("http://120.27.41.245:3000/database_info");
                    handler.sendEmptyMessage(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        return view ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new RecyclerViewMaterialAdapter(new DataBaseRecyclerViewAdapter(mContentItems));
        mRecyclerView.setAdapter(mAdapter);



        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }
    public String getJSONObject(String path) {
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
                result = new String(byteArrayOutputStream.toByteArray());

                // 数据形式：{"total":2,"success":true,"arrayData":[{"id":1,"name":"张三"},{"id":2,"name":"李斯"}]}
                // 返回的数据形式是一个Object类型，所以可以直接转换成一个Object



            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return result;
    }
}

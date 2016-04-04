package com.dy.manager.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dy.manager.Adpter.MessageRecyclerViewAdapter;
import com.dy.manager.Bean.Message;
import com.dy.manager.Dao.MessageDBHelper;
import com.dy.manager.R;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class MessageRecyclerViewFragment extends Fragment {
    public static final String ACTION_UPDATEUI = "action.updateMessage";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private UpdateUIBroadcastReceiver broadcastReceiver;


    @Override

    public void onStart() {

        super.onStart();



    }
    @Override

    public void onStop() {



        super.onStop();

    }
    private static final int ITEM_COUNT = 0;

    private List<Message> mContentItems = new ArrayList<>();

    public static MessageRecyclerViewFragment newInstance() {
        return new MessageRecyclerViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 动态注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_UPDATEUI);
        broadcastReceiver = new UpdateUIBroadcastReceiver();
        getContext().registerReceiver(broadcastReceiver, filter);
        return inflater.inflate(R.layout.fragment_recyclerview_message, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new RecyclerViewMaterialAdapter(new MessageRecyclerViewAdapter(mContentItems));
        mRecyclerView.setAdapter(mAdapter);

        selectData();


        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }


    private class UpdateUIBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
              selectData();

        }
    }

    private void selectData() {

        MessageDBHelper messageDBHelper = new MessageDBHelper(getContext(), "messagedb", null, 1);
        SQLiteDatabase readableDatabase = messageDBHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery("select * from Message", null);
        /**
         *   + "id integer primary key autoincrement, "
         + "title text, "
         + "body text, "
         + "count integer, "
         + "time text)";
         */
        mContentItems.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String body = cursor.getString(cursor.getColumnIndex("body"));
            int count = cursor.getInt(cursor.getColumnIndex("count"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            Message message = new Message();
            message.setTime(time);
            message.setMessageCount(count);
            message.setMessageBody(body);
            message.setMessageTitle(title);
            mContentItems.add(message);

        }
        mAdapter.notifyDataSetChanged();
        readableDatabase.close();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(broadcastReceiver);
    }

}

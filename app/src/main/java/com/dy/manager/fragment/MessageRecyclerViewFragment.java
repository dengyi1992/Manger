package com.dy.manager.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

        {
            for (int i = 0; i < ITEM_COUNT; ++i)
                mContentItems.add(new Message());
            mAdapter.notifyDataSetChanged();
        }

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }


    private class UpdateUIBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String name = intent.getStringExtra("name");
            String time = intent.getStringExtra("time");
            Message message = new Message();
            message.setMessageTitle(name);
            message.setMessageBody("更新成功");
            message.setMessageCount(1);
            message.setTime(time);
            mContentItems.add(0,message);
            mAdapter.notifyDataSetChanged();
            System.out.println("fragment copy"+name);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(broadcastReceiver);
    }
}

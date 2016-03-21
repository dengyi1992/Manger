package com.dy.manager.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dy.manager.Adpter.TaskRecyclerViewAdapter;
import com.dy.manager.Bean.TaskBean;
import com.dy.manager.R;
import com.dy.manager.view.NewTask;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class TaskRecyclerViewFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private static final int ITEM_COUNT = 0;

    private List<TaskBean> mContentItems = new ArrayList<>();

    private FloatingActionButton mFab1FloatingActionButton;
    private FloatingActionButton mFab2FloatingActionButton;
    private FloatingActionButton mFab3FloatingActionButton;
    private FloatingActionMenu mMenu1FloatingActionMenu;
    private final int TASKCODE=0;

    public static TaskRecyclerViewFragment newInstance() {
        return new TaskRecyclerViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview_task, container, false);




        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mFab1FloatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab1);
        mFab2FloatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab2);
        mFab3FloatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab3);
        mMenu1FloatingActionMenu = (FloatingActionMenu) view.findViewById(R.id.menu1);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new RecyclerViewMaterialAdapter(new TaskRecyclerViewAdapter(mContentItems));
        mRecyclerView.setAdapter(mAdapter);

        {
            for (int i = 0; i < ITEM_COUNT; ++i)
                mContentItems.add(new TaskBean());
            mAdapter.notifyDataSetChanged();
        }

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);


        mFab1FloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getContext(), NewTask.class),TASKCODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       switch (resultCode){
           case 0:
               //如果成功就加入到mContentItems

               if (data!=null){
                   Bundle result = data.getBundleExtra("result");
                   TaskBean taskBean = new TaskBean();
                   taskBean.setCycle(result.getInt("cycle"));
                   taskBean.setInterfaceTag(result.getString("interfacetag"));
                   taskBean.setInterfaceUrl(result.getString("interfaceurl"));
                   taskBean.setRepeat(true);
                   taskBean.setType(result.getString("type"));
                   mContentItems.add(0,taskBean);
                   mAdapter.notifyDataSetChanged();
               }else {
                   return;
               }

               break;
           case 1:
               break;
           default:break;
       }

    }
}

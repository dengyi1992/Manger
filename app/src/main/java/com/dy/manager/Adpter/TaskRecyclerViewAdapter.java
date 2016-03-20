package com.dy.manager.Adpter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dy.manager.Bean.TaskBean;
import com.dy.manager.R;

import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.TaskViewHolder> {

    List<TaskBean> contents;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    public TaskRecyclerViewAdapter(List<TaskBean> contents) {
        this.contents = contents;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_big_task, parent, false);

        return new TaskViewHolder(view) {
        };

    }


    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        TaskBean taskBean = contents.get(position);
        holder.nameTextView.setText("名字："+taskBean.getInterfaceTag());
        holder.descTextView.setText("描述："+taskBean.getInterfaceUrl());
        holder.cycleTextView.setText("周期："+taskBean.getCycle()+"分钟");
        holder.isrepeatTextView.setText(taskBean.isRepeat()?"重复":"不重复");
        holder.typeTextView.setText("类型："+taskBean.getType());
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public TextView descTextView;
        public TextView cycleTextView;
        public TextView isrepeatTextView;
        public TextView typeTextView;

        public TaskViewHolder(View view) {
            super(view);
            nameTextView = (TextView) view.findViewById(R.id.tv_name);
            descTextView = (TextView) view.findViewById(R.id.tv_desc);
            cycleTextView = (TextView) view.findViewById(R.id.tv_cycle);
            isrepeatTextView = (TextView) view.findViewById(R.id.tv_isrepeat);
            typeTextView = (TextView) view.findViewById(R.id.tv_type);
        }
    }
}
package com.dy.manager.Adpter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dy.manager.Bean.Message;
import com.dy.manager.R;

import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class MessageRecyclerViewAdapter extends RecyclerView.Adapter<MessageRecyclerViewAdapter.MessageViewHolder> {

    List<Message> contents;


    public MessageRecyclerViewAdapter(List<Message> contents) {
        this.contents = contents;
    }


    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_card_big_message, parent, false);

        return new MessageViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {

        Message message = contents.get(position);
        holder.titleTextView.setText(message.getMessageTitle());
        holder.contentTextView.setText(message.getMessageBody());
        holder.countTextView.setText("更新" + message.getMessageCount() + "条");
        holder.timeTextView.setText("更新时间：" + message.getTime());

    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView contentTextView;
        private TextView timeTextView;
        private TextView countTextView;

        public MessageViewHolder(View view) {
            super(view);
            titleTextView = (TextView) view.findViewById(R.id.tv_title);
            contentTextView = (TextView) view.findViewById(R.id.tv_content);
            countTextView = (TextView) view.findViewById(R.id.tv_count);
            timeTextView = (TextView) view.findViewById(R.id.tv_time);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    System.out.println(contents.get(getAdapterPosition() - 1).getMessageTitle());
                    new AlertDialog.Builder(view.getContext()).setTitle("温馨提示")
                            .setMessage("是否删除 " + contents.get(getAdapterPosition() - 1).getMessageTitle() + "?")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();


                    return true;
                }
            });
        }

    }
}
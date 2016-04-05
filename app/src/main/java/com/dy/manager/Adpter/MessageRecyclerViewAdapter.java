package com.dy.manager.Adpter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dy.manager.Bean.Message;
import com.dy.manager.R;
import com.dy.manager.fragment.MessageRecyclerViewFragment;

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
                    System.out.println(contents.get(getAdapterPosition() - 1).getTime());
                    new AlertDialog.Builder(view.getContext()).setTitle("温馨提示")
                            .setMessage("是否删除 " + contents.get(getAdapterPosition() - 1).getMessageTitle() + "?")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Message message = contents.get(getAdapterPosition() - 1);
                                    int id = message.getId();
                                    findByIdAndDelete(id);
                                    contents.remove(getAdapterPosition() - 1);
                                    notifyDataSetChanged();
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

    private void findByIdAndDelete(int id) {
        SQLiteDatabase writableDatabase = MessageRecyclerViewFragment.messageDBHelper.getWritableDatabase();
        String DELETESQL = "DELETE FROM MESSAGE WHERE id = '" + id + "'";
        try {
            writableDatabase.execSQL(DELETESQL);
        } catch (Exception e) {
            Log.d("database exception ", e.toString());
        }
        writableDatabase.close();
    }

}
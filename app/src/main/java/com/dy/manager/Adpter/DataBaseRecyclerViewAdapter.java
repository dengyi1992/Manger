package com.dy.manager.Adpter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dy.manager.Bean.DataBaseInfo;
import com.dy.manager.R;

import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class DataBaseRecyclerViewAdapter extends RecyclerView.Adapter<DataBaseRecyclerViewAdapter.DataBaseViewHolder> {

    List<DataBaseInfo.ContentEntity> contents;

    public DataBaseRecyclerViewAdapter(List<DataBaseInfo.ContentEntity> contents) {
        this.contents = contents;
    }


    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public DataBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_card_big_database, parent, false);


        return new DataBaseViewHolder(view);
    }


    @Override
    public void onBindViewHolder(DataBaseViewHolder holder, int position) {
        DataBaseInfo.ContentEntity contentEntity = contents.get(position);
        holder.tableNameTextView.setText("表名：" + contentEntity.getTABLE_NAME());
        holder.tableAutoIncrementTextView.setText("自增长数:" + contentEntity.getAUTO_INCREMENT());
        holder.tableAvgLengthTextView.setText("平均行长度：" + contentEntity.getAVG_ROW_LENGTH());
        holder.tableRowsTextView.setText("行数：" + contentEntity.getTABLE_ROWS());
        holder.tableDataLengthTextView.setText("数据长度" + contentEntity.getDATA_LENGTH());

    }

    public class DataBaseViewHolder extends RecyclerView.ViewHolder {
        private TextView tableNameTextView;
        private TextView tableRowsTextView;
        private TextView tableAvgLengthTextView;
        private TextView tableDataLengthTextView;
        private TextView tableAutoIncrementTextView;

        public DataBaseViewHolder(View itemView) {
            super(itemView);
            tableNameTextView = (TextView) itemView.findViewById(R.id.tv_table_name);
            tableRowsTextView = (TextView) itemView.findViewById(R.id.tv_table_rows);
            tableAvgLengthTextView = (TextView) itemView.findViewById(R.id.tv_table_avg_length);
            tableDataLengthTextView = (TextView) itemView.findViewById(R.id.tv_table_data_length);
            tableAutoIncrementTextView = (TextView) itemView.findViewById(R.id.tv_table_auto_increment);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), contents.get(getAdapterPosition() - 1).getTABLE_NAME(), Toast.LENGTH_SHORT).show();
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View view) {
                    new AlertDialog.Builder(view.getContext()).setTitle("选择对已选表的操作")
                            .setMessage("表名： " + contents.get(getAdapterPosition() - 1).getTABLE_NAME())
                            .setPositiveButton("查看详情", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    /**
                                     * 跳转到详情页面
                                     * 先判断是否是行数大于0如果大于0的话就可以跳转
                                     */
                                    if (contents.get(getAdapterPosition()-1).getTABLE_ROWS()>0){
                                        //跳转到详情页面
                                    }else {
                                        Toast.makeText(view.getContext(),"该数据库没有数据！！！",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            })
                            .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(view.getContext(),"取消",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("清空数据", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    /**
                                     * 此处需要输入密码进行验证
                                     */
                                    Toast.makeText(view.getContext(),"清空数据",Toast.LENGTH_SHORT).show();
                                }
                            }).show();


                    return true;
                }
            });
        }
    }
}
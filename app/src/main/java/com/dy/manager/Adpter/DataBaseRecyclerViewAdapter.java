package com.dy.manager.Adpter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dy.manager.Bean.DataBaseInfo;
import com.dy.manager.R;

import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class DataBaseRecyclerViewAdapter extends RecyclerView.Adapter<DataBaseRecyclerViewAdapter.DataBaseViewHolder> {

    List<DataBaseInfo.ContentEntity>  contents;

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
        holder.tableNameTextView.setText("表名："+ contentEntity.getTABLE_NAME());
        holder.tableAutoIncrementTextView.setText("自增长数:"+contentEntity.getAUTO_INCREMENT());
        holder.tableAvgLengthTextView.setText("平均行长度："+contentEntity.getAVG_ROW_LENGTH());
        holder.tableRowsTextView.setText("行数："+contentEntity.getTABLE_ROWS());
        holder.tableDataLengthTextView.setText("数据长度"+contentEntity.getDATA_LENGTH());

    }

    public class DataBaseViewHolder extends RecyclerView.ViewHolder {
        private TextView tableNameTextView ;
        private TextView tableRowsTextView;
        private TextView tableAvgLengthTextView ;
        private TextView tableDataLengthTextView ;
        private TextView tableAutoIncrementTextView;
        public DataBaseViewHolder(View itemView) {
            super(itemView);
            tableNameTextView =  (TextView) itemView.findViewById(R.id.tv_table_name);
            tableRowsTextView = (TextView) itemView.findViewById(R.id.tv_table_rows);
            tableAvgLengthTextView = (TextView) itemView.findViewById(R.id.tv_table_avg_length);
            tableDataLengthTextView = (TextView) itemView.findViewById(R.id.tv_table_data_length);
            tableAutoIncrementTextView = (TextView) itemView.findViewById(R.id.tv_table_auto_increment);
        }
    }
}
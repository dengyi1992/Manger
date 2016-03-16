package com.dy.manager.Services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.dy.manager.fragment.MessageRecyclerViewFragment;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

/**
 * Created by deng on 16-3-13.
 */
public class MessageService extends Service {
    private Socket mSocket;
    private LocalBroadcastManager broadcaster;


    {
        try {
            mSocket = IO.socket("http://120.27.41.245:2999/");
        } catch (URISyntaxException e) {}
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        broadcaster = LocalBroadcastManager.getInstance(this);
        super.onCreate();
        mSocket.on("dengyi", onNewMessage);
        mSocket.on("taskfinish", onTaskFinish);
        mSocket.connect();
        attemptSend();
    }
    private void attemptSend() {

        mSocket.emit("new admin", "test test");
    }
    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject jsonObject = (JSONObject) args[0];
            try {
                String deng = jsonObject.getString("deng");
                System.out.println("###############################################33"+deng);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    };
    private Emitter.Listener onTaskFinish = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject jsonObject = (JSONObject) args[0];
            try {
                String name = jsonObject.getString("iname");
                String time = jsonObject.getString("time");
                System.out.println("++++++++++++++++"+name+time+"++++++++++++++++++++");
                Intent intent = new Intent();
                intent.setAction(MessageRecyclerViewFragment.ACTION_UPDATEUI);
                intent.putExtra("name",name);
                intent.putExtra("time",time);
                sendBroadcast(intent);
//                System.out.println("##更新完的接口##"+iname+"##更新完成时间##"+time);
                //任务完成，将所发任务的消息加入数据列表，并显示，可以加通知栏显示
                //更新消息列表有难度，并加入已读与否的标记
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    };
    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
        mSocket.off("new message", onNewMessage);
    }


}

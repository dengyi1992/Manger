package com.dy.manager.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

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
    {
        try {
            mSocket = IO.socket("http://10.170.66.1:2999/");
        } catch (URISyntaxException e) {}
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
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
                String iname = jsonObject.getString("iname");
                String time = jsonObject.getString("time");
                System.out.println("##################更新完的接口"+iname+"##更新完成时间##"+time);
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

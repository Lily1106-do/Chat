package com.ucas.chat.utils;


import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.task.DownloadTask;
import com.arialyy.aria.util.FileUtil;

import org.greenrobot.eventbus.EventBus;

public class AriaDownLoadUtils {
    String TAG = "AnyRunnModule";
    private Context mContext;
    private String mUrl;
    private long mTaskId = -1;

    public AriaDownLoadUtils(Context context) {
        Aria.download(this).register();
        mContext = context;
    }

    @Download.onWait
    void onWait(DownloadTask task) {
        Log.d(TAG, "wait ==> " + task.getDownloadEntity().getFileName());
    }

    @Download.onPre
    protected void onPre(DownloadTask task) {
        Log.d(TAG, "onPre");
    }

    @Download.onTaskStart
    void taskStart(DownloadTask task) {
        Log.d(TAG, "onStart");
        EventBus.getDefault().post(new EventBusEvent("taskStart"));
    }

    @Download.onTaskRunning
    protected void running(DownloadTask task) {
        Log.d(TAG, "running task.getPercent()= "+task.getPercent());// 获取百分比进度
        Log.d(TAG, "running task.getPercent()= "+task.getConvertSpeed());// 获取速度
        // eventBus --->  把消息发送出去  activity 接收 更新下载进度
        String percentAndSpeed = task.getPercent() + "," + task.getConvertSpeed();
        EventBus.getDefault().post(new EventBusEvent("running",percentAndSpeed));
    }

    @Download.onTaskResume
    void taskResume(DownloadTask task) {
        Log.d(TAG, "resume");
    }

    @Download.onTaskStop
    void taskStop(DownloadTask task) {
        Log.d(TAG, "stop");
        EventBus.getDefault().post(new EventBusEvent("taskStop"));
    }

    @Download.onTaskCancel
    void taskCancel(DownloadTask task) {
        Log.d(TAG, "cancel");
        EventBus.getDefault().post(new EventBusEvent("taskCancel"));
    }

    @Download.onTaskFail
    void taskFail(DownloadTask task) {
        Log.d(TAG, "fail");
        EventBus.getDefault().post(new EventBusEvent("taskFail"));
    }

    @Download.onTaskComplete
    void taskComplete(DownloadTask task) {
        Log.d(TAG, "path ==> " + task.getDownloadEntity().getServerFileName());
        // eventBus --->  把消息发送出去  activity 接收
        EventBus.getDefault().post(new EventBusEvent("taskComplete"));
    }


    void start(String url,String fileName) {
        mUrl = url;
        if (FileUtil.createDir(Environment.getExternalStorageDirectory().getPath() + "/fileTest")){
            mTaskId = Aria.download(this)
                    .load(url)
                    .setFilePath(Environment.getExternalStorageDirectory().getPath() + "/fileTest/"+fileName)
                    .resetState()
                    .create();
        }

    }

    void stop() {
        Aria.download(this).load(mTaskId).stop();
    }

    void cancel() {
        Aria.download(this).load(mTaskId).cancel();
    }

    void unRegister() {
        Aria.download(this).unRegister();
    }
}


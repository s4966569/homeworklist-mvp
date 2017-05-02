package com.example.sunpeng.homeworklist_mvp.data.source.local;

import android.os.Handler;
import android.os.Message;

import com.example.sunpeng.homeworklist_mvp.data.Homework;
import com.example.sunpeng.homeworklist_mvp.data.source.HomeworkDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by sunpeng on 2017/4/26.
 */

public class HomeworkLocalDataSource implements HomeworkDataSource {
    private static HomeworkLocalDataSource INSTANCE ;
    List<Homework> mHomeworks;
    List<Homework> mMoreHomeworks;

    private static int LOAD_DATA_FINISHED = 1;
    private static int LOAD_DATA_ERROR = 2;
    private static int LOAD_MORE_DATA_FINISHED =3;
    private static int LOAD_MORE_DATA_ERROR = 4;
    private static int NO_MORE_DATA = 5;

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            LoadHomeworkCallback callback = (LoadHomeworkCallback) msg.obj;
            if(msg.what == LOAD_DATA_FINISHED){
                callback.onHomeworkLoaded(mHomeworks);
            }else if(msg.what == LOAD_DATA_ERROR){
                callback.onDataError("获取数据失败");
            }else if(msg.what == LOAD_MORE_DATA_FINISHED){
                callback.onHomeworkLoaded(mMoreHomeworks);
            }else if(msg.what == LOAD_MORE_DATA_ERROR){
                callback.onDataError("加载更多失败");
            }else if(msg.what == NO_MORE_DATA){
                callback.onDataError("没有更多数据了");
            }
        }
    };
    public HomeworkLocalDataSource() {
        mHomeworks = new ArrayList<>();
    }

    public static HomeworkLocalDataSource getInstance(){
        if(INSTANCE == null){
            INSTANCE = new HomeworkLocalDataSource();
        }
        return INSTANCE;
    }

    private List<Homework> initHomeworks(int count) {
        List<Homework> homeworks = new ArrayList<>();
        for(int i = 0;i < count ;i++){
            Homework homework = new Homework();
            homework.setName("家庭作业" + i);
            homeworks.add(homework);
        }
        return homeworks;
    }

    @Override
    public void getHomework(LoadHomeworkCallback callback) {
        mHomeworks = initHomeworks(30);
        sendLoadCompleteMsg(callback);
    }

    @Override
    public void getMoreHomework(LoadHomeworkCallback callback) {
        mMoreHomeworks = initHomeworks(10);
        sendLoadMoreCompleteMsg(callback);
    }

    private void sendLoadCompleteMsg(LoadHomeworkCallback callback){
        double randomNum = Math.random();
        Message msg = new Message();
        msg.obj = callback;
        if(randomNum > 0 && randomNum < 0.6){
            msg.what = LOAD_DATA_FINISHED;
        }else {
            msg.what = LOAD_DATA_ERROR;
        }
        mHandler.sendMessageDelayed(msg,2000);
    }

    private void sendLoadMoreCompleteMsg(LoadHomeworkCallback callback){
        double randomNum = Math.random();
        Message msg = new Message();
        msg.obj = callback;
        if(randomNum > 0 && randomNum <0.4){
            msg.what = LOAD_MORE_DATA_FINISHED;
        }else {
            msg.what = NO_MORE_DATA;
        }
        mHandler.sendMessageDelayed(msg,2000);
    }
}

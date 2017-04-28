package com.example.sunpeng.homeworklist_mvp.data.source;

import com.example.sunpeng.homeworklist_mvp.data.Homework;

import java.util.List;

/**
 * Created by sunpeng on 2017/4/26.
 */

public interface HomeworkDataSource {



    interface LoadHomeworkCallback {
        void onHomeworkLoaded(List<Homework> homeworks);
        void onDataError(String errorMessage);
    }

    void getHomework(LoadHomeworkCallback callback);
    void getMoreHomework(LoadHomeworkCallback callback);
}

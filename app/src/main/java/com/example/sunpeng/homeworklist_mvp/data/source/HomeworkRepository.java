package com.example.sunpeng.homeworklist_mvp.data.source;


import com.example.sunpeng.homeworklist_mvp.data.Homework;
import com.example.sunpeng.homeworklist_mvp.data.source.local.HomeworkLocalDataSource;

import java.util.List;

/**
 * Created by sunpeng on 2017/4/26.
 */

public class HomeworkRepository implements HomeworkDataSource {
    private static HomeworkRepository INSTANCE = null;

    private final HomeworkLocalDataSource mHomeworkLocalDataSource;

    List<Homework> mCachedHomeworks;

    boolean mCacheIsDirty = false;

    private HomeworkRepository(HomeworkLocalDataSource mHomeworkLocalDataSource) {
        this.mHomeworkLocalDataSource = mHomeworkLocalDataSource;
    }

    public static HomeworkRepository getInstance(HomeworkLocalDataSource homeworkLocalDataSource){
        if(INSTANCE == null){
            INSTANCE = new HomeworkRepository(homeworkLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }
    @Override
    public void getHomework(LoadHomeworkCallback callback) {
        if(mCachedHomeworks != null && !mCacheIsDirty){
            callback.onHomeworkLoaded(mCachedHomeworks);
        }
        getHomeworksFromLocalDataSource(callback);
    }

    @Override
    public void getMoreHomework(LoadHomeworkCallback callback) {
        getMoreHomeworksFromLocalDataSource(callback);
    }

    public void refreshHomeworks(){
        mCacheIsDirty = true;
    }
    private void getHomeworksFromLocalDataSource(final LoadHomeworkCallback callback) {
        mHomeworkLocalDataSource.getHomework(new LoadHomeworkCallback() {
            @Override
            public void onHomeworkLoaded(List<Homework> homeworks) {
                refreshCache(homeworks,false);
                callback.onHomeworkLoaded(mCachedHomeworks);
            }

            @Override
            public void onDataError(String errorMessage) {
                callback.onDataError(errorMessage);
            }
        });
    }

    private void getMoreHomeworksFromLocalDataSource(final LoadHomeworkCallback callback){
        mHomeworkLocalDataSource.getMoreHomework(new LoadHomeworkCallback() {
            @Override
            public void onHomeworkLoaded(List<Homework> homeworks) {
                refreshCache(homeworks,true);
                callback.onHomeworkLoaded(mCachedHomeworks);
            }

            @Override
            public void onDataError(String errorMessage) {
                callback.onDataError(errorMessage);
            }
        });
    }

    private void refreshCache(List<Homework> homeworks,boolean more){
        if(more){
            mCachedHomeworks.addAll(homeworks);
        }else {
            mCachedHomeworks = homeworks;
        }
        mCacheIsDirty = false;
    }
}

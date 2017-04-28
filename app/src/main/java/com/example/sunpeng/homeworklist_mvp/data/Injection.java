package com.example.sunpeng.homeworklist_mvp.data;

import com.example.sunpeng.homeworklist_mvp.data.source.HomeworkRepository;
import com.example.sunpeng.homeworklist_mvp.data.source.local.HomeworkLocalDataSource;

/**
 * Created by sunpeng on 2017/4/27.
 */

public class Injection {
    public static HomeworkRepository provideHomeworkRepository(){
        return HomeworkRepository.getInstance(HomeworkLocalDataSource.getInstance());
    }
}

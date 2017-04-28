package com.example.sunpeng.homeworklist_mvp.homework;

import com.example.sunpeng.homeworklist_mvp.data.Homework;
import com.example.sunpeng.homeworklist_mvp.util.BasePresenter;
import com.example.sunpeng.homeworklist_mvp.util.BaseView;

import java.util.List;

/**
 * Created by sunpeng on 2017/4/25.
 */

public interface HomeworkContract {
    interface View extends BaseView<Presenter>{
        void setLoadingIndicator(boolean active);
        void showHomeworks(List<Homework> homeworks);
        void showMoreHomeworks(List<Homework> homeworks);
        void showNoHomeworks();
        void showLoadingHomeworksError(String message);
        void showLoadingMoreHomeworksError(String message);
        void showHomeworkDetail(Homework homework);
        boolean isActive();
    }

    interface Presenter extends BasePresenter{
        void loadHomeworks();
        void loadMoreHomeworks();
        void clickHomeworItem(Homework homework);
    }
}

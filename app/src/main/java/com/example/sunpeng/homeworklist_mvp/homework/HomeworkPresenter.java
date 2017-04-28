package com.example.sunpeng.homeworklist_mvp.homework;

import com.example.sunpeng.homeworklist_mvp.data.Homework;
import com.example.sunpeng.homeworklist_mvp.data.source.HomeworkDataSource;
import com.example.sunpeng.homeworklist_mvp.data.source.HomeworkRepository;

import java.util.List;

/**
 * Created by sunpeng on 2017/4/26.
 */

public class HomeworkPresenter implements HomeworkContract.Presenter {
    private final HomeworkRepository mHomeworkResponsitory;

    private final HomeworkContract.View mHomeworksView;

    private boolean mIsLoadingMore = false;

    public HomeworkPresenter(HomeworkRepository homeworkRepository, HomeworkContract.View homeworkView) {
        this.mHomeworkResponsitory = homeworkRepository;
        this.mHomeworksView = homeworkView;

        mHomeworksView.setPresenter(this);
    }

    @Override
    public void start() {
        loadHomeworks();
    }

    @Override
    public void loadHomeworks() {
        mHomeworksView.setLoadingIndicator(true);
        mHomeworkResponsitory.refreshHomeworks();
        mHomeworkResponsitory.getHomework(new HomeworkDataSource.LoadHomeworkCallback() {
            @Override
            public void onHomeworkLoaded(List<Homework> homeworks) {
                mHomeworksView.setLoadingIndicator(false);
                if(!mHomeworksView.isActive()){
                    return;
                }
                if(homeworks == null || homeworks.size() ==0){
                    mHomeworksView.showNoHomeworks();
                }
                mHomeworksView.showHomeworks(homeworks);
            }

            @Override
            public void onDataError(String errorMessage) {
                mHomeworksView.setLoadingIndicator(false);
                if(!mHomeworksView.isActive()){
                    return;
                }
                mHomeworksView.showLoadingHomeworksError(errorMessage);
            }
        });
    }

    @Override
    public void loadMoreHomeworks() {
        if(mIsLoadingMore){
            return;
        }else {
            mIsLoadingMore = true;
        }
        mHomeworkResponsitory.getMoreHomework(new HomeworkDataSource.LoadHomeworkCallback() {
            @Override
            public void onHomeworkLoaded(List<Homework> homeworks) {
                if(!mHomeworksView.isActive()){
                    return;
                }
                if(homeworks == null || homeworks.size() ==0){
                    mHomeworksView.showNoHomeworks();
                }
                mIsLoadingMore = false;
                mHomeworksView.showHomeworks(homeworks);
            }

            @Override
            public void onDataError(String errorMessage) {
                if(!mHomeworksView.isActive()){
                    return;
                }
                mIsLoadingMore = false;
                mHomeworksView.showLoadingMoreHomeworksError(errorMessage);
            }
        });
    }

    @Override
    public void clickHomeworkItem(Homework homework) {
        mHomeworksView.showHomeworkDetail(homework);
    }
}

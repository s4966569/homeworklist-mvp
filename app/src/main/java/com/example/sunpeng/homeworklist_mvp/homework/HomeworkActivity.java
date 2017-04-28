package com.example.sunpeng.homeworklist_mvp.homework;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sunpeng.homeworklist_mvp.R;
import com.example.sunpeng.homeworklist_mvp.data.Injection;
import com.example.sunpeng.homeworklist_mvp.util.ActivityUtils;

public class HomeworkActivity extends AppCompatActivity {

    private HomeworkPresenter mHomeworkPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HomeworkFragment homeworkFragment =
                (HomeworkFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if(homeworkFragment == null){
            homeworkFragment = HomeworkFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),homeworkFragment,R.id.contentFrame);
        }

        mHomeworkPresenter = new HomeworkPresenter(
                Injection.provideHomeworkRepository(),homeworkFragment);
    }
}

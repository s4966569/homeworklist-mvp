package com.example.sunpeng.homeworklist_mvp.homework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sunpeng.homeworklist_mvp.R;
import com.example.sunpeng.homeworklist_mvp.data.Homework;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunpeng on 2017/4/25.
 */

public class HomeworkFragment extends Fragment implements HomeworkContract.View {
    private HomeworkContract.Presenter mPresenter;

    private View mNoHomeworksView, mErrorView;

    private RecyclerView mRecyclerView;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private HomeworksAdapter mHomeworksAdapter;

    public static HomeworkFragment newInstance() {
        return new HomeworkFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomeworksAdapter = new HomeworksAdapter(new ArrayList<Homework>(),mItemClickListener);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.homeworks_frag,container,false);
        mNoHomeworksView = root.findViewById(R.id.noDataLayout);
        mErrorView = root.findViewById(R.id.errorLayout);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mHomeworksAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                if(newState == RecyclerView.SCROLL_STATE_IDLE && layoutManager.findLastVisibleItemPosition() == layoutManager.getItemCount()-1){
                    mPresenter.loadMoreHomeworks();
                }
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadHomeworks();
            }
        });
        mPresenter.start();
        return root;
    }

    @Override
    public void setPresenter(HomeworkContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        mSwipeRefreshLayout.setRefreshing(active);
    }

    @Override
    public void showHomeworks(List<Homework> homeworks) {
        showHomeworskView();
        mHomeworksAdapter.replaceData(homeworks);
    }

    @Override
    public void showNoHomeworks() {
        showNoHomeworksView();
    }

    @Override
    public void showLoadingHomeworksError(String message) {
        showMessage(message);
        showErrorView();
    }

    @Override
    public void showLoadingMoreHomeworksError(String message) {
        showMessage(message);
    }

    private void showErrorView(){
        mRecyclerView.setVisibility(View.GONE);
        mNoHomeworksView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
    }

    private void showNoHomeworksView(){
        mRecyclerView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
        mNoHomeworksView.setVisibility(View.VISIBLE);
    }
    private void showHomeworskView(){
        mNoHomeworksView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }
    private void showMessage(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showHomeworkDetail(Homework homework) {
        showMessage(homework.getName());
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    HomeworkItemClickListener mItemClickListener = new HomeworkItemClickListener() {
        @Override
        public void onHomeworkClick(Homework homework) {
            mPresenter.clickHomeworkItem(homework);
        }
    };

    private static class HomeworksAdapter extends RecyclerView.Adapter<HomeworksAdapter.HomeworkViewHolder>{

        private List<Homework> mHomeworks;

        private HomeworkItemClickListener mItemListener;

        public HomeworksAdapter(List<Homework> homeworks, HomeworkItemClickListener homeworkItemClickListener) {
            mHomeworks = homeworks;
            mItemListener = homeworkItemClickListener;
        }

        @Override
        public HomeworkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new HomeworkViewHolder(inflater.inflate(R.layout.homework_item,parent,false));
        }

        @Override
        public void onBindViewHolder(HomeworkViewHolder holder, int position) {
            holder.mName.setText(getItem(position).getName());
            if(position % 2 ==0){
                holder.mName.setBackgroundColor(holder.mName.getContext().getResources().getColor(android.R.color.holo_green_light));
            }else {
                holder.mName.setBackgroundColor(holder.mName.getContext().getResources().getColor(android.R.color.holo_orange_light));
            }
        }

        @Override
        public int getItemCount() {
            return mHomeworks.size();
        }

        public Homework getItem(int position){
           return mHomeworks.get(position);
        }

        public void replaceData(List<Homework> homeworks){
            mHomeworks = homeworks;
            notifyDataSetChanged();
        }

        class HomeworkViewHolder extends RecyclerView.ViewHolder{

            TextView mName;
            public HomeworkViewHolder(View itemView) {
                super(itemView);
                mName = (TextView) itemView.findViewById(R.id.tv_name);
                mName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mItemListener != null){
                            mItemListener.onHomeworkClick(getItem(getLayoutPosition()));
                        }
                    }
                });
            }
        }
    }

    public interface HomeworkItemClickListener{
        void onHomeworkClick(Homework homeworks);
    }
}

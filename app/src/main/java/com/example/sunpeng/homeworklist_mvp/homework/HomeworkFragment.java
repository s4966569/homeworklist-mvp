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
import android.widget.ProgressBar;
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

    private boolean mIsLoadingMore = false;

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
                loadMore();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0){
                    loadMore();
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

    private void loadMore() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        int totalItemCount = layoutManager.getItemCount();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        if(!mIsLoadingMore && lastVisibleItemPosition + 1 == totalItemCount){
            mIsLoadingMore = true;
            mPresenter.loadMoreHomeworks();
        }
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
    public void setLoadingMoreIndicator(boolean active) {
        if(active){
            mHomeworksAdapter.addFooterView();
        }else {
            mIsLoadingMore = false;
            mHomeworksAdapter.removeFooterView();
        }
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

    private static class HomeworksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private static int TYPE_NORMAL = 0;

        private static int TYPE_FOOTER =1;

        private List<Homework> mHomeworks;

        private HomeworkItemClickListener mItemListener;

        private int insertPos;

        public HomeworksAdapter(List<Homework> homeworks, HomeworkItemClickListener homeworkItemClickListener) {
            mHomeworks = homeworks;
            mItemListener = homeworkItemClickListener;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            if(viewType == TYPE_NORMAL){
                return new HomeworkViewHolder(inflater.inflate(R.layout.homework_item,parent,false));
            }else if(viewType == TYPE_FOOTER){
                return new FooterViewHolder(inflater.inflate(R.layout.footer_loadmore,parent,false));
            }else {
                return new HomeworkViewHolder(inflater.inflate(R.layout.homework_item,parent,false));
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof HomeworkViewHolder){
                ((HomeworkViewHolder)holder).mName.setText(getItem(position).getName());
                if(position % 2 ==0){
                    ((HomeworkViewHolder)holder).mName.setBackgroundColor(((HomeworkViewHolder)holder).mName.getContext().getResources().getColor(android.R.color.holo_green_light));
                }else {
                    ((HomeworkViewHolder)holder).mName.setBackgroundColor(((HomeworkViewHolder)holder).mName.getContext().getResources().getColor(android.R.color.holo_orange_light));
                }
            }
        }

        @Override
        public int getItemCount() {
            return mHomeworks.size();
        }

        @Override
        public int getItemViewType(int position) {
            if(getItem(position) != null){
                return TYPE_NORMAL;
            }else {
                return TYPE_FOOTER;
            }
        }

        public Homework getItem(int position){
           return mHomeworks.get(position);
        }

        public void replaceData(List<Homework> homeworks){
            mHomeworks = homeworks;
            notifyDataSetChanged();
        }

        public void addFooterView(){
            mHomeworks.add(null);
            notifyItemInserted(getItemCount() -1);
            insertPos  = mHomeworks.size() - 1;
        }

        public void removeFooterView(){
            if(insertPos < mHomeworks.size()){
                mHomeworks.remove(insertPos);
                notifyItemRemoved(insertPos);
            }
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

        class FooterViewHolder extends RecyclerView.ViewHolder{

            ProgressBar mProgressBar;
            public FooterViewHolder(View itemView) {
                super(itemView);
                mProgressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            }
        }
    }

    public interface HomeworkItemClickListener{
        void onHomeworkClick(Homework homeworks);
    }
}

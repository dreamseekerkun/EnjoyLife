package com.dream.plmm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dream.plmm.R;
import com.dream.plmm.activity.PhotoDetailActivity;
import com.dream.plmm.adapter.OnItemChildClickListener;
import com.dream.plmm.adapter.RecyclerViewAdapter;
import com.dream.plmm.adapter.ViewHolderHelper;
import com.dream.plmm.bean.TypeMM;
import com.dream.plmm.config.HostURL;
import com.dream.plmm.netWork.ServiceFactory;
import com.dream.plmm.utils.BitmapUtils;
import com.dream.plmm.utils.DateUtils;
import com.dream.plmm.utils.DialogUtil;
import com.dream.plmm.widget.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by likun on 16/8/18.
 */
public class MMListFragment extends BaseFragment implements OnItemChildClickListener,SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListAdapter listAdapter;
    private ArrayList<TypeMM.TngouEntity> mDatas = new ArrayList<>();
    private int type;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_main_content, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.id_recylerView);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.sw_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);
        swipeRefreshLayout.setOnRefreshListener(this);

        listAdapter = new ListAdapter(recyclerView);
        listAdapter.setOnItemChildClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(listAdapter);
        SpacesItemDecoration itemDecoration = new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {//上拉加载更多
                Toast.makeText(mActivity, "没有美女了", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null ){
            type = getArguments().getInt("type");
        }
    }

    public static MMListFragment getInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        MMListFragment mmListFragment = new MMListFragment();
        mmListFragment.setArguments(bundle);
        return mmListFragment;
    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("imgUrl", mDatas.get(position).getImg());
        Intent intent = new Intent(mActivity, PhotoDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {//onCreate--initView--setUserVisibleHint
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            getMMList(type);
        }
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        Toast.makeText(mActivity, "没有美女了", Toast.LENGTH_SHORT).show();
    }

    class ListAdapter extends RecyclerViewAdapter<TypeMM.TngouEntity> {

        public ListAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.recyclerview_photos_item);
        }

        @Override
        protected void fillData(ViewHolderHelper viewHolderHelper, int position, TypeMM.TngouEntity model) {
            BitmapUtils.display(mActivity, HostURL.PicDetail + model.getImg(), viewHolderHelper.getImageView(R.id.news_info_photo));
            viewHolderHelper.getTextView(R.id.news_info_title).setText(model.getTitle());
            viewHolderHelper.getTextView(R.id.news_info_desc).setText(DateUtils.ConvertTime(model.getTime()));
        }

        @Override
        protected void setItemChildListener(ViewHolderHelper viewHolderHelper) {
            viewHolderHelper.setItemChildClickListener(R.id.card_view);
        }
    }

    private void getMMList(final int type) {

        Call<TypeMM> typeMMCall = ServiceFactory.getMmService().getTypeMM(type, 1, 10);
        typeMMCall.enqueue(new Callback<TypeMM>() {
            @Override
            public void onResponse(Call<TypeMM> call, Response<TypeMM> response) {
                if (response.isSuccessful()) {
                    DialogUtil.close();
                    mDatas = response.body().getTngou();
                    listAdapter.setDatas(mDatas);
                    listAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<TypeMM> call, Throwable t) {
                DialogUtil.close();
            }
        });
    }

}

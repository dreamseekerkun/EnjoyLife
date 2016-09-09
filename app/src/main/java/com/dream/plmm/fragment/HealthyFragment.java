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

import com.dream.plmm.R;
import com.dream.plmm.activity.HealthyInfoDetailActivity;
import com.dream.plmm.adapter.OnItemChildClickListener;
import com.dream.plmm.adapter.RecyclerViewAdapter;
import com.dream.plmm.adapter.ViewHolderHelper;
import com.dream.plmm.bean.HealthyInfoListEntity;
import com.dream.plmm.config.HostURL;
import com.dream.plmm.netWork.ServiceFactory;
import com.dream.plmm.utils.BitmapUtils;
import com.dream.plmm.utils.DateUtils;
import com.dream.plmm.utils.DialogUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by likun on 16/8/24.
 */
public class HealthyFragment extends BaseFragment implements OnItemChildClickListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListAdapter listAdapter;
    private ArrayList<HealthyInfoListEntity.TngouEntity> healthyList = new ArrayList<>();

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
        listAdapter = new ListAdapter(recyclerView);
        listAdapter.setOnItemChildClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(listAdapter);
        SpacesItemDecoration itemDecoration = new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(itemDecoration);
        return view;
    }

    int type;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("id");
    }

    static int id;
    public static HealthyFragment getInstance(int position) {

        switch (position) {
            case 0:
                id = 11;
                break;
            case 1:
                id = 7;
                break;
            case 2:
                id = 5;
                break;
            case 3:
                id = 4;
                break;
            case 4:
                id = 6;
                break;
            case 5:
                id = 13;
                break;
            case 6:
                id = 8;
                break;
            case 7:
                id = 3;
                break;
            case 8:
                id = 12;
                break;
            case 9:
                id = 1;
                break;
            case 10:
                id = 2;
                break;
            case 11:
                id = 10;
                break;
            case 12:
                id = 9;
                break;
            default:
                break;
        }
        HealthyFragment healthyListFragment = new HealthyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        healthyListFragment.setArguments(bundle);
        return healthyListFragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {//onCreate--initView--setUserVisibleHint
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getHealthyInfoList(type);
        }
    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        Intent intent = new Intent(mActivity, HealthyInfoDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id",healthyList.get(position).getId());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    class ListAdapter extends RecyclerViewAdapter<HealthyInfoListEntity.TngouEntity> {

        public ListAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.recyclerview_photos_item);
        }

        @Override
        protected void fillData(ViewHolderHelper viewHolderHelper, int position, HealthyInfoListEntity.TngouEntity model) {
            BitmapUtils.display(mActivity, HostURL.PicDetail + model.getImg(), viewHolderHelper.getImageView(R.id.news_info_photo));
            viewHolderHelper.getTextView(R.id.news_info_title).setText(model.getTitle());
            viewHolderHelper.getTextView(R.id.news_info_desc).setText(DateUtils.ConvertTime(model.getTime()));
        }

        @Override
        protected void setItemChildListener(ViewHolderHelper viewHolderHelper) {
            viewHolderHelper.setItemChildClickListener(R.id.card_view);
        }
    }

    private void getHealthyInfoList(int type) {
        Call<HealthyInfoListEntity> call = ServiceFactory.getHealthyService().getHealthyInfoList(type, 10);
        call.enqueue(new Callback<HealthyInfoListEntity>() {
            @Override
            public void onResponse(Call<HealthyInfoListEntity> call, Response<HealthyInfoListEntity> response) {
                if (response.isSuccessful()) {
                    DialogUtil.close();
                    healthyList = response.body().getTngou();
                    listAdapter.setDatas(healthyList);
                    listAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<HealthyInfoListEntity> call, Throwable t) {

            }
        });
    }
}

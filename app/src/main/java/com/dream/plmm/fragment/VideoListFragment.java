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
import com.dream.plmm.activity.VideoPlayActivity;
import com.dream.plmm.adapter.OnItemChildClickListener;
import com.dream.plmm.adapter.RecyclerViewAdapter;
import com.dream.plmm.adapter.ViewHolderHelper;
import com.dream.plmm.bean.ResponseVideosListEntity;
import com.dream.plmm.bean.VideosListEntity;
import com.dream.plmm.config.Contants;
import com.dream.plmm.netWork.ServiceFactory;
import com.dream.plmm.utils.BitmapUtils;
import com.dream.plmm.utils.DialogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by likun on 16/9/5.
 */
public class VideoListFragment extends BaseFragment implements OnItemChildClickListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListAdapter listAdapter;
    Map<String,String> queryMap;
    private List<VideosListEntity> videoType = new ArrayList<>();

    int type;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("id");
    }

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

    public static VideoListFragment getInstance(int position){
        VideoListFragment videoListFragment = new VideoListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", position);
        videoListFragment.setArguments(bundle);
        return videoListFragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {//onCreate--initView--setUserVisibleHint
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getVideoInfoList(type);
        }
    }

    String kind;
    private void getVideoInfoList(int type) {
        switch (type){
            case 0:
                kind = "热门";
                break;
            case 1:
                kind = "搞笑";
                break;
            case 2:
                kind = "动漫";
                break;
            case 3:
                kind = "综艺";
                break;
            default:
                break;
        }
        queryMap = new HashMap<>();
        queryMap.put("keyword",kind);
        queryMap.put("page","1");
        queryMap.put("count","20");
        queryMap.put("public_type","all");
        queryMap.put("paid","0");
        queryMap.put("orderby","published");
        queryMap.put("client_id", Contants.CLIENT_ID);
        Call<ResponseVideosListEntity> call = ServiceFactory.getVideoService().getVideoInfos(queryMap);
        call.enqueue(new Callback<ResponseVideosListEntity>() {
            @Override
            public void onResponse(Call<ResponseVideosListEntity> call, Response<ResponseVideosListEntity> response) {
                if(response.isSuccessful()){
                    DialogUtil.close();
                    videoType = response.body().getVideos();
                    listAdapter.setDatas(videoType);
                }
            }

            @Override
            public void onFailure(Call<ResponseVideosListEntity> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(VideoPlayActivity.INTENT_VIDEO_EXTRAS,videoType.get(position));
        Intent intent = new Intent(mActivity, VideoPlayActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    class ListAdapter extends RecyclerViewAdapter<VideosListEntity> {

        public ListAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.list_item_videos_card);
        }

        @Override
        protected void fillData(ViewHolderHelper viewHolderHelper, int position, VideosListEntity model) {
            BitmapUtils.display(mActivity, model.getThumbnail_v2(), viewHolderHelper.getImageView(R.id.list_item_videos_card_image));
            viewHolderHelper.getTextView(R.id.list_item_videos_card_title).setText(model.getTitle());
//            viewHolderHelper.getTextView(R.id.news_info_desc).setText(DateUtils.ConvertTime(model.getTime()));
        }

        @Override
        protected void setItemChildListener(ViewHolderHelper viewHolderHelper) {
            viewHolderHelper.setItemChildClickListener(R.id.list_item_videos_card_play);
        }
    }

}

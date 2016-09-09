package com.dream.plmm.activity;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baseproject.utils.Logger;
import com.dream.plmm.R;
import com.dream.plmm.bean.VideosListEntity;
import com.dream.plmm.utils.BitmapUtils;
import com.youku.player.base.YoukuBasePlayerManager;
import com.youku.player.base.YoukuPlayer;
import com.youku.player.base.YoukuPlayerView;
import com.youku.player.plugin.YoukuPlayerListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by likun on 16/9/8.
 */
public class VideoPlayActivity extends Activity implements View.OnClickListener {

    @Bind(R.id.player_back)
    ImageButton playerBack;
    @Bind(R.id.player_title)
    TextView playerTitle;
    @Bind(R.id.player_title_bar)
    LinearLayout playerTitleBar;
    @Bind(R.id.full_holder)
    YoukuPlayerView mYoukuPlayerView;
    @Bind(R.id.player_view_count)
    TextView playerViewCount;
    @Bind(R.id.player_comment_count)
    TextView playerCommentCount;
    @Bind(R.id.player_favor_count)
    TextView playerFavorCount;
    @Bind(R.id.player_user_avatar)
    ImageView playerUserAvatar;
    @Bind(R.id.player_user_name)
    TextView playerUserName;
    @Bind(R.id.player_publish_time)
    TextView playerPublishTime;
    @Bind(R.id.player_tag_group)
    TagGroup playerTagGroup;

    private YoukuBasePlayerManager basePlayerManager;
    private YoukuPlayer youkuPlayer;
    public static final String INTENT_VIDEO_EXTRAS = "INTENT_VIDEO_EXTRAS";
    private VideosListEntity videoBean;

    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoBean =getIntent().getExtras().getParcelable(INTENT_VIDEO_EXTRAS);
        Log.e("VideoPlayActivity", "videoBean " + videoBean.toString());
        if(null != videoBean){
            id = videoBean.getId();
        }
        setContentView(R.layout.activity_player);
        ButterKnife.bind(this);
        init();
        try {
            basePlayerManager = new YoukuBasePlayerManager(this) {

                @Override
                public void setPadHorizontalLayout() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onInitializationSuccess(YoukuPlayer player) {
                    // TODO Auto-generated method stub
                    // 初始化成功后需要添加该行代码
                    addPlugins();

                    // 实例化YoukuPlayer实例
                    youkuPlayer = player;

                    // 进行播放
                    goPlay();
                }

                @Override
                public void onSmallscreenListener() {
                    playerTitleBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFullscreenListener() {
                    playerTitleBar.setVisibility(View.GONE);
                }
            };
            basePlayerManager.onCreate();
            //控制竖屏和全屏时候的布局参数。这两句必填。
            mYoukuPlayerView
                    .setSmallScreenLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
            mYoukuPlayerView
                    .setFullScreenLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT));
            // 初始化播放器相关数据
            mYoukuPlayerView.initialize(basePlayerManager);

            //添加播放器的回调
            basePlayerManager.setPlayerListener(new YoukuPlayerListener(){
                @Override
                public void onCompletion() {
                    // TODO Auto-generated method stub
                    super.onCompletion();
                }
                @Override
                public void onStartBuffering() {
                    // TODO Auto-generated method stub
                    super.onStartBuffering();
                }
                //...
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        playerBack.setOnClickListener(this);
        playerTitle.setText(videoBean.getTitle());
        playerViewCount.setText(getResources().getString(R.string.player_view_count) + videoBean.getView_count());
        playerCommentCount.setText(getResources().getString(R.string.player_comment_count) + videoBean.getComment_count());
        playerFavorCount.setText(getResources().getString(R.string.player_favor_count) + videoBean.getFavorite_count());
        playerPublishTime.setText(getResources().getString(R.string.player_publish) + videoBean.getPublished());
        BitmapUtils.display(this,videoBean.getUser().getAvatar_large(),playerUserAvatar);
        playerUserName.setText(videoBean.getUser().getName());
        String tagAll = videoBean.getTags();
        String[] tags = tagAll.split(",");
        if (null != tags && tags.length != 0) {
            playerTagGroup.setTags(tags);
        }

    }

    private void goPlay() {
        youkuPlayer.playVideo(id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        basePlayerManager.onDestroy();
    }

    @Override
    public void onBackPressed() { // android系统调用
        Logger.d("sgh", "onBackPressed before super");
        super.onBackPressed();
        Logger.d("sgh", "onBackPressed");
        basePlayerManager.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        basePlayerManager.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean managerKeyDown = basePlayerManager.onKeyDown(keyCode, event);
        if (basePlayerManager.shouldCallSuperKeyDown()) {
            return super.onKeyDown(keyCode, event);
        } else {
            return managerKeyDown;
        }

    }

    @Override
    public void onLowMemory() { // android系统调用
        super.onLowMemory();
        basePlayerManager.onLowMemory();
    }

    @Override
    protected void onPause() {
        super.onPause();
        basePlayerManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        basePlayerManager.onResume();
    }

    @Override
    public boolean onSearchRequested() { // android系统调用
        return basePlayerManager.onSearchRequested();
    }

    @Override
    protected void onStart() {
        super.onStart();
        basePlayerManager.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        basePlayerManager.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.player_back:
                finish();
                break;
        }
    }
}

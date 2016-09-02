package com.dream.plmm.fragment;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

import com.dream.plmm.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.List;


public class BottomMenuFragment extends DialogFragment implements View.OnClickListener {


    private final String TAG = "BottomMenuFragment";
    private SHARE_MEDIA share_media = SHARE_MEDIA.ALIPAY;
    final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
            {
                    SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA,
                    SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
            };

    public BottomMenuFragment() {
        // Required empty public constructor
    }


    private List<MenuItem> menuItems;

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    UMImage image;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        image = new UMImage(getActivity(),R.drawable.iv_user_icon);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//设置背景透明

        getDialog().getWindow().setWindowAnimations(R.style.menu_animation);//添加一组进出动画

        View view = inflater.inflate(R.layout.activity_share, container, false);

        //view.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.menu_appear));//添加一个加载动画，这样的问题是没办法添加消失动画，有待进一步研究

        initView(view);
        return view;
    }

    ImageButton ibWeChat;
    ImageButton ibWeChatMoments;
    ImageButton ibQQ;
    ImageButton ibQzone;

    private void initView(View view) {
        ibWeChat = (ImageButton) view.findViewById(R.id.share_wechat);
        ibWeChatMoments = (ImageButton) view.findViewById(R.id.share_wechatmoments);
        ibQQ = (ImageButton) view.findViewById(R.id.share_qq);
        ibQzone = (ImageButton) view.findViewById(R.id.share_qzone);

        ibWeChat.setOnClickListener(this);
        ibWeChatMoments.setOnClickListener(this);
        ibQQ.setOnClickListener(this);
        ibQzone.setOnClickListener(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView: ");

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");

        //设置弹出框宽屏显示，适应屏幕宽度
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels, getDialog().getWindow().getAttributes().height);

        //移动弹出菜单到底部
        WindowManager.LayoutParams wlp = getDialog().getWindow().getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        // wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(wlp);
    }

    @Override
    public void onStop() {
        this.getView().setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.menu_disappear));
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_wechat:
                new ShareAction(getActivity()).setDisplayList( displaylist )
                        .withText( "呵呵" )
                        .withTitle("title")
                        .withTargetUrl("http://www.baidu.com")
                        .withMedia( image )
                        .setListenerList(umShareListener)
                        .open();
                break;
            case R.id.share_wechatmoments:
                break;
            case R.id.share_qq:
                break;
            case R.id.share_qzone:
                break;
            default:
                break;
        }
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(getActivity(), platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(getActivity(), platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(), platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(getActivity()).onActivityResult(requestCode, resultCode, data);
    }
}

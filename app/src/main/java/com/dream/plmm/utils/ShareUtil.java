package com.dream.plmm.utils;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.dream.plmm.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * Created by likun on 16/9/2.
 */
public class ShareUtil {

    static Activity activity;
    //分享平台
    final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
            {
                    SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA,
                    SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
            };
    UMImage image;
    ShareAction shareAction;
    private ShareUtil() {
        shareAction = new ShareAction(activity);
        image = new UMImage(activity, R.mipmap.start);
    }

    private static ShareUtil shareUtil;

    public static ShareUtil getInstance(Activity activity) {
        ShareUtil.activity = activity;
        if (shareUtil == null) {
            synchronized (ShareUtil.class) {
                shareUtil = new ShareUtil();
            }
        }
        return shareUtil;
    }

    public void share(String text,String title,String url){
        shareAction.setDisplayList(displaylist)
                .withText(text)
                .withTitle(title)
                .withTargetUrl(url)
                .withMedia(image)
                .setListenerList(umShareListener)
                .open();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(activity, "收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "分享成功啦", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(activity, "分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(activity, "分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

}

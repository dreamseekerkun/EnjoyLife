package com.dream.plmm.netWork;


import com.dream.plmm.config.HostURL;

/**
 * Created by likun on 16/4/19.
 */
public class ServiceFactory {

    private static MMService mmService;

    public static MMService getMmService() {
        if (mmService == null) {
            mmService = HttpClient.getIns(HostURL.BASE_URL).createService(MMService.class);
        }
        return mmService;
    }

    private static HealthyService healthyService;

    public static HealthyService getHealthyService() {
        if (healthyService == null) {
            healthyService = HttpClient.getIns(HostURL.BASE_URL).createService(HealthyService.class);
        }
        return healthyService;
    }

    private static VideoService videoService;

    public static VideoService getVideoService(){
        if(videoService == null){
            videoService = HttpClient.getYouKuInstance(HostURL.YOUKU_VIDEOS_URLS).createService(VideoService.class);
        }
        return videoService;
    }

}

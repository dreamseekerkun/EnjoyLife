package com.dream.plmm.bean;

import java.util.ArrayList;

/**
 * Created by likun on 16/8/19.
 */
public class HealthyInfoList {

    /**
     * status : true
     * total : 1238
     * tngou : [{"count":23,"description":"立秋吃蜜不吃姜，记住要多吃水果少食瓜 除蜂蜜外，也可多进食些芝麻、杏仁等食品，既补脾胃又能养肺润肠，可防止秋燥带来的津液不足，常见的干咳、咽干口燥、肌肤失去光泽、肠燥便秘等身体不适症状也能得到缓解","fcount":0,"id":20049,"img":"/lore/160816/8e429ae69d754577c243bbbbf74300ff.jpg","keywords":"作用 适可而止 有 失去光泽 蜂蜜 ","loreclass":3,"rcount":0,"time":1471352151000,"title":"要不吃或少吃辛辣烧烤类食品"}]
     */

    private boolean status;
    private int total;
    /**
     * count : 23
     * description : 立秋吃蜜不吃姜，记住要多吃水果少食瓜 除蜂蜜外，也可多进食些芝麻、杏仁等食品，既补脾胃又能养肺润肠，可防止秋燥带来的津液不足，常见的干咳、咽干口燥、肌肤失去光泽、肠燥便秘等身体不适症状也能得到缓解
     * fcount : 0
     * id : 20049
     * img : /lore/160816/8e429ae69d754577c243bbbbf74300ff.jpg
     * keywords : 作用 适可而止 有 失去光泽 蜂蜜
     * loreclass : 3
     * rcount : 0
     * time : 1471352151000
     * title : 要不吃或少吃辛辣烧烤类食品
     */

    private ArrayList<TngouEntity> tngou;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<TngouEntity> getTngou() {
        return tngou;
    }

    public void setTngou(ArrayList<TngouEntity> tngou) {
        this.tngou = tngou;
    }

    public static class TngouEntity {
        private int count;
        private String description;
        private int fcount;
        private int id;
        private String img;
        private String keywords;
        private int loreclass;
        private int rcount;
        private long time;
        private String title;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getFcount() {
            return fcount;
        }

        public void setFcount(int fcount) {
            this.fcount = fcount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public int getLoreclass() {
            return loreclass;
        }

        public void setLoreclass(int loreclass) {
            this.loreclass = loreclass;
        }

        public int getRcount() {
            return rcount;
        }

        public void setRcount(int rcount) {
            this.rcount = rcount;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
